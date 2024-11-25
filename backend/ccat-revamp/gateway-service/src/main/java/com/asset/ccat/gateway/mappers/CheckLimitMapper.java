/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.mappers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.models.requests.UpdateAccumlatorModel;
import com.asset.ccat.gateway.models.requests.UpdateAccumulatorsRequest;
import com.asset.ccat.gateway.models.requests.UpdateBalanceAndDateRequest;
import com.asset.ccat.gateway.models.requests.UpdateDedicatedAccount;
import com.asset.ccat.gateway.models.requests.UpdateDedicatedBalanceRequest;
import com.asset.ccat.gateway.models.requests.admin.user.CheckLimitRequest;
import com.asset.ccat.gateway.models.requests.customer_care.prepaidVBP.PrepaidVBPSubscriptionRequest;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static java.util.stream.Collectors.toList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author wael.mohamed
 */
@Component
public class CheckLimitMapper {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    public CheckLimitRequest mapFrom(UpdateBalanceAndDateRequest request) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokendata.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(username);
        limitRequest.setRequestId(UUID.randomUUID().toString());
        limitRequest.setSessionId(sessionId);
        limitRequest.setUserId(userId);
        limitRequest.setActionType(request.getAdjustmentMethod());
        limitRequest.setAmount(request.getAdjustmentAmount());
        return limitRequest;
    }

    public CheckLimitRequest mapFrom(UpdateDedicatedBalanceRequest request) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokendata.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(username);
        limitRequest.setRequestId(UUID.randomUUID().toString());
        limitRequest.setSessionId(sessionId);
        limitRequest.setUserId(userId);

        List<UpdateDedicatedAccount> dedAccs = request.getList();

        Optional<Integer> actionType = dedAccs.stream().map(UpdateDedicatedAccount::getAdjustmentMethod).findFirst();
        limitRequest.setActionType(actionType.get());

        List<Float> amountsss = dedAccs.stream().map(UpdateDedicatedAccount::getAdjustmentAmount).collect(toList());
        Float adjustmentAmount = amountsss.stream().reduce(0f, Float::sum);
        limitRequest.setAmount(adjustmentAmount);
        return limitRequest;
    }

    public CheckLimitRequest mapFrom(UpdateAccumulatorsRequest request) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokendata.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(username);
        limitRequest.setRequestId(UUID.randomUUID().toString());
        limitRequest.setSessionId(sessionId);
        limitRequest.setUserId(userId);
        List<UpdateAccumlatorModel> accumlators = request.getList();

        Optional<Integer> actionType = accumlators.stream().map(UpdateAccumlatorModel::getAdjustmentMethod).findFirst();
        limitRequest.setActionType(actionType.get());

        List<Float> amounts = accumlators.stream().map(UpdateAccumlatorModel::getAdjustmentAmount).collect(toList());
        Float adjustmentAmount = amounts.stream().reduce(0f, Float::sum);
        limitRequest.setAmount(adjustmentAmount);
        return limitRequest;
    }

    public CheckLimitRequest mapFrom(UpdateDedicatedBalanceRequest request, Integer adjustmentMethod, Float adjustmentAmount, Float balance) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokendata.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(username);
        limitRequest.setRequestId(UUID.randomUUID().toString());
        limitRequest.setSessionId(sessionId);
        limitRequest.setUserId(userId);

//        List<UpdateDedicatedAccount> dedAccs = request.getList();
//
//        Optional<Integer> actionType = dedAccs.stream().map(UpdateDedicatedAccount::getAdjustmentMethod).findFirst();
//        limitRequest.setActionType(actionType.get());
//        List<Float> amountsss = dedAccs.stream().map(UpdateDedicatedAccount::getAdjustmentAmount).collect(toList());
//        Float adjustmentAmount = amountsss.stream().reduce(0f, Float::sum);
        limitRequest.setActionType(adjustmentMethod);
        limitRequest.setAmount(adjustmentAmount);
        limitRequest.setBalance(balance);
        return limitRequest;
    }

    public CheckLimitRequest mapFrom(UpdateAccumulatorsRequest request, Integer adjustmentMethod, Float adjustmentAmount) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(request.getToken());
        String sessionId = tokendata.get(Defines.SecurityKeywords.SESSION_ID).toString();
        String username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
        Integer userId = Integer.parseInt(tokendata.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(username);
        limitRequest.setRequestId(UUID.randomUUID().toString());
        limitRequest.setSessionId(sessionId);
        limitRequest.setUserId(userId);
//        List<UpdateAccumlatorModel> accumlators = request.getList();
//
//        Optional<Integer> actionType = accumlators.stream().map(UpdateAccumlatorModel::getAdjustmentMethod).findFirst();
//        limitRequest.setActionType(actionType.get());
//
//        List<Float> amounts = accumlators.stream().map(UpdateAccumlatorModel::getAdjustmentAmount).collect(toList());
//        Float adjustmentAmount = amounts.stream().reduce(0f, Float::sum);

        limitRequest.setActionType(adjustmentMethod);
        limitRequest.setAmount(adjustmentAmount);
        return limitRequest;
    }
    public CheckLimitRequest mapFrom(PrepaidVBPSubscriptionRequest request) throws GatewayException {
        CheckLimitRequest limitRequest = new CheckLimitRequest();
        HashMap<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(request.getToken());
        Integer userId = Integer.parseInt(tokenData.get(Defines.SecurityKeywords.USER_ID).toString());
        limitRequest.setToken(request.getToken());
        limitRequest.setUsername(request.getUsername());
        limitRequest.setRequestId(request.getRequestId());
        limitRequest.setSessionId(request.getSessionId());
        limitRequest.setUserId(userId);
        limitRequest.setActionType(1);
        limitRequest.setAmount(request.getTransactionAmount());
        return limitRequest;
    }

}
