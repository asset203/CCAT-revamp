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
public class ChargesParser implements Parser {

    @Override
    public void parse(Node currentNode, Map<String, Object> responseStrArr) throws AIRServiceException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing Charges Result Info response");
        Node allDataNode = currentNode.getChildNodes().item(3);
        Node arrayNode = allDataNode.getChildNodes().item(1);
        int allLength = arrayNode.getChildNodes().getLength();

        Map<String, String> charges = new HashMap<>();
        for (int y = 0; y < allLength; y++) {
            Node dataNode = arrayNode.getChildNodes().item(y);
            int length = dataNode.getChildNodes().getLength();
            String nameStr = "";
            String valueStr;
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
}
