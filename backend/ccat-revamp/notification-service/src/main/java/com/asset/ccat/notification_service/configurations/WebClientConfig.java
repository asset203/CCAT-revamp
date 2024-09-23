package com.asset.ccat.notification_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;


/**
 * @author Mahmoud Shehab
 */
@Configuration
public class WebClientConfig {

    @Bean
    WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public WebClient webClient() {
        return WebClient.create();
    }

//    @Bean
//    public WebClient webClient() {
//        ConnectionProvider connectionProvider = ConnectionProvider.builder("customPool")
//                .maxConnections(100)
//                .pendingAcquireMaxCount(50) // Max pending requests
//                .maxIdleTime(Duration.ofSeconds(20)) // Max idle time before closing the connection
//                .maxLifeTime(Duration.ofMinutes(5)) // Max lifetime of a connection
//                .build();
//
//        // Customize the event loop for handling I/O
//        LoopResources loopResources = LoopResources.create("customEventLoop", 4, true); // Customize the number of event loop threads
//
//        // Build the HttpClient with connection pooling and timeouts
//        HttpClient httpClient = HttpClient.create(connectionProvider)
//                .runOn(loopResources) // Use custom event loop
//                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000) // Connection timeout
//                .responseTimeout(Duration.ofSeconds(30)) // Response timeout
//                .doOnConnected(conn -> conn
//                        .addHandlerLast(new ReadTimeoutHandler(30, TimeUnit.SECONDS)) // Read timeout
//                        .addHandlerLast(new WriteTimeoutHandler(30, TimeUnit.SECONDS)) // Write timeout
//                );
//
//        // Build and return the WebClient
//        return WebClient.builder()
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .filter((request, next) -> {
//                    CCATLogger.INTERFACE_LOGGER.info("Making request to: {}",  request.url());
//                    return next.exchange(request);
//                })
//                .build();
//    }
}
