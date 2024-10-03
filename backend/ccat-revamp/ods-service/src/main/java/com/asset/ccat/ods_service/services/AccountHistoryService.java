/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.database.dao.AccountHistoryDao;
import com.asset.ccat.ods_service.defines.Defines;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.SubscriberActivityModel;
import com.asset.ccat.ods_service.models.requests.AccountHistoryRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class AccountHistoryService {

    @Autowired
    private AccountHistoryDao accountHistoryDao;

    public List<SubscriberActivityModel> getAccountHistory(AccountHistoryRequest request) throws ODSException {
        return accountHistoryDao.retrieveNewRecords(request.getMsisdn(),
                request.getDateFrom(), request.getDateTo());
    }

}
