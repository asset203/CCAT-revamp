package com.asset.ccat.air_service.parsers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.OfferModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class OfferParser implements Parser {

    private final AIRUtils aIRUtils;

    private final LookupsService lookupsService;

    @Autowired
    public OfferParser(AIRUtils aIRUtils, LookupsService lookupsService) {
        this.aIRUtils = aIRUtils;
        this.lookupsService = lookupsService;
    }

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing offer information.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        List<OfferModel> offers = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                OfferModel offerModel = new OfferModel();
                processValueNode(currentChild, offerModel);
                offers.add(offerModel);
            }
        }
        responseStrArr.put(AIRDefines.offerInformation, offers);
    }

    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, OfferModel offerModel) throws AIRServiceException {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, offerModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, OfferModel offerModel) throws AIRServiceException {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, offerModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, OfferModel offerModel) throws AIRServiceException {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.offerID:
                setOfferIdAndDescription(offerModel, value);
                break;
            case AIRDefines.startDate:
            case AIRDefines.startDateTime:
                offerModel.setStartDate(aIRUtils.formatDateString(value));
                break;
            case AIRDefines.expiryDate:
            case AIRDefines.expiryDateTime:
                offerModel.setExpiryDate(aIRUtils.formatDateString(value));
                break;
            case AIRDefines.offerType:
                setOfferTypeAndDescription(offerModel, value);
                break;
            case AIRDefines.offerState:
                setOfferStateAndDescription(offerModel, value);
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

    private void setOfferIdAndDescription(OfferModel offerModel, String value) throws AIRServiceException {
        Map<Integer, OfferModel> offersMap = lookupsService.getOffers();
        int offerId = Integer.parseInt(value);
        offerModel.setOfferId(offerId);
        offerModel.setOfferDesc(offersMap.get(offerId) == null ? ""
                : (offersMap.get(offerId)).getOfferDesc());
    }

    private void setOfferTypeAndDescription(OfferModel offerModel, String value) throws AIRServiceException {
        int offerTypeId = Integer.parseInt(value);
        offerModel.setOfferTypeId(offerTypeId);
        offerModel.setOfferType(
                lookupsService.getOffersTypes().get(offerTypeId) == null ? ""
                        : (lookupsService.getOffersTypes().get(offerTypeId).getTypeDesc()));
    }

    private void setOfferStateAndDescription(OfferModel offerModel, String value) throws AIRServiceException {
        int offerStateId = Integer.parseInt(value);
        offerModel.setOfferStateId(offerStateId);
        offerModel.setOfferState(lookupsService.getOfferStates().get(offerStateId) == null ? ""
                : (lookupsService.getOfferStates().get(offerStateId).getStateDesc()));
    }

}
