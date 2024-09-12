package com.asset.ccat.air_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Mahmoud Shehab
 */
@Configuration
public class WebClientConfig {

//    @Bean
//    public WebClient webClient() {
//        return WebClient.create();
//    }

    @Bean
    public WebClient webClient() {
        // Increase the buffer size to handle larger responses
        ExchangeStrategies strategies = ExchangeStrategies.builder()
                .codecs(conf -> conf
                        .defaultCodecs()
                        .maxInMemorySize(10 * 1024 * 1024)) // Set to 10 MB
                .build();

        return WebClient.builder()
                .exchangeStrategies(strategies)
                .build();
    }
}
