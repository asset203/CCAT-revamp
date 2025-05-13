package com.asset.ccat.dynamic_page.parsers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class JsonParser {

    @Autowired
    private ObjectMapper objectMapper;

    public HashMap<String, Object> parseJsonResponse(String responseString, List<HttpParameterModel> outputParametersList) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started JsonParser - parseJsonResponse()");
            HashMap<String, Object> responseMap = new HashMap<>();

            if (outputParametersList != null && !outputParametersList.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("Start converting JSON string to Map using jackson");
                JsonNode jsonNode = objectMapper.readTree(responseString);
                Map<String, Object> jsonMap;
                
                if (jsonNode.isArray()) {
                    // If it's an array, wrap it in a map with a default key
                    jsonMap = new HashMap<>();
                    jsonMap.put("data", objectMapper.convertValue(jsonNode, List.class));
                } else {
                    // If it's an object, convert it directly to a map
                    jsonMap = objectMapper.convertValue(jsonNode, Map.class);
                }
                
                CCATLogger.DEBUG_LOGGER.debug("Json Map >> " + jsonMap);

                CCATLogger.DEBUG_LOGGER.debug("Start extracting outputs from json map");
                for (HttpParameterModel parameterModel : outputParametersList) {
                    String paramName = parameterModel.getParameterName();
                    String jsonPath = parameterModel.getJsonPath() == null || parameterModel.getJsonPath().isBlank() ?
                            paramName : parameterModel.getJsonPath().trim();

                    Object paramVal = getValueFromJsonMap(jsonMap, jsonPath);
                    responseMap.put(paramName, paramVal);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No configured outputs found, skipping Json parsing");
            }

            CCATLogger.DEBUG_LOGGER.debug("Ended JsonParser - parseJsonResponse()");
            return responseMap;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occurred while parsing JSON response >> " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing JSON response >> " + ex.getMessage(), ex);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_HTTP_RESPONSE_BODY_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private Object getValueFromJsonMap(Map<String, Object> jsonMap, String jsonPath) {
        Object value = null;
        if (!jsonPath.contains(".")) {
            value = jsonMap.get(jsonPath);
        } else {
            String subParamPath = "";
            while (jsonPath.contains(".") && jsonMap != null) {
                int indexOfDot = jsonPath.indexOf(".");
                subParamPath = jsonPath.substring(0, indexOfDot);
                jsonPath = jsonPath.substring(indexOfDot + 1);
                jsonMap = jsonMap.get(subParamPath) == null ? null : (Map) jsonMap.get(subParamPath);
            }
            value = jsonMap == null ? null : jsonMap.get(jsonPath);
        }
        return value;
    }
}
