package com.asset.ccat.gateway.models.responses.admin.migration;


import com.asset.ccat.gateway.models.admin.MigrationModel;

import java.io.File;
import java.util.List;

/**
 * @author assem.hassan
 */
public class AdmServiceClassExportResponse {

    private File file;


    public AdmServiceClassExportResponse() {
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
