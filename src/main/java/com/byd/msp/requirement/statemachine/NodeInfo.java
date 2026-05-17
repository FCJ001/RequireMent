package com.byd.msp.requirement.statemachine;

import lombok.Data;

import java.util.List;

/**
 * 节点信息（节点ID + 流转关系）
 */
@Data
public class NodeInfo {

    private String nodeId;

    private String nodeName;

    private List<Transition> transitions;

    @Data
    public static class Transition {
        private String event;
        private String targetNodeId;
    }
}
