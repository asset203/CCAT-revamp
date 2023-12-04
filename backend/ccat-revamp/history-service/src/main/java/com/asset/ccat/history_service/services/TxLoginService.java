package com.asset.ccat.history_service.services;

import com.asset.ccat.history_service.database.dao.TxLoginDao;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import com.asset.ccat.rabbitmq.models.TxLoginModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author assem.hassan
 */
@Component
public class TxLoginService {

    @Autowired
    private TxLoginDao txLoginDao;

    public void insertTxLoginBatch(List<TxLoginModel> txLoginBatchList) throws HistoryException {
        txLoginDao.insertTxLoginBatch(txLoginBatchList);
    }

    public void insertIntoTxLogin(TxLoginModel txLoginModel) throws HistoryException {
        txLoginDao.insertIntoTxLogin(txLoginModel);
    }

}
