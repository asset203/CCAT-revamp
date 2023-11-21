package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HttpUtils {

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    @Autowired
    private ObjectMapper objectMapper;

    public String replaceInputValueInString(HttpParameterModel inputParam, Object value, String targetString) throws DynamicPageException {
        // TODO this handles only single input handle list as well(unconfirmed)
        String key = inputParam.getParameterName();
        CCATLogger.DEBUG_LOGGER.debug("Replacing [" + key + "] of type [" + inputParam.getParameterDataType() + "] in string");
        String stringValue = value == null ? null : generalUtils.getString(value, inputParam.getParameterDataType(), inputParam.getDateFormat());

        if (stringValue == null || stringValue.isBlank()) {
            CCATLogger.DEBUG_LOGGER.debug("Input parameter [" + key + "] is not provided, " +
                    "using default value");
            stringValue = inputParam.getDefaultValue();
            if (stringValue == null || stringValue.isBlank()) {
                CCATLogger.DEBUG_LOGGER.debug("Input parameter [" + key + "] has no default value");
                throw new DynamicPageException(ErrorCodes.ERROR.MISSING_FIELD,
                        Defines.SEVERITY.ERROR, "Input parameter [" + key + "]");
            }
        }
        targetString = targetString.replace("$" + key + "$", stringValue);
        return targetString;
    }

    public List<Map> parseRequestHeaders(String headers) {
        CCATLogger.DEBUG_LOGGER.debug("Start preparing request headers [" + headers + "]");
        List<Map> headersMap = null;
        try {
            headersMap = objectMapper.readValue(headers, new TypeReference<List<Map>>() {
            });
        } catch (JsonProcessingException e) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to parse headers string to map >> " + e.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to parse headers string to map >> " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.debug("Request headers will be ignored");
        }
        return headersMap;
    }

    public String getJsonRequestBody(List<HttpParameterModel> inputParamList, HashMap<String, Object> inputParamVals) throws DynamicPageException {
        try {
            HashMap<String, Object> requestMap = new HashMap<>();
            for (HttpParameterModel inputParam : inputParamList) {
                String key = inputParam.getParameterName();
                Object val = inputParamVals == null || inputParamVals.get(key) == null ? inputParam.getDefaultValue() : inputParamVals.get(key);
                if (val == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Input parameter [" + key + "] is not provided and has no default value");
                    throw new DynamicPageException(ErrorCodes.ERROR.MISSING_FIELD,
                            Defines.SEVERITY.ERROR, "Input parameter [" + key + "]");
                }
                if (inputParam.getParameterDataType().equals(ParameterDataTypes.DATE.id)) {
                    requestMap.put(key, generalUtils.castObject(val, inputParam.getParameterDataType(), inputParam.getDateFormat()));
                } else {
                    requestMap.put(key, generalUtils.castObject(val, inputParam.getParameterDataType(), null));
                }
            }

            String jsonBody = objectMapper.writeValueAsString(requestMap);
            return jsonBody;
        } catch (JsonProcessingException e) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to convert request map to json string >> " + e.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to convert request map to json string  >> " + e.getMessage(), e);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_HTTP_REQUEST_BODY_FAILED);
        } catch (DynamicPageException e) {
            throw e;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Error occured while parsing request body >> " + e.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Error occured while parsing request body >> " + e.getMessage(), e);
            throw new DynamicPageException(ErrorCodes.ERROR.PARSE_HTTP_REQUEST_BODY_FAILED);
        }
    }
}
