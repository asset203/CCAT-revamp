/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.history_service.services;

import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.database.dao.SubscriberAdjustmentDao;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.models.requests.AddAdjustmentTransactionRequest;
import com.asset.ccat.history_service.models.requests.AdjustmentTransactionCountRequest;
import com.asset.ccat.history_service.utils.DateUtils;
import java.util.Calendar;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author wael.mohamed
 */
@Service
public class SubscriberAdjustmentService {

    @Autowired
    private SubscriberAdjustmentDao adjustmentDao;
    @Autowired
    private DateUtils dateUtils;
    @Autowired
    private Properties properties;

    public void checkTransactionLimit(AdjustmentTransactionCountRequest request) throws HistoryException {

        int timePeriod = properties.getTransactionsValidationTimePeriod();
        int maxNoOfTransaction = properties.getMaxNoOfTransactions();

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.DATE, -timePeriod);
        String fromDate = dateUtils.getDBFormattedDate(calendar.getTime());

        calendar.setTime(new Date());
        calendar.add(Calendar.DATE, 1);
        String toDate = dateUtils.getDBFormattedDate(calendar.getTime());
        Integer transactionsCount = adjustmentDao.getNumberOfTransactionsInTimePeriod(request.getMsisdn(), fromDate, toDate);

        if (transactionsCount >= maxNoOfTransaction) {
            throw new HistoryException(ErrorCodes.ERROR.MAX_NO_OF_TRANSACTIONS_REACHED);
        }
    }

    public void insertIntoHistory(AddAdjustmentTransactionRequest request) throws HistoryException {
        String msisdnMod = request.getMsisdn().substring(request.getMsisdn().length() - 1);
        adjustmentDao.insertIntoAdjustment(request.getMsisdn(), msisdnMod);
    }
}
