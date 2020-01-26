package com.faryard.api.DTO.node;

import java.util.Date;

public class NodeStatusResponse {
    private Boolean nodeExist;
    private Date lastPingDate;
    private Date creationDate;
    private Date lastActionCommittedDate;
    private Date lastActionPerformedDate;
    private NodeAction lastActionCommitted;
    private String nodeName;
    private String nodeMACAddress;
    private String nodeIP;
    private Boolean isOnline;

    public Boolean getNodeExist() {
        return nodeExist;
    }

    public void setNodeExist(Boolean nodeExist) {
        this.nodeExist = nodeExist;
    }

    public Date getLastPingDate() {
        return lastPingDate;
    }

    public void setLastPingDate(Date lastPingDate) {
        this.lastPingDate = lastPingDate;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Date getLastActionCommittedDate() {
        return lastActionCommittedDate;
    }

    public void setLastActionCommittedDate(Date lastActionCommittedDate) {
        this.lastActionCommittedDate = lastActionCommittedDate;
    }

    public Date getLastActionPerformedDate() {
        return lastActionPerformedDate;
    }

    public void setLastActionPerformedDate(Date lastActionPerformedDate) {
        this.lastActionPerformedDate = lastActionPerformedDate;
    }

    public NodeAction getLastActionCommitted() {
        return lastActionCommitted;
    }

    public void setLastActionCommitted(NodeAction lastActionCommitted) {
        this.lastActionCommitted = lastActionCommitted;
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

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
