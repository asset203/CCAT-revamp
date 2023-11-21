package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.service_offering_models.ServiceOfferingBitModel;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Objects;

@Component
public class ServiceOfferingBitsDetailsExtractor implements ResultSetExtractor<HashMap<Integer, ServiceOfferingBitModel>> {
    @Override
    public HashMap<Integer, ServiceOfferingBitModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingBitsDetailsExtractor() : Started");

        HashMap<Integer, ServiceOfferingBitModel> result = new HashMap<>();
        while (resultSet.next()){
            Integer bitPosition = resultSet.getInt(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_POSITION);
            String bitName = resultSet.getString(DatabaseStructs.ADM_SERVICE_OFFERING_BITS.BIT_NAME);
            Integer serviceClassId = resultSet.getInt(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.SERVICE_CLASS_ID);
            String description = resultSet.getString(DatabaseStructs.ADM_SO_BITS_SC_DESCRIPTION.DESCRIPTION);
            HashMap<Integer,String> serviceClassDescMap = new HashMap<>();
            if (Objects.nonNull(description) && !serviceClassId.equals(0)) {
                serviceClassDescMap.put(serviceClassId, description);
            }

            ServiceOfferingBitModel bitModel = new ServiceOfferingBitModel(bitPosition,bitName,false,serviceClassDescMap);
            result.put(bitPosition,bitModel);

        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceOfferingBitsDetailsExtractor() : Ended Successfully");
        return result;
    }
}
