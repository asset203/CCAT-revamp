package com.asset.ccat.ods_service.services;

import com.asset.ccat.ods_service.cache.DSSCache;
import com.asset.ccat.ods_service.database.dao.ReportsDao;
import com.asset.ccat.ods_service.database.mapper.DssReportMapper;
import com.asset.ccat.ods_service.defines.DSSReports;
import com.asset.ccat.ods_service.exceptions.ODSException;
import com.asset.ccat.ods_service.logger.CCATLogger;
import com.asset.ccat.ods_service.models.requests.ContractBillRequest;
import com.asset.ccat.ods_service.models.responses.DSSResponseModel;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class ContractBillService  implements DSSReportService<DSSResponseModel, ContractBillRequest> {
    private final ReportsDao reportsDao;
    private final DSSCache dssCache;
    private final DssReportMapper mapper;

    public ContractBillService(ReportsDao reportsDao, DSSCache dssCache, DssReportMapper mapper) {
        this.reportsDao = reportsDao;
        this.dssCache = dssCache;
        this.mapper = mapper;
    }

    @Override
    public DSSResponseModel getReport(ContractBillRequest request) throws ODSException, SQLException {
        String spName = getSPName(DSSReports.CONTRACT_BILL.getPageName(), dssCache);
        Map<String, Object> spResponse = reportsDao.executeStoredProcedure(spName, setInParamNameValueMap(request));
        return parseSPResponse(spResponse, spName);
    }

    @Override
    public Map<String, Object> setInParamNameValueMap(ContractBillRequest request) {
        Map<String, Object> paramNameValueMap = new LinkedHashMap<>();
        paramNameValueMap.put("INPUT_IDENTIFIER", request.getMsisdn());
        paramNameValueMap.put("INPUT_TYPE", 1);
        paramNameValueMap.put("NUMBER_OF_BILLS", request.getNumOfBill());
        paramNameValueMap.put("REPORT_TYPE", request.getReportType());
        return paramNameValueMap;
    }

    @Override
    public DSSResponseModel parseSPResponse(Map<String, Object> spResponse, String spName) throws SQLException {
        BigDecimal statusCode = (BigDecimal) spResponse.get("ERROR_CODE");
        String statusMessage = (String) spResponse.get("ERROR_DESCRIPTION");
        CCATLogger.DEBUG_LOGGER.debug("SP Response Code = {}: {}", statusCode, statusMessage);

        @SuppressWarnings("unchecked")
        ArrayList<LinkedCaseInsensitiveMap> array = (ArrayList<LinkedCaseInsensitiveMap>)
                Optional.ofNullable(spResponse.get("CONTRACT_BILLS")).orElse(new ArrayList<>());
        CCATLogger.DEBUG_LOGGER.debug("#Retrieved Data = {}", array.size());

        DSSResponseModel dssModel = mapper.mapRow(array, spName);
        dssModel.setExternalCode(statusCode.intValue());
        dssModel.setExternalDescription(statusMessage);
        return dssModel;
    }
}
