package com.asset.ccat.procedureservice.dto.models.datasource;

public class PaymentGatewayDatasourceModel extends BasicDataSourceModel {


    public PaymentGatewayDatasourceModel() {
    }

    public PaymentGatewayDatasourceModel(String URL, String username, String password, String schema) {
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
