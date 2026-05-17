package com.byd.msp.requirement.statemachine;

import java.util.List;

/**
 * 状态机顶层接口
 */
public interface StateMachine {

    /** 获取状态机ID（业务键） */
    String getId();

    /** 触发事件，按BPMN定义的流程流转 */
    void sendEvent(String event);

    /** 激活挂起的流程并触发事件 */
    void sendActivateEvent(String event);

    /** 强制跳转到指定节点 */
    void setState(String stateId);

    /** 获取当前状态 */
    State getState();

    /** 获取事件触发后的下个状态 */
    State getNextState(String event);

    /** 获取所有节点及流转关系 */
    List<NodeInfo> getNodeIds();

    /** 停止状态机（强制结束流程实例） */
    void stop();

    /** 挂起流程实例 */
    void suspendProcessInstanceById(String processInstanceId);
}
