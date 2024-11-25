/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.LoginException;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ldap.common.LDAPIntegration;
import com.asset.ldap.model.LdapUserModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class LdapService {

    @Autowired
    Properties properties;

    public LdapUserModel authenticateUser(String username, String credential) throws UserManagementException {

        LdapUserModel userModel = null;
        String[] urls = properties.getLdapServerUrl().split(",");
        String domain = properties.getLdapDomainName();
        String searchBase = properties.getLdapUsersSearchBase();
        for (String url : urls) {
            try {
                LDAPIntegration ldapIntegration = new LDAPIntegration(url, domain);

                userModel = ldapIntegration.authenticateUser(username, credential, searchBase);
                if (userModel != null) {
                    return userModel;
                }
            } catch (Exception e) {
                CCATLogger.DEBUG_LOGGER.error("Exception occurred while ldap authentication. ", e);
                CCATLogger.ERROR_LOGGER.error("Exception occurred while ldap authentication. ", e);
            }
        }
        throw new LoginException(ErrorCodes.ERROR.LDAP_AUTH_FAILED);
    }

}
