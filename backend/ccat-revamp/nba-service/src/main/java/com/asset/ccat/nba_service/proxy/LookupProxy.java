/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.proxy;

import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.responses.BaseResponse;
import com.asset.ccat.nba_service.models.shared.NBAServer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class LookupProxy {

    @Autowired
    WebClient webClient;
    @Autowired
    Properties properties;

    public List<NBAServer> getAirServer() throws NBAException {
        List<NBAServer> list = null;
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            CCATLogger.INTERFACE_LOGGER.debug("Requested getAirServer ");
            Mono<BaseResponse<NBAServer[]>> responseAsync = webClient.get()
                    .uri(properties.getLookupsServiceUrls()
                            + Defines.ContextPaths.LOOKUPS
                            + Defines.ContextPaths.NBA_SERVERS)
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<NBAServer[]>>() {
                    }).log();

            BaseResponse<NBAServer[]> response = responseAsync.block();
            if (response.getStatusCode() == ErrorCodes.SUCCESS.SUCCESS) {
                list = Arrays.stream(response.getPayload()).collect(Collectors.toList());
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Error while retrieving lookups server " + response);
                throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + list);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error while retrieving lookups server ");
            CCATLogger.ERROR_LOGGER.error("Error while retrieving lookups server ", ex);
            throw new NBAException(ErrorCodes.ERROR.NO_DATA_FOUND);
        }
        return list;
    }

}
