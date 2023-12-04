package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchDisconnectRequest;
import com.asset.ccat.gateway.models.requests.admin.batch_install_disconnect.BatchInstallRequest;
import com.asset.ccat.gateway.models.requests.admin.user.FileUploadRequest;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class BIDValidator {

    @Autowired
    private Properties properties;

    public void validateBatchInstallRequest(BatchInstallRequest batchInstallRequest) throws GatewayException {
        if (Objects.isNull(batchInstallRequest.getFileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileName");
        } else if (Objects.isNull(batchInstallRequest.getFileExt())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileExt");
        } else if (Objects.isNull(batchInstallRequest.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "file");
        } else if (batchInstallRequest.getFile().getSize() >= properties.getMaxFileUploadSize()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MAX_FILE_UPLOAD_SIZE_EXCEEDED, "");
        } else if (Objects.isNull(batchInstallRequest.getServiceClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "serviceClassId");
        }
    }

    public void validateBatchDisconnectRequest(BatchDisconnectRequest batchDisconnectRequest) throws GatewayException {
        if (Objects.isNull(batchDisconnectRequest.getFileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileName");
        } else if (Objects.isNull(batchDisconnectRequest.getFileExt())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileExt");
        } else if (Objects.isNull(batchDisconnectRequest.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "file");
        } else if (batchDisconnectRequest.getFile().getSize() >= properties.getMaxFileUploadSize()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MAX_FILE_UPLOAD_SIZE_EXCEEDED, "");
        } else if (Objects.isNull(batchDisconnectRequest.getValidateDisconnection())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "validateDisconnection");
        }
    }

}
