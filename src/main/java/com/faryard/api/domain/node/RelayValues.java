package com.faryard.api.domain.node;

public class RelayValues {
    private int relayChannel;
    private boolean isOn;

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
}
