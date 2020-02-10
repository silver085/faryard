package com.faryard.api.domain.node;

public class AdsValues {
    private String adsChannelName;
    private Double percentage;
    private Double voltage;
    private String evaluation;
    private int evaluationIndex;

    public String getAdsChannelName() {
        return adsChannelName;
    }

    public void setAdsChannelName(String adsChannelName) {
        this.adsChannelName = adsChannelName;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }

    public Double getVoltage() {
        return voltage;
    }

    public void setVoltage(Double voltage) {
        this.voltage = voltage;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }

    public int getEvaluationIndex() {
        return evaluationIndex;
    }

    public void setEvaluationIndex(int evaluationIndex) {
        this.evaluationIndex = evaluationIndex;
    }
}
