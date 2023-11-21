package com.asset.ccat.ci_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author nour.ihab
 */
@SpringBootApplication(scanBasePackages = "com.asset")
@PropertySource("classpath:Messages.properties")
@RefreshScope
@EnableScheduling
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

}
