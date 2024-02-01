package com.asset.ccat.gateway.models.responses.customer_care;

import com.asset.ccat.gateway.models.customer_care.GetAllNotePadModel;
import java.util.List;

/**
 *
 * @author nour.ihab
 */
public class GetAllNotePadResponse {

    private List<GetAllNotePadModel> entries;

    public List<GetAllNotePadModel> getEntries() {
        return entries;
    }

    public void setEntries(List<GetAllNotePadModel> entries) {
        this.entries = entries;
    }

}
