package com.asset.ccat.balance_dispute_service.dto.responses;


import com.asset.ccat.balance_dispute_service.dto.models.BalanceDisputeDetailsModel;

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
