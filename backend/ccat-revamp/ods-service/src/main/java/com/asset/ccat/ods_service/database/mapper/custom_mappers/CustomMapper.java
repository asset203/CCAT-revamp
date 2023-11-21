package com.asset.ccat.ods_service.database.mapper.custom_mappers;

import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.asset.ccat.ods_service.models.ods_models.ODSActivityHeaderMapping;

public interface CustomMapper {

    public void handleCustomMapping(SubscriberActivityModel accountHistoryModel, String msisdn, Object[] columns, ODSActivityHeaderMapping headerMappingObject);
}
