package com.asset.ccat.ci_service.proxy;

import com.asset.ccat.ci_service.configurations.Properties;
import com.asset.ccat.ci_service.defines.ErrorCodes;
import com.asset.ccat.ci_service.exceptions.CIServiceException;
import com.asset.ccat.ci_service.logger.CCATLogger;
import com.asset.ccat.ci_service.models.shared.MainProductModel;

import java.time.Duration;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientException;
import reactor.core.publisher.Mono;

/**
 * @author marwa.elshawarby
 */
//TO-DO change name
@Component
public class MainProductProxy {

    @Autowired
    WebClient webClient;

    @Autowired
    Properties properties;

    public List<MainProductModel> getMainProducts(String relatedPartyMsisdn) throws CIServiceException {
        CCATLogger.DEBUG_LOGGER.info("Calling charging system to get main products for [" + relatedPartyMsisdn + "]");
        List<MainProductModel> mainProducts = null;
        long executionTime;
        long start = 0;
        try {
            start = System.currentTimeMillis();
            String uri = properties.getMainProductsFetchUrl().replace("$msisdn$", relatedPartyMsisdn);
            Mono<List<MainProductModel>> responseAsync = webClient.get()
                    .uri(uri)
                    .header("x-api-key", properties.getMainProductsXApiKey())
                    .header(HttpHeaders.CONTENT_TYPE, "application/json")
                    .accept(MediaType.APPLICATION_JSON)
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<List<MainProductModel>>() {
                    }).log();
            // call external system
            mainProducts = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));

            if (mainProducts == null || mainProducts.isEmpty()) {
                //throw Exception if no products returned
                throw new CIServiceException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            executionTime = System.currentTimeMillis() - start;
            CCATLogger.INTERFACE_LOGGER.debug("Response: " + mainProducts);
            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
            CCATLogger.DEBUG_LOGGER.info("Call to charging system finished successfully");
            return mainProducts;
        } catch (CIServiceException ex) {
            throw ex;
        } catch (WebClientException ex) {
            CCATLogger.DEBUG_LOGGER.error("Webclient exception occered while getting main products");
            CCATLogger.ERROR_LOGGER.error("Webclient exception occered while getting main products", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNREACHABLE_EXTERNAL_SERVICE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error occured while getting main products");
            CCATLogger.ERROR_LOGGER.error("Unknown error occured while getting main products", ex);
            throw new CIServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

}
