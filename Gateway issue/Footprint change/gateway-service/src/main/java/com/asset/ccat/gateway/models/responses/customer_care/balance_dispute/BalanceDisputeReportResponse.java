package com.asset.ccat.gateway.models.responses.customer_care.balance_dispute;


import com.asset.ccat.gateway.models.customer_care.balance_dispute.BalanceDisputeDetailsModel;
import com.asset.ccat.gateway.models.customer_care.balance_dispute.BalanceSummarySheetModel;
import com.asset.ccat.gateway.models.customer_care.balance_dispute.UsageSummarySheetModel;
import java.io.Serializable;

/**
 * @author Assem.Hassan
 */
public class BalanceDisputeReportResponse implements Serializable {

  private static final long serialVersionUID = 4814766281914826691L;

  private BalanceSummarySheetModel balanceSummarySheet;
  private UsageSummarySheetModel usageSummarySheet;
  private BalanceDisputeDetailsModel details;
  private Integer totalCount;

  public BalanceDisputeReportResponse() {
    this.balanceSummarySheet = new BalanceSummarySheetModel();
    this.usageSummarySheet = new UsageSummarySheetModel();
    this.details = new BalanceDisputeDetailsModel();
  }


  public BalanceSummarySheetModel getBalanceSummarySheet() {
    return balanceSummarySheet;
  }

  public void setBalanceSummarySheet(
      BalanceSummarySheetModel balanceSummarySheetModel) {
    this.balanceSummarySheet = balanceSummarySheetModel;
  }


  public UsageSummarySheetModel getUsageSummarySheet() {
    return usageSummarySheet;
  }

  public void setUsageSummarySheet(
      UsageSummarySheetModel usageSummarySheetModel) {
    this.usageSummarySheet = usageSummarySheetModel;
  }

  public BalanceDisputeDetailsModel getDetails() {
    return details;
  }

  public void setDetails(
      BalanceDisputeDetailsModel details) {
    this.details = details;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }
}
