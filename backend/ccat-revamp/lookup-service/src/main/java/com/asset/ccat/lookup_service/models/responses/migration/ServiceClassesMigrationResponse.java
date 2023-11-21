package com.asset.ccat.lookup_service.models.responses.migration;

import com.asset.ccat.lookup_service.models.migration.MigrationModel;

import java.util.List;

/**
 * @author assem.hassan
 */
public class ServiceClassesMigrationResponse {

    private List<MigrationModel> serviceClassesMigrationList;


    public ServiceClassesMigrationResponse() {
    }

    public List<MigrationModel> getServiceClassesMigrationList() {
        return serviceClassesMigrationList;
    }

    public void setServiceClassesMigrationList(List<MigrationModel> serviceClassesMigrationList) {
        this.serviceClassesMigrationList = serviceClassesMigrationList;
    }
}
