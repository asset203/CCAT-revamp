package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahmoud Shehab
 */
@Service
public class AccountFlagParser implements Parser {

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("[Air-Response] Start parsing Account flag.");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        int length1 = arrayNode.getChildNodes().getLength();

        Map<String, String> accountFlags = new HashMap<>();
        for (int y = 0; y < length1; y++) {
            Node dataNode = arrayNode.getChildNodes().item(y);
            int length2 = dataNode.getChildNodes().getLength();
            String nameStr = "";
            for (int i = 0; i < length2; i++) {
                Node currentChild = dataNode.getChildNodes().item(i);
                if (currentChild.getNodeName().equalsIgnoreCase("name"))
                    nameStr = getNodeTextContent(currentChild);

                if (isValueNode(currentNode)) {
                    String valueStr = getNodeTextContent(currentChild);
                    accountFlags.put(nameStr, valueStr);
                }
            }
        }
        responseStrArr.put(AIRDefines.accountFlags, accountFlags);
    }

    private boolean isValueNode(Node node) {
        return node.getNodeName().equalsIgnoreCase("value");
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : "";
    }

}
