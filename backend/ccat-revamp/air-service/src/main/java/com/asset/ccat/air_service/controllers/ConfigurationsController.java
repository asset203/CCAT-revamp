package com.asset.ccat.air_service.controllers;

import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.Defines;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.models.requests.GetConfigurationsRequest;
import com.asset.ccat.air_service.models.responses.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.security.sasl.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;

/**
 *
 * @author mahmoud.shehab
 */
@RestController
@RequestMapping(Defines.ContextPaths.CONFIGURATIONS)
public class ConfigurationsController {

    @Autowired
    Properties configuration;

    @RequestMapping(value = Defines.WEB_ACTIONS.GET, method = RequestMethod.POST)
    public ResponseEntity<BaseResponse<Properties>> getProperties(HttpServletRequest req,
            @RequestBody GetConfigurationsRequest configurationsRequest) throws AuthenticationException, Exception {
        
        return new ResponseEntity(new BaseResponse<>(ErrorCodes.SUCCESS.SUCCESS,
                "success", 0,
                configuration), HttpStatus.OK);
    }
}
