package com.faryard.api.DTO.node;

public class NodePingRequest {
    private String id;
    private String ip;
    private String macAddress;
    private SensorsStatus sensorsStatus;

    public SensorsStatus getSensorsStatus() {
        return sensorsStatus;
    }

    public void setSensorsStatus(SensorsStatus sensorsStatus) {
        this.sensorsStatus = sensorsStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
