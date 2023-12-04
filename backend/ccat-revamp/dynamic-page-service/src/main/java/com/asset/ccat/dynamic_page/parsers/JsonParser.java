package com.asset.ccat.dynamic_page.parsers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.fasterxml.jackson.databind.ObjectMapper;
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
                Map<String, Object> jsonMap = objectMapper.readValue(responseString, Map.class);
                CCATLogger.DEBUG_LOGGER.debug("Json Map >> " + jsonMap);

                CCATLogger.DEBUG_LOGGER.debug("Start extracting outputs from json map");
                for (HttpParameterModel parameterModel : outputParametersList) {
                    String paramName = parameterModel.getParameterName();
                    String jsonPath = parameterModel.getJsonPath() == null || parameterModel.getJsonPath().isBlank() ?
                            paramName : parameterModel.getJsonPath().trim();
//                    boolean isList = parameterModel.getParameterDataType().equals(ParameterDataTypes.CURSOR.id);

                    Object paramVal = getValueFromJsonMap(jsonMap, jsonPath);
                    responseMap.put(paramName, paramVal);

//                    if(!isList){
//                        responseMap.put(paramName)
//                    }
                }

            } else {
                CCATLogger.DEBUG_LOGGER.debug("No configured outputs found, skipping Json parsing");
            }

            CCATLogger.DEBUG_LOGGER.debug("Ended JsonParser - parseJsonResponse()");
            return responseMap;
        }
//        catch (DynamicPageException ex) {
//            throw ex;
//        }
        catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Exception occurred while parsing xml response >> " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Exception occurred while parsing xml response >> " + ex.getMessage(), ex);
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
