/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.models.responses.lookup;

import com.asset.ccat.gateway.models.shared.AccountGroupModel;
import java.util.List;

/**
 *
 * @author wael.mohamed
 */
public class GetAllAccountGroupsResponse {

    List<AccountGroupModel> accountGroups;

    public GetAllAccountGroupsResponse() {
    }

    public GetAllAccountGroupsResponse(List<AccountGroupModel> accountGroups) {
        this.accountGroups = accountGroups;
    }

    public List<AccountGroupModel> getAccountGroups() {
        return accountGroups;
    }

    public void setAccountGroups(List<AccountGroupModel> accountGroups) {
        this.accountGroups = accountGroups;
    }

}
