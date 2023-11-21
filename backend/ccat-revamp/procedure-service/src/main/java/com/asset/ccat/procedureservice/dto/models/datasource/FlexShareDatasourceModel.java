package com.asset.ccat.procedureservice.dto.models.datasource;

public class FlexShareDatasourceModel extends BasicDataSourceModel{
    public FlexShareDatasourceModel() {
    }
    public FlexShareDatasourceModel(String URL, String username, String password, String schema) {
        super(URL, username, password, schema);
    }
}
