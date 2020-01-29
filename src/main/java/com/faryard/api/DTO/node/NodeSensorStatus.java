package com.faryard.api.DTO.node;

import java.util.Date;
import java.util.List;

public class NodeSensorStatus extends NodeStatusResponse {
    private Date lastSensorUpdate;
    private List<NodeComponentDTO> components;

    public Date getLastSensorUpdate() {
        return lastSensorUpdate;
    }

    public void setLastSensorUpdate(Date lastSensorUpdate) {
        this.lastSensorUpdate = lastSensorUpdate;
    }

    public List<NodeComponentDTO> getComponents() {
        return components;
    }

    public void setComponents(List<NodeComponentDTO> components) {
        this.components = components;
    }
}
