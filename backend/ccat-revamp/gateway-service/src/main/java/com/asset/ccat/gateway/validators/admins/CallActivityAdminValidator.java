package com.asset.ccat.gateway.validators.admins;


import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.call_activity_admin.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class CallActivityAdminValidator {

    @Autowired
    private Properties properties;

    public void isReasonActivityGetValid(GetReasonActivityRequest request) throws GatewayException {
        if (Objects.isNull(request.getActivityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "activityId");
        }

    }

    public void validateDeleteActivityReasonRequest(DeleteReasonActivityRequest request) throws GatewayException {
        if (Objects.isNull(request.getActivityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "activityId");
        }

    }

    public void validateGetAllActivityReasonsRequest(GetAllActivitiesWithTypeRequest request) throws GatewayException {

        if (Objects.isNull(request.getActivityType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "activityType");
        }
//        if (Objects.isNull(request.getParentId())) {
//            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "parentId");
//        }

    }


    public void validateAddActivityReasonRequest(AddActivityReasonRequest request) throws GatewayException {
        if (Objects.isNull(request.getReasonActivity())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity");
        }
        if (Objects.isNull(request.getReasonActivity().getActivityType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity.activityType");
        }
        if (Objects.isNull(request.getReasonActivity().getActivityName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity.activityName");
        }
    }

    public void validateUpdateActivityReasonRequest(UpdateReasonActivityRequest request) throws GatewayException {
        if (Objects.isNull(request.getReasonActivity())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity");
        }
        if (Objects.isNull(request.getReasonActivity().getActivityType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity.activityType");
        }
        if (Objects.isNull(request.getReasonActivity().getActivityName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity.activityName");
        }
        if (Objects.isNull(request.getReasonActivity().getActivityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "reasonActivity.activityId");
        }
    }

    public void validateUploadActivityReasonRequest(UploadActivitiesFileRequest request) throws GatewayException {
        if (Objects.isNull(request.getFileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileName");
        } else if (Objects.isNull(request.getFileExt())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileExt");
        } else if (Objects.isNull(request.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "file");
        } else if (request.getFile().getSize() >= properties.getMaxFileUploadSize()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MAX_FILE_UPLOAD_SIZE_EXCEEDED, "");
        }
    }
}
