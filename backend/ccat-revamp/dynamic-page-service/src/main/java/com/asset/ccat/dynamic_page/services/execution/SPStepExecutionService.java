package com.asset.ccat.dynamic_page.services.execution;

import com.asset.ccat.dynamic_page.constants.ParameterTypes;
import com.asset.ccat.dynamic_page.database.dao.StoredProcedureDao;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.mappers.StoredProcedureStepResponseMapper;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageStepOutputModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureParameterModel;
import com.asset.ccat.dynamic_page.utils.GeneralUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public class SPStepExecutionService {

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    @Autowired
    private StoredProcedureDao storedProcedureDao;

    @Autowired
    private StoredProcedureStepResponseMapper storedProcedureStepResponseMapper;

    public List<DynamicPageStepOutputModel> execute(
            ProcedureConfigurationModel configurationModel, HashMap<String, Object> inputParametersVals
    ) throws DynamicPageException {
        // prepare inputParamMap
        CCATLogger.DEBUG_LOGGER.debug("Preparing step input parameters for stored procedure call");
        MapSqlParameterSource spInParamters = prepareInputParameters(configurationModel.getParameters(), inputParametersVals);

        CCATLogger.DEBUG_LOGGER.debug("Calling the SP");
        HashMap<String, Object> responseMap = storedProcedureDao.callStoredProcedure(configurationModel, spInParamters);

        CCATLogger.DEBUG_LOGGER.debug("Mapping returned response map from sp call");
        List<DynamicPageStepOutputModel> ouputList = storedProcedureStepResponseMapper.map(configurationModel, responseMap);

        CCATLogger.DEBUG_LOGGER.info("Finished executing stored procedure step");
        return ouputList;

    }

    private MapSqlParameterSource prepareInputParameters(List<ProcedureParameterModel> stepParametersList, HashMap<String, Object> inputParametersVals) throws DynamicPageException {
        MapSqlParameterSource paramaters = new MapSqlParameterSource();
        List<ProcedureParameterModel> inputparamList = stepParameterUtil.getProcedureInputParamList(stepParametersList);
        if (inputparamList != null && !inputparamList.isEmpty()) {
            for (ProcedureParameterModel inStepParameter : inputparamList) {
                String paramName = inStepParameter.getParameterName();
                Object paramVal = inputParametersVals == null || inputParametersVals.get(paramName) == null ?
                        inStepParameter.getDefaultValue() : inputParametersVals.get(paramName);
                if (paramVal == null) {
                    CCATLogger.DEBUG_LOGGER.debug("Input param [" + paramName + "] is not provided and has no default value");
                    throw new DynamicPageException(ErrorCodes.ERROR.MISSING_FIELD, Defines.SEVERITY.ERROR, paramName);
                }
                int sqlType = generalUtils.getSqlType(inStepParameter.getParameterDataType());
                paramVal = generalUtils.castObject(paramVal, inStepParameter.getParameterDataType(), inStepParameter.getDateFormat());

                paramaters.addValue(paramName, paramVal, sqlType);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("No input parameters are configured");
        }
        return paramaters;
    }
}
