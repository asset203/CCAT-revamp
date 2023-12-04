/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.database.dao.SubscriberDao;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;
import java.io.IOException;

/**
 * @author mahmoud.shehab
 */
public class DeleteSubscriberOperation implements Operation {

  private final SubscriberDao dao;

  public DeleteSubscriberOperation(SubscriberDao dao) {
    this.dao = dao;
  }

  @Override
  public String operate(UCIPModel model) throws SimulatorsException {
    int isDeleted = dao.deleteMsisdn(model.getMsisdn());

    String filePath = model.getPath() + model.getMethod();

    String returnBody = "";
    try {
      returnBody = FileUtils.readFileAsString(filePath, model);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
    return returnBody;
  }


}
