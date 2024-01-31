package com.asset.ccat.gateway.proxy;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
public class ServiceRefreshProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    @LogExecutionTime
    public void refreshCIService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing ci-service.");
        List<String> list = null;
        try {
            Mono<List<String>> responseAsync = webClient
                    .post()
                    .uri(properties.getCiServiceUrls()
                            + Defines.CI_SERVICE.CONTEXT_PATH
                            + "/actuator/busrefresh")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    }).log();

            List<String> response = responseAsync.block();
            if (response != null && !response.isEmpty()) {
                response.forEach(property -> CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + property + "]"));
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing ci-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [CI-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [CI-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ci-service");
        }
    }

    @LogExecutionTime
    public void refreshAirService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing air-service.");
        try {
            webClient
            .post()
            .uri(properties.getAirServiceUrls()
                    + Defines.AIR_SERVICE.CONTEXT_PATH
                    + "/actuator/busrefresh")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(String.class)
            .subscribe(response -> {
               CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + response + "]");
            });
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing air-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [air-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [air-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "air-service");
        }
    }

    @LogExecutionTime
    public void refreshHistoryService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing history-service.");
        List<String> list = null;
        try {
            Mono<List<String>> responseAsync = webClient
                    .post()
                    .uri(properties.getHistoryServiceUrls()
                            + Defines.ContextPaths.HISTORY_SERVICE_CONTEXT_PATH
                            + "/actuator/busrefresh")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    }).log();

            List<String> response = responseAsync.block();
            if (response != null && !response.isEmpty()) {
                response.forEach(property -> CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + property + "]"));
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing history-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [history-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [history-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "history-service");
        }
    }

    @LogExecutionTime
    public void refreshNbaService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing NBA-service.");
        List<String> list = null;
        try {
            Mono<List<String>> responseAsync = webClient
                    .post()
                    .uri(properties.getNbaServiceUrls()
                            + Defines.ContextPaths.NBA_SERVICE
                            + "/actuator/busrefresh")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    }).log();

            List<String> response = responseAsync.block();
            if (response != null && !response.isEmpty()) {
                response.forEach(property -> CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + property + "]"));
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing NBA-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [NBA-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [NBA-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "NBA-service");
        }
    }

    @LogExecutionTime
    public void refreshOdsService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing ODS-service.");
        List<String> list = null;
        try {
            Mono<List<String>> responseAsync = webClient
                    .post()
                    .uri(properties.getOdsServiceUrls()
                            + Defines.ODS_SERVICE.CONTEXT_PATH
                            + "/actuator/busrefresh")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    }).log();

            List<String> response = responseAsync.block();
            if (response != null && !response.isEmpty()) {
                response.forEach(property -> CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + property + "]"));
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing ODS-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [ODS-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [ODS-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "ODS-service");
        }
    }

    @LogExecutionTime
    public void refreshUserManagementService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing user-management-service.");
        List<String> list = null;
        try {

            String URI = properties.getUserManagementUrls()
                    + Defines.ContextPaths.USER_MANAGEMENT_CONTEXT_PATH
                    + "/actuator/busrefresh";

                    webClient
                    .post()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(String.class)
                    .subscribe(response -> {
                        CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + response + "]");
                    });
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing user-management-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [user-management-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [user-management-service]", ex);
        }
    }

    @LogExecutionTime
    public void refreshLookupService() throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing lookup-service.");
        List<String> list = null;
        try {
            Mono<List<String>> responseAsync = webClient
                    .post()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.LOOKUP_SERVICE.CONTEXT_PATH
                            + "/actuator/busrefresh")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<String>>() {
                    }).log();

            List<String> response = responseAsync.block();
            if (response != null && !response.isEmpty()) {
                response.forEach(property -> CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + property + "]"));
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing lookup-service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing [lookup-service]");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing [lookup-service]", ex);
            throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null, "lookup-service");
        }
    }

    @LogExecutionTime
    public void refreshService(String URI){
        CCATLogger.DEBUG_LOGGER.debug("Started refreshing service.");
        try {
            webClient
                    .post()
                    .uri(URI)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToFlux(String.class)
                    .subscribe(response -> {
                        CCATLogger.DEBUG_LOGGER.debug("Updated --> [" + response + "]");
                    });
            CCATLogger.DEBUG_LOGGER.debug("Finished refreshing service.");
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while refreshing service");
            CCATLogger.ERROR_LOGGER.error("Error while refreshing service With URL [ "+URI+"]", ex);
        }
    }

}
