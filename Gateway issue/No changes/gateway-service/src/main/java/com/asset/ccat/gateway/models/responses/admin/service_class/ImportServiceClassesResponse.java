package com.asset.ccat.gateway.models.responses.admin.service_class;

import com.asset.ccat.gateway.models.shared.ServiceClassesMigrationSummary;

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
