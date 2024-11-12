package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.shared.LookupModel;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommunityAndInfoParser implements Parser {
    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing Community And Information.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        List<LookupModel> communitiesList = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                LookupModel communityModel = new LookupModel();
                processValueNode(currentChild, communityModel);
                communitiesList.add(communityModel);
            }
        }
        responseStrArr.put(AIRDefines.communityInformationCurrent, communitiesList);
    }
    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, LookupModel communityModel) {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, communityModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, LookupModel communityModel) {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, communityModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, LookupModel communityModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        if (nameStr.equals(AIRDefines.communityID))
            communityModel.setKey(value);

    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

}
