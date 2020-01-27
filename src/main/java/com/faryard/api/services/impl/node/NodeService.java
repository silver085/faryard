package com.faryard.api.services.impl.node;


import com.faryard.api.DTO.node.NodeExecuteAction;
import com.faryard.api.DTO.node.*;
import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeAction;
import com.faryard.api.domain.node.NodeComponent;
import com.faryard.api.domain.node.NodeStatus;
import com.faryard.api.repositories.NodeRepository;
import com.faryard.api.services.exceptions.ExceptionActionAlreadyConfirmed;
import com.faryard.api.services.exceptions.ExceptionActionNotFound;
import com.faryard.api.services.exceptions.ExceptionNodeStillPerformingException;
import com.faryard.api.services.impl.exceptions.NodeAlreadyRegisteredException;
import com.faryard.api.services.impl.exceptions.NodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NodeService {
    @Autowired
    NodeRepository nodeRepository;

    @Autowired
    ActionService actionService;

    @Autowired
    MessageSource messageSource;

    public NodeSimpleResponse registerNode(RegisterNodeRequest request) throws NodeAlreadyRegisteredException{
        Node node = nodeRepository.findByNodeMACAddress(request.getNodeMacAddress()).orElse(null);
        if(node != null){
            throw new NodeAlreadyRegisteredException("error.node.alreadyregistered");
        }

        Node newNode = new Node();
        newNode.setCreationDate(new Date());
        newNode.setLastPingDate(new Date());
        newNode.setNodeIP(request.getNodeIp());
        newNode.setNodeName(request.getNodeName());
        newNode.setNodeMACAddress(request.getNodeMacAddress());
        newNode.setNodeStatus(NodeStatus.Online);
        nodeRepository.save(newNode);

        NodeSimpleResponse response = new NodeSimpleResponse();
        response.setStatus(NodeResponseStatus.OK);
        response.setMessage("api.node.registered");
        response.setNodeId(newNode.getId());

        return response;
    }

    public NodeSimpleResponse nodePing(NodePingRequest request) throws NodeNotFoundException{
        Node node = nodeRepository.findByIdAndNodeMACAddress(request.getId(), request.getMacAddress()).orElse(null);
        if(node == null){
            throw new NodeNotFoundException("api.node.unknownnode");
        }

        node.setLastPingDate(new Date());
        node.setNodeStatus(NodeStatus.Online);
        nodeRepository.save(node);
        NodeSimpleResponse response = new NodeSimpleResponse();
        response.setNodeId(node.getId());
        response.setStatus(NodeResponseStatus.OK);
        response.setMessage("api.node.pingok");
        NodeAction lastUnconfirmedAction = actionService.getLastUnconfirmedActionForNode(node);
        if(lastUnconfirmedAction != null){
            response.setRequiredAction(lastUnconfirmedAction.getAction().getAction());
            response.setLastActionId(lastUnconfirmedAction.getId());
        }

        return response;
    }

    public NodeSimpleResponse existNode(String macAddress) {
        Node node = nodeRepository.findByNodeMACAddress(macAddress).orElse(null);
        boolean exists = node != null;


        NodeSimpleResponse response = new NodeSimpleResponse();
        response.setStatus(NodeResponseStatus.OK);
        response.setMessage(Boolean.toString(exists));
        if(exists){
            response.setNodeId(node.getId());
            node.setLastPingDate(new Date());
            node.setNodeStatus(NodeStatus.Online);
            nodeRepository.save(node);

        }
        return response;
    }

    public NodeStatusResponse nodeStatus(String macAddress) {
        Node node = nodeRepository.findByNodeMACAddress(macAddress).orElse(null);
        NodeStatusResponse response = new NodeStatusResponse();
        if(node != null){
            response.setNodeExist(true);
            response.setLastPingDate(node.getLastPingDate());
            response.setCreationDate(node.getCreationDate());
            response.setNodeName(node.getNodeName());
            response.setNodeMACAddress(node.getNodeMACAddress());
            response.setNodeIP(node.getNodeIP());
            response.setOnline(node.getNodeStatus().equals(NodeStatus.Online));

           // response.setLastActionCommittedDate(node.getLastActionCommittedDate());
           // response.setLastActionCommitted(node.getLa);
        } else {
            response.setNodeExist(false);
        }

        return response;
    }

    public List<Node> getExpiredNodes() {
        Instant instant = Instant.from(Instant.now()
                .atZone(ZoneId.of("Europe/Rome"))
                .minusMinutes(10));  //Date 10 minutes back
        return nodeRepository.findNodesByLastPingDateBefore(Date.from(instant));

    }

    public void updateNode(Node node) {
        nodeRepository.save(node);
    }

    public NodeStatusResponse nodeStatusById(String nodeId) {
        Node node = nodeRepository.findById(nodeId).orElse(null);
        NodeStatusResponse response = new NodeStatusResponse();
        if(node != null){
            return this.nodeStatus(node.getNodeMACAddress());
        }else{
            response.setNodeExist(false);
        }
        return response;
    }

    public NodeSimpleResponse doAction(NodeExecuteAction nodeAction) {
        Node node = nodeRepository.findById(nodeAction.getNodeId()).orElse(null);
        NodeSimpleResponse response = new NodeSimpleResponse();

        if(node!=null && node.getNodeStatus() == NodeStatus.Online){
            try {
                actionService.saveActionForNode(node, nodeAction);
                node.setLastActionCommittedDate(new Date());
                nodeRepository.save(node);
                response.setNodeId(node.getId());
                response.setMessage("action committed");
                response.setStatus(NodeResponseStatus.OK);
            } catch (ExceptionNodeStillPerformingException e) {
                response.setNodeId(node.getId());
                response.setMessage("node is still performing aciton");
                response.setRequiredAction(actionService.getLastActionForNode(node));
                response.setStatus(NodeResponseStatus.ERROR);
            }

        } else{
            response.setMessage("node not found or not online");
            response.setStatus(NodeResponseStatus.ERROR);
        }
        return response;
    }

    public NodeSimpleResponse updateNodeConfiguration(NodeConfigurationRequest nodeConfigurationRequst) {
        Node node = nodeRepository.findById(nodeConfigurationRequst.getNodeId()).orElse(null);
        NodeSimpleResponse response = new NodeSimpleResponse();
        if(node != null){
            List<NodeComponent> nodeComponents = nodeConfigurationRequst.getComponents().stream()
                    .map(nodeComponentDTO -> {
                        NodeComponent nodeComponent = new NodeComponent();
                        nodeComponent.setComponentName(nodeComponentDTO.getComponentName());
                        nodeComponent.setComponentReference(nodeComponentDTO.getComponentReference());
                        nodeComponent.setComponentIO(nodeComponentDTO.getComponentIO());
                        nodeComponent.setComponentPin(nodeComponentDTO.getComponentPin());
                        nodeComponent.setComponentHealth(nodeComponentDTO.getComponentHealth());
                        nodeComponent.setLastComponentUoM(nodeComponentDTO.getLastComponentUoM());
                        nodeComponent.setLastComponentValue(nodeComponentDTO.getLastComponentValue());
                        return nodeComponent;
                    }).collect(Collectors.toList());
            node.setComponents(nodeComponents);
            nodeRepository.save(node);
            try {
                actionService.markAsDone(nodeConfigurationRequst.getLastActionId());
                response.setMessage("configuration saved");
                response.setNodeId(node.getId());
                response.setRequiredAction(null);
                response.setLastActionId(null);
                response.setStatus(NodeResponseStatus.OK);
            } catch (ExceptionActionNotFound exceptionActionNotFound) {
                response.setStatus(NodeResponseStatus.ERROR);
                response.setMessage("configuration saved, action not found");
            } catch (ExceptionActionAlreadyConfirmed exceptionActionAlreadyConfirmed) {
                response.setNodeId(node.getId());
                response.setStatus(NodeResponseStatus.ERROR);
                response.setMessage("configuration saved, action was already confirmed");
            }
        } else {
            response.setStatus(NodeResponseStatus.ERROR);
            response.setMessage("node not found");
        }

        return response;
    }
}
