package com.asset.ccat.dynamic_page.services.execution;

import com.asset.ccat.dynamic_page.adapters.HttpAdapter;
import com.asset.ccat.dynamic_page.constants.HTTPContentType;
import com.asset.ccat.dynamic_page.constants.HTTPMethodType;
import com.asset.ccat.dynamic_page.constants.HTTPResponseFormType;
import com.asset.ccat.dynamic_page.constants.ParameterDataTypes;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.mappers.HTTPStepResponseMapper;
import com.asset.ccat.dynamic_page.models.dynamic_page.*;
import com.asset.ccat.dynamic_page.utils.GeneralUtils;
import com.asset.ccat.dynamic_page.utils.StepParameterUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class HTTPStepExecutionService {

    @Autowired
    private GeneralUtils generalUtils;

    @Autowired
    private StepParameterUtil stepParameterUtil;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HttpAdapter httpAdapter;

    @Autowired
    private HTTPStepResponseMapper httpStepResponseMapper;

    public List<DynamicPageStepOutputModel> execute(
            HttpConfigurationModel configurationModel, HashMap<String, Object> inputParametersVals
    ) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start executing HTTP step");
        Map<String, Object> responseMap = httpAdapter.handleHttpRequest(configurationModel, inputParametersVals);
        CCATLogger.DEBUG_LOGGER.debug("Mapping returned response String returned from HTTP request");
        List<DynamicPageStepOutputModel> ouputList = httpStepResponseMapper.map(configurationModel, responseMap);
        CCATLogger.DEBUG_LOGGER.debug("Finished executing HTTP step");
        return ouputList;
    }


}
