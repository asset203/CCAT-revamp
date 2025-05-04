package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.defines.OfferTypes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.OffersMapper;
import com.asset.ccat.air_service.models.OfferModel;
import com.asset.ccat.air_service.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.GetOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.OfferModelRequest;
import com.asset.ccat.air_service.models.requests.offer.OfferRequest;
import com.asset.ccat.air_service.models.responses.offer.GetAllOffersResponse;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import com.asset.ccat.air_service.utils.ReplacePlaceholderBuilder;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * @author wael.mohamed
 */
@Service
public class OfferService {
    private final Properties properties;
    private final AIRRequestsCache aIRRequestsCache;
    private final AIRProxy aIRProxy;
    private final AIRUtils aIRUtils;
    private final AIRParser aIRParser;
    private final OffersMapper offersMapper;

    public OfferService(Properties properties, AIRRequestsCache aIRRequestsCache, AIRProxy aIRProxy, AIRUtils aIRUtils, AIRParser aIRParser, OffersMapper offersMapper) {
        this.properties = properties;
        this.aIRRequestsCache = aIRRequestsCache;
        this.aIRProxy = aIRProxy;
        this.aIRUtils = aIRUtils;
        this.aIRParser = aIRParser;
        this.offersMapper = offersMapper;
    }

    public GetAllOffersResponse getOffers(GetOfferRequest offerRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername().toLowerCase())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_OFFERS));

            CCATLogger.DEBUG_LOGGER.debug(" AIR get offers request is: {}", xmlRequest);
            String getOffersAirResponse = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(getOffersAirResponse);
            CCATLogger.DEBUG_LOGGER.debug("All Offers after parsing: {}", resultMap);
            List<OfferModel> offers = offersMapper.mapGetOffer(resultMap);

            CCATLogger.DEBUG_LOGGER.debug("#Offers for with count= {} ", offers != null ? offers.size() : 0);
            return new GetAllOffersResponse(offers);
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.error("IOException | SAXException occurred while parsing getOffers request. ", ex);
            CCATLogger.ERROR_LOGGER.error("IOException | SAXException occurred while parsing getOffers request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while parsing getOffers request. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing getOffers request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void addAndUpdateOffer(OfferRequest offerRequest) throws AIRServiceException, AIRException {
        String expiryDate = "";
        String startDate = "";
        try {
            OfferModelRequest newOfferModel = offerRequest.getOffer();
            if (newOfferModel.getExpiryDate() != null) {
                String expiryKeyTag = OfferTypes.TIMER_OFFER.getTypeId().equals(newOfferModel.getOfferTypeId()) ?
                        AIRDefines.expiryDateTime : AIRDefines.expiryDate;
                String expiryValueTag = (newOfferModel.getExpiryDate() == null) ?
                        AIRDefines.INFINITY_DATE_AIR : aIRUtils.formatNewAIR(newOfferModel.getExpiryDate());
                CCATLogger.DEBUG_LOGGER.debug("Offer's Expiry = {}", expiryValueTag);
                expiryDate = new ReplacePlaceholderBuilder()
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, expiryKeyTag)
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, expiryValueTag)
                        .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_DATE);
            }

            // StartDate should be in the future (at least 5 minutes ahead) to be accepted from the Air side
            if (newOfferModel.getStartDate() != null) {
                Instant nowPlus5Min = Instant.now().plus(Duration.ofMinutes(5));
                Instant offerStartInstant = newOfferModel.getStartDate().toInstant();

                boolean isAccountOffer = OfferTypes.ACCOUNT_OFFER.getTypeId().equals(newOfferModel.getOfferTypeId());
                CCATLogger.DEBUG_LOGGER.debug("nowPlus5Mins = {} | offerStartInstant = {}, isAccountOffer = {}", nowPlus5Min.getEpochSecond(), offerStartInstant.getEpochSecond(), isAccountOffer);
                if ((isAccountOffer && offerStartInstant.isAfter(nowPlus5Min))
                        || (!isAccountOffer && newOfferModel.getStartDate().after(Date.from(nowPlus5Min)))) {

                    String startDateKeyTag = OfferTypes.TIMER_OFFER.getTypeId().equals(newOfferModel.getOfferTypeId())
                            ? AIRDefines.startDateTime
                            : AIRDefines.startDate;
                    String startDateValueTag = aIRUtils.formatNewAIR(newOfferModel.getStartDate());
                    CCATLogger.DEBUG_LOGGER.debug("Offer's Start time = {}", startDateValueTag);
                    startDate = new ReplacePlaceholderBuilder()
                            .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, startDateKeyTag)
                            .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, startDateValueTag)
                            .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_DATE);
                }
            }


            String offerType = "";
            if (newOfferModel.getOfferTypeId() != -1) {
                offerType = new ReplacePlaceholderBuilder()
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.offerType)
                        .addPlaceholder(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, String.valueOf(newOfferModel.getOfferTypeId()))
                        .buildUrl(AIRDefines.AIR_TAGS.TAG_MEMBER_INT);
            }
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.UPDATE_OFFER.OFFER_ID, newOfferModel.getOfferId().toString())
                    .addPlaceholder(AIRDefines.UPDATE_OFFER.OFFER_TYPE, offerType)
                    .addPlaceholder(AIRDefines.UPDATE_OFFER.OFFER_START_DATE, startDate)
                    .addPlaceholder(AIRDefines.UPDATE_OFFER.OFFER_EXPIRY_DATE, expiryDate)
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_OFFER));
            CCATLogger.DEBUG_LOGGER.debug(" AIR addAndUpdateOffer request is: {}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            CCATLogger.DEBUG_LOGGER.debug("addAndUpdateOffer parsed responseMap = {}", resultMap);
            offersMapper.mapAddAndUpdateOffer(resultMap);
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.error("IOException | SAXException occurred while parsing air request. ", ex);
            CCATLogger.ERROR_LOGGER.error("IOException | SAXException occurred while parsing air request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while parsing air request. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing air request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void deleteOffer(DeleteOfferRequest offerRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = new ReplacePlaceholderBuilder()
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1")
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername())
                    .addPlaceholder(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn())
                    .addPlaceholder(AIRDefines.UPDATE_OFFER.OFFER_ID, offerRequest.getOfferId().toString())
                    .buildUrl(aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.DELETE_OFFER));

            CCATLogger.DEBUG_LOGGER.debug(" AIR deleteOffer request is {}", xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            HashMap resultMap = aIRParser.parse(result);
            CCATLogger.DEBUG_LOGGER.debug("Delete Offer parsed responseMap = {}", resultMap);
            offersMapper.mapDeleteOffer(result, resultMap);
        } catch (AIRException | AIRServiceException ex) {
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.error("IOException | SAXException occurred while parsing air request. ", ex);
            CCATLogger.ERROR_LOGGER.error("IOException | SAXException occurred while parsing air request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while parsing air request. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing air request. ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
