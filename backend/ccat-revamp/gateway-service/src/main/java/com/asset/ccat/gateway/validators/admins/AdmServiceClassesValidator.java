package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.admin.Accumlator;
import com.asset.ccat.gateway.models.admin.DedAccount;
import com.asset.ccat.gateway.models.admin.ServiceClassModel;
import com.asset.ccat.gateway.models.requests.admin.service_class.AddServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.GetServiceClassRequest;
import com.asset.ccat.gateway.models.requests.admin.service_class.UpdateServiceClassRequest;
import com.asset.ccat.gateway.models.requests.service_class.ImportServiceClassesRequest;
import com.asset.ccat.gateway.util.GatewayUtil;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class AdmServiceClassesValidator {

    @Autowired
    private Properties properties;

    public void validateServiceClass(GetServiceClassRequest request) throws GatewayException {
        if (Objects.isNull(request.getCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "code");
        }
    }

    public void validateImportServiceClass(ImportServiceClassesRequest request) throws GatewayException {
        if (Objects.isNull(request.getFile())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "file");
        }else if (Objects.isNull(request.getFileExt())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileExt");
        }else if (Objects.isNull(request.getFileName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "fileName");
        }else if (request.getFile().getSize() >= properties.getMaxFileUploadSize()) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MAX_FILE_UPLOAD_SIZE_EXCEEDED, "");
        }else if (request.getFile().getSize() <= 0) {
            throw new GatewayValidationException(ErrorCodes.WARNING.EMPTY_FILE, "");
        }
    }

    public void validateAddServiceClass(AddServiceClassRequest request) throws GatewayException {
        validate(request.getServiceClass());
        validateAccumaltors(request.getServiceClass().getAccumlators());
        validateDedAccounts(request.getServiceClass().getDedAccounts());
        //validateEligibleServiceClasses(request.getServiceClass().getEligibleServiceClasses());
    }

    public void validateUpdateServiceClass(UpdateServiceClassRequest request) throws GatewayException {
        validate(request.getServiceClass());
        validateAccumaltors(request.getServiceClass().getAccumlators());
        validateDedAccounts(request.getServiceClass().getDedAccounts());
        //validateEligibleServiceClasses(request.getServiceClass().getEligibleServiceClasses());
    }

    private void validateAccumaltors(List<Accumlator> accumlators) throws GatewayException {
        if (accumlators != null) {
            Set<Accumlator> accumlatorSet = new HashSet<>();
            for (Accumlator accumlator : accumlators) {
                if (Objects.isNull(accumlator.getAccID())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Accumulator seq Id");
                } else if (Objects.isNull(accumlator.getDescription()) || Objects.equals(accumlator.getDescription(), "")) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "AccumulatorDescription for [" + accumlator.getAccID() + "]");
                } else if (accumlatorSet.add(accumlator) == false) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Accumulator seq Id [" + accumlator.getAccID()+"]");
                }
            }
        }
    }

    private void validateDedAccounts(List<DedAccount> dedAccounts) throws GatewayException {
        if (dedAccounts != null) {
            Set<DedAccount> dedAccountSet = new HashSet<>();
            for (DedAccount dedAccount : dedAccounts) {
                if (Objects.isNull(dedAccount.getDaID())) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "Dedecated Account seq Id");
                } else if (Objects.isNull(dedAccount.getDescription()) || Objects.equals(dedAccount.getDescription(), "")) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "DedAccountDescription for [" + dedAccount.getDaID() + "]");
                } else if (dedAccountSet.add(dedAccount) == false) {
                    throw new GatewayValidationException(ErrorCodes.WARNING.DUPLICATED_DATA, "Dedecated Account seq Id [" + dedAccount.getDaID()+"]");
                }
            }
        }
    }

    private void validate(ServiceClassModel serviceClass) throws GatewayException {
        if (Objects.isNull(serviceClass.getCode())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "code");
        } else if (GatewayUtil.lengthOfInteger(serviceClass.getCode()) > 4) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, " code , maximum code length must be only 4 digits");
        } else if (Objects.isNull(serviceClass.getName())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "name");
        } else if (Boolean.TRUE.equals(serviceClass.getIsCiConversion() && serviceClass.getCiServiceName() == null) ||
                Boolean.TRUE.equals(serviceClass.getIsCiConversion() && serviceClass.getCiPackageName() == null)) {
            throw new GatewayValidationException(ErrorCodes.WARNING.INVALID_INPUT, "CI Service Name or Package Name");
        }

    }

}
