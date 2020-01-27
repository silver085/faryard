package com.faryard.api.facades;


import com.faryard.api.DTO.node.NodeExecuteAction;
import com.faryard.api.DTO.node.*;
import com.faryard.api.services.impl.exceptions.NodeAlreadyRegisteredException;
import com.faryard.api.services.impl.exceptions.NodeNotFoundException;
import com.faryard.api.services.impl.node.NodeService;
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

    public NodeSimpleResponse existNode(String macAddress) {
        return nodeService.existNode(macAddress);
    }

    public NodeStatusResponse nodeStatus(String macAddress) {
        return nodeService.nodeStatus(macAddress);
    }

    public NodeStatusResponse nodeStatusById(String nodeId) {
        return nodeService.nodeStatusById(nodeId);
    }

    public NodeSimpleResponse doAction(NodeExecuteAction nodeAction) {
        return nodeService.doAction(nodeAction);
    }

    public NodeSimpleResponse nodeConfiguration(NodeConfigurationRequest nodeConfigurationRequst) {
        return nodeService.updateNodeConfiguration(nodeConfigurationRequst);
    }
}
