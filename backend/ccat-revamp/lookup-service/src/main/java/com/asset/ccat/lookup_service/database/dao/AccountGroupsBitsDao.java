package com.asset.ccat.lookup_service.database.dao;

import com.asset.ccat.lookup_service.annotation.LogExecutionTime;
import com.asset.ccat.lookup_service.database.extractors.AccountGroupBitsWithDescExtractor;
import com.asset.ccat.lookup_service.database.row_mapper.AccountGroupsBitsRowMapper;
import com.asset.ccat.lookup_service.defines.DatabaseStructs;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupBitModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import com.asset.ccat.lookup_service.util.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;

@Repository
public class AccountGroupsBitsDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private Utils utils;
    @Autowired
    private AccountGroupBitsWithDescExtractor accountGroupBitsWithDescExtractor;

    private String retrieveAccountGroupsBits;
    private String retrieveAccountGroupsBitsWithDesc;
    private String updateAccountGroupBitQuery;


    @LogExecutionTime
    public List<AccountGroupBitModel> retrieveAccountGroupsBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Starting AccountGroupsBitsDao - retrieveAccountGroupsBits");

        List<AccountGroupBitModel> accountGroupsBits;

        try {
            if (retrieveAccountGroupsBits == null) {

                StringBuilder query = new StringBuilder();
                query.append("Select * From ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.TABLE_NAME)
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_POSITION)
                        .append(" ASC ");
                retrieveAccountGroupsBits = query.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveAccountGroupsBits);

            accountGroupsBits = jdbcTemplate.query(retrieveAccountGroupsBits.toString(), new AccountGroupsBitsRowMapper());
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());

        }
        CCATLogger.DEBUG_LOGGER.info("result is " + accountGroupsBits);
        CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsBitsDao - retrieveAccountGroupsBits");
        return accountGroupsBits;
    }


    @LogExecutionTime
    public int updateAccountGroupBit(AccountGroupBitModel accountGroupBit) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting AccountGroupsBitsDao - updateAccountGroupBit");

        try {
            if (updateAccountGroupBitQuery == null) {
                StringBuilder query = new StringBuilder();
                query
                        .append("update " + DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.TABLE_NAME
                                + " SET "
                                + DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_NAME + " = ? "
                                + " WHERE "
                                + DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_POSITION + " = ?");
                updateAccountGroupBitQuery = query.toString();
            }
            int res = jdbcTemplate.update(updateAccountGroupBitQuery.toString(),
                    accountGroupBit.getBitName(),
                    accountGroupBit.getBitPosition()
            );
            CCATLogger.DEBUG_LOGGER.debug("SqlStatement = " + updateAccountGroupBitQuery);
            CCATLogger.DEBUG_LOGGER.info("result is " + res);
            CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsBitsDao - updateAccountGroupBit");
            return res;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
            throw new LookupException(ErrorCodes.ERROR.DATABASE_ERROR, Defines.SEVERITY.ERROR, e.getMessage());
        }
    }

    @LogExecutionTime
    public List<AccountGroupBitDescModel> retrieveAccountGroupsBitswithDesc() {
        CCATLogger.DEBUG_LOGGER.info("Starting AccountGroupsBitsDao - retrieveAccountGroupsBitswithDesc");

        List<AccountGroupBitDescModel> accountGroupsBits = null;

        try {
            if (retrieveAccountGroupsBitsWithDesc == null) {
                StringBuilder queryBuilder = new StringBuilder();
                queryBuilder.append("SELECT * FROM ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.TABLE_NAME)
                        .append(" lEFT JOIN ")
                        .append(DatabaseStructs.ADM_AG_BITS_SC_DESCRIPTION.TABLE_NAME)
                        .append(" ON ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_POSITION)
                        .append(" = ")
                        .append(DatabaseStructs.ADM_AG_BITS_SC_DESCRIPTION.TABLE_NAME).append(".").append(DatabaseStructs.ADM_AG_BITS_SC_DESCRIPTION.BIT_POSITION)
                        .append(" Order By ")
                        .append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.TABLE_NAME).append(".").append(DatabaseStructs.ADM_ACCOUNT_GROUP_BITS.BIT_POSITION)
                        .append(" ASC ");
                retrieveAccountGroupsBitsWithDesc = queryBuilder.toString();

            }

            CCATLogger.DEBUG_LOGGER.info("SqlStatement = " + retrieveAccountGroupsBitsWithDesc);

            accountGroupsBits = jdbcTemplate.query(retrieveAccountGroupsBitsWithDesc, accountGroupBitsWithDescExtractor);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("EXCEPTION --> \n" + e);
            CCATLogger.ERROR_LOGGER.error("EXCEPTION --> ", e);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ending AccountGroupsBitsDao - retrieveAccountGroupsBitswithDesc");
        return accountGroupsBits;
    }

}
