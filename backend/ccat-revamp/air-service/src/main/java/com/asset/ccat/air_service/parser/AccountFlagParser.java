package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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

        // Locate the "struct" node
        Node structNode = findChildNode(currentNode, "struct");
        if (structNode == null) {
            CCATLogger.DEBUG_LOGGER.error("No 'struct' node found in AccountFlagParser.");
            return;
        }

        NodeList memberNodes = structNode.getChildNodes();
        Map<String, String> accountFlags = new HashMap<>();

        for (int i = 0; i < memberNodes.getLength(); i++) {
            Node memberNode = memberNodes.item(i);
            if (!"member".equalsIgnoreCase(memberNode.getNodeName()))
                continue; // Skip non-member nodes

            String name = extractChildNodeTextContent(memberNode, "name");
            String value = extractChildNodeTextContent(memberNode, "value");

            if (name != null && value != null)
                accountFlags.put(name, value);
        }

        responseStrArr.put(AIRDefines.accountFlags, accountFlags);
    }

    private Node findChildNode(Node parent, String nodeName) {
        NodeList children = parent.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            Node child = children.item(i);
            if (nodeName.equalsIgnoreCase(child.getNodeName()))
                return child;

            Node found = findChildNode(child, nodeName);
            if (found != null)
                return found;
        }
        return null;
    }

    private String extractChildNodeTextContent(Node parent, String nodeName) {
        Node childNode = findChildNode(parent, nodeName);
        return (childNode != null) ? getNodeTextContent(childNode) : null;
    }

    private String getNodeTextContent(Node node) {
        return (node != null && node.getTextContent() != null) ? node.getTextContent().trim() : null;
    }
}
