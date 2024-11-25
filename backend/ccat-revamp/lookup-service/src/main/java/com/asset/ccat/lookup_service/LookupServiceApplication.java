package com.asset.ccat.lookup_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(scanBasePackages = "com.asset")
@PropertySource("classpath:Messages.properties")
@RefreshScope
public class LookupServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LookupServiceApplication.class, args);
    }

}
