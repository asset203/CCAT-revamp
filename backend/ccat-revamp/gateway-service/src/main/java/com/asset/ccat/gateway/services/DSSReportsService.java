/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.defines.DSSReports;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.customer_care.DSSReportModel;
import com.asset.ccat.gateway.models.requests.customer_care.history.DSSReportRequest;
import com.asset.ccat.gateway.models.requests.report.GetContractBillReportRequest;
import com.asset.ccat.gateway.models.requests.report.GetDssReportRequest;
import com.asset.ccat.gateway.proxy.DSSProxy;
import com.asset.ccat.gateway.redis.model.DSSRedisModel;
import com.asset.ccat.gateway.redis.repository.DSSRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.function.Function;

/**
 * @author wael.mohamed
 */
@Component
public class DSSReportsService {

    private final DSSRepository dssRepository;

    private final DSSProxy dSSProxy;

    public DSSReportsService(DSSRepository dssRepository, DSSProxy dSSProxy) {
        this.dssRepository = dssRepository;
        this.dSSProxy = dSSProxy;
    }

    public DSSReportModel getTrafficBehaviorReport(DSSReportRequest request) throws GatewayException {
        return dSSProxy.getTrafficBehaviorReport(request);
    }

    public DSSReportModel getBtiVrReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> "BTIVR".equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || "BTIVR".equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getBtiVrReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName("BTIVR");
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }

    public DSSReportModel getUSSDReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> "USSD".equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null){
            report = dSSProxy.getUSSDReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName("USSD");
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, Math.min(fetchCount, report.getData().size()));
            report.setData(data);
        }
        return report;
    }

    public DSSReportModel getVodafoneOneRedeemReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> "VodafoneOneRedeem".equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });

        if(redisModel == null || !"VodafoneOneRedeem".equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getVodafoneOneRedeemReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName("VodafoneOneRedeem");
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
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
    
	public DSSReportModel getContractBillReport(GetContractBillReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> DSSReports.CONTRACT_BILL.getPageName().equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || !DSSReports.CONTRACT_BILL.getPageName().equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getContractBillReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName(DSSReports.CONTRACT_BILL.getPageName());
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPagination() != null){
            int offset = request.getPagination().getOffset() == null ? 0 : request.getPagination().getOffset();
            int fetchCount = request.getPagination().getFetchCount() == null ? report.getData().size() : request.getPagination().getFetchCount();

            offset = Math.max(0, Math.min(offset, report.getData().size()));
            fetchCount = Math.max(0, Math.min(fetchCount, report.getData().size() - offset));

            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }
    public DSSReportModel getVodafoneOneProfileReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> DSSReports.VODAFONE_ONE.getPageName().equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || !DSSReports.VODAFONE_ONE.getPageName().equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getVodafoneOneProfileReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName(DSSReports.VODAFONE_ONE.getPageName());
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }
    public DSSReportModel getContractBalanceReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> DSSReports.CONTRACT_BALANCE.getPageName().equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || !DSSReports.CONTRACT_BALANCE.getPageName().equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getContractBalanceReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName(DSSReports.CONTRACT_BALANCE.getPageName());
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }
    public DSSReportModel getContractBalanceTransferReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> DSSReports.CONTRACT_BALANCE_TRANSFER.getPageName().equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || !DSSReports.CONTRACT_BALANCE.getPageName().equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getContractBalanceTransferReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName(DSSReports.CONTRACT_BALANCE_TRANSFER.getPageName());
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }
	public DSSReportModel getVisitedUrlReport(DSSReportRequest request) throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .filter(model -> "VisitedUrl".equalsIgnoreCase(model.getDssPageName()))
                .orElseGet(() -> {
                    dssRepository.findById(request.getMsisdn()).ifPresent(oldModel -> {
                        CCATLogger.DEBUG_LOGGER.debug("Deleting the old cached page -- {}", oldModel.getDssPageName());
                        dssRepository.deleteById(request.getMsisdn());
                    });
                    return null;
                });
        if(redisModel == null || "VisitedUrl".equalsIgnoreCase(redisModel.getDssPageName())){
            report = dSSProxy.getVisitedUrlReport(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName("VisitedUrl");
            dssRepository.save(redisModel);
        } else {
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(), new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }
        if(request.getPaginationModel() != null){
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();
            List<Map<Integer, String>> data = report.getData().subList(offset, fetchCount);
            report.setData(data);
        }
        return report;
    }

    // GENERIC METHOD
    // TODO :: TEST METHOD
    public DSSReportModel getReport(DSSReportRequest request, String pageName, Function<DSSReportRequest, DSSReportModel> fetchReportFunction)
            throws GatewayException, JsonProcessingException {
        DSSReportModel report;
        ObjectMapper mapper = new ObjectMapper();
        DSSRedisModel redisModel = dssRepository.findById(request.getMsisdn())
                .orElse(null);

        if (redisModel == null || pageName.equalsIgnoreCase(redisModel.getDssPageName())) {
            // Fetch report using the provided function
            report = fetchReportFunction.apply(request);
            redisModel = new DSSRedisModel(request.getMsisdn(), report);

            // Serialize data to JSON and cache it
            String json = mapper.writeValueAsString(report.getData());
            redisModel.setJsonData(json);
            redisModel.setDssPageName(pageName);
            dssRepository.save(redisModel);
        } else {
            // Retrieve the report from the cache
            report = redisModel.getDssReportModel();
            List<Map<Integer, String>> data = mapper.readValue(redisModel.getJsonData(),
                    new TypeReference<List<Map<Integer, String>>>() {});
            report.setData(data);
        }

        // Handle pagination
        if (request.getPaginationModel() != null) {
            int offset = request.getPaginationModel().getOffset() == null ? 0 : request.getPaginationModel().getOffset();
            int fetchCount = request.getPaginationModel().getFetchCount() == null ? report.getData().size() : request.getPaginationModel().getFetchCount();

            //validate that offset is less than or equal to the size of the list
            offset = Math.max(0, Math.min(offset, report.getData().size()));
            //validate that fetchCount is less than the list size
            fetchCount = Math.max(0, Math.min(fetchCount, report.getData().size() - offset));

            List<Map<Integer, String>> data = report.getData().subList(offset, Math.min(report.getData().size(), offset + fetchCount));
            report.setData(data);
        }

        return report;
    }


}
