package com.faryard.api.DTO.node;

public class NodeSimpleResponse {
    private String nodeId;
    private NodeResponseStatus status;
    private String message;
    private String requiredAction;
    private String requiredCommand;
    private String lastActionId;

    public java.lang.String getNodeId() {
        return nodeId;
    }

    public void setNodeId(java.lang.String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeResponseStatus getStatus() {
        return status;
    }

    public void setStatus(NodeResponseStatus status) {
        this.status = status;
    }

    public java.lang.String getMessage() {
        return message;
    }

    public void setMessage(java.lang.String message) {
        this.message = message;
    }

    public String getRequiredAction() {
        return requiredAction;
    }

    public void setRequiredAction(String requiredAction) {
        this.requiredAction = requiredAction;
    }

    public String getLastActionId() {
        return lastActionId;
    }

    public void setLastActionId(String lastActionId) {
        this.lastActionId = lastActionId;
    }

    public String getRequiredCommand() {
        return requiredCommand;
    }

    public void setRequiredCommand(String requredCommand) {
        this.requiredCommand = requredCommand;
    }
}
