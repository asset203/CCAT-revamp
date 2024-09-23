package com.asset.ccat.air_service.services;

import com.asset.ccat.air_service.cache.AIRRequestsCache;
import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRException;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.mappers.OffersMapper;
import com.asset.ccat.air_service.models.OfferModel;
import com.asset.ccat.air_service.models.requests.offer.DeleteOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.GetOfferRequest;
import com.asset.ccat.air_service.models.requests.offer.OfferRequest;
import com.asset.ccat.air_service.models.responses.offer.GetAllOffersResponse;
import com.asset.ccat.air_service.parser.AIRParser;
import com.asset.ccat.air_service.proxy.AIRProxy;
import com.asset.ccat.air_service.proxy.LookupProxy;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author wael.mohamed
 */
@Service
public class OfferService {

    @Autowired
    LookupProxy configServerProxy;
    @Autowired
    Properties properties;
    @Autowired
    AIRRequestsCache aIRRequestsCache;
    @Autowired
    AIRProxy aIRProxy;
    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    AIRParser aIRParser;
    @Autowired
    OffersMapper offersMapper;


    public GetAllOffersResponse getOffers(GetOfferRequest offerRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.GET_OFFERS);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername().toLowerCase());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            CCATLogger.DEBUG_LOGGER.debug(" AIR get offers request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR get offers response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            CCATLogger.DEBUG_LOGGER.debug(" AIR get offers response is " + resultMap);
            List<OfferModel> offers = offersMapper.mapGetOffer(offerRequest.getMsisdn(), resultMap);
            if (Objects.isNull(offers) || offers.isEmpty()) {
                throw new AIRServiceException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR);
            }
            CCATLogger.DEBUG_LOGGER.debug("getOffers Ended successfully... with count [ " + offers.size() + " ]");
            return new GetAllOffersResponse(offers);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("getOffers Ended with AIRException.");
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("getOffers() Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Unknown error in getOffers() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in getOffers()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void addAndUpdateOffer(OfferRequest offerRequest) throws AIRServiceException, AIRException {
        CCATLogger.DEBUG_LOGGER.info("user: " + offerRequest.getUsername() + " | msisdn: " + offerRequest.getMsisdn() + "| Call AIR to updateOffer()");
        String expiryDate = "";
        String startDate = "";
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.UPDATE_OFFER);
            if (offerRequest.getOffer().getExpiryDate() != null) {
                expiryDate = AIRDefines.AIR_TAGS.TAG_MEMBER_DATE;
                if (offerRequest.getOffer().getOfferTypeId() == 2) {
                    expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.expiryDateTime);
                } else {
                    expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.expiryDate);
                }
                if (offerRequest.getOffer().getExpiryDate() == null) {
                    expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, AIRDefines.INFINITY_DATE_AIR);
                } else {
                    expiryDate = expiryDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE, aIRUtils.formatNewAIR(offerRequest.getOffer().getExpiryDate()));
                }
            }
            if (offerRequest.getOffer().getStartDate() != null && offerRequest.getOffer().getStartDate().after(new Date())) {
                startDate = AIRDefines.AIR_TAGS.TAG_MEMBER_DATE;
                if (offerRequest.getOffer().getOfferTypeId() == 2) {
                    startDate = startDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.startDateTime);
                } else {
                    startDate = startDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.startDate);
                }
                startDate = startDate.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE,
                        aIRUtils.formatNewAIR(offerRequest.getOffer().getStartDate()));
            }
            String offerType = "";
            if (offerRequest.getOffer().getOfferTypeId() != -1) {
                offerType = AIRDefines.AIR_TAGS.TAG_MEMBER_INT;
                offerType = offerType.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_KEY, AIRDefines.offerType);
                offerType = offerType.replace(AIRDefines.AIR_TAGS.TAG_MEMBER_VALUE,
                        String.valueOf(offerRequest.getOffer().getOfferTypeId()));
            }
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_OFFER.OFFER_ID, offerRequest.getOffer().getOfferId().toString());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_OFFER.OFFER_TYPE, offerType);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_OFFER.OFFER_START_DATE, startDate);
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_OFFER.OFFER_EXPIRY_DATE, expiryDate);
            CCATLogger.DEBUG_LOGGER.debug(" AIR addAndUpdateOffer request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR addAndUpdateOffer response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            offersMapper.mapAddAndUpdateOffer(offerRequest.getMsisdn(), resultMap);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.info("addAndUpdateOffer Ended with AIRException.");
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.info("addAndUpdateOffer Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.error(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("addAndUpdateOffer Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.error("Unknown error in addAndUpdateOffer() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in addAndUpdateOffer()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public void deleteOffer(DeleteOfferRequest offerRequest) throws AIRServiceException, AIRException {
        try {
            String xmlRequest = aIRRequestsCache.getAirRequestsCache().get(AIRDefines.AIR_COMMAND_KEY.DELETE_OFFER);
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TRANSACTION_ID, "1");
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_TIME_STAMP, aIRUtils.getCurrentFormattedDate());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_HOST_NAME, properties.getOriginHostName());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_NODE_TYPE, properties.getOriginNodeType());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.ORIGIN_OPERATOR_ID, offerRequest.getUsername());
            xmlRequest = xmlRequest.replace(AIRDefines.AIR_BASE_PLACEHOLDER.SUBSCRIBER_NUMBER, offerRequest.getMsisdn());
            xmlRequest = xmlRequest.replace(AIRDefines.UPDATE_OFFER.OFFER_ID, offerRequest.getOfferId().toString());
            CCATLogger.DEBUG_LOGGER.debug(" AIR deleteOffer request is " + xmlRequest);
            String result = aIRProxy.sendAIRRequest(xmlRequest);
            CCATLogger.DEBUG_LOGGER.debug(" AIR deleteOffer response is " + result);
            HashMap resultMap = aIRParser.parse(result);
            offersMapper.mapDeleteOffer(result, resultMap);
        } catch (AIRServiceException | AIRException ex) {
            CCATLogger.DEBUG_LOGGER.debug("deleteOffer Ended with AIRException.");
            throw ex;
        } catch (IOException | SAXException ex) {
            CCATLogger.DEBUG_LOGGER.debug("deleteOffer Ended with IOException | SAXException.");
            CCATLogger.DEBUG_LOGGER.info(" Error while parsing response " + ex);
            CCATLogger.ERROR_LOGGER.error(" Error while parsing response ", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.ERROR_PARSING_RESPONSE);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("deleteOffer Ended with Exception.");
            CCATLogger.DEBUG_LOGGER.error("Unknown error in deleteOffer() | ex: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Unknown error in deleteOffer()", ex);
            throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
