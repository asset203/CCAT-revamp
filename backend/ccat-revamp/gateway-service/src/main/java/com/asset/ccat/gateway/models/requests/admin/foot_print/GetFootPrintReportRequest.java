package com.asset.ccat.gateway.models.requests.admin.foot_print;

import com.asset.ccat.gateway.models.admin.foot_print.SearchFootPrintReportModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author Assem.Hassan
 */
public class GetFootPrintReportRequest extends BaseRequest {

    private SearchFootPrintReportModel searchFootPrintReport;

    public SearchFootPrintReportModel getSearchFootPrintReport() {
        return searchFootPrintReport;
    }

    public void setSearchFootPrintReport(SearchFootPrintReportModel searchFootPrintReport) {
        this.searchFootPrintReport = searchFootPrintReport;
    }

    @Override
    public String toString() {
        return "GetFootPrintReportRequest{" +
                "searchFootPrintReport=" + searchFootPrintReport +
                '}';
    }
}
