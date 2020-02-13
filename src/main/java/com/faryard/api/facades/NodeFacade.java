package com.faryard.api.facades;


import com.faryard.api.DTO.GraphicElementsDTO;
import com.faryard.api.DTO.MarkActionDoneRequest;
import com.faryard.api.DTO.SwitchRelayRequest;
import com.faryard.api.DTO.SwitchRelayResponse;
import com.faryard.api.DTO.node.NodeExecuteAction;
import com.faryard.api.DTO.node.*;
import com.faryard.api.services.impl.exceptions.NodeAlreadyRegisteredException;
import com.faryard.api.services.impl.exceptions.NodeNotFoundException;
import com.faryard.api.services.impl.node.NodeHistoryService;
import com.faryard.api.services.impl.node.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class NodeFacade {
    @Autowired
    NodeService nodeService;
    @Autowired
    NodeHistoryService nodeHistoryService;

    public NodeSimpleResponse registerNode(RegisterNodeRequest request) {
        try {
            return nodeService.registerNode(request);
        }catch(NodeAlreadyRegisteredException e){
            NodeErrorResponse errorResponse = new NodeErrorResponse();
            errorResponse.setMessage(e.getMessage());
            return errorResponse;
        }
    }

    public NodeSimpleResponse nodePing(NodePingRequest request, HttpServletRequest servletRequest) {
        try {
            return nodeService.nodePing(request, servletRequest);
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


    public NodeSensorStatusResponse nodeSensorsStatus(String nodeId) {
        return nodeService.nodeSensorsStatus(nodeId);
    }

    public NodeSimpleResponse markActionAsDone(MarkActionDoneRequest actionDoneRequest) {
        return nodeService.markActionDone(actionDoneRequest);
    }

    public GraphicElementsDTO buildGraph(String nodeId, String timespan, String startDate, String endDate) {
        return nodeHistoryService.getNodeHistoryWithTimeSpan(nodeId, timespan, startDate, endDate);
    }

    public SwitchRelayResponse switchRelayOn(SwitchRelayRequest request) {
       return nodeService.switchRelayOn(request);
    }
}
