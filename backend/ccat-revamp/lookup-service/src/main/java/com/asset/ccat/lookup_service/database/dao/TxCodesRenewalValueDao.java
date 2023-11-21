package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.database.extractors.TxCodesRenewalValueExtractor;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.TxCodeRenewalValue;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class TxCodesRenewalValueDao {

    private final JdbcTemplate jdbcTemplate;
    private final TxCodesRenewalValueExtractor txCodesRenewalValueExtractor;

    private String retrieveCodesRenewalValueQuery;

    public TxCodesRenewalValueDao(JdbcTemplate jdbcTemplate, TxCodesRenewalValueExtractor txCodesRenewalValueExtractor) {
        this.jdbcTemplate = jdbcTemplate;
        this.txCodesRenewalValueExtractor = txCodesRenewalValueExtractor;
    }

    public Map<Integer, TxCodeRenewalValue> retrieveCodesRenewalsValues() {
        Map<Integer, TxCodeRenewalValue> renewalsValues = new HashMap<>();
        try {
            retrieveCodesRenewalValueQuery = "SELECT * FROM " + DatabaseStructs.ADM_TX_CODES_RENEWAL_VALUE.TABLE_NAME;
            renewalsValues = jdbcTemplate.query(retrieveCodesRenewalValueQuery, txCodesRenewalValueExtractor);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while retrieving CI Codes renewal value" + retrieveCodesRenewalValueQuery);
            CCATLogger.ERROR_LOGGER.error("Error while retrieving CI Codes renewal value " + retrieveCodesRenewalValueQuery, ex);
        }
        return renewalsValues;
    }

}
