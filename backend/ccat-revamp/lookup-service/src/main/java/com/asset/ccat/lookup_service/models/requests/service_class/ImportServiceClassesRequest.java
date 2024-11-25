package com.asset.ccat.lookup_service.models.requests.service_class;

import com.asset.ccat.lookup_service.models.migration.MigrationModel;
import com.asset.ccat.lookup_service.models.requests.BaseRequest;


import java.util.List;

public class ImportServiceClassesRequest extends BaseRequest {
    List<MigrationModel> migrationModels;

    public List<MigrationModel> getMigrationModels() {
        return migrationModels;
    }

    public void setMigrationModels(List<MigrationModel> migrationModels) {
        this.migrationModels = migrationModels;
    }
}
