package com.asset.ccat.gateway.models.requests.admin.marquee;

import com.asset.ccat.gateway.models.admin.Es2alnyMarqueeModel;
import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class UpdateMarqueesRequest extends BaseRequest {

    Es2alnyMarqueeModel marquee;

    public Es2alnyMarqueeModel getMarquee() {
        return marquee;
    }

    public void setMarquee(Es2alnyMarqueeModel marquee) {
        this.marquee = marquee;
    }

    @Override
    public String toString() {
        return "UpdateMarqueesRequest{" +
                "marquee=" + marquee +
                '}';
    }
}
