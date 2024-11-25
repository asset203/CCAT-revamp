package com.asset.usagesimulator.services;

import com.asset.usagesimulator.dao.BonusAndDedicatedDao;
import com.asset.usagesimulator.loggers.AppLogger;
import com.asset.usagesimulator.models.BonusAndDedicatedAdjustmentL;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class BonusAndDedicatedService {
    private final BonusAndDedicatedDao badDao;

    public BonusAndDedicatedService(BonusAndDedicatedDao badDao) {
        this.badDao = badDao;
    }

    public void insertBADRecord(String msisdn) throws JsonProcessingException {
        String badRequest = "{ \"results\": [" +
                "{ \"TRANS_TYPE\": \"CRE\", \"TRANS_CODE\": \"RESETBILLLIMITMI\", \"DA_IDS\": 68, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-11\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"MIONBILLIM-CLR\", \"DA_IDS\": 68, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-11\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-06\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-06\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-07\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-07\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-08\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-08\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-09\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-09\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-10\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-10\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-11\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-11\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"SET-DA-0-NOBAL-FLAG\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-05\"}," +
                "{ \"TRANS_TYPE\": \"DEB\", \"TRANS_CODE\": \"CLR-DA-NOBAL-EXPIRE\", \"DA_IDS\": 32, \"DA_AMOUNTS\": 0, \"TRX_DATE\": \"2024-11-05\"}" +
                "]}";
//        String jsonRequest = formatToJson(badRequest);

        try {
            ObjectMapper mapper = new ObjectMapper();
            BonusAndDedicatedAdjustmentL l = mapper.readValue(badRequest, BonusAndDedicatedAdjustmentL.class);
            int[] updatedRows = badDao.insertBonusAndDedicatedAdjustments(msisdn, l);
            AppLogger.DEBUG_LOGGER.debug("updated rows = {}", updatedRows);

        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }

    }

    private String formatToJson(String request) {
        // Step 1: Replace the 'results=[' part and the ']'
        request = request.replace("results=[", "{ \"results\": [").replace("]", "]}");

        // Step 2: Replace '=' with ':' and ensure all keys and values are properly quoted
        request = request.replaceAll("([A-Za-z_]+)=", "\"$1\":")
                .replaceAll(",\\s*}", "}")
                .replaceAll(",\\s*\\{", "{");

        // Step 3: Ensure values that are strings are properly quoted
        request = request.replaceAll(": ([A-Za-z0-9-]+)", ": \"$1\"");

        // Step 4: Ensure date values are correctly quoted as strings
        request = request.replaceAll("TRX_DATE: (\\d{4}-\\d{2}-\\d{2})", "TRX_DATE: \"\"$1\"\"");
        // Return the formatted JSON string
        return request;
    }
}
