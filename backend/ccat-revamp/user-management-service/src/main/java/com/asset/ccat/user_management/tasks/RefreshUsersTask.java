/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.tasks;

import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.manager.UsersManager;
import org.springframework.stereotype.Component;

/**
 *
 * @author Mahmoud Shehab
 */
@Component
public class RefreshUsersTask implements Runnable {

    private final UsersManager userManager;

    public RefreshUsersTask(UsersManager userManager) {
        this.userManager = userManager;
    }

    @Override
    public void run() {
        CCATLogger.DEBUG_LOGGER.info("Start refresh Task");
        userManager.refreshUsers();
    }

}
