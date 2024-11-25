package com.asset.ccat.history_service.models.requests.foot_print;


import com.asset.ccat.history_service.models.SearchFootPrintReportModel;
import com.asset.ccat.history_service.models.requests.BaseRequest;

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
}
