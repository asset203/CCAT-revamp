package com.asset.ccat.air_service.parsers;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import com.asset.ccat.air_service.models.DedicatedAccount;
import com.asset.ccat.air_service.models.shared.LookupModel;
import com.asset.ccat.air_service.services.LookupsService;
import com.asset.ccat.air_service.utils.AIRUtils;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class DedicatedAccountParser implements Parser {
    private final AIRUtils aIRUtils;
    private final LookupsService lookupsService;

    public DedicatedAccountParser(AIRUtils aIRUtils, LookupsService lookupsService) {
        this.aIRUtils = aIRUtils;
        this.lookupsService = lookupsService;
    }

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing Dedicated account response.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        Node dataNode = arrayNode.getChildNodes().item(1);
        int length = dataNode.getChildNodes().getLength();
        List<DedicatedAccount> primeDedicatedAccounts = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            Node currentChild = dataNode.getChildNodes().item(i);
            if (isValueNode(currentChild)) {
                DedicatedAccount dedicatedAccount = new DedicatedAccount();
                processValueNode(currentChild, dedicatedAccount);
                primeDedicatedAccounts.add(dedicatedAccount);
            }
        }
        responseStrArr.put(AIRDefines.primeDedicatedAccountInformation, primeDedicatedAccounts);
    }


    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private void processValueNode(Node valueNode, DedicatedAccount dedicatedAccount) throws AIRServiceException {
        int valuesSize = valueNode.getChildNodes().getLength();
        for (int c = 0; c < valuesSize; c++) {
            Node structNode = valueNode.getChildNodes().item(c);
            if (isStructNode(structNode)) {
                parseStructNode(structNode, dedicatedAccount);
            }
        }
    }

    private boolean isStructNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("struct");
    }

    private void parseStructNode(Node structNode, DedicatedAccount dedicatedAccount) throws AIRServiceException {
        int membersLength = structNode.getChildNodes().getLength();
        for (int u = 0; u < membersLength; u++) {
            Node memberNode = structNode.getChildNodes().item(u);
            if (isMemberNode(memberNode)) {
                extractMemberData(memberNode, dedicatedAccount);
            }
        }
    }

    private boolean isMemberNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("member");
    }

    private void extractMemberData(Node memberNode, DedicatedAccount dedicatedAccount) throws AIRServiceException {
        String nameStr = getNodeTextContent(memberNode.getChildNodes().item(1));
        Node valueNode = memberNode.getChildNodes().item(3);
        String value = getNodeTextContent(valueNode);

        switch (nameStr) {
            case AIRDefines.dedicatedAccountID:
                dedicatedAccount.setId(value);
                break;
            case AIRDefines.dedicatedAccountValue1:
                dedicatedAccount.setBalancePT(value);
                dedicatedAccount.setBalance(aIRUtils.amountInLE(value));
                break;
            case AIRDefines.expiryDate:
                dedicatedAccount.setExpiryDate(aIRUtils.formatDateString(value));
                break;
            case AIRDefines.dedicatedAccountUnitType:
                dedicatedAccount.setUnitType(Integer.parseInt(value));
                LookupModel lookupModel = lookupsService.getUnitTypes().get(value);
                dedicatedAccount.setUnitTypeDesc(lookupModel == null ? "" : lookupModel.getValue());
                break;
            default:
                break;
        }
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }
}
