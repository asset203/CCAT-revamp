package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.customer_care.LanguageModel;
import com.asset.ccat.gateway.models.requests.customer_care.language.UpdateLanguageRequest;
import com.asset.ccat.gateway.proxy.LanguageProxy;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author nour.ihab
 */
@Service
public class LanguageService {

    @Autowired
    LanguageProxy languageProxy;

    public void updateLanguage(UpdateLanguageRequest udateLanguageRequest) throws GatewayException {
        languageProxy.updateLanguage(udateLanguageRequest);
    }

    public List<LanguageModel> getAllLanguages() throws GatewayException {
        List<LanguageModel> GetAllLanguagesResponse = languageProxy.getAllLangauges();
        return GetAllLanguagesResponse;
    }
}
