package com.faryard.api.DTO.node;

public enum  NodeResponseStatus {
    OK("OK"),
    ERROR("ERROR");

    private String status;

    NodeResponseStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
