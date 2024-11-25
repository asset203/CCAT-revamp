package com.asset.ccat.user_management.models.responses.lookup;

import com.asset.ccat.user_management.models.shared.LkFeatureModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetAllFeaturesResponse {

    List<LkFeatureModel> features;

    public GetAllFeaturesResponse() {
    }

    public GetAllFeaturesResponse(List<LkFeatureModel> features) {
        this.features = features;
    }

    public List<LkFeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<LkFeatureModel> features) {
        this.features = features;
    }

}
