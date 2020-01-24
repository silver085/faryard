package com.faryard.api.facades;

import com.faryard.api.DTO.node.NodeErrorResponse;
import com.faryard.api.DTO.node.NodePingRequest;
import com.faryard.api.services.impl.exceptions.NodeAlreadyRegisteredException;
import com.faryard.api.services.impl.exceptions.NodeNotFoundException;
import com.faryard.api.services.impl.node.NodeService;
import com.faryard.api.DTO.node.NodeSimpleResponse;
import com.faryard.api.DTO.node.RegisterNodeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NodeFacade {
    @Autowired
    NodeService nodeService;
    public NodeSimpleResponse registerNode(RegisterNodeRequest request) {
        try {
            return nodeService.registerNode(request);
        }catch(NodeAlreadyRegisteredException e){
            NodeErrorResponse errorResponse = new NodeErrorResponse();
            errorResponse.setMessage(e.getMessage());
            return errorResponse;
        }
    }

    public NodeSimpleResponse nodePing(NodePingRequest request) {
        try {
            return nodeService.nodePing(request);
        }catch(NodeNotFoundException e){
            NodeErrorResponse errorResponse = new NodeErrorResponse();
            errorResponse.setMessage(e.getMessage());
            return errorResponse;
        }
    }
}
