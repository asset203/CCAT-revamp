package com.asset.ccat.nba_service.services;

import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines.NBA_DEFINES;
import com.asset.ccat.nba_service.defines.Defines.NBA_PLACEHOLDERS;
import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.models.requests.AcceptGiftRequest;
import com.asset.ccat.nba_service.models.requests.GiftModel;
import com.asset.ccat.nba_service.models.requests.SendGiftRequest;
import com.asset.ccat.nba_service.models.requests.SubscriberRequest;
import com.asset.ccat.nba_service.models.requests.tibco.RedeemTibcoGiftRequest;
import com.asset.ccat.nba_service.models.requests.tibco.SendTibcoSMSRequest;
import com.asset.ccat.nba_service.models.responses.GetAllTibcoGiftsResponse;
import com.asset.ccat.nba_service.proxy.db.TibcoProxy;
import com.asset.ccat.nba_service.utils.TibcoParser;
import com.asset.ccat.nba_service.utils.PlaceholderBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

/**
 * @author Ihab Tawffiq
 */
@Service
public class TibcoNbaGiftsService {


  private final Properties properties;
  private final TibcoProxy tibcoProxy;
  private final ResourceLoader resourceLoader;


  public TibcoNbaGiftsService(Properties properties, TibcoProxy tibcoProxy,
      ResourceLoader resourceLoader) {
    this.properties = properties;
    this.tibcoProxy = tibcoProxy;
    this.resourceLoader = resourceLoader;
  }

  public List<GiftModel> getAllTibcoGifts(SubscriberRequest subscriberRequest) throws NBAException {
    CCATLogger.DEBUG_LOGGER.info("Get All Tibco Offers Tibco Gift Started ");

    String tibcoUrl = new PlaceholderBuilder()
            .addPlaceholder(NBA_PLACEHOLDERS.TRIGGER_ID, properties.getTibcoGetOffersTriggerId())
            .addPlaceholder(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, subscriberRequest.getMsisdn())
            .addPlaceholder(NBA_PLACEHOLDERS.AGENT_ID, subscriberRequest.getUsername())
            .addPlaceholder(NBA_PLACEHOLDERS.CHANNEL_ID, properties.getTibcoGetOffersChannelId())
            .buildUrl(properties.getTibcoGetOffersUrl());

    GetAllTibcoGiftsResponse response = tibcoProxy.getAllTibcoGifts(tibcoUrl);
    return TibcoParser.parseAssignGiftResponse(response);
  }

  public void redeemOffer(AcceptGiftRequest acceptGiftRequest) throws IOException, NBAException {
    if(acceptGiftRequest.getId() == null)
      acceptGiftRequest.setId("");

    String tibcoRequestBody = readRequestTemplate(NBA_DEFINES.REDEEM_OFFER_CLASSPATH);

    tibcoRequestBody = new PlaceholderBuilder()
            .addPlaceholder(NBA_PLACEHOLDERS.CATEGORY, properties.getTibcoRedeemGiftCategory())
            .addPlaceholder(NBA_PLACEHOLDERS.CHANNEL_ID, properties.getTibcoRedeemGiftChannelId())
            .addPlaceholder(NBA_PLACEHOLDERS.PROMO_ID, properties.getPromoId())

            .addPlaceholder(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, acceptGiftRequest.getMsisdn())
            .addPlaceholder(NBA_PLACEHOLDERS.SHORT_CODE, acceptGiftRequest.getGiftShortCode())
            .addPlaceholder(NBA_PLACEHOLDERS.AGENT_ID, acceptGiftRequest.getUsername())
            .addPlaceholder(NBA_PLACEHOLDERS.W_LIST,
                    Objects.nonNull(acceptGiftRequest.getWlist()) && !acceptGiftRequest.getWlist().isBlank()
                            ? ",{\"value\":\"" + acceptGiftRequest.getWlist() + "\",\"listHierarchyId\":\"wlistId\"}"
                            : "")
            .buildUrl(tibcoRequestBody);
    CCATLogger.DEBUG_LOGGER.debug("Request Body after replacements: {}", tibcoRequestBody);
    ObjectMapper mapper = new ObjectMapper();
    RedeemTibcoGiftRequest redeemTibcoGiftRequest = mapper.readValue(tibcoRequestBody, RedeemTibcoGiftRequest.class);
//    String jsonString = mapper.writeValueAsString(redeemTibcoGiftRequest);
    tibcoProxy.redeemGift(redeemTibcoGiftRequest);
  }

  public void sendSMSGift(SendGiftRequest sendGiftRequest) throws IOException, NBAException {
    String tibcoRequestBody = readRequestTemplate(NBA_DEFINES.SEND_SMS_CLASSPATH);

    tibcoRequestBody = new PlaceholderBuilder()
            .addPlaceholder(NBA_PLACEHOLDERS.PROMO_ID, properties.getPromoId())
            .addPlaceholder(NBA_PLACEHOLDERS.CATEGORY, properties.getTibcoSendSmsCategory())
            .addPlaceholder(NBA_PLACEHOLDERS.CHANNEL_ID, properties.getTibcoSendSmsChannelId())
            .addPlaceholder(NBA_PLACEHOLDERS.SHORT_CODE, properties.getTibcoSendSmsShortCode())

            .addPlaceholder(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, sendGiftRequest.getMsisdn())
            .addPlaceholder(NBA_PLACEHOLDERS.AGENT_ID, sendGiftRequest.getUsername())
            .addPlaceholder(NBA_PLACEHOLDERS.GIFT_SEQ_ID, sendGiftRequest.getGiftSeqId())
            .addPlaceholder(NBA_PLACEHOLDERS.W_LIST,
                    Objects.nonNull(sendGiftRequest.getWlist())
                            ? ",{\"value\":\"" + sendGiftRequest.getWlist() + "\",\"listHierarchyId\":\"wlistId\"}"
                            : "")
            .buildUrl(tibcoRequestBody);
    CCATLogger.DEBUG_LOGGER.debug("Request Body after replacements: {}", tibcoRequestBody);
    ObjectMapper mapper = new ObjectMapper();
    SendTibcoSMSRequest sendTibcoSMSRequest = mapper.readValue(tibcoRequestBody, SendTibcoSMSRequest.class);

//    String jsonString = mapper.writeValueAsString(sendTibcoSMSRequest);
    tibcoProxy.sendSMSGift(sendTibcoSMSRequest);
  }

  private String readRequestTemplate(String fileClassPath) throws NBAException {
    try {
      CCATLogger.DEBUG_LOGGER.debug("Reading [{}]", fileClassPath);
      Resource sendSmsGiftRequestTemplate = resourceLoader.getResource(fileClassPath);
      InputStream inputStream = sendSmsGiftRequestTemplate.getInputStream();
      return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Exception occurred while reading the file. ", ex);
      CCATLogger.ERROR_LOGGER.error("Exception occurred while reading the file. ", ex);
      throw new NBAException(ErrorCodes.ERROR.SEND_SMS_TEMPLATE_NOT_FOUND, "");
    }
  }
}
