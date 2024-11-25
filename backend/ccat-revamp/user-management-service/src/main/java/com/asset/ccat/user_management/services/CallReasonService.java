package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.database.dao.CallReasonDao;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.callReason.AddCallReasonRequest;
import com.asset.ccat.user_management.models.requests.callReason.CheckCallReasonRequest;
import com.asset.ccat.user_management.models.requests.callReason.UpdateCallReasonRequest;
import com.asset.ccat.user_management.models.responses.callReason.CallReasonModel;
import org.springframework.stereotype.Service;

@Service
public class CallReasonService {

    private CallReasonDao callReasonDao;

    public CallReasonService(CallReasonDao callReasonDao) {
        this.callReasonDao = callReasonDao;
    }

    public CallReasonModel addCallReason(AddCallReasonRequest callReasonRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> addCallReason() : Started ");
        CallReasonModel callReason = new CallReasonModel();
        callReason.setMsisdn(callReasonRequest.getMsisdn());
        callReason.setUserId(callReasonRequest.getUserId());
        callReason.setUsername(callReasonRequest.getUsername());
        char msisdnLastDigit = callReasonRequest.getMsisdn().charAt(callReasonRequest.getMsisdn().length()-1);
        callReason.setMsisdnLastDigit(msisdnLastDigit+"");
        Integer callReasonId = callReasonDao.addCallReason(callReason);
        callReason.setCallReasonId(callReasonId);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> addCallReason() : Ended successfully ");
        return callReason;
    }
    public void updateCallReason(UpdateCallReasonRequest updateCallReasonRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> updateCallReason() : Started ");
        CallReasonModel callReasonModel = new CallReasonModel();
        callReasonModel.setReason(updateCallReasonRequest.getReason());
        callReasonModel.setCallReasonId(updateCallReasonRequest.getCallReasonId());
        callReasonModel.setType(updateCallReasonRequest.getType());
        callReasonModel.setFamily(updateCallReasonRequest.getFamily());
        callReasonModel.setDirection(updateCallReasonRequest.getDirection());
        callReasonDao.updateCallReason(callReasonModel);
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> updateCallReason() : Ended successfully ");
    }
    public CallReasonModel checkCallReason(CheckCallReasonRequest checkCallReasonRequest) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> checkCallReason() : Started ");
        CallReasonModel callReasonModel = callReasonDao.checkCallReason(checkCallReasonRequest.getUserId());
        CCATLogger.DEBUG_LOGGER.debug("CallReasonService -> checkCallReason() : Ended successfully ");
        return callReasonModel;
    }
}
