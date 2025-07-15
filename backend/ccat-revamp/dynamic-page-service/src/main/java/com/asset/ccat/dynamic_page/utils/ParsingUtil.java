package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.constants.ParameterTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ResponseParameterParsingResponse;
import com.github.wnameless.json.flattener.JsonFlattener;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class ParsingUtil {

    private static String getXPath(Node node) {
        Node parent = node.getParentNode();
        if (parent == null) {
            return node.getNodeName();
        }

        return getXPath(parent) + "/" + node.getNodeName();
    }

    public List<ProcedureParameterModel> parseProcedureParameters(String parametersQuery) throws DynamicPageException {
        try {
            List<ProcedureParameterModel> parametersList = new ArrayList<>();

            //remove brackers
            parametersQuery = parametersQuery.replace("(", "");
            parametersQuery = parametersQuery.replace(")", "");
            parametersQuery = parametersQuery.replace("\n", "");
            parametersQuery = parametersQuery.replaceAll("\\s+", " ");

            //remove additional spaces
            parametersQuery = parametersQuery.trim();

            // split by [,] to get list of param entries -- entry: [paramName in/out paramType]
            String[] paramEntries = parametersQuery.split(",");

            //start parsing each entry to parameter model
            int order = 1;
            for (String entry : paramEntries) {
                //skip empty entries
                if (entry.isEmpty() || entry.isBlank()) {
                    continue;
                }

                String[] paramDetails = entry.trim().split(" ");

                ProcedureParameterModel parameterModel = new ProcedureParameterModel();
                parameterModel.setParameterOrder(order);
                parameterModel.setDisplayOrder(order++);
                parameterModel.setParameterName(paramDetails[0].trim().replaceAll("^\"|\"$", ""));//remove double quotes if present
                parameterModel.setDisplayName(paramDetails[0].trim().replaceAll("^\"|\"$", ""));//remove double quotes if present
                parameterModel.setParameterType(paramDetails[1].trim().toLowerCase().equals("in") ? ParameterTypes.IN.id : ParameterTypes.OUT.id);
                parameterModel.setParameterDataType(getType(paramDetails[2].trim().toLowerCase()));

                parametersList.add(parameterModel);
            }
            return parametersList;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse query [" + parametersQuery + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private int getType(String databaseType) {
        if (databaseType.contains("number")) {
            return ParameterDataTypes.INT.id;
        } else if (databaseType.contains("sys_refcursor")) {
            return ParameterDataTypes.CURSOR.id;
        } else if (databaseType.contains("date")) {
            return ParameterDataTypes.DATE.id;
        } else {
            return ParameterDataTypes.STRING.id;
        }
    }

    public List<HttpParameterModel> parseHttpParameters(String parametersQuery) throws DynamicPageException {
        try {

            List<HttpParameterModel> parametersList = new ArrayList<>();
            String regex = "\\$(.*?)\\$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(parametersQuery);
            int order = 1;

            while (matcher.find()) {
                String parameterName = matcher.group(1);
                HttpParameterModel parameterModel = new HttpParameterModel();
                parameterModel.setParameterName(parameterName);
                parameterModel.setDisplayName(parameterName);
                parameterModel.setParameterDataType(ParameterDataTypes.STRING.id);
                parameterModel.setParameterType(ParameterTypes.IN.id);
                parameterModel.setParameterOrder(order);
                parameterModel.setDisplayOrder(order++);

                parametersList.add(parameterModel);
            }
            return parametersList;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse query [" + parametersQuery + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    public ResponseParameterParsingResponse parseXMLHttpParameters(String xmlFileContent) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started ParsingUtil -> parseXMLHttpParameters()");
            List<HttpParameterModel> parametersList = new ArrayList<>();
            List<String> paths = new ArrayList<>();
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "//*[not(*)]";
            //String expression = "//*[not(*) or self::dummyTriggerParamsList]";
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlFileContent));
            Document document = db.parse(is);

            document.getDocumentElement().normalize();

            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
           /* for(int i = 0 ; i < nodeList.getLength(); i++) {
                String xpathName = getXPath(nodeList.item(i)).replace("#document","");
                String parameterName = xpathName;
                //If the Xpath have more than one node

                if(xpathName.contains("/")){
                    String[] split = xpathName.split("/");
                    parameterName = split[split.length - 1];
                }

                HttpParameterModel parameterModel = new HttpParameterModel();
                parameterModel.setParameterName(parameterName);
                parameterModel.setDisplayName(parameterName);
                parameterModel.setxPath(xpathName);
                parameterModel.setParameterDataType(
                        parametersList.contains(parameterModel)
                                ? ParameterDataTypes.CURSOR.id
                                : ParameterDataTypes.STRING.id
                );
                parameterModel.setParameterType(ParameterTypes.OUT.id);
                parameterModel.setParameterOrder(i);
                parameterModel.setDisplayOrder(i);
                paths.add(xpathName);
                parametersList.add(parameterModel);
            }*/
            // 1️ First pass: count occurrences of leaf XPath
            Map<String, Integer> xpathCounts = new HashMap<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                String xpathName = getXPath(nodeList.item(i)).replace("#document", "");
                xpathCounts.put(xpathName, xpathCounts.getOrDefault(xpathName, 0) + 1);
            }

            // Maintain a set of parent paths already added
            Set<String> addedParentPaths = new HashSet<>();

            for (int i = 0; i < nodeList.getLength(); i++) {
                String xpathName = getXPath(nodeList.item(i)).replace("#document", "");
                String parameterName = xpathName;

                if (xpathName.contains("/")) {
                    String[] split = xpathName.split("/");
                    parameterName = split[split.length - 1];
                }

                int occurrences = xpathCounts.get(xpathName);

                if (occurrences > 1) {
                    // Detected duplicate leaf node ⇒ promote parent as CURSOR param
                    String parentXPath = xpathName.substring(0, xpathName.lastIndexOf("/"));
                    String parentName = parentXPath.substring(parentXPath.lastIndexOf("/") + 1);

                    if (!addedParentPaths.contains(parentXPath)) {
                        HttpParameterModel parameterModel = new HttpParameterModel();
                        parameterModel.setParameterName(parentName);
                        parameterModel.setDisplayName(parentName);
                        parameterModel.setxPath(parentXPath);
                        parameterModel.setParameterDataType(ParameterDataTypes.CURSOR.id);
                        parameterModel.setParameterType(ParameterTypes.OUT.id);
                        parameterModel.setParameterOrder(parametersList.size());
                        parameterModel.setDisplayOrder(parametersList.size());

                        parametersList.add(parameterModel);
                        paths.add(parentXPath);
                        addedParentPaths.add(parentXPath);
                    }

                    continue;  // Skip adding individual leaf paramKey/paramValue
                }

                // Unique leaf node ⇒ add normally
                HttpParameterModel parameterModel = new HttpParameterModel();
                parameterModel.setParameterName(parameterName);
                parameterModel.setDisplayName(parameterName);
                parameterModel.setxPath(xpathName);
                parameterModel.setParameterDataType(ParameterDataTypes.STRING.id);
                parameterModel.setParameterType(ParameterTypes.OUT.id);
                parameterModel.setParameterOrder(parametersList.size());
                parameterModel.setDisplayOrder(parametersList.size());

                parametersList.add(parameterModel);
                paths.add(xpathName);

            }
            CCATLogger.DEBUG_LOGGER.debug("Ended ParsingUtil -> parseXMLHttpParameters() Successfully");
            return new ResponseParameterParsingResponse(parametersList, paths);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse parameters [" + xmlFileContent + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    public ResponseParameterParsingResponse parseJsonHttpParameters(String json) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started ParsingUtil -> parseJsonHttpParameters()");

            List<HttpParameterModel> parametersList = new ArrayList<>();
            List<String> paths = new ArrayList<>();
            Map<String, Object> flattenedJsonMap = JsonFlattener.flattenAsMap(json);
            List<String> jsonPaths = new ArrayList<>(flattenedJsonMap.keySet());
            for (int i = 0; i < jsonPaths.size(); i++) {
                String jsonPath = jsonPaths.get(i);
                String parameterName = jsonPath;
                //If the jsonPathName have more than one node

                if (jsonPath.contains(".")) {
                    String[] split = jsonPath.split("\\.");
                    parameterName = split[split.length - 1];
                }

                HttpParameterModel parameterModel = new HttpParameterModel();
                parameterModel.setParameterName(parameterName);
                parameterModel.setDisplayName(parameterName);
                parameterModel.setParameterDataType(ParameterDataTypes.STRING.id);
                parameterModel.setParameterType(ParameterTypes.OUT.id);
                parameterModel.setParameterOrder(i);
                parameterModel.setDisplayOrder(i);
                parameterModel.setJsonPath(jsonPath);
                paths.add(jsonPath);
                parametersList.add(parameterModel);
            }
            CCATLogger.DEBUG_LOGGER.debug("Ended ParsingUtil -> parseJsonHttpParameters() Successfully");
            return new ResponseParameterParsingResponse(parametersList, paths);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse parameters [" + json + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }

    }

    public ResponseParameterParsingResponse parseXSDHttpParameters(String xsdFileContent, String rootName) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started ParsingUtil -> parseXSDHttpParameters()");
            XmlGeneratorFromXSD xmlGeneratorFromXSD = new XmlGeneratorFromXSD();
            String xmlContent = xmlGeneratorFromXSD.generateXmlInstance(xsdFileContent, rootName);
            List<HttpParameterModel> parametersList = new ArrayList<>();
            List<String> paths = new ArrayList<>();
            XPath xPath = XPathFactory.newInstance().newXPath();
            String expression = "//*[not(*)]";
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlContent));
            Document document = db.parse(is);

            document.getDocumentElement().normalize();

            NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(document, XPathConstants.NODESET);
            for (int i = 0; i < nodeList.getLength(); i++) {
                String xpathName = getXPath(nodeList.item(i)).replace("#document", "");
                String parameterName = xpathName;
                //If the Xpath have more than one node

                if (xpathName.contains("/")) {
                    String[] split = xpathName.split("/");
                    parameterName = split[split.length - 1];
                }

                HttpParameterModel parameterModel = new HttpParameterModel();
                parameterModel.setParameterName(parameterName);
                parameterModel.setDisplayName(parameterName);
                parameterModel.setParameterDataType(ParameterDataTypes.STRING.id);
                parameterModel.setParameterType(ParameterTypes.OUT.id);
                parameterModel.setParameterOrder(i);
                parameterModel.setDisplayOrder(i);
                parameterModel.setxPath(xpathName);
                paths.add(xpathName);
                parametersList.add(parameterModel);
            }
            CCATLogger.DEBUG_LOGGER.debug("Ended ParsingUtil -> parseXSDHttpParameters() Successfully");
            return new ResponseParameterParsingResponse(parametersList, paths);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse parameters [" + xsdFileContent + "]", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_PARAMETERS_QUERY_FAILED, Defines.SEVERITY.ERROR);
        }
    }


}
