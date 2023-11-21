//package com.asset.ccat.ci_service.proxy;
//
//import com.asset.ccat.ci_service.defines.ErrorCodes;
//import com.asset.ccat.ci_service.exceptions.CIServiceException;
//import com.asset.ccat.ci_service.logger.CCATLogger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.stereotype.Component;
//import org.springframework.web.reactive.function.client.WebClient;
//import reactor.core.publisher.Mono;
//
///**
// *
// * @author Mahmoud Shehab
// */
//@Component
//public class CIProxy {
//
//    @Autowired
//    WebClient webClient;
//
//    public String sendGetRequest(String request) throws CIServiceException {
//        String response = "";
//        long executionTime;
//        long start = 0;
//        try {
//            start = System.currentTimeMillis();
//            CCATLogger.DEBUG_LOGGER.info("Start call CI " + request);
//            CCATLogger.INTERFACE_LOGGER.debug("Requested Parameter: " + request);
//            Mono<String> responseAsync = webClient.get()
//                    .uri(request)
//                    .header(HttpHeaders.CONTENT_TYPE, "text/xml")
//                    .retrieve()
//                    .bodyToMono(String.class);
//            response = responseAsync.block();
//            executionTime = System.currentTimeMillis() - start;
//            CCATLogger.INTERFACE_LOGGER.debug("Response: " + response);
//            CCATLogger.INTERFACE_LOGGER.info("executed in " + executionTime + "ms");
//        } catch (Exception ex) {
//            CCATLogger.DEBUG_LOGGER.info("Error while calling CI " + request);
//            CCATLogger.ERROR_LOGGER.error("Error while calling CI " + request, ex);
//            throw new CIServiceException(ErrorCodes.ERROR.CI_UNREACHABLE);
//        }
//        return response;
//    }
//
//}
