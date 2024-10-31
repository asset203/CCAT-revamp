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
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;

/**
 * @author Mayar Ezz el-Din
 */
@Component
@Aspect
public class SubscriberOwnershipAspect {

    private final LockingAdministrationRepository lockingRepository;
    private final JwtTokenUtil jwtTokenUtil;

    @Autowired
    public SubscriberOwnershipAspect(LockingAdministrationRepository lockingRepository, JwtTokenUtil jwtTokenUtil) {
        this.lockingRepository = lockingRepository;
        this.jwtTokenUtil = jwtTokenUtil;
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
        }
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
        String username = jwtTokenUtil.extractDataFromToken(token).get(Defines.SecurityKeywords.USERNAME).toString();
        CCATLogger.DEBUG_LOGGER.debug("The request is from username={}", username);
        if (username == null)
            throw new GatewayValidationException(ErrorCodes.ERROR.NOT_AUTHORIZED);
        return username;
    }
}
