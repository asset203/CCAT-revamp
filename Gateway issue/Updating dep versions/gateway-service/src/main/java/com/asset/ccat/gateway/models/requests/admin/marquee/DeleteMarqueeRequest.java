package com.asset.ccat.gateway.models.requests.admin.marquee;

import com.asset.ccat.gateway.models.requests.BaseRequest;

/**
 * @author nour.ihab
 */
public class DeleteMarqueeRequest extends BaseRequest {

    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DeleteMarqueeRequest{" +
                "id=" + id +
                '}';
    }
}
