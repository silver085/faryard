package com.faryard.api.DTO.node;
public class AdsStatus {
    private String name;
    private String channel;
    private String adc;
    private long channelValue;
    private Double channelVoltage;
    private Double percentage;
    private AdsEvaluation evaluation;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getAdc() {
        return adc;
    }

    public void setAdc(String adc) {
        this.adc = adc;
    }

    public long getChannelValue() {
        return channelValue;
    }

    public void setChannelValue(long channelValue) {
        this.channelValue = channelValue;
    }

    public Double getChannelVoltage() {
        return channelVoltage;
    }

    public void setChannelVoltage(Double channelVoltage) {
        this.channelVoltage = channelVoltage;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public AdsEvaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(AdsEvaluation evaluation) {
        this.evaluation = evaluation;
    }
}

