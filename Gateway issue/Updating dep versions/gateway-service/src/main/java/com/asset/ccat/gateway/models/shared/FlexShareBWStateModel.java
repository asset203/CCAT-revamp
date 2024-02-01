package com.asset.ccat.gateway.models.shared;

public class FlexShareBWStateModel {

    private String currentState;
    private String destinationState;
    private Integer updatedValue;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getDestinationState() {
        return destinationState;
    }

    public void setDestinationState(String destinationState) {
        this.destinationState = destinationState;
    }

    public Integer getUpdatedValue() {
        return updatedValue;
    }

    public void setUpdatedValue(Integer updatedValue) {
        this.updatedValue = updatedValue;
    }

    @Override
    public String toString() {
        return "FlexShareBWStateModel{" +
                "currentState='" + currentState + '\'' +
                ", destinationState='" + destinationState + '\'' +
                ", updatedValue=" + updatedValue +
                '}';
    }
}
