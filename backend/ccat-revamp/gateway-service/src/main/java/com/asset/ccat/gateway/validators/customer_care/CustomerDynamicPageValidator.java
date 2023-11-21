package com.asset.ccat.gateway.validators.customer_care;

import java.util.Objects;

import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ExecuteDynamicPageStepRequest;
import org.springframework.stereotype.Component;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.requests.customer_care.customer_dynamic_page.ViewDynamicPageRequest;

@Component
public class CustomerDynamicPageValidator {

	public void validateViewDynamicPage(ViewDynamicPageRequest viewDynamicPageRequest)
			throws GatewayValidationException {
		if (Objects.isNull(viewDynamicPageRequest.getPrivilegeId())) {
			throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "privilegeId");
		}
	}

	public void validateExecuteStepRequest(ExecuteDynamicPageStepRequest request)
			throws GatewayValidationException {
		if (Objects.isNull(request.getPageId())) {
			throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "pageId");
		}
		if (Objects.isNull(request.getPrivilegeId())) {
			throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "privilegeId");
		}
		if (Objects.isNull(request.getStepId())) {
			throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "stepId");
		}
	}
}
