package com.faryard.api.domain.node;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document("Node")
public class Node {
    @Id
    private String id;
    @Indexed(unique = true, direction = IndexDirection.DESCENDING, dropDups = true)
    private String nodeName;
    private String nodeMACAddress;
    private String nodeIP;
    private Date creationDate;
    private Date lastPingDate;
    private NodeStatus nodeStatus;
    private Date lastActionCommittedDate;
    private Date lastActionConfirmedDate;
    private List<NodeComponent> components;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

    public String getNodeMACAddress() {
        return nodeMACAddress;
    }

    public void setNodeMACAddress(String nodeMACAddress) {
        this.nodeMACAddress = nodeMACAddress;
    }

    public String getNodeIP() {
        return nodeIP;
    }

    public void setNodeIP(String nodeIP) {
        this.nodeIP = nodeIP;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastPingDate() {
        return lastPingDate;
    }

    public void setLastPingDate(Date lastPingDate) {
        this.lastPingDate = lastPingDate;
    }

    public NodeStatus getNodeStatus() {
        return nodeStatus;
    }

    public void setNodeStatus(NodeStatus nodeStatus) {
        this.nodeStatus = nodeStatus;
    }

    public Date getLastActionCommittedDate() {
        return lastActionCommittedDate;
    }

    public void setLastActionCommittedDate(Date lastActionCommittedDate) {
        this.lastActionCommittedDate = lastActionCommittedDate;
    }

    public Date getLastActionConfirmedDate() {
        return lastActionConfirmedDate;
    }

    public void setLastActionConfirmedDate(Date lastActionConfirmedDate) {
        this.lastActionConfirmedDate = lastActionConfirmedDate;
    }

    public List<NodeComponent> getComponents() {
        return components;
    }

    public void setComponents(List<NodeComponent> components) {
        this.components = components;
    }
}
