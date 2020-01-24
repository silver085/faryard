package com.faryard.api.domain.node;

public enum  NodeStatus {
    Online("Online"),
    Offline("Offline");
    private String status ;

    NodeStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
