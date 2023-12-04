package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.SendSMSRequest;
import com.asset.ccat.gateway.models.requests.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescRequest;
import com.asset.ccat.gateway.models.responses.admin.account_groups_bits_desc.GetAllAccountGroupsBitsDescResponse;
import com.asset.ccat.gateway.proxy.SendSmsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendSmsService  {

    @Autowired
    SendSmsProxy sendSmsProxy;

    public void sendSMS(SendSMSRequest request) throws GatewayException {
          sendSmsProxy.sendSMS(request);

    }
}
