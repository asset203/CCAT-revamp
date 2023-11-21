package com.asset.ccat.dynamic_page.services;

import com.asset.ccat.dynamic_page.cache.DynamicPagesCache;
import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.database.dao.DynamicPagesDao;
import com.asset.ccat.dynamic_page.database.dao.HttpConfigurationDao;
import com.asset.ccat.dynamic_page.database.dao.LKFeaturesDao;
import com.asset.ccat.dynamic_page.database.dao.ProcedureConfigurationDao;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.mappers.DynamicPageResponseMapper;
import com.asset.ccat.dynamic_page.models.dynamic_page.DynamicPageModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.HttpConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.ProcedureConfigurationModel;
import com.asset.ccat.dynamic_page.models.dynamic_page.StepModel;
import com.asset.ccat.dynamic_page.models.requests.dynamic_page.*;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.AddDynamicPageResponse;
import com.asset.ccat.dynamic_page.models.responses.dynamic_page.ViewDynamicPageResponse;
import com.asset.ccat.dynamic_page.models.shared.LkFeatureModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @author assem.hassan
 */
@Component
public class DynamicPagesService {

    @Autowired
    private DynamicPagesDao dynamicPagesDao;
    @Autowired
    private LKFeaturesDao lkFeaturesDao;

    @Autowired
    private ProcedureConfigurationDao procedureConfigurationDao;
    @Autowired
    private HttpConfigurationDao httpConfigurationDao;

    @Autowired
    private DynamicPagesCache dynamicPagesCache;

    @Autowired
    private DynamicPageResponseMapper dynamicPageResponseMapper;

    @Autowired
    private StepService stepService;

    public List<DynamicPageModel> retrieveAllAdminDynamicPages() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - retrieveAllAdminDynamicPages()");
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all AdminDynamicPages");
        List<DynamicPageModel> dynamicPagesList = dynamicPagesDao.retrieveAllDynamicPages();
        if (Objects.isNull(dynamicPagesList) || dynamicPagesList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No Pages Found!!");
            throw new DynamicPageException(ErrorCodes.ERROR.NO_DYNAMIC_PAGES_FOUND, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all AdminDynamicPages");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesService - retrieveAllAdminDynamicPages()");

        return dynamicPagesList;
    }

    @Transactional(rollbackFor = Exception.class)
    public AddDynamicPageResponse addAdminDynamicPage(AddDynamicPageRequest addDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - addAdminDynamicPage()");
        CCATLogger.DEBUG_LOGGER.debug("Start adding profile [" + addDynamicPageRequest.getPageName() + "]");
        Integer privilegeId = lkFeaturesDao.addLKFeature(new LkFeatureModel(addDynamicPageRequest.getPrivilegeName(),
                addDynamicPageRequest.getPageName()));
        if (Objects.isNull(privilegeId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add to LKFeatures");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "LKFeatures");
        }
        Integer pageId = dynamicPagesDao.addDynamicPage(addDynamicPageRequest, privilegeId);
        CCATLogger.DEBUG_LOGGER.debug("Added AdminDynamicPage successfully with ID [" + pageId + "]");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesService - addAdminDynamicPage()");
        return new AddDynamicPageResponse(pageId, privilegeId);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAdminDynamicPage(UpdateDynamicPageRequest updateDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - updateAdminDynamicPage()");
        CCATLogger.DEBUG_LOGGER.debug("Start updating DynamicPage with ID [" + updateDynamicPageRequest.getPageId() + "]");

        boolean isFeatureUpdated = lkFeaturesDao.updateFeatureLabel(updateDynamicPageRequest.getPrivilegeId(), updateDynamicPageRequest.getPageName());
        if (!isFeatureUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update feature for AdminDynamicPage with ID [" + updateDynamicPageRequest.getPageId() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "DynamicPage Feature");
        }
        boolean isUpdated = dynamicPagesDao.updateDynamicPage(updateDynamicPageRequest);
        if (!isUpdated) {
            CCATLogger.DEBUG_LOGGER.error("Failed to update AdminDynamicPage with ID [" + updateDynamicPageRequest.getPageId() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.UPDATE_FAILED, Defines.SEVERITY.ERROR, "DynamicPage");
        }
        CCATLogger.DEBUG_LOGGER.debug("Updated profile successfully");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesService - updateAdminDynamicPage()");
    }

    public HashMap<Integer, DynamicPageModel> retrieveAllPagesWithDetails() {
        HashMap<Integer, DynamicPageModel> pages = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - retrieveAllPagesWithDetails()");
            CCATLogger.DEBUG_LOGGER.info("Start retrieving all dynamic pages with details");

            CCATLogger.DEBUG_LOGGER.debug("Calling DynamicPagesDao - retrieveAllDnPagesWithSteps()");
            pages = dynamicPagesDao.retrieveAllDnPagesWithSteps();

            if (Objects.isNull(pages) || pages.isEmpty()) {
                CCATLogger.DEBUG_LOGGER.debug("No pages found");
            } else {
                CCATLogger.DEBUG_LOGGER.debug("Calling HttpStepConfigDao - retrieveAllHttpConfigWithParameters");
                HashMap<Integer, HttpConfigurationModel> httpConfigurationMap = httpConfigurationDao.retrieveAllHTTPConfigWithParameters();

                CCATLogger.DEBUG_LOGGER.debug("Calling SPStepConfigDao - retrieveAllProcedureConfigWithParameters");
                HashMap<Integer, ProcedureConfigurationModel> spStepsConfigMap = procedureConfigurationDao.retrieveAllProcedureConfigWithParameters();
                pages.values().forEach((page) -> {
                    page.getSteps().forEach((step) -> {
                        if (step.getStepType().equals(StepTypes.PROCEDURE.type)) { // SP step
                            step.setStepConfiguration(spStepsConfigMap.get(step.getId()));
                        }else if (step.getStepType().equals(StepTypes.HTTP.type)){ // HTTP step
                            step.setStepConfiguration(httpConfigurationMap.get(step.getId()));
                        }
                    });
                });
            }

            CCATLogger.DEBUG_LOGGER.info("Finished retrieving all dynamic pages with details");
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to retrieve all dynamic pages with details", ex);
        }
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPagesService - retrieveAllPagesWithDetails()");
        return pages;
    }

    public ViewDynamicPageResponse viewDynamicPage(ViewDynamicPageRequest viewPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - viewDynamicPage()");
        CCATLogger.DEBUG_LOGGER.info("Start serving view dynamic page request");
        DynamicPageModel page = dynamicPagesCache.getDynamicPages().get(viewPageRequest.getPrivilegeId());
        if (page == null) {
            CCATLogger.DEBUG_LOGGER.info("Page with Privilege ID [" + viewPageRequest.getPrivilegeId() + "] was not found");
            throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }
        ViewDynamicPageResponse response = dynamicPageResponseMapper.map(page);
        CCATLogger.DEBUG_LOGGER.info("Finished serving view dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ended DynamicPagesService - viewDynamicPage()");

        return response;
    }

    public DynamicPageModel retrieveDynamicPage(GetDynamicPageRequest getDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - retrieveDynamicPage()");
        CCATLogger.DEBUG_LOGGER.debug("Start serving get dynamic page request for page with ID: [" + getDynamicPageRequest.getPageId() + "]");
        HashMap<Integer, DynamicPageModel> map = dynamicPagesDao.retrieveDynamicPageWithSteps(getDynamicPageRequest.getPageId());
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Dynamic page with ID [" + getDynamicPageRequest.getPageId() + "] was not found");
            throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND);
        }
        DynamicPageModel page = (DynamicPageModel) map.values().toArray()[0];
        HashMap<Integer, HttpConfigurationModel> httpConfigurationMap = httpConfigurationDao.retrieveAllHTTPConfigWithParameters();
        CCATLogger.DEBUG_LOGGER.debug("Calling SPStepConfigDao - retrieveAllProcedureConfigWithParameters");
        HashMap<Integer, ProcedureConfigurationModel> spStepsConfigMap = procedureConfigurationDao.retrieveAllProcedureConfigWithParameters();
        page.getSteps().forEach((step) -> {
            if (step.getStepType().equals(StepTypes.PROCEDURE.type)) { // SP step
                step.setStepConfiguration(spStepsConfigMap.get(step.getId()));
            } else if (step.getStepType().equals(StepTypes.HTTP.type)){ // HTTP step
                step.setStepConfiguration(httpConfigurationMap.get(step.getId()));
            }
        });
        CCATLogger.DEBUG_LOGGER.debug("Done serving get dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesService - retrieveDynamicPage()");
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDynamicPage(DeleteDynamicPageRequest deleteDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Started DynamicPagesService - deleteDynamicPage()");
        CCATLogger.DEBUG_LOGGER.debug("Start serving delete dynamic page request for page with ID: [" + deleteDynamicPageRequest.getPageId() + "]");

        CCATLogger.DEBUG_LOGGER.debug("Start retrieving dynamic page");
        HashMap<Integer, DynamicPageModel> map = dynamicPagesDao.retrieveDynamicPageWithSteps(deleteDynamicPageRequest.getPageId());
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Dynamic page with ID [" + deleteDynamicPageRequest.getPageId() + "] was not found");
            throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND);
        }
        DynamicPageModel page = (DynamicPageModel) map.values().toArray()[0];
        CCATLogger.DEBUG_LOGGER.debug("Finished retrieving page");

        if (page.getSteps() != null && !page.getSteps().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start deleting dynamic page steps");
            for (StepModel step : page.getSteps()) {
                stepService.deleteStep(new DeleteStepRequest(page.getId(), step.getId()), true);
            }
            CCATLogger.DEBUG_LOGGER.debug("Finished deleting page steps");
        }

        CCATLogger.DEBUG_LOGGER.debug("Start deleting dynamic page");
        boolean isDeleted = dynamicPagesDao.deleteDynamicPage(page.getId());
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete dynamic page with ID [" + page.getId() + "]");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "Dynamic page");
        }

        boolean isPrivilegeDeleted = lkFeaturesDao.deleteFeature(page.getPrivilegeId());
        if (!isPrivilegeDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete dynamic page privilege");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "Dynamic page privilege");
        }

        CCATLogger.DEBUG_LOGGER.debug("Done serving delete dynamic page request");
        CCATLogger.DEBUG_LOGGER.debug("Ending DynamicPagesService - deleteDynamicPage()");
    }
}
