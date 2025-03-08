/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.AccumulatorModel;
import com.asset.ccat.gateway.models.customer_care.DedicatedAccount;
import com.asset.ccat.gateway.models.requests.*;
import com.asset.ccat.gateway.models.requests.admin.user.CheckLimitRequest;
import com.asset.ccat.gateway.models.requests.admin.user.UpdateLimitRequest;
import com.asset.ccat.gateway.proxy.CustomerBalancesProxy;
import com.asset.ccat.rabbitmq.models.TxAdjustmentModel;
import com.asset.rabbitmq.client.util.RabbitmqUtil;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Mahmoud Shehab
 */
@Service
public class CustomerBalancesService {

    @Autowired
    CustomerBalancesProxy customerBalanceProxy;
    @Autowired
    private RabbitmqUtil rabbitmqUtil;

    public List<DedicatedAccount> getDedicatedAccounts(GetDedicatedAccountsRequest request) throws GatewayException {
        return customerBalanceProxy.getDedicatedAccounts(request);
    }

    public List<AccumulatorModel> getAccumulators(SubscriberRequest subscriberRequest) throws GatewayException {
        return customerBalanceProxy.getAccumulators(subscriberRequest);
    }

    public void updateBalanceAndDate(UpdateBalanceAndDateRequest balanceAndDateRequest) throws GatewayException {
        checkTransactionLimit(balanceAndDateRequest);
        customerBalanceProxy.updateBalanceAndDates(balanceAndDateRequest);
        //TODO call RabbitMQ
        enqueueTxSubscriberAdjustment(new TxAdjustmentModel(balanceAndDateRequest.getMsisdn()));

    }

    public void enqueueTxSubscriberAdjustment(TxAdjustmentModel adjustmentModel) throws GatewayException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start enqueuing tx_adjustment to rabbitmq");
            rabbitmqUtil.publishMsgToQueue(Defines.RABBIT_MQ.EXCHANGE_NAME, Defines.RABBIT_MQ.TX_ADJUSTMENT_QUEUE, adjustmentModel);
            CCATLogger.DEBUG_LOGGER.debug("Finished enqueuing tx_adjustment");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to enqueue tx_adjustment with error: " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("Failed to enqueue tx_adjustment", ex);
            throw new GatewayException(ErrorCodes.ERROR.LOG_TX_ADJUSTMENT_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    public void updateDedicatedAccounts(UpdateDedicatedBalanceRequest updateDedicatedBalanceRequest) throws GatewayException {
        customerBalanceProxy.updateDedicatedAccounts(updateDedicatedBalanceRequest);
    }

    public void updateAccumulators(UpdateAccumulatorsRequest accumlatorsRequest) throws GatewayException {
        customerBalanceProxy.updateAccumulators(accumlatorsRequest);
    }

    public void checkLimit(CheckLimitRequest request) throws GatewayException {
        customerBalanceProxy.checkLimit(request);
    }
    public void updateLimit(UpdateLimitRequest request) throws GatewayException {
        customerBalanceProxy.updateLimit(request);
    }

    public void checkTransactionLimit(SubscriberRequest request) throws GatewayException {
        customerBalanceProxy.checkTransactionLimit(request);
    }
}
