/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.configurations.constants.LkMonetaryLimits;
import com.asset.ccat.user_management.database.dao.UsersDao;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.user.CheckLimitRequest;
import com.asset.ccat.user_management.models.requests.user.UpdateLimitRequest;
import com.asset.ccat.user_management.models.users.LkProfileLimit;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wael.mohamed
 */
@Service
public class UserLimitService {

    @Autowired
    private UsersDao usersDao;

    public void checkLimit(CheckLimitRequest limitRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start check user limit with ID[" + limitRequest.getUserId() + "]");
        Map<Integer, LkProfileLimit> profileLimits = usersDao.retrieveProfileLimits(limitRequest.getUserId());

        LkProfileLimit rebateMaxLimit = profileLimits.get(LkMonetaryLimits.REBATE_MAX.id);
        LkProfileLimit debitMaxLimit = profileLimits.get(LkMonetaryLimits.DEBIT_MAX.id);

        LkProfileLimit dailyRebateMaxLimit = profileLimits.get(LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id);
        LkProfileLimit dailyDebitMaxLimit = profileLimits.get(LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id);

        //Check For Adding
        if (limitRequest.getActionType() == 1) {
            checkRebate(rebateMaxLimit.getValue(), limitRequest, dailyRebateMaxLimit.getValue());
        }//Check For Subtracting
        else if (limitRequest.getActionType() == 2) {
            checkDebit(debitMaxLimit.getValue(), limitRequest, dailyDebitMaxLimit.getValue());
        }//Check For Setting
        else if(limitRequest.getActionType() == 3){
            if(limitRequest.getBalance().compareTo(limitRequest.getAmount()) > 0){
                BigDecimal finalSetAmount = limitRequest.getBalance().subtract(limitRequest.getAmount());
                limitRequest.setAmount(finalSetAmount);
                checkDebit(debitMaxLimit.getValue(), limitRequest, dailyDebitMaxLimit.getValue());
            }else if (limitRequest.getBalance().compareTo(limitRequest.getAmount()) < 0 ){
                BigDecimal finalSetAmount = limitRequest.getAmount().subtract(limitRequest.getBalance());
                limitRequest.setAmount(finalSetAmount);
                checkRebate(rebateMaxLimit.getValue(), limitRequest, dailyRebateMaxLimit.getValue());
            }
        }
//        else if(limitRequest.getActionType() == 0){
//            limitRequest.setAmount(limitRequest.getBalance());
//            checkDebit(debitMaxLimit.getValue(), limitRequest, dailyDebitMaxLimit.getValue());
//        }
        CCATLogger.DEBUG_LOGGER.debug("Done check user limit with ID[" + limitRequest.getUserId() + "]");
    }

    private void checkDebit(BigDecimal maxValuePerTransaction, CheckLimitRequest limitRequest, BigDecimal dailyDebitMax) throws UserManagementException{
        if (maxValuePerTransaction.compareTo(BigDecimal.ZERO) > 0) {
            //check updateDebit limit of the profile per transaction
            if (limitRequest.getAmount().compareTo(maxValuePerTransaction) > 0) {
                throw new UserManagementException(ErrorCodes.ERROR.DEBIT_EXCEEDED, Defines.SEVERITY.VALIDATION, "" + maxValuePerTransaction);
            } else {
                BigDecimal remainingLimit = getTodaysRemainingLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id, dailyDebitMax);
                if (remainingLimit.compareTo(BigDecimal.ZERO) > 0 && remainingLimit.compareTo(limitRequest.getAmount()) < 0) {
                    CCATLogger.DEBUG_LOGGER.debug("Maximum debit exceeded. Maximum daily debit amount allowed is " + maxValuePerTransaction + " L.E. and only " + remainingLimit + " L.E. is remaining.");
                    throw new UserManagementException(ErrorCodes.ERROR.DEBIT_ALLOWED, Defines.SEVERITY.VALIDATION, "" + maxValuePerTransaction + "," + remainingLimit);
                } else if (remainingLimit.compareTo(BigDecimal.ZERO) <= 0 ) {
                    CCATLogger.DEBUG_LOGGER.debug("You have used all the allowable daily debit amount.");
                    throw new UserManagementException(ErrorCodes.ERROR.DEBIT_ALL_USED, Defines.SEVERITY.VALIDATION);
                }
            }
        }
    }

    public void checkRebate(BigDecimal maxValuePerTransaction, CheckLimitRequest limitRequest, BigDecimal dailyRebateMax) throws UserManagementException {
        if (maxValuePerTransaction.compareTo(BigDecimal.ZERO) > 0) {
            if (limitRequest.getAmount().compareTo(maxValuePerTransaction) > 0) {
                throw new UserManagementException(ErrorCodes.ERROR.REBATE_EXCEEDED, Defines.SEVERITY.VALIDATION, "" + maxValuePerTransaction);
            } else {
                BigDecimal remainingLimit = getTodaysRemainingLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id, dailyRebateMax);
                if (remainingLimit.compareTo(BigDecimal.ZERO) > 0&& remainingLimit.compareTo(limitRequest.getAmount()) < 0) {
                    CCATLogger.DEBUG_LOGGER.debug("Maximum rebate exceeded. Maximum daily rebate amount allowed is " + maxValuePerTransaction + " L.E. and only " + remainingLimit + " L.E. is remaining.");
                    throw new UserManagementException(ErrorCodes.ERROR.REBATE_ALLOWED, Defines.SEVERITY.VALIDATION, "" + maxValuePerTransaction + "," + remainingLimit);
                } else if (remainingLimit.compareTo(BigDecimal.ZERO) <= 0) {
                    CCATLogger.DEBUG_LOGGER.debug("You have used all the allowable daily rebate amount.");
                    throw new UserManagementException(ErrorCodes.ERROR.REBATE_ALL_USED, Defines.SEVERITY.VALIDATION);
                }
            }
        }
    }

    public void updateLimit(UpdateLimitRequest limitRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start UserLimitService -> updateLimit() to Update user limit with ID[" + limitRequest.getUserId() + "]");
        if (limitRequest.getActionType() == 1) {
            usersDao.updateDailyLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id, limitRequest.getAmount());
        } else if (limitRequest.getActionType() == 2) {
            usersDao.updateDailyLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id, limitRequest.getAmount());
        }
        else if(limitRequest.getActionType() == 3){
            if(limitRequest.getBalance()>limitRequest.getAmount()){
                float finalSetAmount = limitRequest.getBalance() - limitRequest.getAmount();
                usersDao.updateDailyLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id, finalSetAmount);
            }else if (limitRequest.getBalance()<limitRequest.getAmount()){
                float finalSetAmount = limitRequest.getAmount() - limitRequest.getBalance();
                usersDao.updateDailyLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_REBATE_MAX.id, finalSetAmount);
            }
        }
//        else if(limitRequest.getActionType() == 0){
//            usersDao.updateDailyLimit(limitRequest.getUserId(), LkMonetaryLimits.DAILY_TOTAL_DEBIT_MAX.id, limitRequest.getBalance());
//        }
        CCATLogger.DEBUG_LOGGER.debug("Done Update user limit with ID[" + limitRequest.getUserId() + "]");
    }

    public BigDecimal getTodaysRemainingLimit(Integer userId, Integer limitId, BigDecimal dailyLimit) throws UserManagementException {
        BigDecimal totalRebate = usersDao.retrieveSumofDailyMonetaryLimits(userId, limitId);
        BigDecimal remainingAmount = dailyLimit.subtract(totalRebate);
        return remainingAmount;
    }
}
