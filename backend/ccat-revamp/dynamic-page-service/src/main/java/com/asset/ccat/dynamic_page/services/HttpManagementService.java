package com.asset.ccat.dynamic_page.services;

import com.asset.ccat.dynamic_page.constants.ParametersResponseType;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpParameterModel;
import com.asset.ccat.dynamic_page.models.requests.ParseHttpParametersRequest;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.ResponseParameterParsingRequest;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ResponseParameterParsingResponse;
import com.asset.ccat.dynamic_page.utils.ParsingUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class HttpManagementService {


    private final ParsingUtil parsingUtil;

    public HttpManagementService(ParsingUtil parsingUtil) {
        this.parsingUtil = parsingUtil;
    }

    public List<HttpParameterModel> parseHttpParametersQuery(ParseHttpParametersRequest parseRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpManagementService -> parseHttpParametersQuery()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse Http parameters query request");
        List<HttpParameterModel> parameters = parsingUtil.parseHttpParameters(parseRequest.getQuery());
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse Http parameters query request successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended HttpManagementService -> parseHttpParametersQuery()");
        return parameters;
    }

    public ResponseParameterParsingResponse parseResponseHttpParameters(ResponseParameterParsingRequest parseRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started HttpManagementService -> parseResponseHttpParameters()");
        CCATLogger.DEBUG_LOGGER.info("Start serving parse Response Http parameters");
        ResponseParameterParsingResponse parameters ;
        if(parseRequest.getType().intValue()==(ParametersResponseType.XML.id.intValue())){
            parameters = parsingUtil.parseXMLHttpParameters(parseRequest.getFileContent());
        }else if(parseRequest.getType().intValue()==(ParametersResponseType.JSON.id.intValue())){
            parameters = parsingUtil.parseJsonHttpParameters(parseRequest.getFileContent());
        }else if(parseRequest.getType().intValue()==(ParametersResponseType.XSD.id.intValue())){
            parameters = parsingUtil.parseXSDHttpParameters(parseRequest.getFileContent(),parseRequest.getRootName());
        }else {
            parameters = null;
        }
        CCATLogger.DEBUG_LOGGER.info("Finished serving parse Response Http parameters successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ended HttpManagementService -> parseResponseHttpParameters()");
        return parameters;
    }
}
