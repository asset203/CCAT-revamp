package com.asset.ccat.balancedisputemapperservice.models.response;

import com.asset.ccat.balancedisputemapperservice.models.BalanceDisputeDetailsModel;
import com.asset.ccat.balancedisputemapperservice.models.BalanceSummarySheetModel;
import com.asset.ccat.balancedisputemapperservice.models.UsageSummarySheetModel;

/**
 * @author Assem.Hassan
 */
public class BalanceDisputeReportResponse {

  private BalanceSummarySheetModel balanceSummarySheet;
  private UsageSummarySheetModel usageSummarySheet;
  private BalanceDisputeDetailsModel details;

  public BalanceDisputeReportResponse() {
    this.balanceSummarySheet = new BalanceSummarySheetModel();
    this.usageSummarySheet = new UsageSummarySheetModel();
    this.details = new BalanceDisputeDetailsModel();
  }

  public BalanceSummarySheetModel getBalanceSummarySheet() {
    return balanceSummarySheet;
  }

  public void setBalanceSummarySheet(
      BalanceSummarySheetModel balanceSummarySheet) {
    this.balanceSummarySheet = balanceSummarySheet;
  }

  public UsageSummarySheetModel getUsageSummarySheet() {
    return usageSummarySheet;
  }

  public void setUsageSummarySheet(
      UsageSummarySheetModel usageSummarySheet) {
    this.usageSummarySheet = usageSummarySheet;
  }

  public BalanceDisputeDetailsModel getDetails() {
    return details;
  }

  public void setDetails(
      BalanceDisputeDetailsModel details) {
    this.details = details;
  }
}
