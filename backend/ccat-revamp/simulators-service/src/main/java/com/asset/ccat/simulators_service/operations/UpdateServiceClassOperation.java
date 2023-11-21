
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.database.dao.SubscriberDao;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;

import java.io.IOException;


public class UpdateServiceClassOperation implements Operation {


  private final SubscriberDao dao;

  public UpdateServiceClassOperation(SubscriberDao dao) {
    this.dao = dao;
  }

  @Override
  public String operate(UCIPModel model) throws SimulatorsException {

    int result = dao.updateServiceClass(model.getMsisdn(),model.getServiceClassId());
    String filePath = "";
    if (result>0) {
      filePath = model.getPath() + model.getMethod();
    } else {
      filePath = model.getPath() + model.getMethod() + "NotFound";
    }
    String returnBody = "";
    try {
      returnBody = FileUtils.readFileAsString(filePath, model);
    } catch (IOException ex) {
      ex.printStackTrace();
    }

    return returnBody;
  }

}
