/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.AccumulatorModel;
import com.asset.ccat.air_service.models.DedicatedAccount;
import com.asset.ccat.air_service.models.FamilyAndFriendsModel;
import com.asset.ccat.air_service.models.OfferModel;
import com.asset.ccat.air_service.models.PamInformationModel;
import com.asset.ccat.air_service.models.PromotionPlanModel;
import com.asset.ccat.air_service.models.ServiceOfferingBitsModel;
import com.asset.ccat.air_service.models.VoucherModel;
import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;
import com.asset.ccat.air_service.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.air_service.models.shared.LookupModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * @author Mahmoud Shehab
 */
@Component
public class AIRParser {

    @Autowired
    AIRUtils aIRUtils;
    @Autowired
    LookupsService lookupsService;

    @Autowired
    OfferInformationParser offerInformationParser;

    private DocumentBuilderFactory factory;
    private DocumentBuilder builder;

    public AIRParser() throws ParserConfigurationException {

    }

    private Document createDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        try (InputStream inputStream = new ByteArrayInputStream(xml.getBytes())) {
            factory = DocumentBuilderFactory.newInstance();
            builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);
        }
    }
    
    @LogExecutionTime
    public HashMap parse(String xml) throws SAXException, IOException, AIRServiceException, ParserConfigurationException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing the AIR XML response");
        HashMap responseStrArr = new HashMap();
        if (xml == null || xml.isEmpty() || xml.isBlank()) {
            return null;
        }
        Document document = createDocument(xml);
        Node node = document.getElementsByTagName("struct").item(0);
        NodeList members = node.getChildNodes();
        for (int x = 0; x < members.getLength(); x++) {
            Node currentNode = members.item(x);
            if (currentNode.getNodeName().equals("member")) {
                Node nameNode = currentNode.getChildNodes().item(0);
                String name = nameNode.getTextContent();
                name = name.trim();
                if (name.equals("\n") || name.equals("")) {
                    nameNode = currentNode.getChildNodes().item(1);
                    name = nameNode.getTextContent();
                }
                CCATLogger.DEBUG_LOGGER.info("Start parsing tag=[{}] node=[{}]", name, nameNode);
                switch (name) {
                    case AIRDefines.dedicatedAccountInformation:
                        parseDedicatedAccountInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.accountFlags:
                        parseAccountFlags(currentNode, responseStrArr);
                        break;
                    case AIRDefines.CHARGES_RESULT_INFO:
                        parseChargesInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.serviceOfferings:
                        parseServiceOffering(currentNode, responseStrArr);
                        break;
                    case AIRDefines.accumulatorInformation:
                        parseAccumulatorInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.transactionRecords:
                        parseTransactionRecords(currentNode, responseStrArr);
                        break;
                    case AIRDefines.fafInformationList:
                        parseFamilyAndFriends(currentNode, responseStrArr);
                        break;
                    case AIRDefines.communityInformationCurrent:
                        parseCommunityAndInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.promotionPlanInformation:
                        parsePromotionPlanInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.offerInformation:
                        offerInformationParser.parseOfferInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.usageCounterUsageThresholdInformation:
                        parseUsageCounterUsageThresholdInformation(currentNode, responseStrArr);
                        break;
                    case AIRDefines.pamInformationList:
                        parsePam(currentNode, responseStrArr);
                        break;
                    default:
                        if (AIRDefines.AIR_TAGS.SIMPLE_TAGS.contains(name))
                            parseSimpleTags(name, currentNode, responseStrArr);
                        break;
                }

            }
        }
        return responseStrArr;
    }

    public void parseSimpleTags(String nodeName, Node currentNode, HashMap responseStrArr) {
        Node valueNode = currentNode.getChildNodes().item(1);
        String valueStr = valueNode.getTextContent();
        CCATLogger.DEBUG_LOGGER.info("let's set in map node " + valueNode + " and value " + valueStr + " and nodeName " + nodeName);
        if (valueStr.equals(nodeName)) {
            valueNode = currentNode.getChildNodes().item(3);
            valueStr = valueNode.getTextContent();
        }
        valueStr = valueStr.trim();
        responseStrArr.put(nodeName, valueStr);
    }

    public void parseDedicatedAccountInformation(Node currentNode, HashMap responseStrArr) throws AIRServiceException {
        Node allDataNode = currentNode.getChildNodes().item(3);
        ArrayList<DedicatedAccount> primeDedicatedAccounts = new ArrayList<DedicatedAccount>();
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            DedicatedAccount dedicatedAccount = new DedicatedAccount();
            Node currentChild = dataNode.getChildNodes().item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.dedicatedAccountID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    dedicatedAccount.setId(itemValue.getTextContent().trim());
                                } else if (nameStr.equals(AIRDefines.dedicatedAccountValue1)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    dedicatedAccount.setBalancePT(itemValue.getTextContent().trim());
                                    //dedicatedAccount.setBalanceDisplay(itemValue.getTextContent().trim());
                                    dedicatedAccount.setBalance(aIRUtils.amountInLE(itemValue.getTextContent().trim()));
                                } else if (nameStr.equals(AIRDefines.expiryDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String expiryDate = itemValue.getTextContent().trim();
                                    dedicatedAccount.setExpiryDate(aIRUtils.parseAirDate(expiryDate));
                                } else if (nameStr.equals(AIRDefines.dedicatedAccountUnitType)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String value = itemValue.getTextContent().trim();
                                    dedicatedAccount.setUnitType(Integer.parseInt(value));
                                    LookupModel lookupModel = lookupsService.getUnitTypes().get(value);
                                    dedicatedAccount.setUnitTypeDesc(lookupModel == null ? "" : lookupModel.getValue());
                                }
                            }
                        }
                        primeDedicatedAccounts.add(dedicatedAccount);
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.primeDedicatedAccountInformation, primeDedicatedAccounts);
    }

    public void parseAccountFlags(Node currentNode, HashMap responseStrArr) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        HashMap accountFlags = new HashMap();
        Node arrayNode = allDataNode.getChildNodes().item(1);
        int allLength = arrayNode.getChildNodes().getLength();

        for (int y = 0; y < allLength; y++) {
            Node dataNode = arrayNode.getChildNodes().item(y);
            int length = dataNode.getChildNodes().getLength();
            String nameStr = "";
            String valueStr = "";
            for (int i = 0; i < length; i++) {
                Node currentChild = dataNode.getChildNodes().item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("name")) {
                    nameStr = currentChild.getTextContent().trim();
                }
                if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                    valueStr = currentChild.getTextContent().trim();
                    accountFlags.put(nameStr, valueStr);
                }
            }
        }
        responseStrArr.put(AIRDefines.accountFlags, accountFlags);
    }

    public void parseChargesInformation(Node currentNode, HashMap responseStrArr) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        HashMap charges = new HashMap();
        Node arrayNode = allDataNode.getChildNodes().item(1);
        int allLength = arrayNode.getChildNodes().getLength();

        for (int y = 0; y < allLength; y++) {
            Node dataNode = arrayNode.getChildNodes().item(y);
            int length = dataNode.getChildNodes().getLength();
            String nameStr = "";
            String valueStr = "";
            for (int i = 0; i < length; i++) {
                Node currentChild = dataNode.getChildNodes().item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("name")) {
                    nameStr = currentChild.getTextContent().trim();
                }
                if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                    valueStr = currentChild.getTextContent().trim();
                    charges.put(nameStr, valueStr);
                }
            }
        }
        responseStrArr.put(AIRDefines.CHARGES_RESULT_INFO, charges);
    }

    public void parseServiceOffering(Node currentNode, HashMap responseStrArr) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        ArrayList serviceBits = new ArrayList();
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            ServiceOfferingBitsModel bitsModel = new ServiceOfferingBitsModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.serviceOfferingActiveFlag)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    if (itemValue.getTextContent().trim().equalsIgnoreCase("1")) {
                                        bitsModel.setValue("true");
                                    } else {
                                        bitsModel.setValue("false");
                                    }
                                }
                                if (nameStr.equals(AIRDefines.serviceOfferingID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String cBit = itemValue.getTextContent().trim();
                                    bitsModel.setKey(cBit);
                                    bitsModel.setName("bit_" + cBit);
                                    serviceBits.add(bitsModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.serviceOfferings, serviceBits);
    }

    public void parseAccumulatorInformation(Node currentNode, HashMap responseStrArr) {

        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<AccumulatorModel> accumulatorModels = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            AccumulatorModel accumulatorModel = new AccumulatorModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        int memberCount = 0;
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.accumulatorID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    accumulatorModel.setId(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.accumulatorStartDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String startDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    accumulatorModel.setStartDate(aIRUtils.parseAirDate(startDate));
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.accumulatorEndDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String endDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim().substring(0, 8);
                                    accumulatorModel.setResetDate(aIRUtils.parseAirDate(endDate));
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.accumulatorValue)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    accumulatorModel.setValue(new Float(itemValue.getTextContent().trim()));
                                    memberCount++;
                                }
//                                            if (memberCount == 4) {
//                                                accumulatorModels.add(accumulatorModel);
//                                            }
                            }
                        }
                        accumulatorModels.add(accumulatorModel);
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.accumulatorInformation, accumulatorModels);
    }

    public void parseTransactionRecords(Node currentNode, HashMap responseStrArr) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<VoucherModel> voucherModels = new ArrayList<>();
        VoucherModel voucherModel = new VoucherModel();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            voucherModel = new VoucherModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        int memberCount = 0;
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.operatorId)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    voucherModel.setOperatorId(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.newState)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    voucherModel.setNewState(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.timestamp)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    voucherModel.setTimeStamp(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.subscriberId)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    voucherModel.setSubscriberId(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.transactionId)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    voucherModel.setTransactionId(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (memberCount == ((cValueNode.getChildNodes().getLength() - 1) / 2)) {
                                    voucherModels.add(voucherModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.transactionRecords, voucherModels);
    }

    public void parseFamilyAndFriends(Node currentNode, HashMap responseStrArr) {

        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<FamilyAndFriendsModel> fafModels = new ArrayList<FamilyAndFriendsModel>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            FamilyAndFriendsModel fafModel = new FamilyAndFriendsModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        int memberCount = 0;
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.fafNumber)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    fafModel.setNumber(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.fafIndicator)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    fafModel.setInd(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.owner)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    fafModel.setOwner(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (memberCount == 3) {
                                    fafModels.add(fafModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.fafInformationList, fafModels);

    }

    public void parseCommunityAndInformation(Node currentNode, HashMap responseStrArr) {

        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<LookupModel> communitiesList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.communityID)) {
                                    LookupModel communityModel = new LookupModel();
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    communityModel.setKey(itemValue.getTextContent().trim());
                                    communitiesList.add(communityModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.communityInformationCurrent, communitiesList);

    }

    public void parsePromotionPlanInformation(Node currentNode, HashMap responseStrArr) {

        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<PromotionPlanModel> promotionPlanModels = new ArrayList<PromotionPlanModel>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            PromotionPlanModel promotionPlanModel = new PromotionPlanModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        int memberCount = 0;
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.promotionEndDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String endDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    promotionPlanModel.setEndDate(aIRUtils.formatAirToCcatDate(endDate));
                                    promotionPlanModel.setOldEndDate(aIRUtils.formatAirToCcatDate(endDate));
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.promotionPlanID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    promotionPlanModel.setPromotionPlanId(itemValue.getTextContent().trim());
                                    memberCount++;
                                }
                                if (nameStr.equals(AIRDefines.promotionStartDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String startDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    promotionPlanModel.setStartDate(aIRUtils.formatAirToCcatDate(startDate));
                                    promotionPlanModel.setOldStartDate(aIRUtils.formatAirToCcatDate(startDate));
                                    memberCount++;
                                }
                                if (memberCount == 3) {
                                    promotionPlanModels.add(promotionPlanModel);
                                }
                            }
                        }
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.promotionPlanInformation, promotionPlanModels);

    }

    public void parseUsageCounterUsageThresholdInformation(Node currentNode, HashMap responseStrArr) throws AIRServiceException {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<UsageCountersModel> usageList = new ArrayList<UsageCountersModel>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                UsageCountersModel usageModel = new UsageCountersModel();
                int valuesSize
                        = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.usageCounterID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String id = itemValue.getTextContent().trim();
                                    usageModel.setId(Integer.parseInt(id));
                                    usageModel.setDescription(lookupsService.getUsageCountersDesc().get(id) == null ? "" : ((LookupModel) lookupsService.getUsageCountersDesc().get(id)).getValue());
                                } else if (nameStr.equals(AIRDefines.usageCounterValue)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String value = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    usageModel.setValue(value);
                                } else if (nameStr.equals(AIRDefines.usageCounterMonetaryValue1)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    int tempMonetaryValue1 = Integer.parseInt(itemValue.getTextContent().trim());
                                    tempMonetaryValue1 /= 100;
                                    usageModel.setMonetaryValue1(tempMonetaryValue1 + "");
                                } else if (nameStr.equals(AIRDefines.usageCounterMonetaryValue2)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    usageModel.setMonetaryValue2(itemValue.getTextContent().trim());
                                } else if (nameStr.equals(AIRDefines.usageThresholdInformation)) {
                                    List<UsageThresholdInformationModel> list = parseThresholdInformation(subMember);
                                    usageModel.setUsageThresholdInformation(list);
                                }
                            }
                        }

                    }
                }
                usageList.add(usageModel);
            }
        }
        responseStrArr.put(AIRDefines.usageCounterUsageThresholdInformation, usageList);
    }

    public List<UsageThresholdInformationModel> parseThresholdInformation(Node currentNode) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        var usageThresholdList = new ArrayList<UsageThresholdInformationModel>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                var usageThresholdInformationModel = new UsageThresholdInformationModel();
                int valuesSize
                        = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.usageThresholdID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String id = itemValue.getTextContent().trim();
                                    usageThresholdInformationModel.setUsageThresholdID(Integer.parseInt(id));
                                } else if (nameStr.equals(AIRDefines.usageThresholdSource)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String data = itemValue.getTextContent().trim();
                                    usageThresholdInformationModel.setUsageThresholdSource(Integer.parseInt(data));
                                } else if (nameStr.equals(AIRDefines.usageThresholdValue)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String data = itemValue.getTextContent().trim();
                                    usageThresholdInformationModel.setUsageThresholdValue(data);
                                }
                            }
                        }
                    }
                }
                usageThresholdList.add(usageThresholdInformationModel);
            }
        }
        return usageThresholdList;
    }

    public void parsePam(Node currentNode, HashMap responseStrArr) throws AIRServiceException {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<PamInformationModel> pamInformationList = new ArrayList<PamInformationModel>();
        PamInformationModel pamModel = null;
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            pamModel = new PamInformationModel();
            if (currentChild.getNodeName().equalsIgnoreCase("value")) {
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (cValueNode.getNodeName().equalsIgnoreCase("struct")) {
                        for (int u = 0; u < cValueNode.getChildNodes().getLength(); u++) {
                            Node subMember = cValueNode.getChildNodes().item(u);
                            if (subMember.getNodeName().equalsIgnoreCase("member")) {
                                Node subNameNode = subMember.getChildNodes().item(1);
                                String nameStr = subNameNode.getTextContent();
                                if (nameStr.equals(AIRDefines.pamServiceID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    pamModel.setPamServiceID(itemValue.getTextContent().trim());
                                } else if (nameStr.equals(AIRDefines.pamClassID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String pamClassID = itemValue.getTextContent().trim();
                                    Integer classId = Integer.parseInt(pamClassID);
                                    pamModel.setPamClassID(classId);
                                    pamModel.setPamClassDesc(lookupsService.getPamClasses().get(classId) == null ? pamClassID : lookupsService.getPamClasses().get(classId).getDescription());
                                } else if (nameStr.equals(AIRDefines.scheduleID)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String scheduleIDStr = itemValue.getTextContent().trim();
                                    Integer scheduleId = Integer.parseInt(scheduleIDStr);
                                    pamModel.setPamScheduleID(scheduleId);
                                    pamModel.setPamScheduleDesc(lookupsService.getPamSchedule().get(scheduleId) == null ? scheduleIDStr : lookupsService.getPamClasses().get(scheduleId).getDescription());
                                } else if (nameStr.equals(AIRDefines.currentPamPeriod)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String currentPamPeriod = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    pamModel.setCurrentPamPeriod(currentPamPeriod);
                                } else if (nameStr.equals(AIRDefines.deferredToDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String deferredToDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    pamModel.setDeferredToDate(aIRUtils.formatAir2CCDateTime(deferredToDate));
                                } else if (nameStr.equals(AIRDefines.lastEvaluationDate)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    String lastEvaluationDate = itemValue.getTextContent().trim() == null ? "" : itemValue.getTextContent().trim();
                                    pamModel.setLastEvaluationDate(aIRUtils.formatAir2CCDateTime(lastEvaluationDate));
                                } else if (nameStr.equals(AIRDefines.pamServicePriority)) {
                                    Node itemValue = subMember.getChildNodes().item(3);
                                    pamModel.setPamServicePriority(itemValue.getTextContent().trim());
                                }
                            }
                        }
                        pamInformationList.add(pamModel);
                    }
                }
            }
        }
        responseStrArr.put(AIRDefines.pamInformationList, pamInformationList);

    }
}
