package com.faryard.api.domain.node;

public enum Action {
    IDLE("IDLE"),
    RELAY("RELAY"),
    EXECUTEACTION("EXECUTEACTION");

    private String action;

    Action(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }


}
