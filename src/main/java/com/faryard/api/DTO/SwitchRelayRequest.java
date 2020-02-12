package com.faryard.api.DTO;

public class SwitchRelayRequest {
    private String nodeId;
    private String relayIndex;
    private Boolean status;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getRelayIndex() {
        return relayIndex;
    }

    public void setRelayIndex(String relayIndex) {
        this.relayIndex = relayIndex;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
