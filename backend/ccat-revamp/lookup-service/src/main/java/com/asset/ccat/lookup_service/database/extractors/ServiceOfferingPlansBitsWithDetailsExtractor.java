package com.asset.ccat.lookup_service.database.extractors;


import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;


public class ServiceOfferingPlansBitsWithDetailsExtractor implements ResultSetExtractor<HashMap<Integer,ServiceOfferingPlanBitModel>> {
    private HashMap<Integer, ServiceOfferingPlanBitModel> plansMap ;
    private HashMap<Integer, ServiceOfferingBitModel> bitsMap ;

    public ServiceOfferingPlansBitsWithDetailsExtractor(HashMap<Integer,ServiceOfferingPlanBitModel> plansMap,
                                                        HashMap<Integer,ServiceOfferingBitModel> bitsMap) {
        this.plansMap = plansMap;
        this.bitsMap = bitsMap;
    }

    @Override
    public HashMap<Integer, ServiceOfferingPlanBitModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingPlansBitsWithDetailsExtractor() Start Extracting the result set");
        while (resultSet.next()){
            Integer planId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.PLAN_ID);
            Integer bitPosition = resultSet.getInt(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.BIT_POSITION);
            Boolean enabled = resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.IS_ENABLED);

            HashMap<Integer,ServiceOfferingBitModel> currentPlanBitsMap = (HashMap<Integer, ServiceOfferingBitModel>) bitsMap.clone();
            ServiceOfferingPlanBitModel currentPlan = plansMap.get(planId);

            ServiceOfferingBitModel currentBit = currentPlanBitsMap.get(bitPosition);
            currentBit.setIsEnabled(enabled);
            currentPlanBitsMap.put(bitPosition,currentBit);
            currentPlan.setBitModelHashMap(currentPlanBitsMap);

            Double decimalValue = currentPlan.getDecimalValue();
            decimalValue+= Math.pow(2.0,bitPosition.doubleValue());
            currentPlan.setDecimalValue(decimalValue);

            plansMap.put(planId,currentPlan);

        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingPlansBitsWithDetailsExtractor() Ended Extracting the result set Successfully");

        return plansMap;
    }
}
