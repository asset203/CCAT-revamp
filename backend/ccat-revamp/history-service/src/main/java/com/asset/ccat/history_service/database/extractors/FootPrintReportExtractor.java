package com.asset.ccat.history_service.database.extractors;

import com.asset.ccat.history_service.defines.DBStructs;
import com.asset.ccat.rabbitmq.models.FootprintDetailsModel;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author Assem Hassan
 */
@Component
public class FootPrintReportExtractor implements ResultSetExtractor<HashMap<String, FootprintModel>> {
    @Override
    public HashMap<String, FootprintModel> extractData(ResultSet resultSet) throws SQLException, DataAccessException {
        HashMap<String, FootprintModel> footprintReportMap = new HashMap<>();
        String requestId;

        while (resultSet.next()) {
            requestId = resultSet.getString(DBStructs.TX_USER_FOOTPRINT.REQUEST_ID);
            if (Objects.isNull(footprintReportMap.get(requestId))) {
                FootprintModel footprintModel = new FootprintModel();
                footprintModel.setRequestId(requestId);
                footprintModel.setId(resultSet.getLong(DBStructs.TX_USER_FOOTPRINT.ID));
                footprintModel.setPageName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.PAGE_NAME));
                footprintModel.setTabName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.TAB_NAME));
                footprintModel.setActionName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.ACTION_NAME));
                footprintModel.setActionType(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.ACTION_TYPE));
                footprintModel.setActionTime(new Date(resultSet.getTimestamp(DBStructs.TX_USER_FOOTPRINT.ACTION_TIME).getTime()));
                footprintModel.setUserName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.USERNAME));
                footprintModel.setProfileName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.PROFILE_NAME));
                footprintModel.setMsisdn(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.MSISDN));
                footprintModel.setStatus(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.STATUS));
                footprintModel.setErrorMessage(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.ERROR_MESSAGE));
                footprintModel.setErrorCode(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.ERROR_CODE));
                footprintModel.setSessionId(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.SESSION_ID));
                footprintModel.setMachineName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT.MACHINE_NAME));
                footprintModel.setFootPrintDetails(new ArrayList<>());

                footprintReportMap.put(requestId, footprintModel);
            }

            FootprintDetailsModel footprintDetailsModel = new FootprintDetailsModel();
            footprintDetailsModel.setRequestId(requestId);
            footprintDetailsModel.setParamName(resultSet.getString(DBStructs.TX_USER_FOOTPRINT_DETAILS.PARAM_NAME));
            footprintDetailsModel.setOldValue(resultSet.getString(DBStructs.TX_USER_FOOTPRINT_DETAILS.OLD_VALUE));
            footprintDetailsModel.setNewValue(resultSet.getString(DBStructs.TX_USER_FOOTPRINT_DETAILS.NEW_VALUE));
            footprintReportMap.get(requestId).getFootPrintDetails().add(footprintDetailsModel);
        }

        return footprintReportMap;
    }
}
