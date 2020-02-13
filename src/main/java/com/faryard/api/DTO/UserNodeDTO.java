package com.faryard.api.DTO;

import java.util.Date;

public class UserNodeDTO {
    private String nodeId;
    private String nodeIp;
    private String macAddress;
    private Date creationDate;
    private Date lastPingDate;
    private Boolean isOnline = false;
    private String nodeWanIp;
    private String nodeCityLocation;
    private String nodeCountryLocation;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
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

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }

    public String getNodeWanIp() {
        return nodeWanIp;
    }

    public void setNodeWanIp(String nodeWanIp) {
        this.nodeWanIp = nodeWanIp;
    }

    public String getNodeCityLocation() {
        return nodeCityLocation;
    }

    public void setNodeCityLocation(String nodeCityLocation) {
        this.nodeCityLocation = nodeCityLocation;
    }

    public String getNodeCountryLocation() {
        return nodeCountryLocation;
    }

    public void setNodeCountryLocation(String nodeCountryLocation) {
        this.nodeCountryLocation = nodeCountryLocation;
    }
}
