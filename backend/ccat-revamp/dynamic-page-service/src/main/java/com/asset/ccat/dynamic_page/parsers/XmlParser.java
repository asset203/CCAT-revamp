package com.asset.ccat.dynamic_page.parsers;

import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpResponseMappingModel;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class XmlParser {

    private final DocumentBuilderFactory factory;
    private final DocumentBuilder builder;
    private final XPath xPathInstance;

    public XmlParser() throws ParserConfigurationException {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        xPathInstance = XPathFactory.newInstance().newXPath();
    }

    private Document createDocument(String xml) throws IOException, SAXException {
        xml = xml.replaceAll(">\\s+<", "><"); // remove any extra white spaces
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());
        Document document = builder.parse(inputStream);
        return document;
    }

    // This parser extracts the desired output parameters from given XML to a map
    public HashMap<String, Object> parseXmlResponse(String responseString, List<HttpParameterModel> outputParametersList) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started XmlParser - parseXmlResponse()");
        HashMap<String, Object> responseMap = new HashMap<>();
        try {
            if (outputParametersList != null && !outputParametersList.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("Creating document from xml response string");

                Document document = createDocument(responseString);
                for (HttpParameterModel parameterModel : outputParametersList) {
                    String paramName = parameterModel.getParameterName();
                    Object paramVal = null;
                    String xPathExp = parameterModel.getxPath() == null || parameterModel.getxPath().isBlank() ? "//" + paramName : parameterModel.getxPath().trim();
                    boolean isList = parameterModel.getParameterDataType().equals(ParameterDataTypes.CURSOR.id);
//                            && parameterModel.getHttpResponseMappingModels() != null
//                            && !parameterModel.getHttpResponseMappingModels().isEmpty();


                    if (!isList) {
                        //handling flat data
                        CCATLogger.DEBUG_LOGGER.debug("Handling flat data structure");
                        CCATLogger.DEBUG_LOGGER.debug("Retrieving output parameter from xml using x-path expression >> " + xPathExp);
                        paramVal = getNodeValueByXpath(xPathExp, document, paramName);
                    } else {
                        //handling list data
                        CCATLogger.DEBUG_LOGGER.debug("Retrieving output parameter from xml using x-path expression >> " + xPathExp);
                        NodeList targetList = getNodesByXPath(xPathExp, document, paramName);
                        if (targetList != null && targetList.getLength() > 0) {

                            List<HttpResponseMappingModel> responseMappings = parameterModel.getHttpResponseMappingModels();
                            List<HashMap<String, String>> list = new ArrayList<>();

                            if (responseMappings != null && !responseMappings.isEmpty()) {
                                CCATLogger.DEBUG_LOGGER.debug("Using defined response mappings to get list items");
                                /*
                                 * iterate over nodes
                                 * create document for each node
                                 * apply xpath of each mapping on created document
                                 * perform mapping
                                 * */

                                for (int i = 0; i < targetList.getLength(); i++) {
                                    Node itemNode = targetList.item(i);
                                    Document itemNodeXml = createDocumentFromNode(itemNode);
                                    HashMap<String, String> item = new HashMap<>();

                                    for(HttpResponseMappingModel responseMapping : responseMappings){
                                        Object itemFieldVal = getNodeValueByXpath(responseMapping.getHeaderName(),
                                                itemNodeXml, responseMapping.getHeaderDisplayName());
                                        String fieldStringVal = itemFieldVal == null ? "" : String.valueOf(itemFieldVal);
                                        item.put(responseMapping.getHeaderName(), fieldStringVal);
                                    }
                                    list.add(item);
                                }
                            } else {
                                CCATLogger.DEBUG_LOGGER.debug("Using default XML structure to get list items");
                                for (int i = 0; i < targetList.getLength(); i++) {
                                    Node listItem = targetList.item(i);
                                    // skip comment nodes
                                    if (listItem.getNodeType() == Node.COMMENT_NODE) {
                                        continue;
                                    }
                                    NodeList fields = listItem.getChildNodes();
                                    HashMap<String, String> record = new HashMap<>();
                                    for (int j = 0; j < fields.getLength(); j++) {
                                   /*     Node field = fields.item(j);
                                        // note that you can't access the value directly
                                        record.put(field.getNodeName(), field.getFirstChild().getNodeValue());*/
                                        Node field = fields.item(j);

                                        // Only process element nodes (skip text nodes or whitespace)
                                        if (field.getNodeType() == Node.ELEMENT_NODE) {
                                            String fieldValue = "";

                                            Node firstChild = field.getFirstChild();
                                            if (firstChild != null) {
                                                fieldValue = firstChild.getNodeValue();
                                            }

                                            record.put(field.getNodeName(), fieldValue);
                                        }
                                    }
                                    list.add(record);
                                }
                            }
                            paramVal = list;
                        }
                    }
                    responseMap.put(paramName, paramVal);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No configured outputs found, skipping xml parsing");
            }
            CCATLogger.DEBUG_LOGGER.debug("Ended XmlParser - parseXmlResponse()");
            return responseMap;
        } catch (DynamicPageException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occurred while parsing xml response >> " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing xml response >> " + ex.getMessage(), ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_HTTP_RESPONSE_BODY_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private NodeList getNodesByXPath(String expression, Document document, String paramName) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started XmlParser - getNodesByXPath()");
        try {
            CCATLogger.DEBUG_LOGGER.debug("Compiling expression >> " + expression);
//            XPath xPathInstance = XPathFactory.newInstance().newXPath();
            NodeList nodeList = (NodeList) xPathInstance.compile(expression).evaluate(
                    document, XPathConstants.NODESET);
            if (nodeList == null || nodeList.getLength() <= 0) {
                CCATLogger.DEBUG_LOGGER.debug("Configured X-path expression returned nothing," +
                        " invalid expression");
//                throw new Exception("Compiled expression returned zero nodes");
            }
            CCATLogger.DEBUG_LOGGER.debug("Ended XmlParser - getNodesByXPath()");
            return nodeList;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Error Occured while compiling x-path expression >> " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Error Occured while compiling x-path expression >> " + ex.getMessage(), ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_XPATH_EXPRESSION, Defines.SEVERITY.ERROR, expression, paramName);
        } finally {
            xPathInstance.reset();
        }
    }

    private Document createDocumentFromNode(Node node) {
        Document document = builder.newDocument();
        Element root = document.createElement("root");
        document.appendChild(root);
        document.adoptNode(node);
        root.appendChild(node);
        return document;
    }

    private Object getNodeValueByXpath(String xpathExp, Document document, String nodeName) throws DynamicPageException {
        Object paramVal = null;
        if (!xpathExp.endsWith("/node()")) {
            CCATLogger.DEBUG_LOGGER.debug("Append [/node()] to xpath expression");
            xpathExp += "/node()";
        }
        NodeList xpathResult = getNodesByXPath(xpathExp, document, nodeName);
        if (xpathResult != null && xpathResult.getLength() > 0) {
            Node targetNode = xpathResult.item(0);
            paramVal = targetNode.getNodeValue();
        }
        return paramVal;
    }
}
