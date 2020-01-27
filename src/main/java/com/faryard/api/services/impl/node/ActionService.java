package com.faryard.api.services.impl.node;

import com.faryard.api.DTO.node.NodeExecuteAction;
import com.faryard.api.domain.node.Action;
import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeAction;
import com.faryard.api.repositories.NodeActionRepository;
import com.faryard.api.services.exceptions.ExceptionActionAlreadyConfirmed;
import com.faryard.api.services.exceptions.ExceptionActionNotFound;
import com.faryard.api.services.exceptions.ExceptionNodeStillPerformingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ActionService  {
    @Autowired
    NodeActionRepository nodeActionRepository;

    public void saveActionForNode(Node node, NodeExecuteAction nodeAction) throws ExceptionNodeStillPerformingException {
        if(nodeActionRepository.findByConfirmedIsFalseAndNodeId(nodeAction.getNodeId()).size() > 0){
            throw new ExceptionNodeStillPerformingException();
        }
        NodeAction newAction = new NodeAction();
        newAction.setNodeId(node.getId());
        newAction.setActionCommitDate(new Date());
        newAction.setAction(Action.valueOf(nodeAction.getAction()));
        nodeActionRepository.save(newAction);
    }

    public String getLastActionForNode(Node node) {
        List<NodeAction> actions = nodeActionRepository.findByConfirmedIsFalseAndNodeId(node.getId());
        if(actions.size() > 0){
            NodeAction nodeAction = actions.stream().findFirst().orElse(null);
            return nodeAction.getAction().getAction();
        }
        return Action.IDLE.getAction();
    }

    public NodeAction getLastActionObjectForNode(Node node){
        List<NodeAction> actions = nodeActionRepository.findByConfirmedIsFalseAndNodeId(node.getId());
        if(actions.size() > 0){
            return actions.stream().findFirst().orElse(null);
        }
        return null;
    }

    public NodeAction getLastUnconfirmedActionForNode(Node node){
        return nodeActionRepository.findByConfirmedIsFalseAndNodeId(node.getId()).stream().findFirst().orElse(null);
    }

    public void markAsDone(String lastActionId) throws ExceptionActionNotFound, ExceptionActionAlreadyConfirmed {
        if(nodeActionRepository.findById(lastActionId).isPresent()) {
            NodeAction action = nodeActionRepository.findById(lastActionId).get();
            if(action.getConfirmed()){
                throw new ExceptionActionAlreadyConfirmed();
            }
            action.setActionConfirmedDate(new Date());
            action.setConfirmed(true);
            nodeActionRepository.save(action);
        } else {
            throw new ExceptionActionNotFound();
        }
    }
}
