package com.asset.ccat.simulators_service.operations;


import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import com.asset.ccat.simulators_service.models.UCIPModel;

/**
 * @author mahmoud.shehab
 */
public interface Operation {

  public String operate(UCIPModel model) throws SimulatorsException;
}
