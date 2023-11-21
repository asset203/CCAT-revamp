/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.database.dao.UsersDao;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.manager.UsersManager;
import com.asset.ccat.user_management.models.users.UserModel;
import com.asset.ccat.user_management.security.JwtTokenUtil;
//import com.asset.ldap.common.LDAPIntegration;
//import com.asset.ldap.model.LdapUserModel;
import java.util.List;
import jdk.jshell.ErroneousSnippet;
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

//    public LdapUserModel authenticateUser(String username, String credential) throws UserManagementException {
//
//        LdapUserModel userModel = null;
//        String[] urls = properties.getLdapServerUrl().split(",");
//        String domain = properties.getLdapDomainName();
//        String searchBase = properties.getLdapUsersSearchBase();
//        for (String url : urls) {
//            try {
//                LDAPIntegration ldapIntegration = new LDAPIntegration(url, domain);
//
//                userModel = ldapIntegration.authenticateUser(username, credential, searchBase);
//                if (userModel != null) {
//                    return userModel;
//                }
//            } catch (Exception e) {
//                String msg = "Exception in UserService.authenticateUser()";
//                CCATLogger.DEBUG_LOGGER.info(msg + " IS " + e.getMessage());
//                CCATLogger.ERROR_LOGGER.error(msg + " IS " + e.getMessage(), e);
//            }
//        }
//        throw new UserManagementException(ErrorCodes.ERROR.LDAP_AUTH_FAILED);
//    }

}
