package com.faryard.api.DTO.node;

import java.util.List;

public class SensorsStatus {
    private HygrometerStatus hygrometer;
    private List<AdsStatus> ads;
    private List<RelayStatus> relays;

    public HygrometerStatus getHygrometer() {
        return hygrometer;
    }

    public void setHygrometer(HygrometerStatus hygrometer) {
        this.hygrometer = hygrometer;
    }

    public List<AdsStatus> getAds() {
        return ads;
    }

    public void setAds(List<AdsStatus> ads) {
        this.ads = ads;
    }

    public List<RelayStatus> getRelays() {
        return relays;
    }

    public void setRelays(List<RelayStatus> relays) {
        this.relays = relays;
    }
}
