package com.faryard.api.DTO.node;

public class NodeErrorResponse extends NodeSimpleResponse {

    public NodeErrorResponse() {
        super();
        this.setStatus(NodeResponseStatus.ERROR);
    }
}
