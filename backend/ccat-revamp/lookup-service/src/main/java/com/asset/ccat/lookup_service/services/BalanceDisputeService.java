package com.asset.ccat.lookup_service.services;

import com.asset.ccat.lookup_service.database.dao.BalanceDisputeDao;
import com.asset.ccat.lookup_service.models.LkBalanceDisputeDetailsConfigModel;
import java.util.LinkedHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BalanceDisputeService {

  private final BalanceDisputeDao balanceDisputeDao;

  @Autowired
  public BalanceDisputeService(BalanceDisputeDao balanceDisputeDao) {
    this.balanceDisputeDao = balanceDisputeDao;
  }

  public LinkedHashMap<String, LkBalanceDisputeDetailsConfigModel> getBalanceDisputeDetailsConfiguration(
      Integer profileId) {
    return balanceDisputeDao.retrieveBDDetailsConfiguration(profileId);
  }
}
