package com.byd.msp.requirement.statemachine;

import java.lang.annotation.*;

/**
 * 领域标记注解，用于标记监听器所属的领域
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface WithStateMachine {

    /** 领域标识，对应流程定义Key */
    String domain();
}
