package com.asset.ccat.dynamic_page.utils;

import com.asset.ccat.dynamic_page.constants.ParameterTypes;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class StepParameterUtil {

    ///...................... SP step parameters utils...........................///
    public ProcedureParameterModel getSPStepResponseCodeParameterModel(List<ProcedureParameterModel> outputParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getSPStepResponseCodeParameterModel()");
        ProcedureParameterModel responseCodeParamModel = null;
        if (outputParamList == null || outputParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step output parameters list is empty");
        } else {
            responseCodeParamModel = outputParamList.stream()
                    .filter((stepOutputParam) ->
                            stepOutputParam.getResponseCode() != null && stepOutputParam.getResponseCode())
                    .findFirst()
                    .orElse(null);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getSPStepResponseCodeParameterModel()");
        return responseCodeParamModel;
    }

    public ProcedureParameterModel getSPStepResponseDescParameterModel(List<ProcedureParameterModel> outputParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getSPStepResponseDescParameterModel()");
        ProcedureParameterModel responseCodeParamModel = null;
        if (outputParamList == null || outputParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step output parameters list is empty");
        } else {
            responseCodeParamModel = outputParamList.stream()
                    .filter((stepOutputParam) ->
                            stepOutputParam.getResponseDescription() != null && stepOutputParam.getResponseDescription())
                    .findFirst()
                    .orElse(null);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getSPStepResponseDescParameterModel()");
        return responseCodeParamModel;
    }

    public List<ProcedureParameterModel> getProcedureInputParamList(List<ProcedureParameterModel> stepParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getProcedureInputParamList()");
        List<ProcedureParameterModel> inputParamList = null;
        if (stepParamList == null || stepParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step parameters list is empty");
        } else {
            inputParamList = stepParamList.stream()
                    .filter((stepParam) -> stepParam.getParameterType().equals(ParameterTypes.IN.id))
                    .collect(Collectors.toList());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getProcedureInputParamList()");
        return inputParamList;
    }

    public Map<String, ProcedureParameterModel> getProcedureOutputParamMap(List<ProcedureParameterModel> stepParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getProcedureOutputParamList()");
        Map<String, ProcedureParameterModel> outputParamMap = null;
        if (stepParamList != null && !stepParamList.isEmpty()) {
            outputParamMap = stepParamList.stream()
                    .filter((stepParam) -> stepParam.getParameterType().equals(ParameterTypes.OUT.id))
                    .collect(Collectors.toMap(ProcedureParameterModel::getParameterNameUpperCase,
                            Function.identity()));

        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getProcedureOutputParamList()");
        return outputParamMap;
    }

    ///...................... HTTP step parameters utils...........................///
    public HttpParameterModel getHTTPStepResponseCodeParameterModel(List<HttpParameterModel> outputParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getHTTPStepResponseCodeParameterModel()");
        HttpParameterModel responseCodeParamModel = null;
        if (outputParamList == null || outputParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step output parameters list is empty");
        } else {
            responseCodeParamModel = outputParamList.stream()
                    .filter((stepOutputParam) ->
                            stepOutputParam.getResponseCode() != null && stepOutputParam.getResponseCode())
                    .findFirst()
                    .orElse(null);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getHTTPStepResponseCodeParameterModel()");
        return responseCodeParamModel;
    }

    public HttpParameterModel getHTTPStepResponseDescParameterModel(List<HttpParameterModel> outputParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getHTTPStepResponseDescParameterModel()");
        HttpParameterModel responseCodeParamModel = null;
        if (outputParamList == null || outputParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step output parameters list is empty");
        } else {
            responseCodeParamModel = outputParamList.stream()
                    .filter((stepOutputParam) ->
                            stepOutputParam.getResponseDescription() != null && stepOutputParam.getResponseDescription())
                    .findFirst()
                    .orElse(null);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getHTTPStepResponseDescParameterModel()");
        return responseCodeParamModel;
    }

    public List<HttpParameterModel> getHTTPInputParamList(List<HttpParameterModel> stepParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getHTTPInputParamList()");
        List<HttpParameterModel> inputParamList = null;
        if (stepParamList == null || stepParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step parameters list is empty");
        } else {
            inputParamList = stepParamList.stream()
                    .filter((stepParam) -> stepParam.getParameterType().equals(ParameterTypes.IN.id))
                    .collect(Collectors.toList());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getHTTPInputParamList()");
        return inputParamList;
    }

    public List<HttpParameterModel> getHTTPOutputParamList(List<HttpParameterModel> stepParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getHTTPOutputParamList()");
        List<HttpParameterModel> outputParamList = null;
        if (stepParamList == null || stepParamList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Step parameters list is empty");
        } else {
            outputParamList = stepParamList.stream()
                    .filter((stepParam) -> stepParam.getParameterType().equals(ParameterTypes.OUT.id))
                    .collect(Collectors.toList());

        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getHTTPOutputParamList()");
        return outputParamList;
    }

    public Map<String, HttpParameterModel> getHTTPOutputParamMap(List<HttpParameterModel> stepParamList) {
        CCATLogger.DEBUG_LOGGER.debug("Started StepParameterUtil - getHTTPOutputParamMap()");
        Map<String, HttpParameterModel> outputParamMap = null;
        if (stepParamList != null && !stepParamList.isEmpty()) {
            outputParamMap = stepParamList.stream()
                    .filter((stepParam) -> stepParam.getParameterType().equals(ParameterTypes.OUT.id))
                    .collect(Collectors.toMap(HttpParameterModel::getParameterName,
                            Function.identity()));

        }
        CCATLogger.DEBUG_LOGGER.debug("Ended StepParameterUtil - getHTTPOutputParamMap()");
        return outputParamMap;
    }

}
