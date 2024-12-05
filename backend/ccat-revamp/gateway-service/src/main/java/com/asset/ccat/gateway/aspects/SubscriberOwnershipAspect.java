package com.asset.ccat.gateway.aspects;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.exceptions.GatewayValidationException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.redis.model.LockingAdministration;
import com.asset.ccat.gateway.redis.repository.LockingAdministrationRepository;
import com.asset.ccat.gateway.security.JwtTokenUtil;
import com.asset.ccat.gateway.services.LookupsService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author Mayar Ezz el-Din
 */
@Component
@Aspect
public class SubscriberOwnershipAspect {

    private final LockingAdministrationRepository lockingRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LookupsService lookupsService;

    private boolean hasVIPPower;


    @Autowired
    public SubscriberOwnershipAspect(LockingAdministrationRepository lockingRepository, JwtTokenUtil jwtTokenUtil, LookupsService lookupsService) {
        this.lockingRepository = lockingRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.lookupsService = lookupsService;
    }

    @Before("@annotation(com.asset.ccat.gateway.annotation.SubscriberOwnership)")
    public void checkSubscriberOwnership(JoinPoint joinPoint) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Checking subscriber ownership...");
        Object[] methodArguments = joinPoint.getArgs();
        String subscriberMSISDN = extractMsisdn(methodArguments); //subscriber used in the request

        if (subscriberMSISDN != null) {
            BaseRequest baseRequest = Optional.ofNullable(getRequestFromArgs(methodArguments))
                    .orElseThrow(() -> new GatewayException(ErrorCodes.ERROR.NOT_AUTHORIZED));

            String requestUsername = extractActiveUsername(baseRequest.getToken()); //username from token
            LockingAdministration subscriberOwnerModel = lockingRepository.findById(subscriberMSISDN)
                    .orElse(null);

            CCATLogger.DEBUG_LOGGER.debug("The cached subscriber-owner model = {}", subscriberOwnerModel);
            if (subscriberOwnerModel == null || !requestUsername.equals(subscriberOwnerModel.getUsername()))
                throw new GatewayException(ErrorCodes.ERROR.SUBSCRIBER_OWNER_CONFLICT);

            checkVIPEligibility(subscriberMSISDN);
            hasVIPPower = false; // reset for the next request.
        }
    }

    private void checkVIPEligibility(String subscriberMSISDN) throws GatewayException {
        String requestUrl = getCurrentHttpRequest();
        CCATLogger.DEBUG_LOGGER.debug("User has VIP Power={}", hasVIPPower);
        if(isVIPSubscriber(subscriberMSISDN) && isVipPage(requestUrl) && (!hasVIPPower))
            throw new GatewayException(ErrorCodes.ERROR.VIP_NOT_ELIGIBLE);
    }

    private BaseRequest getRequestFromArgs(Object[] methodArgs) {
        BaseRequest req = null;
        for (Object methodArg : methodArgs)
            if (methodArg instanceof BaseRequest)
                req = (BaseRequest) methodArg;
        return req;
    }


    private String extractMsisdn(Object[] methodArgs) {
        String msisdn;
        for (Object methodArg : methodArgs) {
            msisdn = extractMsisdnFromMethodArg(methodArg);
            CCATLogger.DEBUG_LOGGER.debug("Subscriber's MSISDN = {}", msisdn);
            if (msisdn != null)
                return msisdn;
        }
        return null;
    }

    private String extractMsisdnFromMethodArg(Object methodArg) {
        try {
            Method getMsisdnMethod = methodArg.getClass().getMethod("getMsisdn");
            return (String) getMsisdnMethod.invoke(methodArg);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            try {
                Field msisdnField = methodArg.getClass().getDeclaredField("msisdn");
                return (String) msisdnField.get(methodArg);
            } catch (NoSuchFieldException | IllegalAccessException ex) {
                return null;
            }
        }
    }

    private String extractActiveUsername(String token) throws GatewayException {
        Map<String, Object> tokenData = jwtTokenUtil.extractDataFromToken(token);
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("The request is from username={}", username);
        if (username == null)
            throw new GatewayValidationException(ErrorCodes.ERROR.NOT_AUTHORIZED);
        @SuppressWarnings("unchecked")
        List<String> profileFeatures = (ArrayList<String>) tokenData.get(Defines.SecurityKeywords.PROFILE_ROLE);
        if (profileFeatures.contains("/vip/view"))
            hasVIPPower = true;
        return username;
    }

    private String getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes) {
            HttpServletRequest req = ((ServletRequestAttributes) requestAttributes).getRequest();
                return req.getRequestURL().toString();
        }
        return "";
    }

    private boolean isVipPage(String requestUrl) throws GatewayException {
        String requestContext = requestUrl.split("/ccat")[1];
        Map<String, Boolean> appPages = lookupsService.getAppPages();
        boolean isVIPPage = appPages.get(requestContext);
        CCATLogger.DEBUG_LOGGER.debug("Context: {} -- isVIPPage={}", requestContext, isVIPPage);
        return isVIPPage;
    }

    private boolean isVIPSubscriber(String subscriberMSISDN) throws GatewayException {
        List<String> vipSubscribers = lookupsService.getVIPSubscribers();
        boolean isVIPSubscriber =  vipSubscribers.contains(subscriberMSISDN);
        CCATLogger.DEBUG_LOGGER.debug("sub:{} -- isVIPSubscriber={}", subscriberMSISDN, isVIPSubscriber);
        return isVIPSubscriber;
    }
}
