package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.SOServiceClassDescriptionModel;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlan;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ServicePlanClassDescriptionsExtractor implements ResultSetExtractor<HashMap<Integer, ServiceOfferingPlan>> {


    @Override
    public HashMap<Integer, ServiceOfferingPlan> extractData(ResultSet rs) throws SQLException, DataAccessException {
        HashMap<Integer, ServiceOfferingPlan> serviceOfferingPlansMap = new HashMap<>();
        Integer planId;
        while (rs.next()) {
            planId = rs.getInt(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.PLAN_ID);


            if (serviceOfferingPlansMap.get(planId) == null) {
                ServiceOfferingPlan serviceOfferingPlan = new ServiceOfferingPlan();
                serviceOfferingPlan.setPlanId(planId);
                serviceOfferingPlan.setName(rs.getString(DatabaseStructs.ADM_SERVICE_OFFERING_PLANS.NAME));

                HashMap<Integer, SOServiceClassDescriptionModel> soServiceClassDescriptionMap = new HashMap<>();
                serviceOfferingPlan.setSoServiceClassDescriptions(soServiceClassDescriptionMap);
                serviceOfferingPlansMap.put(planId, serviceOfferingPlan);


            }
            SOServiceClassDescriptionModel soServiceClassDescription = new SOServiceClassDescriptionModel();
            soServiceClassDescription.setServiceClassId(rs.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID));
            soServiceClassDescription.setDescription(rs.getString(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION));


            serviceOfferingPlansMap.get(planId).getSoServiceClassDescriptions().put(rs.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.SERVICE_CLASS_ID),
                    soServiceClassDescription);


        }

        return serviceOfferingPlansMap;
    }
}


