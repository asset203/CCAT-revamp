package com.asset.ccat.gateway.controllers;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.GetConfigurationsRequest;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.users.LiteLoginModel;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author mahmoud.shehab
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = Defines.ContextPaths.CONFIGURATIONS)
public class ConfigurationsController {

  @Autowired
  Properties configuration;

  @PostMapping(value = Defines.WEB_ACTIONS.GET)
  public ResponseEntity<BaseResponse<Properties>> getProperties(HttpServletRequest req,
      @RequestBody GetConfigurationsRequest configurationsRequest) {

    return new ResponseEntity<>(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
        "success",
        Defines.SEVERITY.CLEAR,
        configuration), HttpStatus.OK);
  }

  @GetMapping(value = Defines.WEB_ACTIONS.SSO)
  public ResponseEntity<BaseResponse<LiteLoginModel>> getSSOProperties(HttpServletRequest req) {
    String requestId = UUID.randomUUID().toString();
    ThreadContext.put("requestId", requestId);
    CCATLogger.DEBUG_LOGGER.info("Start getting SSO Properties... " + requestId);
    CCATLogger.DEBUG_LOGGER.debug("SSO Properties { ssoVfUrl : " + configuration.getSsoVfUrl()
        + ", ssoIntegration : " + configuration.getSsoIntegration() + " }");
    CCATLogger.DEBUG_LOGGER.info("Finish getting SSO Properties. " + requestId);

    return new ResponseEntity<>(
        new BaseResponse<>(
            ErrorCodes.SUCCESS.SUCCESS,
            "success",
            Defines.SEVERITY.CLEAR,
            requestId,
            new LiteLoginModel(configuration.getSsoVfUrl(),
                configuration.getSsoIntegration(),
                configuration.getMsisdnPattern(),
                configuration.getPasswordPattern(),
                configuration.getVoucherSerialNumberLength(),
                configuration.getVoucherNumberLength(),
                configuration.getReportsDefaultSearchPeriod(),
                configuration.getReportsMaxSearchPeriod(),
                configuration.getAccountHistorySearchPeriod(),
                    configuration.getAccountHistoryMaxSearchPeriod(),
                    configuration.getNbaInterfaceSelector())
        ), HttpStatus.OK);
  }
}
