package com.byd.msp.requirement.statemachine;

/**
 * 状态机工厂接口
 */
public interface StateMachineFactory {

    /**
     * 创建状态机（启动流程实例）
     * @param domain 领域（流程定义Key）
     * @param businessId 业务ID
     */
    StateMachine create(String domain, String businessId);

    /**
     * 获取已存在的状态机
     * @param businessId 业务ID
     */
    StateMachine get(String businessId);
}
