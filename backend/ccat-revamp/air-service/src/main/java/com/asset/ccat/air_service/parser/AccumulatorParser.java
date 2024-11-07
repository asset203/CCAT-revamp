package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.models.AccumulatorModel;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class AccumulatorParser {
    private final AIRUtils aIRUtils;

    @Autowired
    public AccumulatorParser(AIRUtils aIRUtils) {
        this.aIRUtils = aIRUtils;
    }

    public void parseAccumulator(Node currentNode, HashMap responseStrArr) {
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        ArrayList<AccumulatorModel> accumulators = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                AccumulatorModel accumulatorModel = new AccumulatorModel();
                processValueNode(currentChild, accumulatorModel);
                accumulators.add(accumulatorModel);
            }
        }
        responseStrArr.put(AIRDefines.accumulatorInformation, accumulators);
    }

    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, AccumulatorModel accumulatorModel) {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, accumulatorModel);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, AccumulatorModel accumulatorModel) {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode))
                extractMemberData(memberNode, accumulatorModel);
        }
    }

    private void extractMemberData(Node memberNode, AccumulatorModel accumulatorModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.accumulatorID:
                accumulatorModel.setId(value);
                break;
            case AIRDefines.accumulatorStartDate:
                accumulatorModel.setStartDate(aIRUtils.parseAirDate(value));
                break;
            case AIRDefines.accumulatorEndDate:
                accumulatorModel.setResetDate(aIRUtils.parseAirDate(value));
                break;
            case AIRDefines.accumulatorValue:
                accumulatorModel.setValue(Float.valueOf(value));
                break;
            default:
                break;
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }
}
