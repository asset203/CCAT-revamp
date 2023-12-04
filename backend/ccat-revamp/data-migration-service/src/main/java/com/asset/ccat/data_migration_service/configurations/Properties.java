package com.asset.ccat.data_migration_service.configurations;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
@ConfigurationProperties
public class Properties {

    private String tablesNames;
    private String writeTablesPath;
    private String readTablesPath;
    private String admSystemPropertiesProfile;
    private String admSystemPropertiesLabel;
    private String admSystemPropertiesCodes;
    private Integer engineMode;

    public String getTablesNames() {
        return tablesNames;
    }

    public void setTablesNames(String tablesNames) {
        this.tablesNames = tablesNames;
    }

    public String getWriteTablesPath() {
        return writeTablesPath;
    }

    public void setWriteTablesPath(String writeTablesPath) {
        this.writeTablesPath = writeTablesPath;
    }

    public String getReadTablesPath() {
        return readTablesPath;
    }

    public void setReadTablesPath(String readTablesPath) {
        this.readTablesPath = readTablesPath;
    }

    public String getAdmSystemPropertiesProfile() {
        return admSystemPropertiesProfile;
    }

    public void setAdmSystemPropertiesProfile(String admSystemPropertiesProfile) {
        this.admSystemPropertiesProfile = admSystemPropertiesProfile;
    }

    public String getAdmSystemPropertiesLabel() {
        return admSystemPropertiesLabel;
    }

    public void setAdmSystemPropertiesLabel(String admSystemPropertiesLabel) {
        this.admSystemPropertiesLabel = admSystemPropertiesLabel;
    }

    public Integer getEngineMode() {
        return engineMode;
    }

    public void setEngineMode(Integer engineMode) {
        this.engineMode = engineMode;
    }

    public String getAdmSystemPropertiesCodes() {
        return admSystemPropertiesCodes;
    }

    public void setAdmSystemPropertiesCodes(String admSystemPropertiesCodes) {
        this.admSystemPropertiesCodes = admSystemPropertiesCodes;
    }
}

