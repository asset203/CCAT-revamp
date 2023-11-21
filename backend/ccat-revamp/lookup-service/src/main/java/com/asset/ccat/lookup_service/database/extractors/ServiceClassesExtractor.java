/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.ServiceClassModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class ServiceClassesExtractor implements ResultSetExtractor<ArrayList<ServiceClassModel>> {

    @Override
    public ArrayList<ServiceClassModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        ArrayList result = new ArrayList();
        HashMap<Integer, ServiceClassModel> serviceMap = new HashMap();
        Integer count = 0;
        while (resultSet.next()) {
            count++;
            Integer id = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE);
            if (serviceMap.get(id) == null) {
                ServiceClassModel model = new ServiceClassModel();
                model.setName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.NAME));
                model.setCode(resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
                model.setCiPackageName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME));
                model.setCiServiceName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME));
                model.setIsAllowedMigration(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION));
                model.setIsCiConversion(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION));
                model.setIsGrandfather(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER));

                HashMap<Integer, String> accumlators = new HashMap();
                HashMap<Integer, DedicatedAccount> dedAccounts = new HashMap();
                Integer accId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID);
                String description = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION);

                if (accId != null) {
                    accumlators.put(accId, description);
                }
                model.setAccumlators(accumlators);

                Integer daId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
                String daDes = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION);
                Float ratingFactor = resultSet.getFloat(DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR);

                DedicatedAccount dedAccount = new DedicatedAccount();
                dedAccount.setDaID(daId);
                dedAccount.setDescription(daDes);
                dedAccount.setRatingFactor(ratingFactor);
                if (daId != 0) {
                    dedAccounts.put(daId, dedAccount);
                }
                model.setDedAccounts(dedAccounts);
                serviceMap.put(id, model);

            } else {
                ServiceClassModel model = serviceMap.get(id);
                Integer accId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID);
                String description = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION);

                if (accId != null) {
                    model.getAccumlators().put(accId, description);
                }
                Integer daId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
                String daDes = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION);
                Float ratingFactor = resultSet.getFloat(DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR);

                DedicatedAccount dedAccount = new DedicatedAccount();
                dedAccount.setDaID(daId);
                dedAccount.setDescription(daDes);
                dedAccount.setRatingFactor(ratingFactor);
                if (daId != 0) {
                    model.getDedAccounts().put(daId, dedAccount);
                }
            }
        }
        CCATLogger.DEBUG_LOGGER.info("Count is " + count);
        if (serviceMap.values() != null) {
            result.addAll(serviceMap.values());
        }
        return result;
    }

}
