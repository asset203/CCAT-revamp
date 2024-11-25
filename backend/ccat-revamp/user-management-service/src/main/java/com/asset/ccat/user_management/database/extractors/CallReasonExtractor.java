package com.asset.ccat.user_management.database.extractors;

import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.callReason.CallReasonModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CallReasonExtractor implements ResultSetExtractor<CallReasonModel> {
    @Override
    public CallReasonModel extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        CallReasonModel callReasonModel = null;
        while (resultSet.next()){
            callReasonModel = new CallReasonModel();
            callReasonModel.setCallReasonId(resultSet.getInt(DatabaseStructs.TX_CALL_REASONS.CALL_REASON_ID));
            callReasonModel.setReason(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.REASON));
            callReasonModel.setDirection(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.DIRECTION));
            callReasonModel.setFamily(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.FAMILY));
            callReasonModel.setType(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.TYPE));
            callReasonModel.setMsisdn(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.MSISDN));
            callReasonModel.setMsisdnLastDigit(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.MSISDN_LAST_DIGIT));
            callReasonModel.setUserId(resultSet.getInt(DatabaseStructs.TX_CALL_REASONS.USER_ID));
            callReasonModel.setUsername(resultSet.getString(DatabaseStructs.TX_CALL_REASONS.USER_NAME));
            CCATLogger.DEBUG_LOGGER.debug("CallReasonExtractor -> extractData() : Ended Successfully " + callReasonModel);
        }
        return callReasonModel;
    }
}
