package com.asset.ccat.procedureservice.services;

import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.constants.FlexType;
import com.asset.ccat.procedureservice.database.dao.FlexShareDao;
import com.asset.ccat.procedureservice.defines.Defines;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.dto.requests.flex_share.FlexShareInquiryRequest;
import com.asset.ccat.procedureservice.dto.requests.flex_share.FlexShareUpdateRequest;
import com.asset.ccat.procedureservice.dto.models.flex_share.FlexShareInquirySPResponse;
import com.asset.ccat.procedureservice.dto.responses.flex_share.FlexShareInquiryResponse;
import com.asset.ccat.procedureservice.exceptions.FlexShareException;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class FlexShareService {

    private final FlexShareDao flexShareDao;
    private final Properties properties;

    public FlexShareService (FlexShareDao flexShareDao, Properties properties) {
        this.flexShareDao = flexShareDao;
        this.properties = properties;
    }

    public FlexShareInquiryResponse inquiry(FlexShareInquiryRequest request) throws ProcedureException, FlexShareException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> inquiry() : Started");
        FlexShareInquiryResponse response = new FlexShareInquiryResponse();
        FlexShareInquirySPResponse inquiryResponse = flexShareDao.inquiry(request.getMsisdn());
        if(!Integer.valueOf(inquiryResponse.getStatusOut()).equals(ErrorCodes.FLEX_SHARE_SUCCESS.SUCCESS)){
            CCATLogger.DEBUG_LOGGER.debug("Inquiry Flex-share failure " + properties.getFlexShareInquiryStoredProcedureName());
            throw new FlexShareException(ErrorCodes.FLEX_SHARE_ERRORS.PROCESS_FAILURE,null," Inquiry ");
        }
        Integer bundleOut = inquiryResponse.getBundleOut();
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> inquiry() : Ended Successfully");
        switch (bundleOut){
            case 1 :
                response.setFlexType(FlexType.FS_PROFITABLE);
                response.setParameterOut(inquiryResponse.getParam2Out());
                return response;
            case 2:
                response.setFlexType(FlexType.FS_BLACKLIST);
                response.setParameterOut(inquiryResponse.getParam2Out());
                return response;
            case 3:
                response.setFlexType(FlexType.FS_WHITELIST);
                response.setParameterOut(inquiryResponse.getParam2Out());
                return response;
            default:
                return null;
        }


    }

    public void update(FlexShareUpdateRequest request) throws ProcedureException, FlexShareException {
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> update() : Started");
        Map<String,Object> spResponse = flexShareDao.update(request.getMsisdn(),request.getInputValue());
        if(!Integer.valueOf(spResponse.get(Defines.FLEX_SHARE.STATUS_OUT).toString()).equals(ErrorCodes.FLEX_SHARE_SUCCESS.SUCCESS)){
            CCATLogger.DEBUG_LOGGER.debug("Update Flex-share failure " + properties.getFlexShareInquiryStoredProcedureName());
            throw new FlexShareException(ErrorCodes.FLEX_SHARE_ERRORS.PROCESS_FAILURE,null," Inquiry ");
        }
        CCATLogger.DEBUG_LOGGER.debug("FlexShareService -> update() : Ended Successfully");

    }
}
