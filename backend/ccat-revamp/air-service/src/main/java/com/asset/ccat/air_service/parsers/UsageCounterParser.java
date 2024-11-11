package com.asset.ccat.air_service.parsers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.customer_care.UsageCountersModel;
import com.asset.ccat.air_service.models.customer_care.UsageThresholdInformationModel;
import com.asset.ccat.air_service.services.LookupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class UsageCounterParser implements Parser { 

    private final LookupsService lookupsService;

    @Autowired
    public UsageCounterParser(LookupsService lookupsService) {
        this.lookupsService = lookupsService;
    }

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing UsageCounters response");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        List<UsageCountersModel> usageList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                UsageCountersModel usageModel = new UsageCountersModel();
                processValueNode(currentChild, usageModel);
                usageList.add(usageModel);
            }
        }
        responseStrArr.put(AIRDefines.usageCounterUsageThresholdInformation, usageList);
    }

    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, UsageCountersModel usageModel) throws AIRServiceException {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, usageModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, UsageCountersModel usageModel) throws AIRServiceException {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, usageModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, UsageCountersModel usageModel) throws AIRServiceException {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.usageCounterID:
                usageModel.setId(Integer.parseInt(value));
                usageModel.setDescription(lookupsService.getUsageCountersDesc().get(value) == null ? "" :
                        lookupsService.getUsageCountersDesc().get(value).getValue());
                break;
            case AIRDefines.usageCounterValue:
                usageModel.setValue(value);
                break;
            case AIRDefines.usageCounterMonetaryValue1:
                int tempMonetaryValue1 = Integer.parseInt(value);
                tempMonetaryValue1 /= 100;
                usageModel.setMonetaryValue1(tempMonetaryValue1 + "");
                break;
            case AIRDefines.usageCounterMonetaryValue2:
                usageModel.setMonetaryValue2(value);
                break;
            case AIRDefines.usageThresholdInformation:
                List<UsageThresholdInformationModel> list = parseThresholdInformation(memberNode);
                usageModel.setUsageThresholdInformation(list);
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

    private List<UsageThresholdInformationModel> parseThresholdInformation(Node currentNode) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        var usageThresholdList = new ArrayList<UsageThresholdInformationModel>();

        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                var usageThresholdInformationModel = new UsageThresholdInformationModel();
                int valuesSize = currentChild.getChildNodes().getLength();
                for (int c = 0; c < valuesSize; c++) {
                    Node cValueNode = currentChild.getChildNodes().item(c);
                    if (isStructNode(cValueNode)) {
                        parseThresholdStructNode(cValueNode, usageThresholdInformationModel);
                    }
                }
                usageThresholdList.add(usageThresholdInformationModel);
            }
        }
        return usageThresholdList;
    }

    private void parseThresholdStructNode(Node structNode, UsageThresholdInformationModel usageThresholdInformationModel) {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractThresholdMemberData(memberNode, usageThresholdInformationModel);
            }
        }
    }

    private void extractThresholdMemberData(Node memberNode, UsageThresholdInformationModel usageThresholdInformationModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.usageThresholdID:
                usageThresholdInformationModel.setUsageThresholdID(Integer.parseInt(value));
                break;
            case AIRDefines.usageThresholdSource:
                usageThresholdInformationModel.setUsageThresholdSource(Integer.parseInt(value));
                break;
            case AIRDefines.usageThresholdValue:
                usageThresholdInformationModel.setUsageThresholdValue(value);
                break;
            default:
                break;
        }
    }
}
