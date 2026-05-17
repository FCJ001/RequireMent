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
                    // 没有注解 → 监听所有领域；有注解且匹配 → 监听该领域
                    return annotation == null || annotation.domain().equals(domain);
                })
                .collect(Collectors.toList());
    }
}
