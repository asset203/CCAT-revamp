package com.asset.ccat.balance_dispute_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication(scanBasePackages = "com.asset")
@PropertySource("classpath:Messages.properties")
@EnableScheduling
@RefreshScope
public class BalanceDisputeServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(BalanceDisputeServiceApplication.class, args);
	}

}
