package com.faryard.api.repositories;

import com.faryard.api.domain.node.Node;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface NodeRepository extends MongoRepository<Node, String> {
    Optional<Node> findById(String id);
    Optional<Node> findByNodeMACAddress(String macAddress);
    Optional<Node> findByIdAndNodeMACAddress(String id, String macAddress);
}
