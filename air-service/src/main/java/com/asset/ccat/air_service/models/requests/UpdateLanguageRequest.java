/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.models.requests;

/**
 * @author wael.mohamed
 */
public class UpdateLanguageRequest extends SubscriberRequest {

    private Integer languageId;

    public UpdateLanguageRequest() {
    }

    public UpdateLanguageRequest(Integer languageId) {
        this.languageId = languageId;
    }

    public Integer getLanguageId() {
        return languageId;
    }

    public void setLanguageId(Integer languageId) {
        this.languageId = languageId;
    }

    @Override
    public String toString() {
        return "UpdateLanguageRequest{" +
                "languageId=" + languageId +
                '}';
    }
}
