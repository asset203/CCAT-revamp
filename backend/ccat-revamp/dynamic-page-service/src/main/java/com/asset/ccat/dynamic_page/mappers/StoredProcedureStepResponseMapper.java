package com.asset.ccat.dynamic_page.mappers;

import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.dynamic_page.utils.GeneralUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class StoredProcedureStepResponseMapper {

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    public List<DynamicPageStepOutputModel> map(
            ProcedureConfigurationModel stepConfig, HashMap<String, Object> responseMap
    ) throws DynamicPageException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started StoredProcedureStepResponseMapper - map()");
            CCATLogger.DEBUG_LOGGER.info("Start mapping received stored procedure response");

            List<DynamicPageStepOutputModel> outputList = null;

            if (responseMap != null && !responseMap.isEmpty()) {
                Map<String, ProcedureParameterModel> outputParamMap =
                        stepParameterUtil.getProcedureOutputParamMap(stepConfig.getParameters());

                outputList = responseMap.entrySet().stream().map((entry) -> {
                    String respKey = entry.getKey();
                    Object respVal = entry.getValue();
                    ProcedureParameterModel outputParamModel = outputParamMap.get(respKey);

                    Integer dataType = outputParamModel.getParameterDataType();
                    Integer parameterOrder = outputParamModel.getDisplayOrder();
                    Boolean isHidden = outputParamModel.getHidden();
                    String parameterName = outputParamModel.getDisplayName() == null || outputParamModel.getDisplayName().isBlank() ?
                            outputParamModel.getParameterName() : outputParamModel.getDisplayName();

                    Integer parameterId = outputParamModel.getId();

                    Object parameterValue = generalUtils.castObject(respVal, dataType, null);
                    return new DynamicPageStepOutputModel(parameterName,
                            dataType,
                            parameterOrder,
                            parameterId,
                            isHidden,
                            parameterValue);
                }).sorted().collect(Collectors.toList());
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Received response map is empty");
            }

            CCATLogger.DEBUG_LOGGER.info("Finished mapping received stored procedure response");
            CCATLogger.DEBUG_LOGGER.debug("Ended StoredProcedureStepResponseMapper - map()");
            return outputList;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to map stored procedure response : " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.error("Failed to map stored procedure response", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.RESPONSE_MAPPING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}
