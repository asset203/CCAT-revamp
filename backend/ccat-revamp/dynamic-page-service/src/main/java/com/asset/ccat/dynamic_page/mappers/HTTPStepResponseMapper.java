package com.asset.ccat.dynamic_page.mappers;

import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.asset.ccat.dynamic_page.utils.GeneralUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class HTTPStepResponseMapper {

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    public List<DynamicPageStepOutputModel> map(
            HttpConfigurationModel stepConfig, Map<String, Object> responseMap
    ) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started HTTPStepResponseMapper - map()");
            CCATLogger.DEBUG_LOGGER.info("Start mapping received stored procedure response");

            List<DynamicPageStepOutputModel> outputList = null;
            Integer maxAllowedRecords = stepConfig.getMaxRecords() == null ? 0 : stepConfig.getMaxRecords();
            Map<String, HttpParameterModel> outputParamMap =
                    stepParameterUtil.getHTTPOutputParamMap(stepConfig.getParameters());

            if (outputParamMap != null && !outputParamMap.isEmpty()) {
                if (responseMap != null && !responseMap.isEmpty()) {
                    List<HttpParameterModel> outputParamList = new ArrayList<>(outputParamMap.values());
                    HttpParameterModel responseCodeModel = stepParameterUtil.getHTTPStepResponseCodeParameterModel(outputParamList);
                    HttpParameterModel responseDescModel = stepParameterUtil.getHTTPStepResponseDescParameterModel(outputParamList);

                    if (responseCodeModel != null) {
                        CCATLogger.DEBUG_LOGGER.debug("Start validating response code");
                        validateResponseCode(responseCodeModel, responseDescModel, responseMap, stepConfig.getSuccessCode());
                    } else {
                        CCATLogger.DEBUG_LOGGER.debug("No response code output configured, Skip validating response code");
                    }

                    outputList = outputParamMap.entrySet().stream().map((entry) -> {
                        String respKey = entry.getKey();
                        HttpParameterModel outputParamModel = entry.getValue();
                        Object respVal = responseMap.get(respKey);

                        Integer dataType = outputParamModel.getParameterDataType();
                        Boolean isHidden = outputParamModel.getHidden();
                        Integer parameterOrder = outputParamModel.getDisplayOrder() != null ?
                                outputParamModel.getDisplayOrder() : outputParamModel.getParameterOrder();
                        String parameterName = outputParamModel.getDisplayName() == null || outputParamModel.getDisplayName().isBlank() ?
                                outputParamModel.getParameterName() : outputParamModel.getDisplayName();

                        Integer parameterId = outputParamModel.getId();
                        Object parameterValue;
                        if (dataType.equals(ParameterDataTypes.CURSOR.id)) {
                            List list = (List) respVal;
                            parameterValue = mapListOutput(outputParamModel, list, maxAllowedRecords);

                        } else {
                            parameterValue = generalUtils.castObject(respVal, dataType, null);
                        }
                        return new DynamicPageStepOutputModel(parameterName,
                                dataType,
                                parameterOrder,
                                parameterId,
                                isHidden,
                                parameterValue);
                    }).sorted().collect(Collectors.toList());

                } else {
                    CCATLogger.DEBUG_LOGGER.debug("Received Empty response");
                    throw new DynamicPageException(ErrorCodes.ERROR.INVALID_RESPONSE, Defines.SEVERITY.ERROR);
                }
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No configured output params configured");
            }

            CCATLogger.DEBUG_LOGGER.info("Finished mapping received Http response");
            CCATLogger.DEBUG_LOGGER.debug("Ended HTTPStepResponseMapper - map()");
            return outputList;
        } catch (DynamicPageException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to map Http response : " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to map Http response", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.RESPONSE_MAPPING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private void validateResponseCode(HttpParameterModel responseCodeModel,
                                      HttpParameterModel responseDescModel,
                                      Map<String, Object> responseMap,
                                      String successCode) throws DynamicPageException {
        Object responseCode = responseMap.get(responseCodeModel.getParameterName());
        if (responseCode == null) {
            CCATLogger.DEBUG_LOGGER.debug("Response code is missing from received response");
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_RESPONSE, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Response Code as received >> " + responseCode);
        String responseCodeAsString = generalUtils.getString(responseCode, responseCodeModel.getParameterDataType(), null);
        CCATLogger.DEBUG_LOGGER.debug("Response Code as string >> " + responseCodeAsString);

        String responseDesc = "";
        if (responseDescModel != null && responseMap.get(responseDescModel.getParameterName()) != null) {
            responseDesc = (String) responseMap.get(responseDescModel.getParameterName());
            CCATLogger.DEBUG_LOGGER.debug("Response description >> " + responseDesc);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("No response description configured or received");
        }

        if (!responseCodeAsString.equals(successCode)) {
            // work around for non integer response code TODO: fix this workaround
            String message = "Status Code: " + responseCodeAsString + " Status Msg: " + responseDesc;
            throw new DynamicPageException(ErrorCodes.ERROR.UNKNOWN_ERROR, message, Defines.SEVERITY.ERROR);
        } else {
            CCATLogger.DEBUG_LOGGER.debug("Response code is success");
        }
    }

    private Object mapListOutput(HttpParameterModel outputParamModel,
                                 List array,
                                 Integer maxAllowedRecord) {
        List<HashMap<String, Object>> records = new ArrayList<>();
        for (int i = 0; i < array.size(); i++) {
            if (maxAllowedRecord != 0 && i >= maxAllowedRecord) {
                CCATLogger.DEBUG_LOGGER.info("Max allowed number of records exceeded");
                break;
            }
            Map row = (Map) array.get(i);
            HashMap<String, Object> record = new HashMap<>();
            if (outputParamModel.getHttpResponseMappingModels() != null && !outputParamModel.getHttpResponseMappingModels().isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("Mapping list using defined response mappings");
                outputParamModel.getHttpResponseMappingModels().forEach((mappingModel) ->
                        record.put(mappingModel.getHeaderDisplayName(), row.get(mappingModel.getHeaderName())));
            } else {
                CCATLogger.DEBUG_LOGGER.debug("No Response mapping found, taking response as it is");
                row.entrySet().forEach((entry) -> {
                    Map.Entry mapEntry = (Map.Entry) entry;
                    record.put((String) mapEntry.getKey(), mapEntry.getValue());
                });
            }
            records.add(record);
        }
        return records;
    }
}
