package com.asset.ccat.lookup_service.services;



import com.asset.ccat.lookup_service.database.dao.AccountGroupsBitsDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AccountGroupBitModel;
import com.asset.ccat.lookup_service.models.responses.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountGroupsBitsService {


    @Autowired
    private AccountGroupsBitsDao accountGroupsBitsDao;

    public GetAllAccountGroupsBitsDescResponse getAllAccountGroupsBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all AccountGroupsBits.");
        List<AccountGroupBitModel> accountGroupsBits = accountGroupsBitsDao.retrieveAccountGroupsBits();
        if (accountGroupsBits == null || accountGroupsBits.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No accountGroupsBits were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "accountGroupsBits");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all accountGroupsBits with size[" + accountGroupsBits.size() + "].");
        return new GetAllAccountGroupsBitsDescResponse(accountGroupsBits);
    }


    public void updateAccountGroupBit(AccountGroupBitModel accountGroupBit) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating accountGroupBit with BIT_POSITION[" + accountGroupBit.getBitPosition() + "].");
        int isUpdated = accountGroupsBitsDao.updateAccountGroupBit(accountGroupBit);
        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update accountGroupBit.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "bitPosition " + accountGroupBit.getBitPosition());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating accountGroupBit with BIT_POSITION[" + accountGroupBit.getBitPosition() + "].");
    }

}
