/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.proxy.http;

import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class HttpProxy {

    @Autowired
    WebClient webClient;

    public String sendNBARequest(String url) throws NBAException {
        String response = "";
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.debug("Requested Paramter: " + url);
            Mono<String> responseAsync = webClient.get()
                    .uri(url)
                    .accept(MediaType.TEXT_PLAIN)
                    .retrieve()
                    .bodyToMono(String.class).log();
            response = responseAsync.block();
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error while calling NBA " + url);
            CCATLogger.ERROR_LOGGER.error("Error while calling NBA " + url, ex);
            throw new NBAException(ErrorCodes.ERROR.NBA_ERROR);
        }
        return response;
    }
}
