package com.asset.ccat.air_service.parsers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.VoucherModel;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class TransactionRecordsParser implements Parser {

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing TransactionRecords.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();

        ArrayList<VoucherModel> voucherModels = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                VoucherModel voucherModel = new VoucherModel();
                int memberCount = processValueNode(currentChild, voucherModel);
                if (memberCount == ((getValuesSize(currentChild) - 1) / 2)) {
                    voucherModels.add(voucherModel);
                }
            }
        }
        responseStrArr.put(AIRDefines.transactionRecords, voucherModels);
    }

    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private int processValueNode(Node valueNode, VoucherModel voucherModel) {
        int valuesSize = getValuesSize(valueNode);
        int memberCount = 0;
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                memberCount += parseStructNode(structNode, voucherModel);
            }
        }
        return memberCount;
    }

    private int getValuesSize(Node node) {
        return node.getChildNodes().getLength();
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private int parseStructNode(Node structNode, VoucherModel voucherModel) {
        int membersCount = 0;
        int totalMembers = structNode.getChildNodes().getLength();
        for (int u = 0; u < totalMembers; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode) && (extractMemberData(memberNode, voucherModel))) {
                membersCount++;
            }
        }
        return membersCount;
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private boolean extractMemberData(Node memberNode, VoucherModel voucherModel) {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.operatorId:
                voucherModel.setOperatorId(value);
                return true;
            case AIRDefines.newState:
                voucherModel.setNewState(value);
                return true;
            case AIRDefines.timestamp:
                voucherModel.setTimeStamp(value);
                return true;
            case AIRDefines.subscriberId:
                voucherModel.setSubscriberId(value);
                return true;
            case AIRDefines.transactionId:
                voucherModel.setTransactionId(value);
                return true;
            default:
                return false;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }
}
