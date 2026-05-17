package com.byd.msp.requirement.statemachine.flowable;

import com.byd.msp.requirement.statemachine.NodeInfo;
import com.byd.msp.requirement.statemachine.State;
import com.byd.msp.requirement.statemachine.StateMachine;
import com.byd.msp.requirement.statemachine.StateMachineListener;
import org.flowable.bpmn.model.*;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Flowable 状态机实现，封装 Flowable 流程实例操作
 */
public class FlowableStateMachine implements StateMachine {

    private final String businessKey;
    private final String domain;
    private final RuntimeService runtimeService;
    private final RepositoryService repositoryService;
    private final TaskService taskService;
    private final Collection<StateMachineListener> listeners;

    public FlowableStateMachine(String businessKey, String domain,
                                RuntimeService runtimeService,
                                RepositoryService repositoryService,
                                TaskService taskService,
                                Collection<StateMachineListener> listeners) {
        this.businessKey = businessKey;
        this.domain = domain;
        this.runtimeService = runtimeService;
        this.repositoryService = repositoryService;
        this.taskService = taskService;
        this.listeners = listeners;
    }

    @Override
    public String getId() {
        return businessKey;
    }

    @Override
    public void sendEvent(String event) {
        State fromState = getState();
        ProcessInstance pi = getProcessInstance();
        if (pi == null) {
            throw new IllegalStateException("流程实例不存在: " + businessKey);
        }

        // 获取当前任务，携带 action 变量完成任务，推动流程流转
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .list();
        if (tasks.isEmpty()) {
            throw new IllegalStateException("当前无待办任务，流程可能已结束");
        }

        Map<String, Object> variables = new HashMap<>();
        variables.put("action", event);
        taskService.complete(tasks.get(0).getId(), variables);

        State toState = getState();
        notifyListeners(fromState, toState, event);
    }

    @Override
    public void sendActivateEvent(String event) {
        ProcessInstance pi = getProcessInstance();
        if (pi != null && pi.isSuspended()) {
            runtimeService.activateProcessInstanceById(pi.getId());
        }
        sendEvent(event);
    }

    @Override
    public void setState(String stateId) {
        State fromState = getState();
        ProcessInstance pi = getProcessInstance();
        if (pi == null) {
            throw new IllegalStateException("流程实例不存在: " + businessKey);
        }

        runtimeService.createChangeActivityStateBuilder()
                .processInstanceId(pi.getId())
                .moveActivityIdTo(fromState.getId(), stateId)
                .changeState();

        State toState = getState();
        notifyListeners(fromState, toState, null);
    }

    @Override
    public State getState() {
        ProcessInstance pi = getProcessInstance();
        if (pi == null) {
            return null;
        }
        List<Task> tasks = taskService.createTaskQuery()
                .processInstanceId(pi.getId())
                .list();
        if (tasks.isEmpty()) {
            // 流程可能已结束，返回结束状态
            return new FlowableState("end", "结束");
        }
        Task task = tasks.get(0);
        return new FlowableState(task.getTaskDefinitionKey(), task.getName());
    }

    @Override
    public State getNextState(String event) {
        ProcessInstance pi = getProcessInstance();
        if (pi == null) {
            return null;
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(pi.getProcessDefinitionId())
                .singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pd.getId());
        State currentState = getState();
        if (currentState == null) {
            return null;
        }

        FlowElement currentElement = bpmnModel.getFlowElement(currentState.getId());
        if (currentElement instanceof FlowNode) {
            FlowNode flowNode = (FlowNode) currentElement;
            for (SequenceFlow outgoing : flowNode.getOutgoingFlows()) {
                String condition = outgoing.getConditionExpression();
                if (condition != null && condition.contains(event)) {
                    FlowElement target = bpmnModel.getFlowElement(outgoing.getTargetRef());
                    return new FlowableState(target.getId(), target.getName());
                }
                // 无条件流转则直接返回
                if (condition == null) {
                    FlowElement target = bpmnModel.getFlowElement(outgoing.getTargetRef());
                    return new FlowableState(target.getId(), target.getName());
                }
            }
        }
        return null;
    }

    @Override
    public List<NodeInfo> getNodeIds() {
        List<NodeInfo> result = new ArrayList<>();
        ProcessInstance pi = getProcessInstance();
        if (pi == null) {
            return result;
        }
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionId(pi.getProcessDefinitionId())
                .singleResult();
        BpmnModel bpmnModel = repositoryService.getBpmnModel(pd.getId());
        for (org.flowable.bpmn.model.Process process : bpmnModel.getProcesses()) {
            for (FlowElement element : process.getFlowElements()) {
                if (element instanceof FlowNode) {
                    FlowNode node = (FlowNode) element;
                    NodeInfo info = new NodeInfo();
                    info.setNodeId(node.getId());
                    info.setNodeName(node.getName());
                    List<NodeInfo.Transition> transitions = new ArrayList<>();
                    for (SequenceFlow outgoing : node.getOutgoingFlows()) {
                        NodeInfo.Transition t = new NodeInfo.Transition();
                        t.setEvent(outgoing.getConditionExpression());
                        t.setTargetNodeId(outgoing.getTargetRef());
                        transitions.add(t);
                    }
                    info.setTransitions(transitions);
                    result.add(info);
                }
            }
        }
        return result;
    }

    @Override
    public void stop() {
        ProcessInstance pi = getProcessInstance();
        if (pi != null) {
            runtimeService.deleteProcessInstance(pi.getId(), "手动停止");
        }
    }

    @Override
    public void suspendProcessInstanceById(String processInstanceId) {
        runtimeService.suspendProcessInstanceById(processInstanceId);
    }

    private ProcessInstance getProcessInstance() {
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .active()
                .list();
        if (instances == null || instances.isEmpty()) {
            return null;
        }
        // 有多个时取最新的
        return instances.stream()
                .max(Comparator.comparing(ProcessInstance::getStartTime))
                .orElse(null);
    }

    private void notifyListeners(State fromState, State toState, String event) {
        if (listeners != null) {
            for (StateMachineListener listener : listeners) {
                listener.stateChanged(businessKey, fromState, toState, event);
            }
        }
    }
}
