package com.asset.ccat.lookup_service.models.responses;

import com.asset.ccat.lookup_service.models.migration.ServiceClassesMigrationSummary;

import java.util.List;

public class ImportServiceClassesResponse {
    private List<ServiceClassesMigrationSummary> summary;

    public ImportServiceClassesResponse() {
    }

    public ImportServiceClassesResponse(List<ServiceClassesMigrationSummary> summary) {
        this.summary = summary;
    }

    public List<ServiceClassesMigrationSummary> getSummary() {
        return summary;
    }

    public void setSummary(List<ServiceClassesMigrationSummary> summary) {
        this.summary = summary;
    }
}
