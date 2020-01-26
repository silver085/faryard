package com.faryard.api.services.impl.node;

import com.faryard.api.DTO.node.NodeStatusResponse;
import com.faryard.api.DTO.node.NodePingRequest;
import com.faryard.api.DTO.node.NodeResponseStatus;
import com.faryard.api.DTO.node.NodeSimpleResponse;
import com.faryard.api.DTO.node.RegisterNodeRequest;
import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeStatus;
import com.faryard.api.repositories.NodeRepository;
import com.faryard.api.services.impl.exceptions.NodeAlreadyRegisteredException;
import com.faryard.api.services.impl.exceptions.NodeNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Service
public class NodeService {
    @Autowired
    NodeRepository nodeRepository;
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
}
