package com.asset.ccat.lookup_service.validators;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.AccountGroupModel;
import com.asset.ccat.lookup_service.services.AccountGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountGroupsValidator {


    @Autowired
    private AccountGroupsService accountGroupsService;

    public void isAccountGroupUpdateValid(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if account group [" + accountGroup + "] not found");
        if (!accountGroupsService.isAccountGroupIdExists(accountGroup.getAccountGroupId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating account group failed , Group with ID [" + accountGroup.getAccountGroupId() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.VALIDATION,"Account Group Id:"+ accountGroup.getAccountGroupId()+"");
        }
        if (accountGroupsService.isAccountGroupDescExists(accountGroup)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating account group failed , Group with Name [" + accountGroup.getAccountGroupDescription() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"Account Group Description:"+ accountGroup.getAccountGroupDescription());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  account group  successfully");
    }

    public void isAccountGroupAddValid(AccountGroupModel accountGroup) throws LookupException {
        CCATLogger.DEBUG_LOGGER.info("Start validating if account group [" + accountGroup + "] exists");
        if (accountGroupsService.isAccountGroupIdExists(accountGroup.getAccountGroupId())) {
            CCATLogger.DEBUG_LOGGER.debug("Validating account group failed , Group with ID [" + accountGroup.getAccountGroupId() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"Account Group Id:" + accountGroup.getAccountGroupId()+"");
        }
        if (accountGroupsService.isAccountGroupDescExists(accountGroup)) {
            CCATLogger.DEBUG_LOGGER.debug("Validating account group failed , Group with Name [" + accountGroup.getAccountGroupDescription() + "] exist");
            throw new LookupException(ErrorCodes.ERROR.DUPLICATED_DATA, Defines.SEVERITY.VALIDATION,"Account Group Description:"+ accountGroup.getAccountGroupDescription());
        }
        CCATLogger.DEBUG_LOGGER.info("Finished validating  account group  successfully");
    }
}
