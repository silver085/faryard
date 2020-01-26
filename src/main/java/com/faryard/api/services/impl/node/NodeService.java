package com.faryard.api.services.impl.node;

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

import java.util.Date;

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
}
