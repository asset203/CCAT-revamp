package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.SmsTemplateParamModel;

import java.util.HashMap;
import java.util.List;

public class GetSmsActionParamMapResponse {

    private HashMap<Integer, List<SmsTemplateParamModel>> actionParamMap;

    public GetSmsActionParamMapResponse() {
    }

    public GetSmsActionParamMapResponse(HashMap<Integer, List<SmsTemplateParamModel>> actionParamMap) {
        this.actionParamMap = actionParamMap;
    }

    public HashMap<Integer, List<SmsTemplateParamModel>> getActionParamMap() {
        return actionParamMap;
    }

    public void setActionParamMap(HashMap<Integer, List<SmsTemplateParamModel>> actionParamMap) {
        this.actionParamMap = actionParamMap;
    }
}
