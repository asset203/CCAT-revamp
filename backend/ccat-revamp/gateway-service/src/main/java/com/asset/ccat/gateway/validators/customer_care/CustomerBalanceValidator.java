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
            float adjustmentAmount = 0f;
            Float balance = 0f;

            for (UpdateDedicatedAccount account : dedicatedBalanceRequest.getList()) {
                float adjAmountValue = account.getAdjustmentAmount() == null ? 0 : account.getAdjustmentAmount();
                adjustmentAmount += adjAmountValue;
                balance += account.getBalance();
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

        Integer adjustmentMethod;
        for (UpdateAccumlatorModel accumulator : accumulatorsRequest.getList()) {
            adjustmentMethod = accumulator.getAdjustmentMethod();
            if (Objects.isNull(accumulator.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
            } else if (Objects.isNull(accumulator.getAdjustmentAmount())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentAmount for [" + accumulator.getId() + "]");
            } else if (Objects.isNull(accumulator.getAdjustmentMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "adjustmentMethod for [" + accumulator.getId() + "]");
            } else if (!adjustmentMethod.equals(accumulator.getAdjustmentMethod())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MUST_BE_MATCHED, "adjustmentMethod");
            }
        }

//        if (Objects.nonNull(adjustmentMethod) && adjustmentMethod.intValue()!=0 && adjustmentMethod.intValue()!=-1) {
//            service.checkLimit(mapper.mapFrom(accumulatorsRequest, adjustmentMethod, adjustmentAmount));
//        }
    }
}
