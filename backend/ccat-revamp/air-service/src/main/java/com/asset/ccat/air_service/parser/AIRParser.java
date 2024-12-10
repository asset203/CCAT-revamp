/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.parser;

import com.asset.ccat.air_service.annotation.LogExecutionTime;
import com.asset.ccat.air_service.defines.AIRDefines;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.factories.ParserFactory;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Mahmoud Shehab
 */
@Component
public class AIRParser {

    private final ParserFactory parserFactory;

    @Autowired
    public AIRParser(ParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    private Document createDocument(String xml) throws IOException, SAXException, ParserConfigurationException {
        try (InputStream inputStream = new ByteArrayInputStream(xml.getBytes())) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(inputStream);
        }
    }

    @LogExecutionTime
    public HashMap<String, Object> parse(String xml) throws SAXException, IOException, AIRServiceException, ParserConfigurationException {
        CCATLogger.DEBUG_LOGGER.debug("Start parsing the AIR XML response");
        if (xml == null || xml.isEmpty() || xml.isBlank())
            return null;

        HashMap<String, Object> responseStrArr = new HashMap<>();
        Document document = createDocument(xml);
        Node node = document.getElementsByTagName("struct").item(0);
        NodeList members = node.getChildNodes();

        for (int x = 0; x < members.getLength(); x++) {
            Node currentNode = members.item(x);
            if (currentNode.getNodeName().equals("member")) {
                Node nameNode = currentNode.getChildNodes().item(0);
                String name = nameNode.getTextContent().trim();
                if (name.equals("\n") || name.equals("")) {
                    nameNode = currentNode.getChildNodes().item(1);
                    name = nameNode.getTextContent();
                }

                if (AIRDefines.AIR_TAGS.SIMPLE_TAGS.contains(name))
                    parseSimpleTags(name, currentNode, responseStrArr);
                else
                    parserFactory.parseAirResponse(name, currentNode, responseStrArr);
            }
        }
        return responseStrArr;
    }

    public void parseSimpleTags(String nodeName, Node currentNode, Map<String, Object> responseStrArr) {
        Node valueNode = currentNode.getChildNodes().item(1);
        String valueStr = valueNode.getTextContent();
        if (valueStr.equals(nodeName)) {
            valueNode = currentNode.getChildNodes().item(3);
            valueStr = valueNode.getTextContent();
        }
        valueStr = valueStr.trim();
        responseStrArr.put(nodeName, valueStr);
    }

}
