package com.byd.msp.requirement.statemachine.flowable;

import com.byd.msp.requirement.statemachine.StateMachine;
import com.byd.msp.requirement.statemachine.StateMachineFactory;
import com.byd.msp.requirement.statemachine.StateMachineListener;
import com.byd.msp.requirement.statemachine.WithStateMachineSupport;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.engine.runtime.ProcessInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;

/**
 * Flowable 状态机工厂实现
 */
@Component
public class FlowableStateMachineFactory implements StateMachineFactory {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private TaskService taskService;

    @Autowired(required = false)
    private List<StateMachineListener> allListeners;

    @Override
    public StateMachine create(String domain, String businessId) {
        // 检查流程定义是否存在
        ProcessDefinition pd = repositoryService.createProcessDefinitionQuery()
                .processDefinitionKey(domain)
                .latestVersion()
                .singleResult();
        if (pd == null) {
            throw new IllegalStateException("流程定义不存在: " + domain);
        }

        // 构建业务键：domain:businessId
        String businessKey = domain + ":" + businessId;

        // 防止重复创建：先检查是否已有活跃的流程实例
        List<ProcessInstance> existingInstances = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKey(businessKey)
                .active()
                .list();
        if (existingInstances != null && !existingInstances.isEmpty()) {
            // 多个时只保留最新的，删除旧的
            if (existingInstances.size() > 1) {
                ProcessInstance latest = existingInstances.stream()
                        .max(Comparator.comparing(ProcessInstance::getStartTime))
                        .orElse(existingInstances.get(0));
                for (ProcessInstance old : existingInstances) {
                    if (!old.getId().equals(latest.getId())) {
                        runtimeService.deleteProcessInstance(old.getId(), "清理重复流程实例");
                    }
                }
            }
            ProcessInstance existing = existingInstances.stream()
                    .max(Comparator.comparing(ProcessInstance::getStartTime))
                    .orElse(existingInstances.get(0));
            Collection<StateMachineListener> domainListeners = filterListeners(domain);
            return new FlowableStateMachine(businessKey, domain,
                    runtimeService, repositoryService, taskService, domainListeners);
        }

        // 启动流程实例
        ProcessInstance pi = runtimeService.startProcessInstanceByKey(domain, businessKey);

        // 获取该领域的监听器
        Collection<StateMachineListener> domainListeners = filterListeners(domain);

        return new FlowableStateMachine(businessKey, domain,
                runtimeService, repositoryService, taskService, domainListeners);
    }

    @Override
    public StateMachine get(String businessId) {
        // 使用 list() 而非 singleResult()，避免多实例时报错
        List<ProcessInstance> instances = runtimeService.createProcessInstanceQuery()
                .processInstanceBusinessKeyLike("%:" + businessId)
                .active()
                .list();

        if (instances == null || instances.isEmpty()) {
            return null;
        }

        // 如果有多个实例，取最新启动的那个（通常是最后一次创建的）
        ProcessInstance pi = instances.stream()
                .max(Comparator.comparing(ProcessInstance::getStartTime))
                .orElse(null);

        if (pi == null) {
            return null;
        }

        // 清理多余的旧实例
        if (instances.size() > 1) {
            for (ProcessInstance old : instances) {
                if (!old.getId().equals(pi.getId())) {
                    try {
                        runtimeService.deleteProcessInstance(old.getId(), "清理重复流程实例");
                    } catch (Exception ignored) {
                    }
                }
            }
        }

        // 从业务键解析领域
        String businessKey = pi.getBusinessKey();
        String domain = businessKey.substring(0, businessKey.indexOf(":"));

        Collection<StateMachineListener> domainListeners = filterListeners(domain);

        return new FlowableStateMachine(businessKey, domain,
                runtimeService, repositoryService, taskService, domainListeners);
    }

    private Collection<StateMachineListener> filterListeners(String domain) {
        if (allListeners == null) {
            return new ArrayList<>();
        }
        return WithStateMachineSupport.filterWithStateMachine(allListeners, domain);
    }
}
