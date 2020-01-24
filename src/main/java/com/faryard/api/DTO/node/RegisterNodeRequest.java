package com.faryard.api.DTO.node;

public class RegisterNodeRequest {
    private String nodeName;
    private String nodeIp;
    private String nodeMacAddress;

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName;
    }

     public String getNodeIp() {
        return nodeIp;
    }

    public void setNodeIp(String nodeIp) {
        this.nodeIp = nodeIp;
    }

    public String getNodeMacAddress() {
        return nodeMacAddress;
    }

    public void setNodeMacAddress(String nodeMacAddress) {
        this.nodeMacAddress = nodeMacAddress;
    }
}
