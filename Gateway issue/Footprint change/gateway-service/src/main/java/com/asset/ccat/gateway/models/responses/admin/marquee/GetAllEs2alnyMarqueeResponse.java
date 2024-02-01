package com.asset.ccat.gateway.models.responses.admin.marquee;

import com.asset.ccat.gateway.models.admin.Es2alnyMarqueeModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllEs2alnyMarqueeResponse {

    private List<Es2alnyMarqueeModel> marquees;

    public GetAllEs2alnyMarqueeResponse() {
    }

    public GetAllEs2alnyMarqueeResponse(List<Es2alnyMarqueeModel> marquees) {
        this.marquees = marquees;
    }

    public List<Es2alnyMarqueeModel> getMarquees() {
        return marquees;
    }

    public void setMarquees(List<Es2alnyMarqueeModel> marquees) {
        this.marquees = marquees;
    }

}
