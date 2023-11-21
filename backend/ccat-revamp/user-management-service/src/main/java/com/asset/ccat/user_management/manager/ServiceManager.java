/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.manager;

import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.services.UserService;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class ServiceManager {
    
    @Autowired
    UserService userservice;
    
    @Autowired
    UsersManager userManager;
    
    @EventListener
    public void startupEvent(ContextRefreshedEvent event) {
        CCATLogger.DEBUG_LOGGER.debug("starting user management service");
        try {
            userManager.init();
        } catch (Exception ex) {
            Logger.getLogger(ServiceManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
