package com.faryard.api.DTO.node;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class RelayStatus {
    private boolean status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private Date lastUpdate;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
