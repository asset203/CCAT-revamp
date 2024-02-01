package com.asset.ccat.gateway.models.requests.admin.marquee;

import com.asset.ccat.gateway.models.admin.Es2alnyMarqueeModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

import java.util.List;

/**
 * @author nour.ihab
 */
public class UpdateAllMarqueesRequest extends BaseRequest {

    List<Es2alnyMarqueeModel> marquees;

    public List<Es2alnyMarqueeModel> getMarquees() {
        return marquees;
    }

    public void setMarquees(List<Es2alnyMarqueeModel> marquees) {
        this.marquees = marquees;
    }

    @Override
    public String toString() {
        return "UpdateAllMarqueesRequest{" +
                "marquees=" + marquees +
                '}';
    }
}
