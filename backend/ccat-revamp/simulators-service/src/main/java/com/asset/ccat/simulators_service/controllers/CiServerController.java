package com.asset.ccat.simulators_service.controllers;

import com.asset.ccat.simulators_service.defines.Defines;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.services.AirServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Defines.ContextPaths.CI_SERVERS)
public class CiServerController {

  @Autowired
  private AirServerService airServerService;

  @PostMapping(Defines.WEB_ACTIONS.GET)
  public String processRequest(@RequestBody String request) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.info("Received Air Request [" + request + "]");
    //TODO
    CCATLogger.DEBUG_LOGGER.info("Finished Serving Air Request Successfully!!");

    return airServerService.processRequest(request);
  }
}
