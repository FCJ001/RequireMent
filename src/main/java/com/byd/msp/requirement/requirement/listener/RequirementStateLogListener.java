package com.byd.msp.requirement.requirement.listener;

import com.byd.msp.requirement.requirement.service.RequirementLogService;
import com.byd.msp.requirement.statemachine.State;
import com.byd.msp.requirement.statemachine.StateMachineListener;
import com.byd.msp.requirement.statemachine.WithStateMachine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 需求状态变更日志监听器（领域: requirement_pool）
 */
@Component
@WithStateMachine(domain = "requirement_pool")
public class RequirementStateLogListener implements StateMachineListener {

    @Autowired
    private RequirementLogService logService;

    @Override
    public void stateChanged(String machineId, State fromState, State toState, String event) {
        String fromNode = fromState != null ? fromState.getId() : "start";
        String fromName = fromState != null ? fromState.getName() : "开始";
        String toNode = toState != null ? toState.getId() : "end";
        String toName = toState != null ? toState.getName() : "结束";

        String content = String.format("状态变更: [%s]%s → [%s]%s, 事件: %s",
                fromNode, fromName, toNode, toName, event);

        // 从 machineId 中提取 requirementNo（格式: requirement_pool:REQ001）
        String requirementNo = machineId;
        if (machineId.contains(":")) {
            requirementNo = machineId.substring(machineId.indexOf(":") + 1);
        }

        logService.log(requirementNo, "状态变更", content, "system", "系统");
    }
}
