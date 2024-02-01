/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.DSSReportRequest;
import com.asset.ccat.gateway.models.requests.report.GetContractBillReportRequest;
import com.asset.ccat.gateway.models.requests.report.GetDssReportRequest;
import com.asset.ccat.gateway.proxy.DSSProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wael.mohamed
 */
@Component
public class DSSReportsService {

    @Autowired
    private DSSProxy dSSProxy;

    public DSSReportModel getTrafficBehaviorReport(DSSReportRequest request) throws GatewayException {
        return dSSProxy.getTrafficBehaviorReport(request);
    }

    public DSSReportModel getBtiVrReport(DSSReportRequest request) throws GatewayException {
        return dSSProxy.getBtiVrReport(request);
    }

    public DSSReportModel getUSSDReport(DSSReportRequest request) throws GatewayException {
        return dSSProxy.getUSSDReport(request);

    }

    public DSSReportModel getGeneralReportData(GetDssReportRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Get Dss Service Report Data Start");
        DSSReportModel vodafoneOneProfile = new DSSReportModel();
        vodafoneOneProfile.setExternalCode(1);
        vodafoneOneProfile.setExternalDescription("external description");
        Map<Integer, String> vodafoneOneProfileHeader = new HashMap<Integer, String>();
        vodafoneOneProfileHeader.put(1, "TRANSFER_DATE");
        vodafoneOneProfileHeader.put(2, "TRANSACTION_WAY");
        vodafoneOneProfileHeader.put(3, "TRANSACTION_TYPE");
        vodafoneOneProfileHeader.put(4, "TRANSITION_CATEGORY");
        vodafoneOneProfileHeader.put(5, "CHANNEL_TYPE");
        vodafoneOneProfileHeader.put(6, "SENDER_MSISDN");
        vodafoneOneProfileHeader.put(7, "RECEIVER_MSISDN");
        vodafoneOneProfileHeader.put(8, "SENDER_CATEGORY");
        vodafoneOneProfileHeader.put(9, "RECEIVER_CATEGORY");
        vodafoneOneProfileHeader.put(10, "TRANSFER_AMOUNT");
        vodafoneOneProfileHeader.put(11, "PRODUCT");
        vodafoneOneProfileHeader.put(12, "REQUEST_SOURCE");
        List<Map<Integer, String>> vodafoneOneProfileData = new ArrayList<Map<Integer, String>>() ;
        vodafoneOneProfile.setHeaders(vodafoneOneProfileHeader);
        CCATLogger.DEBUG_LOGGER.debug("Headers set");
        for(Integer i=1 ;i<5;i++){
            Map<Integer, String> vodafoneOneProfileDataTemp = new HashMap<Integer, String>();
            vodafoneOneProfileDataTemp.put(1, "1010101225");
            vodafoneOneProfileDataTemp.put(2, "50");
            vodafoneOneProfileDataTemp.put(3, "Cat1");
            vodafoneOneProfileDataTemp.put(4, "PRD1");
            vodafoneOneProfileDataTemp.put(5, "trxCategory1");
            vodafoneOneProfileDataTemp.put(6, "chnlType1");
            vodafoneOneProfileDataTemp.put(7, "req1");
            vodafoneOneProfileDataTemp.put(8, "1010101224");
            vodafoneOneProfileDataTemp.put(9, "2014-10-19 00:00:00.0");
            vodafoneOneProfileDataTemp.put(10, "Test1");
            vodafoneOneProfileDataTemp.put(11, "trxType1");
            vodafoneOneProfileDataTemp.put(12, "RCat1");
            vodafoneOneProfileData.add(vodafoneOneProfileDataTemp);
        }
        vodafoneOneProfile.setData(vodafoneOneProfileData);
        CCATLogger.DEBUG_LOGGER.debug("data set");

        vodafoneOneProfile.setTotalNumberOfActivities(100);
        CCATLogger.DEBUG_LOGGER.debug("total number of activities set");

        return vodafoneOneProfile;
    }
    public DSSReportModel getContractBillReport(GetContractBillReportRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Get Dss Service Report Data Start");
        DSSReportModel vodafoneOneProfile = new DSSReportModel();
        vodafoneOneProfile.setExternalCode(1);
        vodafoneOneProfile.setExternalDescription("external description");
        Map<Integer, String> vodafoneOneProfileHeader = new HashMap<Integer, String>();
        vodafoneOneProfileHeader.put(1, "TRANSFER_DATE");
        vodafoneOneProfileHeader.put(2, "TRANSACTION_WAY");
        vodafoneOneProfileHeader.put(3, "TRANSACTION_TYPE");
        vodafoneOneProfileHeader.put(4, "TRANSITION_CATEGORY");
        vodafoneOneProfileHeader.put(5, "CHANNEL_TYPE");
        vodafoneOneProfileHeader.put(6, "SENDER_MSISDN");
        vodafoneOneProfileHeader.put(7, "RECEIVER_MSISDN");
        vodafoneOneProfileHeader.put(8, "SENDER_CATEGORY");
        vodafoneOneProfileHeader.put(9, "RECEIVER_CATEGORY");
        vodafoneOneProfileHeader.put(10, "TRANSFER_AMOUNT");
        vodafoneOneProfileHeader.put(11, "PRODUCT");
        vodafoneOneProfileHeader.put(12, "REQUEST_SOURCE");
        List<Map<Integer, String>> vodafoneOneProfileData = new ArrayList<Map<Integer, String>>() ;
        vodafoneOneProfile.setHeaders(vodafoneOneProfileHeader);
        CCATLogger.DEBUG_LOGGER.debug("Headers set");
        for(Integer i=1 ;i<5;i++){
            Map<Integer, String> vodafoneOneProfileDataTemp = new HashMap<Integer, String>();
            vodafoneOneProfileDataTemp.put(1, "1010101225");
            vodafoneOneProfileDataTemp.put(2, "50");
            vodafoneOneProfileDataTemp.put(3, "Cat1");
            vodafoneOneProfileDataTemp.put(4, "PRD1");
            vodafoneOneProfileDataTemp.put(5, "trxCategory1");
            vodafoneOneProfileDataTemp.put(6, "chnlType1");
            vodafoneOneProfileDataTemp.put(7, "req1");
            vodafoneOneProfileDataTemp.put(8, "1010101224");
            vodafoneOneProfileDataTemp.put(9, "2014-10-19 00:00:00.0");
            vodafoneOneProfileDataTemp.put(10, "Test1");
            vodafoneOneProfileDataTemp.put(11, "trxType1");
            vodafoneOneProfileDataTemp.put(12, "RCat1");
            vodafoneOneProfileData.add(vodafoneOneProfileDataTemp);
        }
        vodafoneOneProfile.setData(vodafoneOneProfileData);
        CCATLogger.DEBUG_LOGGER.debug("data set");

        vodafoneOneProfile.setTotalNumberOfActivities(100);
        CCATLogger.DEBUG_LOGGER.debug("total number of activities set");

        return vodafoneOneProfile;
    }

}
