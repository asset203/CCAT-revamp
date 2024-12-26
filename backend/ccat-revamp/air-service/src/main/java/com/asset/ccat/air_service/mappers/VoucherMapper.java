package com.asset.ccat.air_service.mappers;

import com.asset.ccat.air_service.models.requests.customer_care.voucher.CheckVoucherNumberRequest;
import com.asset.ccat.air_service.models.requests.customer_care.voucher.GetVoucherDetailsRequest;
import org.springframework.stereotype.Component;

@Component
public class VoucherMapper {
    public GetVoucherDetailsRequest toGetVoucherDetails(CheckVoucherNumberRequest checkRequest){
        GetVoucherDetailsRequest getVoucherDetailsRequest = new GetVoucherDetailsRequest();
        getVoucherDetailsRequest.setMsisdn(checkRequest.getMsisdn());
        getVoucherDetailsRequest.setUsername(checkRequest.getUsername());
        getVoucherDetailsRequest.setVoucherSerialNumber(checkRequest.getVoucherSerialNumber());
        getVoucherDetailsRequest.setServerId(checkRequest.getServerId());
        return getVoucherDetailsRequest;
    }
}
