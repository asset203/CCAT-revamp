/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.services;

import java.util.*;

import com.asset.ccat.lookup_service.database.dao.*;
import com.asset.ccat.lookup_service.models.ServiceOfferingPlanDescModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.Accumlator;
import com.asset.ccat.lookup_service.models.AdmServiceClassModel;
import com.asset.ccat.lookup_service.models.DedicatedAccount;
import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import com.asset.ccat.lookup_service.models.migration.ServiceClassesMigrationSummary;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClass;
import com.asset.ccat.lookup_service.models.responses.AdmServiceClassResponse;
import com.asset.ccat.lookup_service.models.responses.ImportServiceClassesResponse;
import com.asset.ccat.lookup_service.models.responses.migration.ServiceClassesMigrationResponse;
import com.asset.ccat.lookup_service.util.Utils;

/**
 * @author wael.mohamed
 */
@Service
public class AdmServiceClassesService {

    @Autowired
    ServiceClassesDao serviceClassesDao;
    @Autowired
    ServiceClassAccDao serviceClassAccDao;
    @Autowired
    ServiceClassDADao serviceClassDAccDao;
    @Autowired
    ServiceClassSOPlanDescDao serviceClassSOPlanDescDao;
    @Autowired
    private SequenceTableDao sequenceDao;
    @Autowired
    private Utils utils;

    public List<AdmServiceClassResponse> getAllServiceClasses() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all service classes");
        List<AdmServiceClassResponse> serviceClasses = serviceClassesDao.retrieveServiceClasses();
        if (serviceClasses == null || serviceClasses.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No service classes were found.");
            throw new LookupException(ErrorCodes.ERROR.SERVICE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all service classes with size[" + serviceClasses.size() + "].");
        return serviceClasses;
    }

    public AdmServiceClassModel getServiceClassById(Integer serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving service class with ID[" + serviceClassId + "].");
        AdmServiceClassModel serviceClass = serviceClassesDao.retrieveServiceClassById(serviceClassId);
        if (serviceClass == null) {
            CCATLogger.DEBUG_LOGGER.error("Retrieving service class with ID [" + serviceClassId + "] was not found.");
            throw new LookupException(ErrorCodes.ERROR.DATA_NOT_FOUND, Defines.SEVERITY.ERROR, "serviceClassId " + serviceClassId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving service class with ID[" + serviceClassId + "].");
        return serviceClass;
    }

    // Do not catch LookupException in this method.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void updateServiceClass(AdmServiceClass serviceClass) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating adm service class with ID[" + serviceClass.getCode() + "].");
        Set<Accumlator> accumlatorSet = new HashSet<>();
        utils.checkAccumlatorsDuplicate(serviceClass, accumlatorSet);

        Set<DedicatedAccount> dedAccountSet = new HashSet<>();
        utils.checkDedAccountsDuplicate(serviceClass, dedAccountSet);

        Set<ServiceOfferingPlanDescModel> soPlansDescriptions = new HashSet<>();
        utils.checkSODescDuplicate(serviceClass, soPlansDescriptions);

        //TODO: 1
        serviceClassesDao.updateServiceClass(serviceClass);
        CCATLogger.DEBUG_LOGGER.debug("AdmServiceClass is updated successfully.");

        //TODO: 2
        serviceClassAccDao.deleteRecordsFromServiceClassACC(serviceClass.getCode());
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassAccumulators is deleted successfully.");

        //TODO: 3
        for (Accumlator acc : accumlatorSet) {
            serviceClassAccDao.addRecordToServiceClassACC(serviceClass.getCode(), acc.getAccID(), acc.getDescription());
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassAccumulators is added successfully.");
        //TODO: 4
        serviceClassDAccDao.deleteRecordsFromServiceClassDA(serviceClass.getCode());
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassDedAccounts is deleted successfully.");

        //TODO: 5
        for (DedicatedAccount dedAcc : dedAccountSet) {
            serviceClassDAccDao.addRecordToServiceClassDA(serviceClass.getCode(), dedAcc);
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassDedAccount is added successfully.");

        //TODO: 4
        serviceClassSOPlanDescDao.deleteServiceClassSOPlanDescription(serviceClass.getCode());
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassServiceOfferingPlansDesc is deleted successfully.");
        for (ServiceOfferingPlanDescModel descModel : soPlansDescriptions) {
            serviceClassSOPlanDescDao.addServiceClassSOPlanDescription(serviceClass.getCode(), descModel);
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassServiceOfferingPlansDesc is added successfully.");
    }

    // Do not catch LookupException in this method.
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = LookupException.class)
    public void addServiceClassWithDetails(AdmServiceClass serviceClass) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding serviceClass with Code[" + serviceClass.getCode() + "].");
        Set<Accumlator> accumlatorSet = new HashSet<>();
        utils.checkAccumlatorsDuplicate(serviceClass, accumlatorSet);

        Set<DedicatedAccount> dedAccountSet = new HashSet<>();
        utils.checkDedAccountsDuplicate(serviceClass, dedAccountSet);

        Set<ServiceOfferingPlanDescModel> soPlansDescriptions = new HashSet<>();
        utils.checkSODescDuplicate(serviceClass, soPlansDescriptions);

        addServiceClass(serviceClass);

        for (Accumlator accumlator : accumlatorSet) {
            serviceClassAccDao.addRecordToServiceClassACC(serviceClass.getCode(), accumlator.getAccID(), accumlator.getDescription());
        }
        for (DedicatedAccount account : dedAccountSet) {
            serviceClassDAccDao.addRecordToServiceClassDA(serviceClass.getCode(), account);
        }

        for (ServiceOfferingPlanDescModel descModel : soPlansDescriptions) {
            serviceClassSOPlanDescDao.addServiceClassSOPlanDescription(serviceClass.getCode(), descModel);
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClassServiceOfferingPlansDesc is added successfully.");
    }

    public void addServiceClass(AdmServiceClass serviceClass) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding serviceClass with Code[" + serviceClass.getCode() + "].");
        //TODO: 1
//        serviceClass.setCode(sequenceDao.getNextId(DatabaseStructs.SEQUENCE.S_ADM_SERVICE_CLASS));
        CCATLogger.DEBUG_LOGGER.debug("ServiceClass is generated ID[" + serviceClass.getCode() + "] successfully.");
        //TODO: 2
        try {
            serviceClassesDao.addServiceClass(serviceClass);
        } catch (LookupException ex) {
            throw new LookupException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "serviceClass");
        }
        CCATLogger.DEBUG_LOGGER.debug("ServiceClass is added with ID[" + serviceClass.getCode() + "] successfully.");
        CCATLogger.DEBUG_LOGGER.debug("Done adding serviceClass with Code[" + serviceClass.getCode() + "].");
    }

    public boolean deleteServiceClass(Integer serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting deleting serviceClass with ID [" + serviceClassId + "].");
        boolean hasChild = serviceClasshasChild(serviceClassId);
        if (hasChild) {
            throw new LookupException(ErrorCodes.ERROR.HAS_CHILD, Defines.SEVERITY.ERROR, "serviceClassId " + serviceClassId);
        }
        boolean isDeleted = serviceClassesDao.deleteServiceClass(serviceClassId);
        CCATLogger.DEBUG_LOGGER.debug("Done deleting serviceClass with ID [" + serviceClassId + "].");
        return isDeleted;
    }

    public boolean serviceClasshasChild(int serviceClassId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if serviceClass with ID[" + serviceClassId + "] has a child.");
        boolean hasChild = serviceClassesDao.serviceClasshasChild(serviceClassId);
        if (hasChild) {
            CCATLogger.DEBUG_LOGGER.error("ServiceClass with ID[" + serviceClassId + "] has a child.");
        }
        return hasChild;
    }

    public ServiceClassesMigrationResponse getAllServiceClassesTables() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all service classes from [ADM-ACC-DA] service class tables");

        ServiceClassesMigrationResponse migrationResponse = new ServiceClassesMigrationResponse();
        List<MigrationModel> migrationModelList = new ArrayList<>();

        migrationModelList.add(serviceClassesDao.retrieveAdmServiceClassesTable());

        migrationModelList.add(serviceClassAccDao.retrieveServiceClassesAccTable());

        migrationModelList.add(serviceClassDAccDao.retrieveServiceClassesDATable());

        migrationModelList.add(serviceClassSOPlanDescDao.retrieveServiceClassSOPlanDescTable());

        migrationResponse.setServiceClassesMigrationList(migrationModelList);

        return migrationResponse;

    }

    //      public HashMap<Integer,List<AdmServiceClassResponse>> getProfilesServiceClasses() throws LookupException {
//        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all profiles service classes");
//        HashMap<Integer,List<AdmServiceClassResponse>> profilesServiceClasses = serviceClassesDao.retrieveProfilesServiceClasses();
//        if (profilesServiceClasses == null || profilesServiceClasses.isEmpty()) {
//            CCATLogger.DEBUG_LOGGER.error("No profiles service classes were found.");
//            throw new LookupException(ErrorCodes.ERROR.SERVICE_NOT_FOUND, Defines.SEVERITY.ERROR);
//        }
//        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all profiles service classes with size[" + profilesServiceClasses.size() + "].");
//        return profilesServiceClasses;
//    }
    @Transactional(rollbackFor = Exception.class)
    public ImportServiceClassesResponse importServiceClasses(List<MigrationModel> migrationModels) throws LookupException {

        CCATLogger.DEBUG_LOGGER.info("Started importing service classes");
        HashSet<Integer> existingCodes = null;
        ArrayList<ServiceClassesMigrationSummary> summary = new ArrayList<>();
        for (MigrationModel migrationModel : migrationModels) {
            if (migrationModel.getTableName().equals(DatabaseStructs.ADM_SERVICE_CLASSES.TABLE_NAME)) {
                CCATLogger.DEBUG_LOGGER.debug("Start merging service classes");
                existingCodes = serviceClassesDao.mergeServiceClasses(migrationModel, summary);
                break;
            }
        }
        if (existingCodes == null) {
            CCATLogger.DEBUG_LOGGER.error("Import service classes failed, No retrieved ids");
            throw new LookupException(ErrorCodes.ERROR.IMPORT_FAILED); // failed to import service classes;
        }

        for (MigrationModel migrationModel : migrationModels) {
            if (migrationModel.getTableName().equals(DatabaseStructs.ADM_SERVICE_CLASS_DA.TABLE_NAME)) {
                CCATLogger.DEBUG_LOGGER.debug("Start merging service classes DA");
                serviceClassDAccDao.mergeServiceClassesDA(migrationModel, existingCodes, summary);
            } else if (migrationModel.getTableName().equals(DatabaseStructs.ADM_SERVICE_CLASS_ACC.TABLE_NAME)) {
                CCATLogger.DEBUG_LOGGER.debug("Start merging service classes accumlators");
                serviceClassAccDao.mergeServiceClassesAcc(migrationModel, existingCodes, summary);
            } else if (migrationModel.getTableName().equals(DatabaseStructs.ADM_SO_SC_DESCRIPTION.TABLE_NAME)) {
                CCATLogger.DEBUG_LOGGER.debug("Start merging service classes accumlators");
                serviceClassSOPlanDescDao.mergeSCSODescriptions(migrationModel, existingCodes, summary);
            }
        }
        CCATLogger.DEBUG_LOGGER.info("Finished importing service classes successfully");
        return new ImportServiceClassesResponse(summary);
    }

    public Map<Integer,List<Integer>> retrieveServiceClassMigrations() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start retrieving service class migrations");
        return serviceClassesDao.retrieveServiceClassMigrations();
    }
}
