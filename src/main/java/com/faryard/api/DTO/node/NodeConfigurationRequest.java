package com.faryard.api.DTO.node;

import java.util.List;

public class NodeConfigurationRequest {
    private String nodeId;
    private String lastActionId;
    private List<NodeComponentDTO> components;

    public List<NodeComponentDTO> getComponents() {
        return components;
    }

    public void setComponents(List<NodeComponentDTO> components) {
        this.components = components;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getLastActionId() {
        return lastActionId;
    }

    public void setLastActionId(String lastActionId) {
        this.lastActionId = lastActionId;
    }
}
