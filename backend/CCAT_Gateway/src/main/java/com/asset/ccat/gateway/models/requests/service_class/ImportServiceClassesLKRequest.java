package com.asset.ccat.gateway.models.requests.service_class;

import com.asset.ccat.gateway.models.admin.MigrationModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

public class ImportServiceClassesLKRequest extends BaseRequest {
    private List<MigrationModel> migrationModels;

    public List<MigrationModel> getMigrationModels() {
        return migrationModels;
    }

    public void setMigrationModels(List<MigrationModel> migrationModels) {
        this.migrationModels = migrationModels;
    }
}
