package com.asset.ccat.gateway.models.responses.admin.batch_install_disconnect;

import com.asset.ccat.gateway.models.admin.FailedMsisdnModel;
import java.util.List;

/**
 *
 * @author marwa.elshawarby
 */
public class BatchInstallResponse {

    private Integer totalNumberOfMsisdns;
    private Integer numberOfSucessMsisdns;
    private Integer numberOfFailedMsisdns;
    private List<FailedMsisdnModel> failedMsisdns;

    public Integer getTotalNumberOfMsisdns() {
        return totalNumberOfMsisdns;
    }

    public void setTotalNumberOfMsisdns(Integer totalNumberOfMsisdns) {
        this.totalNumberOfMsisdns = totalNumberOfMsisdns;
    }

    public Integer getNumberOfSucessMsisdns() {
        return numberOfSucessMsisdns;
    }

    public void setNumberOfSucessMsisdns(Integer numberOfSucessMsisdns) {
        this.numberOfSucessMsisdns = numberOfSucessMsisdns;
    }

    public Integer getNumberOfFailedMsisdns() {
        return numberOfFailedMsisdns;
    }

    public void setNumberOfFailedMsisdns(Integer numberOfFailedMsisdns) {
        this.numberOfFailedMsisdns = numberOfFailedMsisdns;
    }

    public List<FailedMsisdnModel> getFailedMsisdns() {
        return failedMsisdns;
    }

    public void setFailedMsisdns(List<FailedMsisdnModel> failedMsisdns) {
        this.failedMsisdns = failedMsisdns;
    }
}
