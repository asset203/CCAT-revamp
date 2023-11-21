package com.asset.ccat.ci_service.models.responses;

/**
 *
 * @author wael.mohamed
 */
public class MigrateResponse {

    private boolean isAllowedMigration;
    private String responseCode;

    public MigrateResponse() {
    }

    public MigrateResponse(boolean isAllowedMigration) {
        this.isAllowedMigration = isAllowedMigration;
    }

    public MigrateResponse(String responseCode) {
        this.responseCode = responseCode;
    }

    public boolean isIsAllowedMigration() {
        return isAllowedMigration;
    }

    public void setIsAllowedMigration(boolean isAllowedMigration) {
        this.isAllowedMigration = isAllowedMigration;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

}
