package com.faryard.api.DTO.node;



public class NodeExecuteAction {
    private String nodeId;
    private String action;
    private String nodeCommand;

    public java.lang.String getNodeId() {
        return nodeId;
    }

    public void setNodeId(java.lang.String nodeId) {
        this.nodeId = nodeId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public java.lang.String getNodeCommand() {
        return nodeCommand;
    }

    public void setNodeCommand(java.lang.String nodeCommand) {
        this.nodeCommand = nodeCommand;
    }
}
