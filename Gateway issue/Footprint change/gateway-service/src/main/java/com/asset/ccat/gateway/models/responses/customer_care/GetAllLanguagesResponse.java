package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.LanguageModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllLanguagesResponse {

    List<LanguageModel> langauges;

    public List<LanguageModel> getLangaugeModel() {
        return langauges;
    }

    public void setLangaugeModel(List<LanguageModel> langauges) {
        this.langauges = langauges;
    }

}
