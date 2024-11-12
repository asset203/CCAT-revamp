package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.PromotionPlanModel;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PromotionPlanParser implements Parser {
    private int membersCount = 0;
    private final AIRUtils aIRUtils;

    @Autowired
    public PromotionPlanParser(AIRUtils aIRUtils) {
        this.aIRUtils = aIRUtils;
    }


    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing Promotion Plan Response");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        List<PromotionPlanModel> promotionPlanModels = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                PromotionPlanModel promotionPlanModel = new PromotionPlanModel();
                processValueNode(currentChild, promotionPlanModel);
                if(membersCount == 3)
                    promotionPlanModels.add(promotionPlanModel);
                membersCount = 0;
            }
        }
        responseStrArr.put(AIRDefines.promotionPlanInformation, promotionPlanModels);

    }


    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, PromotionPlanModel promotionPlanModel) {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, promotionPlanModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, PromotionPlanModel promotionPlanModel) {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, promotionPlanModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, PromotionPlanModel promotionPlanModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);
        membersCount++;
        switch (nameStr) {
            case AIRDefines.promotionEndDate:
                promotionPlanModel.setEndDate(aIRUtils.formatAirToCcatDate(value));
                promotionPlanModel.setOldEndDate(aIRUtils.formatAirToCcatDate(value));
                break;
            case AIRDefines.promotionPlanID:
                promotionPlanModel.setPromotionPlanId(value);
                break;
            case AIRDefines.promotionStartDate:
                promotionPlanModel.setStartDate(aIRUtils.formatAirToCcatDate(value));
                promotionPlanModel.setOldStartDate(aIRUtils.formatAirToCcatDate(value));
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

}
