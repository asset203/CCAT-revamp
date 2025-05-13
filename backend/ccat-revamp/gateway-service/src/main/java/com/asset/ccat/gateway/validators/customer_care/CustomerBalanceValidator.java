/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.mappers.CheckLimitMapper;
import com.asset.ccat.gateway.models.requests.UpdateAccumlatorModel;
import com.asset.ccat.gateway.models.requests.UpdateAccumulatorsRequest;
import com.asset.ccat.gateway.models.requests.UpdateBalanceAndDateRequest;
import com.asset.ccat.gateway.models.requests.UpdateDedicatedAccount;
import com.asset.ccat.gateway.models.requests.UpdateDedicatedBalanceRequest;
import com.asset.ccat.gateway.services.CustomerBalancesService;

import java.math.BigDecimal;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Mahmoud Shehab
 */
@Component
public class CustomerBalanceValidator {

    @Autowired
    private CustomerBalancesService service;
    @Autowired
    private CheckLimitMapper mapper;

    private static final int MAX_TRANSACTION_AMOUNT = 2147483647; //Max value accepted from the Air
    private static final int MIN_TRANSACTION_AMOUNT = -2147483648;


    public void validateUpdateBalanceAndDate(UpdateBalanceAndDateRequest balanceAndDateRequest) throws GatewayException {
        if (balanceAndDateRequest.getMsisdn() == null
                || balanceAndDateRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(balanceAndDateRequest.getAdjustmentMethod())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentMethod");
        } else if (Objects.isNull(balanceAndDateRequest.getTransactionType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "transactionType");
        } else if (Objects.isNull(balanceAndDateRequest.getTransactionCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "transactionCode");
        }
        service.checkLimit(mapper.mapFrom(balanceAndDateRequest));
    }

    public void validateUpdateDedicatedAccounts(UpdateDedicatedBalanceRequest dedicatedBalanceRequest) throws GatewayException {
        if (dedicatedBalanceRequest.getList() == null || dedicatedBalanceRequest.getList().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "dedicated accounts");
        } else if (dedicatedBalanceRequest.getMsisdn() == null || dedicatedBalanceRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        } else if (Objects.isNull(dedicatedBalanceRequest.getTransactionType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "transactionType");
        } else if (Objects.isNull(dedicatedBalanceRequest.getTransactionCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "transactionCode");
        }
        Integer adjustmentMethod = dedicatedBalanceRequest.getList().get(0).getAdjustmentMethod();
        if (adjustmentMethod != 0) {
            //Integer adjustmentMethod = null;
            BigDecimal adjustmentAmount = BigDecimal.ZERO;
            BigDecimal balance = BigDecimal.ZERO;

            for (UpdateDedicatedAccount account : dedicatedBalanceRequest.getList()) {
                BigDecimal adjAmountValue = account.getAdjustmentAmount() == null ? BigDecimal.ZERO : account.getAdjustmentAmount();
               adjustmentAmount = adjustmentAmount.add(adjAmountValue);
               balance = balance.add(account.getBalance());
                //adjustmentMethod = account.getAdjustmentMethod();
                if (Objects.isNull(account.getId()))
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
                else if (Objects.isNull(account.getAdjustmentMethod()))
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentMethod for [" + account.getId() + "]");
                else if (!adjustmentMethod.equals(account.getAdjustmentMethod()))
                    throw new GatewayValidationException(ErrorCodes.WARNING.MUST_BE_MATCHED, "adjustmentMethod");
            }
            service.checkLimit(mapper.mapFrom(dedicatedBalanceRequest, adjustmentMethod, adjustmentAmount, balance));
        }
    }

    public void validateAccumulators(UpdateAccumulatorsRequest accumulatorsRequest) throws GatewayException {
        if (accumulatorsRequest.getList() == null || accumulatorsRequest.getList().isEmpty()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Accumulators");
        } else if (accumulatorsRequest.getMsisdn() == null || accumulatorsRequest.getMsisdn().isBlank()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }

        Integer adjustmentMethod = accumulatorsRequest.getList().get(0).getAdjustmentMethod();
        for (UpdateAccumlatorModel accumulator : accumulatorsRequest.getList()) {
            if (Objects.isNull(accumulator.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
            } else if (Objects.isNull(accumulator.getAdjustmentAmount())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentAmount for [" + accumulator.getId() + "]");
            } else if (Objects.isNull(accumulator.getAdjustmentMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentMethod for [" + accumulator.getId() + "]");
            } else if (!adjustmentMethod.equals(accumulator.getAdjustmentMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MUST_BE_MATCHED, "adjustmentMethod");
            } else if (accumulator.getAdjustmentAmount() > MAX_TRANSACTION_AMOUNT || accumulator.getAdjustmentAmount() < MIN_TRANSACTION_AMOUNT) {
                throw new GatewayValidationException(ErrorCodes.WARNING.LIMIT_EXCEEDED, " adjustmentAmount for [" + accumulator.getId() + " should be between " + MIN_TRANSACTION_AMOUNT + " and " + MAX_TRANSACTION_AMOUNT);
            }
        }
//        if (Objects.nonNull(adjustmentMethod) && adjustmentMethod.intValue()!=0 && adjustmentMethod.intValue()!=-1) {
//            service.checkLimit(mapper.mapFrom(accumulatorsRequest, adjustmentMethod, adjustmentAmount));
//        }
    }
}
