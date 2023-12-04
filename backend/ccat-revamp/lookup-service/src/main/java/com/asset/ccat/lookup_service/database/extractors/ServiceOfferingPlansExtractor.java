package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;

import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class ServiceOfferingPlansExtractor implements ResultSetExtractor<HashMap<Integer, ServiceOfferingPlan>> {

    @Override
    public HashMap<Integer, ServiceOfferingPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlansMap = new HashMap<>();
        Integer planId;
        while (rs.next()) {
            planId = rs.getInt(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID);

            if (serviceOfferingPlansMap.get(planId) == null) {
                ServiceOfferingPlan serviceOfferingPlan = new ServiceOfferingPlan();
                //       serviceOfferingPlan.setId(rs.getInt(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.ID));
                serviceOfferingPlan.setPlanId(planId);
                serviceOfferingPlan.setName(rs.getString(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME));

                HashMap<Integer, Boolean> serviceBits = new HashMap<>() {
                    {
//                        put(0, false);
                        put(1, false);
                        put(2, false);
                        put(3, false);
                        put(4, false);
                        put(5, false);
                        put(6, false);
                        put(7, false);
                        put(8, false);
                        put(9, false);
                        put(10, false);
                        put(11, false);
                        put(12, false);
                        put(13, false);
                        put(14, false);
                        put(15, false);
                        put(16, false);
                        put(17, false);
                        put(18, false);
                        put(19, false);
                        put(20, false);
                        put(21, false);
                        put(22, false);
                        put(23, false);
                        put(24, false);
                        put(25, false);
                        put(26, false);
                        put(27, false);
                        put(28, false);
                        put(29, false);
                        put(30, false);
                        put(31, false);
                    }
                };
                serviceOfferingPlan.setServicePlanBits(serviceBits);

                serviceOfferingPlansMap.put(planId, serviceOfferingPlan);
            }

            int decimalValue =0;
            serviceOfferingPlansMap.get(planId)
                    .getServicePlanBits()
                    .put(rs.getInt(DatabaseStructs.ADM_SERVICE_OFFG_PLAN_BITS.BIT_POSITION), true);
            for( Integer bitPosition :serviceOfferingPlansMap.get(planId).getServicePlanBits().keySet()) {

                if(serviceOfferingPlansMap.get(planId).getServicePlanBits().get(bitPosition)){
                    decimalValue+=   Math.pow(2, bitPosition);
                }

            };
            serviceOfferingPlansMap.get(planId).setDecimalValue(decimalValue);



    }
        return serviceOfferingPlansMap;
    }
}
