package com.byd.msp.requirement.statemachine;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * 领域过滤工具类：过滤指定领域的监听器
 */
public class WithStateMachineSupport {

    /**
     * 过滤指定领域的监听器
     * @param listeners 所有监听器
     * @param domain    目标领域
     */
    public static Collection<StateMachineListener> filterWithStateMachine(
            Collection<StateMachineListener> listeners, String domain) {
        return listeners.stream()
                .filter(listener -> {
                    WithStateMachine annotation = listener.getClass().getAnnotation(WithStateMachine.class);
                    return annotation != null && annotation.domain().equals(domain);
                })
                .collect(Collectors.toList());
    }
}
