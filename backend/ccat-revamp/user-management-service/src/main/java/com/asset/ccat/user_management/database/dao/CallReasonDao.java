package com.asset.ccat.user_management.database.dao;

import com.asset.ccat.user_management.annotation.LogExecutionTime;
import com.asset.ccat.user_management.database.extractors.CallReasonExtractor;
import com.asset.ccat.user_management.defines.DatabaseStructs;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.callReason.CallReasonModel;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Objects;

@Repository
public class CallReasonDao {

    private final JdbcTemplate jdbcTemplate;

    private final CallReasonExtractor callReasonExtractor;

    private String addCallReasonQuery;
    private String updateCallReasonQuery;
    private String checkCallReasonQuery;

    public CallReasonDao(JdbcTemplate jdbcTemplate, CallReasonExtractor callReasonExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.callReasonExtractor = callReasonExtractor;
    }


    @LogExecutionTime
    public Integer addCallReason(CallReasonModel callReasonModel) throws UserManagementException {
        Integer callReasonId;
        try {
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> addCallReason() : Started with request : " + callReasonModel);
            if (Objects.isNull(addCallReasonQuery)) {
                StringBuilder query = new StringBuilder();
                query.append(" INSERT INTO ")
                        .append(DatabaseStructs.TX_CALL_REASONS.TABLE_NAME)
                        .append(" ( ")
                        .append(DatabaseStructs.TX_CALL_REASONS.CALL_REASON_ID).append(",")
                        .append(DatabaseStructs.TX_CALL_REASONS.USER_ID).append(",")
                        .append(DatabaseStructs.TX_CALL_REASONS.USER_NAME).append(",")
                        .append(DatabaseStructs.TX_CALL_REASONS.MSISDN).append(",")
                        .append(DatabaseStructs.TX_CALL_REASONS.MSISDN_LAST_DIGIT).append(" ) ")
                        .append(" VALUES ( ")
                        .append(DatabaseStructs.TX_CALL_REASONS.SEQUENCE_NAME)
                        .append(".nextval , ? , ? , ? , ?  )");
                addCallReasonQuery = query.toString();
            }

            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplate.update((Connection connection) -> {
                PreparedStatement ps = connection.prepareStatement(addCallReasonQuery, new String[]{DatabaseStructs.TX_CALL_REASONS.CALL_REASON_ID});
                ps.setInt(1, callReasonModel.getUserId());
                ps.setString(2, callReasonModel.getUsername());
                ps.setString(3, callReasonModel.getMsisdn());
                ps.setString(4, callReasonModel.getMsisdnLastDigit());
                return ps;
            }, keyHolder);
            callReasonId = keyHolder.getKey() == null ? null : keyHolder.getKey().intValue();
            callReasonModel.setCallReasonId(callReasonId);
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> addCallReason() : Ended Successfully");
            return callReasonId;
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.error("CallReasonDao -> addCallReason() Error Occurred during executing query : " + addCallReasonQuery);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public void updateCallReason(CallReasonModel callReasonModel) throws UserManagementException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> updateCallReason() : Started with request : " + callReasonModel);
            if (Objects.isNull(updateCallReasonQuery)) {
                StringBuilder query = new StringBuilder();
                query.append(" UPDATE ").append(DatabaseStructs.TX_CALL_REASONS.TABLE_NAME).append(" SET ").append(DatabaseStructs.TX_CALL_REASONS.DIRECTION).append(" = ? , ").append(DatabaseStructs.TX_CALL_REASONS.FAMILY).append(" = ? , ").append(DatabaseStructs.TX_CALL_REASONS.TYPE).append(" = ? , ").append(DatabaseStructs.TX_CALL_REASONS.REASON).append(" = ? ").append(" WHERE ").append(DatabaseStructs.TX_CALL_REASONS.CALL_REASON_ID).append(" = ? ");
                updateCallReasonQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> updateCallReason() : Executing Query : " + updateCallReasonQuery);

            jdbcTemplate.update(updateCallReasonQuery, callReasonModel.getDirection(), callReasonModel.getFamily(), callReasonModel.getType(), callReasonModel.getReason(), callReasonModel.getCallReasonId());
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> updateCallReason() : Ended Successfully");
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.error("CallReasonDao -> updateCallReason() Error Occurred during executing query : " + updateCallReasonQuery);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }

    @LogExecutionTime
    public CallReasonModel checkCallReason(Integer userId) throws UserManagementException {
        CallReasonModel callReasonModel = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> checkCallReason() : Started with userId : " + userId);
            if (Objects.isNull(checkCallReasonQuery)) {
                StringBuilder query = new StringBuilder();
                query.append(" SELECT * FROM  ").append(DatabaseStructs.TX_CALL_REASONS.TABLE_NAME).append(" WHERE ").append(DatabaseStructs.TX_CALL_REASONS.ENTRY_DATE).append(" >= sysdate - 2 AND ").append(DatabaseStructs.TX_CALL_REASONS.USER_ID).append(" = ? AND ").append(DatabaseStructs.TX_CALL_REASONS.DIRECTION).append(" IS NULL AND ").append(DatabaseStructs.TX_CALL_REASONS.FAMILY).append(" IS NULL AND ").append(DatabaseStructs.TX_CALL_REASONS.TYPE).append(" IS NULL AND ").append(DatabaseStructs.TX_CALL_REASONS.REASON).append(" IS NULL ");
                checkCallReasonQuery = query.toString();
            }
            CCATLogger.DEBUG_LOGGER.debug("CallReasonDao -> checkCallReason() : Executing Query : " + checkCallReasonQuery);
            return jdbcTemplate.query(checkCallReasonQuery, callReasonExtractor, userId);
        } catch (Exception ex) {
            CCATLogger.ERROR_LOGGER.error("CallReasonDao -> checkCallReason() Error Occurred during executing query : " + updateCallReasonQuery);
            throw new UserManagementException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, ex.getMessage());
        }
    }
}
