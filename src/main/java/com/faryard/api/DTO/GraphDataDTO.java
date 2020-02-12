package com.faryard.api.DTO;

import com.faryard.api.DTO.node.SensorsStatus;

import java.util.Date;

public class GraphDataDTO {
    private Date date;
    private SensorsStatus sensors;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public SensorsStatus getSensors() {
        return sensors;
    }

    public void setSensors(SensorsStatus sensors) {
        this.sensors = sensors;
    }
}
