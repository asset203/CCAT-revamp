package com.asset.ccat.balancedisputemapperservice.proxy;

import com.asset.ccat.balancedisputemapperservice.annotation.LogExecutionTime;
import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.Defines;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import com.asset.ccat.balancedisputemapperservice.models.LkBalanceDisputeDetailsConfigModel;
import com.asset.ccat.balancedisputemapperservice.models.ServiceClassModel;
import com.asset.ccat.balancedisputemapperservice.models.response.BaseResponse;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author Assem.Hassan
 */
@Component
public class LookupProxy {

  WebClient webClient;
  Properties properties;

  @Autowired
  public LookupProxy(WebClient webClient, Properties properties) {
    this.webClient = webClient;
    this.properties = properties;
  }

  @LogExecutionTime
  public HashMap<String, String> getRegions() throws BalanceDisputeException {
    HashMap<String, String> regions = null;
    try {
      String URI = properties.getLookupsServiceUrls()
          + Defines.ContextPaths.LOOKUPS
          + Defines.ContextPaths.CACHED_LOOKUPS
          + Defines.ContextPaths.REGIONS;
      CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
      Mono<BaseResponse<HashMap<String, String>>> responseAsync = webClient.get()
          .uri(URI)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse<HashMap<String, String>>>() {
          }).log();
      BaseResponse<HashMap<String, String>> response = responseAsync.block();
      if (Objects.nonNull(response)) {
        if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
          regions = response.getPayload();
        } else {
          CCATLogger.DEBUG_LOGGER.info(
              "Error Or No Data Found while retrieving Regions Lookup " + response);
          return null;
        }
      }
      CCATLogger.INTERFACE_LOGGER.debug("Response: " + regions);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving Regions ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving Regions ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE,
          null, "BD-Mapper-Proxy[" + properties.getLookupsServiceUrls() + "]");
    }
    return regions;
  }

  @LogExecutionTime
  public LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> getBDDetailsConfiguration(
      Integer profileId)
      throws BalanceDisputeException {
//    GetDetailsConfigMapRequest request = new GetDetailsConfigMapRequest();
//    request.setProfileId(profileId);
    LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> payload = null;
    try {
      String URI = properties.getLookupsServiceUrls()
          + Defines.ContextPaths.LOOKUPS
          + Defines.ContextPaths.CACHED_LOOKUPS
          + Defines.ContextPaths.BD_DETAILS_CONFIG;
      CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
      Mono<BaseResponse<LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel>>> responseAsync =
          webClient.post()
              .uri(URI)
              .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
              .body(BodyInserters.fromValue(profileId))
              .retrieve()
              .bodyToMono(
                  new ParameterizedTypeReference<BaseResponse<LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel>>>() {
                  }).log();
      BaseResponse<LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel>> response = responseAsync.block();
      if (Objects.nonNull(response)) {
        if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
          payload = response.getPayload();
        } else {
          CCATLogger.DEBUG_LOGGER.info(
              "Error while retrieving BalanceDispute Details Config Lookup " + response);
          throw new BalanceDisputeException(ErrorCodes.ERROR.NO_DATA_FOUND,
              Defines.SEVERITY.ERROR, response.getStatusMessage());
        }
      }
      CCATLogger.INTERFACE_LOGGER.debug("Response: " + payload);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while retrieving BalanceDispute Details Config from LK ");
      CCATLogger.ERROR_LOGGER.error("Error while retrieving BalanceDispute Details Config from LK ",
          ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE,
          null, "BD-Mapper-Proxy[" + properties.getLookupsServiceUrls() + "]");
    }
    return payload;
  }

  @LogExecutionTime
  public List<ServiceClassModel> getAllServiceClasses() throws BalanceDisputeException {
    List<ServiceClassModel> serviceClassList = null;
    try {
      String URI = properties.getLookupsServiceUrls()
          + Defines.ContextPaths.LOOKUPS
          + Defines.ContextPaths.CACHED_LOOKUPS
          + Defines.ContextPaths.SERVICE_CLASSES;
      CCATLogger.DEBUG_LOGGER.info("Calling Lookups With URI : " + URI);
      Mono<BaseResponse<List<ServiceClassModel>>> responseAsync = webClient.get()
          .uri(URI)
          .accept(MediaType.APPLICATION_JSON)
          .retrieve()
          .bodyToMono(new ParameterizedTypeReference<BaseResponse<List<ServiceClassModel>>>() {
          }).log();
      BaseResponse<List<ServiceClassModel>> response = responseAsync.block();
      if (Objects.nonNull(response)) {
        if (response.getStatusCode().equals(ErrorCodes.SUCCESS.SUCCESS)) {
          serviceClassList = response.getPayload();
        } else {
          CCATLogger.DEBUG_LOGGER.info(
              "Error while Retrieving All Service Classes from LK " + response);
          throw new BalanceDisputeException(ErrorCodes.ERROR.NO_DATA_FOUND,
              Defines.SEVERITY.ERROR, response.getStatusMessage());
        }
      }
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.info("Error while Retrieving All Service Classes from LK ");
      CCATLogger.ERROR_LOGGER.error("Error while Retrieving All Service Classes from LK ", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.LOOKUP_SERVER_UNREACHABLE,
          null, "BD-Mapper-Proxy[" + properties.getLookupsServiceUrls() + "]");
    }
    return serviceClassList;
  }
}