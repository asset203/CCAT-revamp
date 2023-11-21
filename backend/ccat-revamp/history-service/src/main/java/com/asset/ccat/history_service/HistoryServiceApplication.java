package com.asset.ccat.history_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author wael.mohamed
 */
@SpringBootApplication(scanBasePackages = "com.asset")
@PropertySource("classpath:Messages.properties")
@RefreshScope
@EnableScheduling
public class HistoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HistoryServiceApplication.class, args);
    }

}
