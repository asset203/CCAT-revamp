package com.asset.ccat.gateway.proxy;

import java.time.Duration;

import com.asset.ccat.gateway.models.responses.customer_care.GetCommunitiesResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.asset.ccat.gateway.annotation.LogExecutionTime;
import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.CommunitiesRequest;
import com.asset.ccat.gateway.models.requests.GetCommunitiesRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;

import reactor.core.publisher.Mono;

@Component
public class CommunitiesProxy {

	@Autowired
	Properties properties;

	@Autowired
	WebClient webClient;

	@LogExecutionTime
	public void updateCommunities(CommunitiesRequest request) throws GatewayException {
		BaseResponse response = null;
		try {
			CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
			Mono<BaseResponse> responseAsync = webClient.post()
					.uri(properties.getAirServiceUrls() + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
							+ Defines.AIR_SERVICE.COMMUNITIES
							+ Defines.WEB_ACTIONS.UPDATE)
					.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
					.body(BodyInserters.fromValue(request)).retrieve()
					.bodyToMono(new ParameterizedTypeReference<BaseResponse>() {
					}).log();
			response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
			if (!response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
				CCATLogger.DEBUG_LOGGER.info("Error while performing updateCommunities " + response);
				CCATLogger.DEBUG_LOGGER.error("Error while performing updateCommunities " + response);
				throw new GatewayException(response.getStatusCode(), response.getStatusMessage());
			}
			CCATLogger.INTERFACE_LOGGER.info("response is [" + response + "]");
		} catch (RuntimeException ex) {
			CCATLogger.DEBUG_LOGGER.info("Error while performing updateCommunities ");
			CCATLogger.ERROR_LOGGER.error("Error while performing updateCommunities ", ex);
			throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null,
					"air-service[" + properties.getAirServiceUrls() + "]");
		}
	}
	
	@LogExecutionTime
	public GetCommunitiesResponse getCommunities(GetCommunitiesRequest request) throws GatewayException {
		BaseResponse<GetCommunitiesResponse> response = null;
		try {
			CCATLogger.INTERFACE_LOGGER.info("request is [" + request + "]");
			Mono<BaseResponse<GetCommunitiesResponse>> responseAsync = webClient.post()
                    .uri(properties.getAirServiceUrls() + Defines.ContextPaths.AIR_SERVICE_CONTEXT_PATH
							+ Defines.AIR_SERVICE.COMMUNITIES
                            + Defines.WEB_ACTIONS.GET)
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                    .body(BodyInserters.fromValue(request))
                    .retrieve()
                    .bodyToMono(new ParameterizedTypeReference<BaseResponse<GetCommunitiesResponse>>() {
                    }).log();
			 response = responseAsync.block(Duration.ofMillis(properties.getResponseTimeout()));
	            if (response != null) {
	                if (response.getStatusCode() != ErrorCodes.SUCCESS.SUCCESS) {
	                    CCATLogger.DEBUG_LOGGER.info("Error while getting communities list " + response);
	                    CCATLogger.DEBUG_LOGGER.error("Error while getting communities list" + response);
	                    throw new GatewayException(response.getStatusCode(), response.getStatusMessage(), null);
	                }
	            }
	            CCATLogger.INTERFACE_LOGGER.info("response is [" + "statusMessage: " + response.getStatusMessage()
	                    + ", statusCode: " + response.getStatusCode()
	                    + ", payload: " + response.getPayload()
	                    + "]");
		} catch (RuntimeException ex) {
			CCATLogger.DEBUG_LOGGER.info("Error while performing getCommunities ");
			CCATLogger.ERROR_LOGGER.error("Error while performing getCommunities ", ex);
			throw new GatewayException(ErrorCodes.ERROR.INTERNAL_SERVICE_UNREACHABLE, null,
					"air-service[" + properties.getAirServiceUrls() + "]");
		}
		CCATLogger.DEBUG_LOGGER.debug("Ended CommunitiesProxy - getCommunities()");
		return response.getPayload();
		
	}
}
