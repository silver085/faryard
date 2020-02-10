package com.faryard.api.domain.node;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;

@Document("NodeHistory")
public class NodeHistory {
    @Id
    private String id;
    private String nodeId;
    private Double environmentTemp;
    private Double environmentHum;
    private List<RelayValues> relaysList;
    private List<AdsValues> adsList;
    private Date registrationDate;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getEnvironmentTemp() {
        return environmentTemp;
    }

    public void setEnvironmentTemp(Double environmentTemp) {
        this.environmentTemp = environmentTemp;
    }

    public Double getEnvironmentHum() {
        return environmentHum;
    }

    public void setEnvironmentHum(Double environmentHum) {
        this.environmentHum = environmentHum;
    }

    public List<RelayValues> getRelaysList() {
        return relaysList;
    }

    public void setRelaysList(List<RelayValues> relaysList) {
        this.relaysList = relaysList;
    }

    public List<AdsValues> getAdsList() {
        return adsList;
    }

    public void setAdsList(List<AdsValues> adsList) {
        this.adsList = adsList;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }
}
