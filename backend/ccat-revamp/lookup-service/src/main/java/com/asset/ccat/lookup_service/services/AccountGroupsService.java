package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.database.dao.AccountGroupsBitsDao;
import com.asset.ccat.lookup_service.database.dao.AccountGroupsDao;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AccountGroupBitDescModel;
import com.asset.ccat.lookup_service.models.AccountGroupModel;
import com.asset.ccat.lookup_service.models.AccountGroupWithBitsModel;
import com.asset.ccat.lookup_service.models.CommunityAdminModel;
import com.asset.ccat.lookup_service.models.responses.account_groups.GetAllAccountGroupsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class AccountGroupsService {


    @Autowired
    private AccountGroupsDao accountGroupsDao;

    @Autowired
    private AccountGroupsBitsDao accountGroupsBitsDao;

    public GetAllAccountGroupsResponse getAllAccountGroups() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all AccountGroups.");
        List<AccountGroupModel> accountGroups = accountGroupsDao.retrieveAccountGroups();
        if (accountGroups == null || accountGroups.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No accountGroups were found.");
            throw new LookupException(ErrorCodes.ERROR.NO_DATA_FOUND, Defines.SEVERITY.ERROR, "accountGroups");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all accountGroups with size[" + accountGroups.size() + "].");
        return new GetAllAccountGroupsResponse(accountGroups);
    }

    public Boolean isAccountGroupIdExists(int accountGroupId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if account group with ID[" + accountGroupId + "] exists in DB");
        return accountGroupsDao.findAccountGroupById(accountGroupId) > 0;
    }

    public Boolean isAccountGroupDescExists(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Checking if account group with Description[" + accountGroup.getAccountGroupDescription()
                + "] exists in DB");
        List<AccountGroupModel> res = accountGroupsDao.findAccountGroup(accountGroup);
        if (res.size() == 0) return false;
        return (!res.get(0).getAccountGroupId().equals(accountGroup.getAccountGroupId()));
    }

    public void updateAccountGroup(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating accountGroup with GROUP_ID[" + accountGroup.getAccountGroupId() + "].");
        int isUpdated = accountGroupsDao.updateAccountGroup(accountGroup);
        if (isUpdated <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update accountGroup.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "groupId " + accountGroup.getAccountGroupId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating accountGroup with GROUP_ID[" + accountGroup.getAccountGroupId() + "].");
    }


    public void deleteAccountGroup(int accountGroupId) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting accountGroup with GROUP_ID[" + accountGroupId + "].");
        int isDeleted = accountGroupsDao.deleteAccountGroup(accountGroupId);
        if (isDeleted <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete accountGroup.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "groupId " + accountGroupId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting accountGroup with GROUP_ID[" + accountGroupId + "].");
    }

    public void addAccountGroup(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding accountGroup");
        int isAdded = accountGroupsDao.addAccountGroup(accountGroup);
        if (isAdded <= 0) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add accountGroup.");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "groupId " + accountGroup.getAccountGroupId());
        }
        CCATLogger.DEBUG_LOGGER.debug("Done adding accountGroup with GROUP_ID[" + accountGroup.getAccountGroupId() + "].");
    }

    public HashMap<Integer, AccountGroupWithBitsModel> getAccountGroupsWithBits() throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Start getting account groups bits with description");
        List<AccountGroupBitDescModel> bitsLookup = accountGroupsBitsDao.retrieveAccountGroupsBitswithDesc();

        CCATLogger.DEBUG_LOGGER.debug("Start getting account groups map");
        HashMap<Integer, AccountGroupWithBitsModel> accountGroupsMap = accountGroupsDao.retrieveAccountGroupsMap(bitsLookup);

        return accountGroupsMap;
    }


}
