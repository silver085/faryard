package com.faryard.api.domain.node;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RelayChannel {
    private boolean isOn;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastUpdate;

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        this.isOn = on;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
