package com.asset.ccat.notification_service.models.requests;

import com.asset.ccat.notification_service.models.SmsTemplateModel;
import com.asset.ccat.notification_service.models.responses.BaseResponse;

import java.util.List;

public class LookupGetTemplatesResponse {
    private BaseResponse<List<SmsTemplateModel>> response;

    public BaseResponse<List<SmsTemplateModel>> getResponse() {
        return response;
    }

    public void setResponse(BaseResponse<List<SmsTemplateModel>> response) {
        this.response = response;
    }
}
