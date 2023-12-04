package com.asset.ccat.gateway.models.requests.customer_care.balance_dispute;


import com.asset.ccat.gateway.models.customer_care.balance_dispute.BalanceSummarySheetModel;
import com.asset.ccat.gateway.models.customer_care.balance_dispute.BalanceTransactionsDetailsModel;
import com.asset.ccat.gateway.models.customer_care.balance_dispute.UsageSummarySheetModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class ExportBalanceDisputeReportRequest extends BaseRequest {

  private BalanceSummarySheetModel balanceSummarySheet;
  private UsageSummarySheetModel usageSummarySheet;
  private BalanceTransactionsDetailsModel balanceTransactionsDetails;


  public ExportBalanceDisputeReportRequest() {
    this.balanceSummarySheet = new BalanceSummarySheetModel();
    this.usageSummarySheet = new UsageSummarySheetModel();
    this.balanceTransactionsDetails = new BalanceTransactionsDetailsModel();
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


  public BalanceTransactionsDetailsModel getBalanceTransactionsDetails() {
    return balanceTransactionsDetails;
  }

  public void setBalanceTransactionsDetails(
      BalanceTransactionsDetailsModel balanceTransactionsDetails) {
    this.balanceTransactionsDetails = balanceTransactionsDetails;
  }
}
