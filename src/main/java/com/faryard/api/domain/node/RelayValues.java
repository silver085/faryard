package com.faryard.api.domain.node;

import java.util.Date;

public class RelayValues {
    private int relayChannel;
    private boolean isOn;
    private Date lastUpdate;

    public int getRelayChannel() {
        return relayChannel;
    }

    public void setRelayChannel(int relayChannel) {
        this.relayChannel = relayChannel;
    }

    public boolean isOn() {
        return isOn;
    }

    public void setOn(boolean on) {
        isOn = on;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
