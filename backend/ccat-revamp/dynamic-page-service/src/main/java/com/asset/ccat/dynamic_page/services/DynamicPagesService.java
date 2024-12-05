package com.asset.ccat.dynamic_page.services;

import com.asset.ccat.dynamic_page.cache.DynamicPagesCache;
import com.asset.ccat.dynamic_page.constants.StepTypes;
import com.asset.ccat.dynamic_page.database.dao.*;
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

    @Autowired
    private LKMenusDao menusDao;

    public List<DynamicPageModel> retrieveAllAdminDynamicPages() throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start retrieving all AdminDynamicPages");
        List<DynamicPageModel> dynamicPagesList = dynamicPagesDao.retrieveAllDynamicPages();
        if (Objects.isNull(dynamicPagesList) || dynamicPagesList.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.error("No Pages Found!!");
            throw new DynamicPageException(ErrorCodes.ERROR.NO_DYNAMIC_PAGES_FOUND, Defines.SEVERITY.ERROR);
        }
        CCATLogger.DEBUG_LOGGER.debug("Done retrieving all AdminDynamicPages");

        return dynamicPagesList;
    }

    @Transactional(rollbackFor = Exception.class)
    public AddDynamicPageResponse addAdminDynamicPage(AddDynamicPageRequest addDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start adding page [{}]", addDynamicPageRequest.getPageName());
        Integer menuId = menusDao.addMenuPage(addDynamicPageRequest.getPageName());
        CCATLogger.DEBUG_LOGGER.debug("The added menu ID = {}", menuId);
        Integer privilegeId = lkFeaturesDao.addLKFeature(new LkFeatureModel(addDynamicPageRequest.getPrivilegeName(),
                addDynamicPageRequest.getPageName(), menuId));
        if (Objects.isNull(privilegeId)) {
            CCATLogger.DEBUG_LOGGER.error("Failed to add to LKFeatures");
            throw new DynamicPageException(ErrorCodes.ERROR.ADD_FAILED, Defines.SEVERITY.ERROR, "LKFeatures");
        }
        Integer pageId = dynamicPagesDao.addDynamicPage(addDynamicPageRequest, privilegeId);
        CCATLogger.DEBUG_LOGGER.debug("Added AdminDynamicPage successfully with ID [" + pageId + "]");
        return new AddDynamicPageResponse(pageId, privilegeId);

    }

    @Transactional(rollbackFor = Exception.class)
    public void updateAdminDynamicPage(UpdateDynamicPageRequest updateDynamicPageRequest) throws DynamicPageException {
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
    }

    public HashMap<Integer, DynamicPageModel> retrieveAllPagesWithDetails() {
        HashMap<Integer, DynamicPageModel> pages = null;
        try {
            CCATLogger.DEBUG_LOGGER.debug("Getting All pages with details ");
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
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to retrieve all dynamic pages with details", ex);
            CCATLogger.ERROR_LOGGER.error("Failed to retrieve all dynamic pages with details", ex);
        }
        return pages;
    }

    public ViewDynamicPageResponse viewDynamicPage(ViewDynamicPageRequest viewPageRequest) throws DynamicPageException {
        DynamicPageModel page = dynamicPagesCache.getDynamicPages().get(viewPageRequest.getPrivilegeId());
        CCATLogger.DEBUG_LOGGER.debug("Retrieved Page = {}", page);
        if (page == null) {
            CCATLogger.DEBUG_LOGGER.info("Page with Privilege ID [" + viewPageRequest.getPrivilegeId() + "] was not found");
            throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND, Defines.SEVERITY.ERROR);
        }

        return dynamicPageResponseMapper.map(page);
    }

    public DynamicPageModel retrieveDynamicPage(GetDynamicPageRequest getDynamicPageRequest) throws DynamicPageException {
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
        return page;
    }

    @Transactional(rollbackFor = Exception.class)
    public void deleteDynamicPage(DeleteDynamicPageRequest deleteDynamicPageRequest) throws DynamicPageException {
        CCATLogger.DEBUG_LOGGER.debug("Start deleting a dynamic page with ID: [{}]", deleteDynamicPageRequest.getPageId());

        HashMap<Integer, DynamicPageModel> map = dynamicPagesDao.retrieveDynamicPageWithSteps(deleteDynamicPageRequest.getPageId());
        if (map == null || map.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Dynamic page with ID [{}] was not found", deleteDynamicPageRequest.getPageId());
            throw new DynamicPageException(ErrorCodes.ERROR.DYNAMIC_PAGE_NOT_FOUND);
        }
        DynamicPageModel page = (DynamicPageModel) map.values().toArray()[0];

        if (page.getSteps() != null && !page.getSteps().isEmpty()) {
            CCATLogger.DEBUG_LOGGER.debug("Start deleting dynamic page steps, #Steps={}", page.getSteps().size());
            for (StepModel step : page.getSteps()) {
                stepService.deleteStep(new DeleteStepRequest(page.getId(), step.getId()), true);
            }
        }

        CCATLogger.DEBUG_LOGGER.debug("Start deleting dynamic page");
        boolean isDeleted = dynamicPagesDao.deleteDynamicPage(page.getId());
        if (!isDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to the delete dynamic page");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "Dynamic page");
        }

        boolean isPrivilegeDeleted = lkFeaturesDao.deleteFeature(page.getPrivilegeId());
        if (!isPrivilegeDeleted) {
            CCATLogger.DEBUG_LOGGER.error("Failed to delete dynamic page privilege");
            throw new DynamicPageException(ErrorCodes.ERROR.DELETE_FAILED, Defines.SEVERITY.ERROR, "Dynamic page privilege");
        }
    }
}
