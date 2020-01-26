package com.faryard.api.DTO.node;

public enum NodeAction {
    IDLE("IDLE"),
    SENDCONFIGURATION("SENDCONFIGURATION"),
    UPDATESENSORSTATUS("UPDATESENSORSTATUS"),
    EXECUTEACTION("EXECUTEACTION");

    private String nodeAction;

    NodeAction(String nodeAction) {
        this.nodeAction = nodeAction;
    }

    public String getNodeAction() {
        return nodeAction;
    }
}
