/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.database.dao.SubscriberDao;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.models.AccountDetailsModel;
import com.asset.ccat.simulators_service.models.UCIPModel;
import com.asset.ccat.simulators_service.utils.FileUtils;
import java.io.IOException;
import java.util.List;

/**
 * @author mahmoud.shehab
 */
public class GetAccountDetailsOperation implements Operation {


  private final SubscriberDao dao;

  public GetAccountDetailsOperation(SubscriberDao dao) {
    this.dao = dao;
  }

  @Override
  public String operate(UCIPModel model) throws SimulatorsException {

    List<AccountDetailsModel> result = dao.getAccountDetails(model.getMsisdn());
    String filePath = "";
    if (result.size()>0) {
      model.setLanguageId(result.get(0).getLanguageId());
      model.setServiceClassId(result.get(0).getServiceClassId());
      model.setAdjustmentAmount(result.get(0).getAdjustmentAmount());
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
