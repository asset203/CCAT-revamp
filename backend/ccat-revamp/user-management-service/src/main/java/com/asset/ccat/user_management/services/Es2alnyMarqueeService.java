/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.services;

import com.asset.ccat.user_management.database.dao.Es2alnyMarqueeDao;
import com.asset.ccat.user_management.defines.Defines.SEVERITY;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.marquee.GetAllMarqueeResponse;
import com.asset.ccat.user_management.models.users.MarqueeModel;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author wael.mohamed
 */
@Service
public class Es2alnyMarqueeService {

    @Autowired
    private Es2alnyMarqueeDao es2alnyMarqueeDao;

    public GetAllMarqueeResponse getAllMarquees() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all marquees.");
        List<MarqueeModel> marquees = es2alnyMarqueeDao.getAllMarquees();
        if (marquees == null || marquees.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No marquees found.");
            //throw new UserManagementException(ErrorCodes.ERROR.NO_MARQUEE_FOUND, SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all marquees.");
        return new GetAllMarqueeResponse(marquees);
    }

    public void deleteMarqueeById(Integer marqueeId) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving marquee with id [" + marqueeId + "]");
        boolean isDeleted = es2alnyMarqueeDao.deleteMarqueeById(marqueeId);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.debug("Marquee with id [" + marqueeId + "] was not found");
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "marquee " + marqueeId);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting marquee with id [" + marqueeId + "]");
    }

    public void addMarquee(MarqueeModel marquee) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding marquee [" + marquee.getTitle() + "]");
        boolean isAdded = es2alnyMarqueeDao.addMarquee(marquee);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add marquee");
            throw new UserManagementException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR, "marquee " + marquee.getTitle());
        }
        CCATLogger.DEBUG_LOGGER.debug("Added marquee successfull.");
    }

    public void updateMarquee(MarqueeModel marquee) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating marquee [" + marquee.getTitle() + "]");
        boolean isAdded = es2alnyMarqueeDao.updateMarquee(marquee);
        if (!isAdded) {
            CCATLogger.DEBUG_LOGGER.error("Failed to updating marquee");
            throw new UserManagementException(ErrorCodes.ERROR.ADD_FAILED, SEVERITY.ERROR, "marquee " + marquee.getTitle());
        }
        CCATLogger.DEBUG_LOGGER.debug("Updated marquee successfull.");
    }

    @Transactional(rollbackFor = UserManagementException.class)
    public void deleteAllMarquees() throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting all marquees.");
        boolean isDeleted = es2alnyMarqueeDao.deleteAllMarquees();
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Deleting all marquees faild.");
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "marquees");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting all marquees.");
    }

    @Transactional(rollbackFor = UserManagementException.class)
    public void deleteAllMarquees(List<Integer> marquees) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting all marquees with size [" + marquees.size() + "]");
        boolean isDeleted = es2alnyMarqueeDao.deleteAllMarquees(marquees);
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Deleting all marquees faild.");
            throw new UserManagementException(ErrorCodes.ERROR.DELETE_FAILED, SEVERITY.ERROR, "marquees");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done deleting all marquees.");
    }

    @Transactional(rollbackFor = UserManagementException.class)
    public void updateAllMarquees(List<MarqueeModel> marquees) throws UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start updating all marquees with size [" + marquees.size() + "]");
        boolean isUpdated = es2alnyMarqueeDao.updateAllMarquees(marquees);
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("updating all marquees faild.");
            throw new UserManagementException(ErrorCodes.ERROR.UPDATE_FAILED, SEVERITY.ERROR, "marquees");
        }
        CCATLogger.DEBUG_LOGGER.debug("Done updating all marquees.");
    }

}
