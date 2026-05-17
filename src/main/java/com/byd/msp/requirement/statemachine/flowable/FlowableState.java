package com.byd.msp.requirement.statemachine.flowable;

import com.byd.msp.requirement.statemachine.State;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Flowable 状态实现
 */
@Data
@AllArgsConstructor
public class FlowableState implements State {

    private String id;

    private String name;
}
