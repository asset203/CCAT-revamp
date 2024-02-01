package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.user.AddUserRequest;
import com.asset.ccat.gateway.models.requests.admin.user.DeleteUserRequest;
import com.asset.ccat.gateway.models.requests.admin.user.FileUploadRequest;
import com.asset.ccat.gateway.models.requests.admin.user.GetUserRequest;
import com.asset.ccat.gateway.models.requests.admin.user.UpdateUserRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class UserAccessValidator {

    public void validateGetUser(GetUserRequest getUserRequest) throws GatewayException {
        if (Objects.isNull(getUserRequest.getUserId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userId");
        }
    }

    public void validateAddUser(AddUserRequest addUserRequest) throws GatewayException {
        if (Objects.isNull(addUserRequest.getUser().getNtAccount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "NtAccount");
        } else if (Objects.isNull(addUserRequest.getUser().getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
    }

    public void validateUpdateUser(UpdateUserRequest updateUserRequest) throws GatewayException {
        if (Objects.isNull(updateUserRequest.getUser().getNtAccount())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "NtAccount");
        } else if (Objects.isNull(updateUserRequest.getUser().getProfileId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "profileId");
        }
    }

    public void validateDeleteUser(DeleteUserRequest deleteUserRequest) throws GatewayException {
        if (Objects.isNull(deleteUserRequest.getUserId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "userId");
        }
    }

    public void validateUploadFile(FileUploadRequest uploadRequest) throws GatewayException {
        if (Objects.isNull(uploadRequest.getFileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileName");
        } else if (Objects.isNull(uploadRequest.getFileExt())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileExt");
        } else if (Objects.isNull(uploadRequest.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "file");
        } else if (Objects.isNull(uploadRequest.getOperationType())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "operationType");
        }
    }

}
