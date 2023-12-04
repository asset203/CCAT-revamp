package com.asset.ccat.balance_dispute_service.dto.models.datasource;

public class BalanceDisputeDatasourceModel extends BasicDataSourceModel {


    public BalanceDisputeDatasourceModel() {
    }

    public BalanceDisputeDatasourceModel(String URL, String username, String password, String schema) {
        super(URL, username, password, schema);
    }

    @Override
    public String toString() {
        return "DataSourceModel{" +
                "URL='" + getURL() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", password='" + getPassword() + '\'' +
                ", schema='" + getSchema() + '\'' +
                '}';
    }
}
