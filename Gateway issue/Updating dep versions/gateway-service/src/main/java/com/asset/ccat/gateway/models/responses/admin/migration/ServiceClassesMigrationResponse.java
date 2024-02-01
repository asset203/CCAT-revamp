package com.asset.ccat.gateway.models.responses.admin.migration;



import com.asset.ccat.gateway.models.admin.MigrationModel;

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
