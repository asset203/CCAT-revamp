package com.asset.ccat.lookup_service.database.extractors;


import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingPlanBitModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;


@Component
public class ServiceOfferingPlansBitsExtractor implements ResultSetExtractor<HashMap<Integer,ServiceOfferingPlanBitModel>> {

    @Override
    public HashMap<Integer, ServiceOfferingPlanBitModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingPlansBitsExtractor() Start Extracting the result set");

        HashMap<Integer,ServiceOfferingPlanBitModel> result = new HashMap<>();

        while (resultSet.next()){
            Integer planId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID);
            String planName = resultSet.getString(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME);
            String description = resultSet.getString(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION);
            Integer serviceClassId = resultSet.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID);
            if(result.containsKey(planId)) {
                if (Objects.nonNull(description) && !serviceClassId.equals(0)) {
                    ServiceOfferingPlanBitModel planBitModel = result.get(planId);
                    HashMap<Integer, String> serviceClassMap = planBitModel.getServiceClassDisc();
                    serviceClassMap.put(serviceClassId, description);

                    serviceClassMap.put(serviceClassId, description);
                    planBitModel.setServiceClassDisc(serviceClassMap);
                    result.put(planId, planBitModel);
                }
            }else {
                HashMap<Integer,String> serviceClassMap = new HashMap<>();
                if (Objects.nonNull(description) && !serviceClassId.equals(0)) {
                    serviceClassMap.put(serviceClassId, description);
                }
                result.put(planId,new ServiceOfferingPlanBitModel(planName,planId,serviceClassMap));
            }
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingPlansBitsExtractor() Ended Extracting the result set Successfully");

        return result;
    }
}
