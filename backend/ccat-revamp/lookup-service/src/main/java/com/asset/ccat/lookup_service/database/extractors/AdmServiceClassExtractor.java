/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.database.extractors;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.models.Accumlator;
import com.asset.ccat.lookup_service.models.AdmServiceClassModel;
import com.asset.ccat.lookup_service.models.DedicatedAccount;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.asset.ccat.lookup_service.models.ServiceOfferingPlanDescModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

/**
 * @author wael.moahmed
 */
@Component
public class AdmServiceClassExtractor implements ResultSetExtractor<AdmServiceClassModel> {

    @Override
    public AdmServiceClassModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        AdmServiceClassModel model = null;
        HashMap<Integer, Accumlator> accumlators;
        HashMap<Integer, DedicatedAccount> dedAccounts;
        HashMap<Integer, ServiceOfferingPlanDescModel> serviceOfferingDescs;
        DedicatedAccount dedAccount;
        Accumlator accumlator;
        while (resultSet.next()) {
            Integer id = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE);
            if (model == null) {
                model = new AdmServiceClassModel();
                model.setName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.NAME));
                model.setCode(resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASSES.CODE));
                model.setCiPackageName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.CI_PACKAGE_NAME));
                model.setCiServiceName(resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASSES.CI_SERVICE_NAME));
                model.setIsAllowedMigration(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_ALLOW_MIGRATION));
                model.setPreActivationAllowed(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.PREACTIVATION_ALLOWED));
                model.setIsCiConversion(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_CI_CONVERSION));
                model.setIsGrandfather(resultSet.getBoolean(DatabaseStructs.ADM_SERVICE_CLASSES.IS_GRANDFATHER));

                accumlators = new HashMap();
                dedAccounts = new HashMap();
                serviceOfferingDescs = new HashMap<>();

                Integer accId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID);
                String description = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION);
                accumlator = new Accumlator();
                accumlator.setAccID(accId);
                accumlator.setDescription(description);
                if (accId != 0) {
                    accumlators.put(accId, accumlator);
                }
                model.setAccumlatorsMap(accumlators);

                Integer daId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
                String daDes = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_DA.DESCRIPTION);
                Float ratting = resultSet.getFloat(DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR);
                dedAccount = new DedicatedAccount();
                dedAccount.setDaID(daId);
                dedAccount.setDescription(daDes);
                dedAccount.setRatingFactor(ratting);
                if (daId != 0) {
                    dedAccounts.put(daId, dedAccount);
                }
                model.setDedAccountsMap(dedAccounts);

                Integer planId = resultSet.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID);
                String soDesc = resultSet.getString(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION_ALIAS);
                if (!planId.equals(0)) {
                    serviceOfferingDescs.put(planId, new ServiceOfferingPlanDescModel(planId, soDesc));
                }
                model.setServiceOfferingPlanDescsMap(serviceOfferingDescs);

            } else {
                Integer accId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_ACC.ACC_ID);
                String description = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_ACC.DESCRIPTION);
                accumlator = new Accumlator();
                accumlator.setAccID(accId);
                accumlator.setDescription(description);

                if (accId != 0) {
                    model.getAccumlatorsMap().put(accId, accumlator);
                }
                Integer daId = resultSet.getInt(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_ID);
                String daDes = resultSet.getString(DatabaseStructs.ADM_SERVICE_CLASS_DA.DA_DESC);
                Float ratting = resultSet.getFloat(DatabaseStructs.ADM_SERVICE_CLASS_DA.RATING_FACTOR);
                dedAccount = new DedicatedAccount();
                dedAccount.setDaID(daId);
                dedAccount.setDescription(daDes);
                dedAccount.setRatingFactor(ratting);
                if (daId != 0) {
                    model.getDedAccountsMap().put(daId, dedAccount);
                }
                Integer planId = resultSet.getInt(DatabaseStructs.ADM_SO_SC_DESCRIPTION.PLAN_ID);
                String desc = resultSet.getString(DatabaseStructs.ADM_SO_SC_DESCRIPTION.DESCRIPTION_ALIAS);
                if (!planId.equals(0)) {
                    model.getServiceOfferingPlanDescsMap().put(planId, new ServiceOfferingPlanDescModel(planId, desc));
                }
            }
        }
        return model;
    }

}
