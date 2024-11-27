package com.asset.ccat.gateway.controllers.admins;

import com.asset.ccat.gateway.models.responses.BaseResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VIPController {

    @PostMapping
    public BaseResponse<String> getVIPs(){

        return null;
    }
}
