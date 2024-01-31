package com.asset.ccat.gateway.validators.admins;

import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.models.admin.Es2alnyMarqueeModel;
import com.asset.ccat.gateway.models.requests.admin.marquee.CreateMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.DeleteMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.GetAllEs2alnyMarqueeRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateAllMarqueesRequest;
import com.asset.ccat.gateway.models.requests.admin.marquee.UpdateMarqueesRequest;
import com.asset.ccat.gateway.models.responses.admin.marquee.GetAllEs2alnyMarqueeResponse;
import com.asset.ccat.gateway.services.MarqueeService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author nour.ihab
 */
@Component
public class Es2alnyMarqueeValidator {

    public void validateCreateMarquee(CreateMarqueeRequest createMarqueeRequest) throws GatewayException {
        if (Objects.isNull(createMarqueeRequest.getDescription())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description");
        } else if (Objects.isNull(createMarqueeRequest.getTitle())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "title");
        }
    }

    public void validateUpdateAllMarquees(UpdateAllMarqueesRequest updateAllMarqueesRequest) throws GatewayException {
        for (Es2alnyMarqueeModel marquees : updateAllMarqueesRequest.getMarquees()) {
            if (Objects.isNull(marquees.getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
            } else if (Objects.isNull(marquees.getDescription())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description for [" + marquees.getId() + "]");
            } else if (Objects.isNull(marquees.getTitle())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "title for [" + marquees.getId() + "]");
            }
        }
    }

    public void validateUpdateMarquee(UpdateMarqueesRequest updateMarqueesRequest) throws GatewayException {
        if (Objects.nonNull(updateMarqueesRequest.getMarquee())) {
            if (Objects.isNull(updateMarqueesRequest.getMarquee().getId())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
            } else if (Objects.isNull(updateMarqueesRequest.getMarquee().getDescription())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "description for [" + updateMarqueesRequest.getMarquee().getId() + "]");
            } else if (Objects.isNull(updateMarqueesRequest.getMarquee().getTitle())) {
                throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "title for [" + updateMarqueesRequest.getMarquee().getId() + "]");
            }
        } else {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "marquee");
        }
    }

    public void validateDeleteMarquee(DeleteMarqueeRequest deleteMarqueeRequest) throws GatewayException {
        if (Objects.isNull(deleteMarqueeRequest.getId())) {
            throw new GatewayValidationException(ErrorCodes.WARNING.MISSING_FIELD, "id");
        }
    }

}
