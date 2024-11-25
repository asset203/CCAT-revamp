package com.asset.ccat.data_migration_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.asset")
public class DataMigrationServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DataMigrationServiceApplication.class, args);
    }

}
