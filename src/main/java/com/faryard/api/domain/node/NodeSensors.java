package com.faryard.api.domain.node;

import java.util.List;

public class NodeSensors {
    private Hygrometer hygrometer;
    private List<ADSChannel> adsChannels;
    private List<RelayChannel> relayChannels;

    public Hygrometer getHygrometer() {
        return hygrometer;
    }

    public void setHygrometer(Hygrometer hygrometer) {
        this.hygrometer = hygrometer;
    }

    public List<ADSChannel> getAdsChannels() {
        return adsChannels;
    }

    public void setAdsChannels(List<ADSChannel> adsChannels) {
        this.adsChannels = adsChannels;
    }

    public List<RelayChannel> getRelayChannels() {
        return relayChannels;
    }

    public void setRelayChannels(List<RelayChannel> relayChannels) {
        this.relayChannels = relayChannels;
    }
}
