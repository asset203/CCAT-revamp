package com.asset.ccat.air_service.proxy;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.shared.AIRServer;
import com.asset.ccat.air_service.models.shared.VoucherServerModel;
import com.asset.ccat.air_service.services.LookupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Objects;

/**
 * @author Mahmoud Shehab
 */
@Component
public class AIRProxy {
    private final WebClient webClient;
    private final LookupsService lookupsService;

    private final Properties properties;

    @Autowired
    public AIRProxy(WebClient webClient, LookupsService lookupsService, Properties properties) {
        this.webClient = webClient;
        this.lookupsService = lookupsService;
        this.properties = properties;
    }

    @LogExecutionTime
    public String sendAIRRequest(String request) throws AIRServiceException {
        try {
            AIRServer aIRServer = lookupsService.getAirServer();
            CCATLogger.DEBUG_LOGGER.info("Start calling the Air Server with URL=[{}]", aIRServer.getUrl());
            CCATLogger.INTERFACE_LOGGER.info("Air request is: {}", request );
            Mono<String> responseAsync = webClient.post()
                    .uri(aIRServer.getUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "text/xml")
                    .header(HttpHeaders.USER_AGENT, aIRServer.getAgentName())
                    .header(HttpHeaders.HOST, aIRServer.getHost())
                    .header(HttpHeaders.AUTHORIZATION, aIRServer.getAuthorization())
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(String.class);
            String response = responseAsync.block(Duration.ofMillis(properties.getAirTimeout()));
            CCATLogger.DEBUG_LOGGER.debug(" AIR response is:\n {}", response);
            if (Objects.isNull(response) || response.isEmpty())
                throw new AIRServiceException(ErrorCodes.ERROR.UNREACHABLE_AIR);
            return response;
        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.error("RuntimeException occurred while calling Air-server. ", ex);
            CCATLogger.ERROR_LOGGER.error("RuntimeException occurred while calling Air-server. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNREACHABLE_AIR);
        }
    }

    @LogExecutionTime
    public String sendVoucherRequest(String request, int serverIndex, int serialNumberLength) throws AIRServiceException {
        String response;
        VoucherServerModel voucherServer;
        try {
            voucherServer = lookupsService.getVoucherServer(serverIndex, serialNumberLength);
            CCATLogger.DEBUG_LOGGER.info("Start Calling Voucher Server URL [{}]", voucherServer.getUrl());
            CCATLogger.INTERFACE_LOGGER.info("request is [{}]",request);
            Mono<String> responseAsync = webClient.post()
                    .uri(voucherServer.getUrl())
                    .header(HttpHeaders.CONTENT_TYPE, "text/xml")
                    .header(HttpHeaders.USER_AGENT, voucherServer.getAgentName())
                    .header(HttpHeaders.HOST, voucherServer.getHost())
                    .header(HttpHeaders.AUTHORIZATION, voucherServer.getAuthorization())
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(String.class);
            response = responseAsync.block(Duration.ofMillis(properties.getAirTimeout()));
            CCATLogger.DEBUG_LOGGER.debug("Response is: {}", response);
            if (Objects.isNull(response) || response.isEmpty())
                throw new AIRServiceException(ErrorCodes.ERROR.VOUCHER_SERVER_UNREACHABLE);

        } catch (RuntimeException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling Voucher Server ", ex);
            CCATLogger.ERROR_LOGGER.error("Error while calling Voucher Server ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.VOUCHER_SERVER_UNREACHABLE);
        }
        return response;
    }
}
