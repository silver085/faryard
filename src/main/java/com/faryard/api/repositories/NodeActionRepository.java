package com.faryard.api.repositories;

import com.faryard.api.domain.node.Action;
import com.faryard.api.domain.node.NodeAction;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface NodeActionRepository extends MongoRepository<NodeAction, String> {
    List<NodeAction> findByConfirmedIsFalseAndNodeId(String nodeId);
}
