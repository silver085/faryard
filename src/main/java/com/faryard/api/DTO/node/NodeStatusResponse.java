package com.faryard.api.DTO.node;

import java.util.Date;

public class NodeStatusResponse {
    private Boolean nodeExist;
    private Date lastPingDate;
    private Date creationDate;
    private Date lastActionCommittedDate;
    private Date lastActionPerformedDate;
    private String lastActionCommitted;
    private java.lang.String nodeName;
    private java.lang.String nodeMACAddress;
    private java.lang.String nodeIP;
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

    public String getLastActionCommitted() {
        return lastActionCommitted;
    }

    public void setLastActionCommitted(String lastActionCommitted) {
        this.lastActionCommitted = lastActionCommitted;
    }

    public java.lang.String getNodeName() {
        return nodeName;
    }

    public void setNodeName(java.lang.String nodeName) {
        this.nodeName = nodeName;
    }

    public java.lang.String getNodeMACAddress() {
        return nodeMACAddress;
    }

    public void setNodeMACAddress(java.lang.String nodeMACAddress) {
        this.nodeMACAddress = nodeMACAddress;
    }

    public java.lang.String getNodeIP() {
        return nodeIP;
    }

    public void setNodeIP(java.lang.String nodeIP) {
        this.nodeIP = nodeIP;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }


}
