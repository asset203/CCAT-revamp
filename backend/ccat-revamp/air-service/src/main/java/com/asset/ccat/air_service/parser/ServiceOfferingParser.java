package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.ServiceOfferingBitsModel;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class ServiceOfferingParser implements Parser {

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing service offering.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        List<ServiceOfferingBitsModel> serviceBits = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                ServiceOfferingBitsModel bitsModel = new ServiceOfferingBitsModel();
                processValueNode(currentChild, bitsModel);
                serviceBits.add(bitsModel);
            }
        }
        responseStrArr.put(AIRDefines.serviceOfferings, serviceBits);
    }


    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, ServiceOfferingBitsModel bitsModel) {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, bitsModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, ServiceOfferingBitsModel bitsModel) {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, bitsModel);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, ServiceOfferingBitsModel bitsModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.serviceOfferingActiveFlag:
                bitsModel.setValue(value.equals("1") ? "true" : "false");
                break;
            case AIRDefines.serviceOfferingID:
                bitsModel.setKey(value);
                bitsModel.setName("bit_" + value);
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }
}
