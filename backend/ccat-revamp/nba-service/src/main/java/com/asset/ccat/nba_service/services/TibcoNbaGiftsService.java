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
    String tibcoUrl = properties.getTibcoGetOffersUrl();
    String triggerId = properties.getTibcoGetOffersTriggerId();
    String agentId = subscriberRequest.getUsername();

    tibcoUrl = tibcoUrl.replace(NBA_PLACEHOLDERS.TRIGGER_ID, triggerId)
        .replace(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, subscriberRequest.getMsisdn())
        .replace(NBA_PLACEHOLDERS.AGENT_ID, agentId);

    GetAllTibcoGiftsResponse response = tibcoProxy.getAllTibcoGifts(tibcoUrl);
    List<GiftModel> resultList = TibcoParser.parseAssignGiftResponse(response);
    CCATLogger.DEBUG_LOGGER.info("Get All Tibco Offers Tibco Gift Ended Successfully ");
    return resultList;
  }

  public void redeemOffer(AcceptGiftRequest acceptGiftRequest) throws IOException, NBAException {

    CCATLogger.DEBUG_LOGGER.info("Start Redeeming Tibco Gift with request : " + acceptGiftRequest);
    String result = "";
    try {
      Resource redeemRequestTemplateResource = resourceLoader.getResource(
          NBA_DEFINES.REDEEM_OFFER_CLASSPATH);
      InputStream inputStream = redeemRequestTemplateResource.getInputStream();
      result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new NBAException(ErrorCodes.ERROR.ACCEPT_OFFER_TEMPLATE_NOT_FOUND, "");
    }

    String category = properties.getTibcoRedeemGiftCategory();
    String channelId = properties.getTibcoRedeemGiftChannelId();
    String wlist = acceptGiftRequest.getWlist();
    result = result.replace(NBA_PLACEHOLDERS.CATEGORY, category)
        .replace(NBA_PLACEHOLDERS.CHANNEL_ID, channelId)
        .replace(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, acceptGiftRequest.getMsisdn())
        .replace(NBA_PLACEHOLDERS.SHORT_CODE, acceptGiftRequest.getGiftShortCode())
        .replace(NBA_PLACEHOLDERS.AGENT_ID, acceptGiftRequest.getUsername())
        .replace(NBA_PLACEHOLDERS.W_LIST,
            Objects.nonNull(wlist) && !wlist.isBlank() ? ",{\"value\":\""
                + acceptGiftRequest.getWlist() + "\","
                + "\"listHierarchyId\":\"wlistId\"}" : "");

    ObjectMapper mapper = new ObjectMapper();
    RedeemTibcoGiftRequest redeemTibcoGiftRequest = mapper.readValue(result,
        RedeemTibcoGiftRequest.class);
    tibcoProxy.redeemGift(redeemTibcoGiftRequest);
    CCATLogger.DEBUG_LOGGER.info("Redeeming Tibco Gift Ended Successfully ");

  }

  public void sendSMSGift(SendGiftRequest sendGiftRequest) throws IOException, NBAException {

    CCATLogger.DEBUG_LOGGER.info("Start Sending Tibco SMS Gift with request : " + sendGiftRequest);
    String result = "";
    try {
      Resource sendSmsGiftRequestTemplate = resourceLoader.getResource(
          NBA_DEFINES.SEND_SMS_CLASSPATH);
      InputStream inputStream = sendSmsGiftRequestTemplate.getInputStream();
      result = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    } catch (Exception ex) {
      throw new NBAException(ErrorCodes.ERROR.SEND_SMS_TEMPLATE_NOT_FOUND, "");
    }

    String category = properties.getTibcoSendSmsCategory();
    String channelId = properties.getTibcoSendSmsChannelId();
    String shortCode = properties.getTibcoSendSmsShortCode();
    String wlist = sendGiftRequest.getWlist();

    result = result.replace(NBA_PLACEHOLDERS.CATEGORY, category)
        .replace(NBA_PLACEHOLDERS.CHANNEL_ID, channelId)
        .replace(NBA_PLACEHOLDERS.SHORT_CODE, shortCode)
        .replace(NBA_PLACEHOLDERS.CUSTOMER_ACCOUNT_ID, sendGiftRequest.getMsisdn())
        .replace(NBA_PLACEHOLDERS.AGENT_ID, sendGiftRequest.getUsername())
        .replace(NBA_PLACEHOLDERS.GIFT_SEQ_ID, sendGiftRequest.getGiftSeqId())
        .replace(NBA_PLACEHOLDERS.W_LIST,
            Objects.nonNull(wlist) && !wlist.isBlank() ? ",{\"value\":\""
                + sendGiftRequest.getWlist() + "\","
                + "\"listHierarchyId\":\"wlistId\"}" : "");
    ObjectMapper mapper = new ObjectMapper();
    SendTibcoSMSRequest sendTibcoSMSRequest = mapper.readValue(result, SendTibcoSMSRequest.class);
    tibcoProxy.sendSMSGift(sendTibcoSMSRequest);
    CCATLogger.DEBUG_LOGGER.info("Sending Tibco SMS Gift Ended Successfully ");
  }
}
