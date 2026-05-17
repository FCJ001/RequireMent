package com.byd.msp.requirement.statemachine;

/**
 * 状态机监听器接口
 */
public interface StateMachineListener {

    /**
     * 状态变更回调
     * @param machineId 状态机ID（业务键）
     * @param fromState 变更前状态
     * @param toState   变更后状态
     * @param event     触发事件
     */
    void stateChanged(String machineId, State fromState, State toState, String event);
}
