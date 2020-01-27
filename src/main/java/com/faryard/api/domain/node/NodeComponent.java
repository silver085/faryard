package com.faryard.api.domain.node;

public class NodeComponent {
    private String componentName;
    private String componentReference;
    private String componentIO;
    private String componentPin;
    private String componentHealth;
    private String lastComponentValue;
    private String lastComponentUoM;

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public String getComponentReference() {
        return componentReference;
    }

    public void setComponentReference(String componentReference) {
        this.componentReference = componentReference;
    }

    public String getComponentIO() {
        return componentIO;
    }

    public void setComponentIO(String componentIO) {
        this.componentIO = componentIO;
    }

    public String getComponentPin() {
        return componentPin;
    }

    public void setComponentPin(String componentPin) {
        this.componentPin = componentPin;
    }

    public String getComponentHealth() {
        return componentHealth;
    }

    public void setComponentHealth(String componentHealth) {
        this.componentHealth = componentHealth;
    }

    public String getLastComponentValue() {
        return lastComponentValue;
    }

    public void setLastComponentValue(String lastComponentValue) {
        this.lastComponentValue = lastComponentValue;
    }

    public String getLastComponentUoM() {
        return lastComponentUoM;
    }

    public void setLastComponentUoM(String lastComponentUoM) {
        this.lastComponentUoM = lastComponentUoM;
    }
}