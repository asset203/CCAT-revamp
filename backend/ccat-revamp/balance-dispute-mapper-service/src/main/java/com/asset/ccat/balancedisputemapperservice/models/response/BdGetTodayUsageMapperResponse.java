package com.asset.ccat.balancedisputemapperservice.models.response;

import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeDetailsModel;

/**
 * @author Assem.Hassan
 */
public class BdGetTodayUsageMapperResponse {

  private BalanceDisputeDetailsModel details;

  public BdGetTodayUsageMapperResponse() {

    this.details = new BalanceDisputeDetailsModel();
  }

  public BalanceDisputeDetailsModel getDetails() {
    return details;
  }

  public void setDetails(
      BalanceDisputeDetailsModel details) {
    this.details = details;
  }
}
