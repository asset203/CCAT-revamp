package com.asset.ccat.simulators_service.operations;

import com.asset.ccat.simulators_service.database.dao.SubscriberDao;
import com.asset.ccat.simulators_service.exceptions.SimulatorsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author mahmoud.shehab
 */
@Service
public class OperationFactory {

  @Autowired
  private SubscriberDao dao;

  public Operation getOperation(String type) throws SimulatorsException {
    switch (type) {
      case "GetAccountDetails":
        return new GetAccountDetailsOperation(dao);
      case "InstallSubscriber":
        return new InstallmentOperation(dao);
      case "DeleteSubscriber":
        return new DeleteSubscriberOperation(dao);
      case "CIBatchDisconnection":
        return new CiBatchDisconnectionOperator();
      case "UpdateAccountDetails":
        return new UpdateAccountDetailsOperation(dao);
      case "UpdateServiceClass":
        return new UpdateServiceClassOperation(dao);
      case "UpdateBalanceAndDate":
        return new UpdateBalanceAndDateClassOperation(dao);
      default:
        return new GeneralOperation();
    }
  }
}
