/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.controllers;

import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.models.requests.GetConfigurationsRequest;
import com.asset.ccat.nba_service.models.responses.BaseResponse;
import javax.security.sasl.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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
