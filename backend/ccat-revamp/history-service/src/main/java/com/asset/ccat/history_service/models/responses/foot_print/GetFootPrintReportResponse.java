package com.asset.ccat.history_service.models.responses.foot_print;

import com.asset.ccat.rabbitmq.models.FootprintModel;

import java.util.HashMap;


/**
 * @author Assem.Hassan
 */
public class GetFootPrintReportResponse {

    private HashMap<String, FootprintModel> footprints;

    public GetFootPrintReportResponse() {
    }

    public GetFootPrintReportResponse(HashMap<String, FootprintModel> footprints) {
        this.footprints = footprints;
    }

    public HashMap<String, FootprintModel> getFootprints() {
        return footprints;
    }

    public void setFootprints(HashMap<String, FootprintModel> footprints) {
        this.footprints = footprints;
    }
}
