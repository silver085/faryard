package com.faryard.api.repositories;

import com.faryard.api.domain.node.Node;
import com.faryard.api.domain.node.NodeHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NodeHistoryRepository extends MongoRepository<NodeHistory, String> {
    Optional<NodeHistory> findTopByNodeId(String nodeId);
    Optional<NodeHistory> findFirstByNodeIdOrderByRegistrationDateDesc(String nodeId);
}
