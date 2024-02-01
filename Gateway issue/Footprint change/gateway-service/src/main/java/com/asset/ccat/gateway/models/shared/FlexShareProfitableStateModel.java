package com.asset.ccat.gateway.models.shared;

public class FlexShareProfitableStateModel {

    private String currentState;
    private String parameterOut;

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String currentState) {
        this.currentState = currentState;
    }

    public String getParameterOut() {
        return parameterOut;
    }

    public void setParameterOut(String parameterOut) {
        this.parameterOut = parameterOut;
    }

    @Override
    public String toString() {
        return "FlexShareProfitableStateModel{" +
                "currentState='" + currentState + '\'' +
                ", parameterOut='" + parameterOut + '\'' +
                '}';
    }
}
