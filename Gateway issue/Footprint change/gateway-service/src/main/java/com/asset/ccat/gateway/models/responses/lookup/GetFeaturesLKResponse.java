package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.users.LkFeatureModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class GetFeaturesLKResponse {

    private List<LkFeatureModel> features;

    public GetFeaturesLKResponse() {
    }

    public GetFeaturesLKResponse(List<LkFeatureModel> features) {
        this.features = features;
    }

    public List<LkFeatureModel> getFeatures() {
        return features;
    }

    public void setFeatures(List<LkFeatureModel> features) {
        this.features = features;
    }
}
