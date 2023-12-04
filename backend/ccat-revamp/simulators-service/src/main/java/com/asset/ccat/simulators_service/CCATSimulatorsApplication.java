package com.asset.ccat.simulators_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.asset")
@EnableAutoConfiguration
public class CCATSimulatorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CCATSimulatorsApplication.class, args);
    }

}
