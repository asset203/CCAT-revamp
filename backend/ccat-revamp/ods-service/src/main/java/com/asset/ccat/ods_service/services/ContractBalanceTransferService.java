package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.cache.DSSCache;
import com.asset.ccat.ods_service.database.dao.ReportsDao;
import com.asset.ccat.ods_service.database.mapper.DssReportMapper;
import com.asset.ccat.ods_service.defines.DSSReports;
import com.asset.ccat.ods_service.defines.enums.SPParams;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.DSSReportRequest;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import com.asset.ccat.ods_service.utils.DateUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ContractBalanceTransferService implements DSSReportService<DSSResponseModel, DSSReportRequest> {
    private final ReportsDao reportsDao;
    private final DSSCache dssCache;
    private final DssReportMapper mapper;
    private final DateUtils dateUtil;

    public ContractBalanceTransferService(ReportsDao reportsDao, DSSCache dssCache, DssReportMapper mapper, DateUtils dateUtil) {
        this.reportsDao = reportsDao;
        this.dssCache = dssCache;
        this.mapper = mapper;
        this.dateUtil = dateUtil;
    }

    @Override
    public DSSResponseModel getReport(DSSReportRequest request) throws ODSException, SQLException {
        String spName = getSPName(DSSReports.CONTRACT_BALANCE_TRANSFER.getPageName(), dssCache);
        Map<String, Object> spResponse = reportsDao.executeStoredProcedure(spName, setInParamNameValueMap(request));
        return parseSPResponse(spResponse, spName);
    }

    @Override
    public Map<String, Object> setInParamNameValueMap(DSSReportRequest request) {
        Map<String, Object> paramNameValueMap = new LinkedHashMap<>();
        paramNameValueMap.put(SPParams.MSISDN.getParamName(), request.getMsisdn());
        paramNameValueMap.put(SPParams.FROM_DATE.getParamName(), dateUtil.getStringDate(request.getDateFrom()));
        paramNameValueMap.put(SPParams.TO_DATE.getParamName(), dateUtil.getStringDate(request.getDateTo()));
        paramNameValueMap.put(SPParams.FLAG.getParamName(), request.getFlag());
        return paramNameValueMap;
    }

    @Override
    public DSSResponseModel parseSPResponse(Map<String, Object> spResponse, String spName) throws SQLException {
        BigDecimal statusCode = (BigDecimal) spResponse.get(SPParams.ERROR_CODE.getParamName());
        String statusMessage = (String) spResponse.get(SPParams.ERROR_DESCRIPTION.getParamName());
        CCATLogger.DEBUG_LOGGER.debug("SP Response Code = {}: {}", statusCode, statusMessage);

        @SuppressWarnings("unchecked")
        ArrayList<LinkedCaseInsensitiveMap> array = (ArrayList<LinkedCaseInsensitiveMap>)
                Optional.ofNullable(spResponse.get(SPParams.OUTPUT_CURSOR.getParamName())).orElse(new ArrayList<>());
        CCATLogger.DEBUG_LOGGER.debug("#Retrieved Data = {}", array.size());

        DSSResponseModel dssModel = mapper.mapRow(array, spName);
        dssModel.setExternalCode(statusCode.intValue());
        dssModel.setExternalDescription(statusMessage);
        return dssModel;
    }
}
