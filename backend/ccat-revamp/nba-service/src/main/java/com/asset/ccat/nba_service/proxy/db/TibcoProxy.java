package com.asset.ccat.nba_service.proxy.db;

import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines.HTTP_HEADERS;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.requests.tibco.RedeemTibcoGiftRequest;
import com.asset.ccat.nba_service.models.requests.tibco.SendTibcoSMSRequest;
import com.asset.ccat.nba_service.models.responses.GetAllTibcoGiftsResponse;
import java.nio.charset.StandardCharsets;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Repository
public class TibcoProxy {

  private final WebClient webClient;

  private final Properties properties;

  public TibcoProxy(WebClient webClient, Properties properties) {
    this.webClient = webClient;
    this.properties = properties;
  }

  public GetAllTibcoGiftsResponse getAllTibcoGifts(String url) throws NBAException {
    try {
      CCATLogger.DEBUG_LOGGER.debug("Start getAllTibcoGifts Get All Tibco Gifts URL : {}",  url);

      Mono<GetAllTibcoGiftsResponse> responseAsync = webClient.get()
          .uri(url)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .header(HttpHeaders.ACCEPT_LANGUAGE, properties.getTibcoAcceptLanguageHeader())
          .header(HTTP_HEADERS.X_SOURCE_SYSTEM, properties.getTibcoApplicationUserHeader())
          .header(HTTP_HEADERS.X_SOURCE_IDENTITY_TOKEN,
              properties.getTibcoApplicationPasswordHeader())
          .accept(MediaType.APPLICATION_JSON)
          .acceptCharset(StandardCharsets.UTF_8)
          .exchangeToMono(clientResponse -> {
            CCATLogger.DEBUG_LOGGER.debug("GetAllTibcoGifts Response Headers: {}", clientResponse.headers().asHttpHeaders().toSingleValueMap());
            return clientResponse.bodyToMono(GetAllTibcoGiftsResponse.class);
          }).doOnError(error -> Mono.error(
              new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable"))).log();
      GetAllTibcoGiftsResponse response = responseAsync.block();
      CCATLogger.DEBUG_LOGGER.debug("GetAllTibcoGifts Response = {}", response);

      return response;
    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while get All Tibco Gifts. ", ex);
      CCATLogger.ERROR_LOGGER.error("Error while getting All Tibco Gifts ", ex);
      throw new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable");
    }
  }

  public void redeemGift(RedeemTibcoGiftRequest redeemTibcoGiftRequest) throws NBAException {
    String uri = properties.getTibcoRedeemOfferUrl();
    try {
      CCATLogger.DEBUG_LOGGER.debug("Start redeeming Tibco Gifts URL : {} and request = {}",  uri, redeemTibcoGiftRequest);

      webClient.post()
          .uri(uri)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .header(HttpHeaders.ACCEPT_LANGUAGE, properties.getTibcoAcceptLanguageHeader())
          .header(HTTP_HEADERS.X_SOURCE_SYSTEM, properties.getTibcoApplicationUserHeader())
          .header(HTTP_HEADERS.X_SOURCE_IDENTITY_TOKEN,
              properties.getTibcoApplicationPasswordHeader())
          .accept(MediaType.APPLICATION_JSON)
          .acceptCharset(StandardCharsets.UTF_8)
          .body(BodyInserters.fromValue(redeemTibcoGiftRequest))
          .exchangeToMono(clientResponse -> {
            CCATLogger.INTERFACE_LOGGER.info(
                "RedeemGift Response Headers: {}",  clientResponse.headers().asHttpHeaders()
                    .toSingleValueMap());
            return clientResponse.bodyToMono(Void.class);
          }).doOnError(error -> Mono.error(
              new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable"))).log();

    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while Redeeming Tibco Gift {}", ex.getMessage());
      CCATLogger.ERROR_LOGGER.error("Error while Redeeming Tibco Gift ", ex);
      throw new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable");
    }
  }


  public void sendSMSGift(SendTibcoSMSRequest sendTibcoSMSRequest) throws NBAException {
    String uri = properties.getTibcoSendSmsUrl();
    try {
      CCATLogger.DEBUG_LOGGER.debug("Start Sending Tibco SMS SMS URL : {} --- with request = {}",  uri, sendTibcoSMSRequest);
      webClient.post()
          .uri(uri)
          .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
          .header(HttpHeaders.ACCEPT_LANGUAGE, properties.getTibcoAcceptLanguageHeader())
          .header(HTTP_HEADERS.X_SOURCE_SYSTEM, properties.getTibcoApplicationUserHeader())
          .header(HTTP_HEADERS.X_SOURCE_IDENTITY_TOKEN,
              properties.getTibcoApplicationPasswordHeader())
          .accept(MediaType.APPLICATION_JSON)

          .acceptCharset(StandardCharsets.UTF_8)
          .body(BodyInserters.fromValue(sendTibcoSMSRequest))
          .exchangeToMono(clientResponse -> {
            CCATLogger.INTERFACE_LOGGER.info(
                "SendSMSGift Response Headers: {}", clientResponse.headers().asHttpHeaders()
                    .toSingleValueMap());
            return clientResponse.bodyToMono(Void.class);
          }).doOnError(error -> Mono.error(
              new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable"))).log();
      CCATLogger.DEBUG_LOGGER.info("Sending Tibco SMS Gift Ended Successfully ");

    } catch (RuntimeException ex) {
      CCATLogger.DEBUG_LOGGER.error("Error while Sending Tibco SMS Gift {}",  ex.getMessage());
      CCATLogger.ERROR_LOGGER.error("Error while Sending Tibco SMS Gift ", ex);
      throw new NBAException(ErrorCodes.ERROR.TIBCO_NBA_UNREACHABLE, "Tibco Unreachable");
    }
  }
}
