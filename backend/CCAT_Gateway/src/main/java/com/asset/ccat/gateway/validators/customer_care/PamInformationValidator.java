package com.asset.ccat.gateway.validators.customer_care;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.AddPamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.DeletePamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.EvaluatePamInformationRequest;
import com.asset.ccat.gateway.models.requests.customer_care.pam_information.GetAllPamsInformationRequest;
import java.util.Objects;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class PamInformationValidator {

    public void validateGetAllPamsInfo(GetAllPamsInformationRequest getAllPamsInformationRequest) throws GatewayException {
        if (Objects.isNull(getAllPamsInformationRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateAddPamInfo(AddPamInformationRequest addPamInformationRequest) throws GatewayException {
        if (Objects.isNull(addPamInformationRequest.getPamId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamId");
        } else if (Objects.isNull(addPamInformationRequest.getCurrentPamPeriodId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "currentPamPeriodId");
        } else if (Objects.isNull(addPamInformationRequest.getPamClassId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamClassId");
        } else if (Objects.isNull(addPamInformationRequest.getPamScheduleId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamScheduleId");
        } else if (Objects.isNull(addPamInformationRequest.getPamServicePriorityId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamServicePriorityId");
        }
    }

    public void validateEvaluatePamInfo(EvaluatePamInformationRequest evaluatePamInformationRequest) throws GatewayException {
        if (Objects.isNull(evaluatePamInformationRequest.getPamId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamId");
        } else if (Objects.isNull(evaluatePamInformationRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }

    public void validateDeletePamInfo(DeletePamInformationRequest deletePamInformationRequest) throws GatewayException {
        if (Objects.isNull(deletePamInformationRequest.getPamId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pamId");
        } else if (Objects.isNull(deletePamInformationRequest.getMsisdn())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "msisdn");
        }
    }
}
