package com.faryard.api.DTO.node;

public enum ActionDTO {
    IDLE("IDLE"),
    EXECUTEACTION("EXECUTEACTION");

    private java.lang.String nodeAction;

    ActionDTO(java.lang.String nodeAction) {
        this.nodeAction = nodeAction;
    }

    public java.lang.String getNodeAction() {
        return nodeAction;
    }
}
