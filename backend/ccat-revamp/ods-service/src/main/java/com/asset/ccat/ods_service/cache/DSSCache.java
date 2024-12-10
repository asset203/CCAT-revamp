package com.asset.ccat.ods_service.cache;

import com.asset.ccat.ods_service.models.DssInterfaceModel;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class DSSCache {
    private Map<String, DssInterfaceModel> dssInterfaceModelMap;

    public Map<String, DssInterfaceModel> getDssInterfaceModelMap() {
        return dssInterfaceModelMap;
    }

    public void setDssInterfaceModelMap(Map<String, DssInterfaceModel> dssInterfaceModelMap) {
        this.dssInterfaceModelMap = dssInterfaceModelMap;
    }
}
