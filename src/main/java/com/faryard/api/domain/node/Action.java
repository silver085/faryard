package com.faryard.api.domain.node;

public enum Action {
    IDLE("IDLE"),
    SENDCONFIGURATION("SENDCONFIGURATION"),
    UPDATESENSORSTATUS("UPDATESENSORSTATUS"),
    EXECUTEACTION("EXECUTEACTION");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
