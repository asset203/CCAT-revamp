package com.asset.ccat.lookup_service.controllers.dss;

import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.BaseResponse;
import com.asset.ccat.lookup_service.models.dss.DssFlagModel;
import com.asset.ccat.lookup_service.services.DSSService;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(Defines.ContextPaths.DSS)
public class DSSController {
    private final DSSService dssService;

    public DSSController(DSSService dssService) {
        this.dssService = dssService;
    }

    @GetMapping(value = Defines.ContextPaths.DSS_FLAGS + Defines.WEB_ACTIONS.GET_ALL)
    public BaseResponse<Map<String, List<DssFlagModel>>> getDssFlags() throws LookupException {
        ThreadContext.put("requestId", UUID.randomUUID().toString());
        CCATLogger.DEBUG_LOGGER.debug("Get All DSS Flags request started");
        Map<String, List<DssFlagModel>> response = dssService.getDssFlags();
        CCATLogger.DEBUG_LOGGER.debug("Get All DSS Flags request ended");
        return new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS, "success", Defines.SEVERITY.CLEAR, response);
    }
}
