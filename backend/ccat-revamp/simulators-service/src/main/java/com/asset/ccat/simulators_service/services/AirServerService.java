package com.asset.ccat.simulators_service.services;

import com.asset.ccat.simulators_service.configurations.Properties;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.operations.Operation;
import com.asset.ccat.simulators_service.operations.OperationFactory;
import com.asset.ccat.simulators_service.parser.RequestParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AirServerService {

  @Autowired
  private Properties properties;
  @Autowired
  private RequestParser requestParser;
  @Autowired
  private OperationFactory operationFactory;

  public String processRequest(String request) throws SimulatorsException {
    CCATLogger.DEBUG_LOGGER.info("Received Air Request [" + request + "]");
    UCIPModel model = RequestParser.parseRequest(request);
    model.setPath(properties.getAirPath());
    Operation operation = operationFactory.getOperation(model.getMethod());
    String response = operation.operate(model).trim();
    CCATLogger.DEBUG_LOGGER.info("Finished Serving Air Request Successfully!!");

    return response;
  }
}
