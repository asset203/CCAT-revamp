/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.database.dao.SubscriberDao;
import com.asset.ccat.simulators_service.defines.ErrorCodes;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;
import java.io.IOException;

/**
 * @author mahmoud.shehab
 */

public class InstallmentOperation implements Operation {

  private final SubscriberDao dao;

  public InstallmentOperation(SubscriberDao dao) {
    this.dao = dao;
  }

  @Override
  public String operate(UCIPModel model) throws SimulatorsException {

    int isInstalled = dao.installMsisdn(model.getMsisdn(),model.getLanguageId(),model.getServiceClassId());
    String filePath = model.getPath() + model.getMethod();

    String returnBody = "";
    try {
      returnBody = FileUtils.readFileAsString(filePath, model);
    } catch (IOException ex) {
      ex.printStackTrace();
      CCATLogger.DEBUG_LOGGER.info("Failed to Read file: " + filePath);
      throw new SimulatorsException(ErrorCodes.ERROR.PARSING_FAILED);
    }
    return returnBody;
  }

}
