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
import org.apache.logging.log4j.ThreadContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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
 * @Description: The SubscriberOwnership aspect ensures the following:
 * 1. The subscriber is locked before any action is performed.
 * 2. The action is executed only by the user who locked the subscriber.
 * 3. The user has the necessary permissions to access the subscriber's data.
 */
@Component
@Aspect
public class SubscriberOwnershipAspect {
    private final LockingAdministrationRepository lockingRepository;
    private final JwtTokenUtil jwtTokenUtil;
    private final LookupsService lookupsService;

    @Autowired
    public SubscriberOwnershipAspect(LockingAdministrationRepository lockingRepository, JwtTokenUtil jwtTokenUtil, LookupsService lookupsService) {
        this.lockingRepository = lockingRepository;
        this.jwtTokenUtil = jwtTokenUtil;
        this.lookupsService = lookupsService;
    }

    @Before("@annotation(com.asset.ccat.gateway.annotation.SubscriberOwnership)")
    public void checkSubscriberOwnership(JoinPoint joinPoint) throws GatewayException {
        String requestId = UUID.randomUUID().toString();
        ThreadContext.put("requestId", requestId);
        CCATLogger.DEBUG_LOGGER.debug("Checking subscriber ownership...");
        Object[] methodArguments = joinPoint.getArgs();
        String subscriberMSISDN = extractMsisdn(methodArguments); //subscriber used in the request

        if (subscriberMSISDN != null) {
            BaseRequest baseRequest = Optional.ofNullable(getRequestFromArgs(methodArguments))
                    .orElseThrow(() -> new GatewayException(ErrorCodes.ERROR.NOT_AUTHORIZED));
            baseRequest.setRequestId(requestId);
            
            String requestUsername = extractActiveUsername(baseRequest.getToken()); //username from token
            LockingAdministration subscriberOwnerModel = lockingRepository.findById(subscriberMSISDN)
                    .orElseThrow(() -> new GatewayException(ErrorCodes.ERROR.SUBSCRIBER_IS_UNLOCKED));

            CCATLogger.DEBUG_LOGGER.debug("The cached subscriber-owner model = {}", subscriberOwnerModel);
            if (subscriberOwnerModel == null || !requestUsername.equals(subscriberOwnerModel.getUsername()))
                throw new GatewayException(ErrorCodes.ERROR.SUBSCRIBER_OWNER_CONFLICT);

            checkVIPEligibility(subscriberMSISDN, baseRequest.getToken());
        }
    }

    private void checkVIPEligibility(String subscriberMSISDN, String token) throws GatewayException {
        String requestUrl = getCurrentHttpRequest();
        boolean hasVIPAccessFlag = hasVIPAccess(token);
        CCATLogger.DEBUG_LOGGER.debug("User has VIP Power={}", hasVIPAccessFlag);
        if (isVIPSubscriber(subscriberMSISDN) && isVipPage(requestUrl) && (!hasVIPAccessFlag))
            throw new GatewayException(ErrorCodes.ERROR.VIP_NOT_ELIGIBLE);
    }

    private BaseRequest getRequestFromArgs(Object[] methodArgs) {
        return Arrays.stream(methodArgs)
                .filter(BaseRequest.class::isInstance)
                .map(BaseRequest.class::cast)
                .findFirst()
                .orElse(null);
    }

    private String extractMsisdn(Object[] methodArgs) {
        return Arrays.stream(methodArgs)
                .map(this::extractMsisdnFromMethodArg)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
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
        Map<String, Object> tokenData = getTokenData(token);
        String username = tokenData.get(Defines.SecurityKeywords.USERNAME).toString();
        ThreadContext.put("sessionId", tokenData.get(Defines.SecurityKeywords.SESSION_ID).toString());

        CCATLogger.DEBUG_LOGGER.debug("Request from username: {}", username);
        if (username == null)
            throw new GatewayValidationException(ErrorCodes.ERROR.NOT_AUTHORIZED);

        return username;
    }

    @Cacheable(value = "tokenCache", key = "#token")
    private Map<String, Object> getTokenData(String token) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Cache miss - extracting data for token");
        return jwtTokenUtil.extractDataFromToken(token);
    }

    private String getCurrentHttpRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
        if (requestAttributes instanceof ServletRequestAttributes servletRequestAttributes) {
            HttpServletRequest req = servletRequestAttributes.getRequest();
            return req.getRequestURL().toString();
        }
        return "";
    }

    private boolean isVipPage(String requestUrl) throws GatewayException {
        String requestContext = requestUrl.split("/ccat")[1];
        boolean isVIPPage = Optional.ofNullable(lookupsService.getAppPages().get(requestContext))
                .orElse(false);
        CCATLogger.DEBUG_LOGGER.debug("Context: {} -- isVIPPage={}", requestContext, isVIPPage);
        return isVIPPage;
    }

    private boolean isVIPSubscriber(String subscriberMSISDN) throws GatewayException {
        boolean isVIPSubscriber = lookupsService.getVIPSubscribers().contains(subscriberMSISDN);
        CCATLogger.DEBUG_LOGGER.debug("sub:{} -- isVIPSubscriber={}", subscriberMSISDN, isVIPSubscriber);
        return isVIPSubscriber;
    }

    @SuppressWarnings("unchecked")
    private boolean hasVIPAccess(String token) throws GatewayException {
        List<String> profileFeatures = (List<String>) getTokenData(token)
                .get(Defines.SecurityKeywords.PROFILE_ROLE);
        return profileFeatures.contains("/vip/view");
    }

}
