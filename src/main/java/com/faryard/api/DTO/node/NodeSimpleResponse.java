package com.faryard.api.DTO.node;

public class NodeSimpleResponse {
    private String nodeId;
    private NodeResponseStatus status;
    private String message;
    private NodeAction requiredAction;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public NodeResponseStatus getStatus() {
        return status;
    }

    public void setStatus(NodeResponseStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public NodeAction getRequiredAction() {
        return requiredAction;
    }

    public void setRequiredAction(NodeAction requiredAction) {
        this.requiredAction = requiredAction;
    }
}
