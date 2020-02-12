package com.faryard.api.DTO;

public class SwitchRelayResponse extends SimpleResponse{
    private String nodeId;
    private int relayIndex;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public int getRelayIndex() {
        return relayIndex;
    }

    public void setRelayIndex(int relayIndex) {
        this.relayIndex = relayIndex;
    }
}
