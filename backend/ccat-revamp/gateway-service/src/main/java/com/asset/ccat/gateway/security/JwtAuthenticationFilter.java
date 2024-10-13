/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.security;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.responses.BaseResponse;
import com.asset.ccat.gateway.models.security.HttpRequestWrapper;
import com.asset.ccat.gateway.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 *
 * @author mahmoud.shehabI
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    MessagesCache messagesCache;

    @Autowired
    private ScheduledTask scheduledTask;

    private final ArrayList<String> AUTH_WHITELIST = new ArrayList<>(Arrays.asList(
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger/v3/api-docs",
            "/swagger-resources",
            "/swagger-resources/",
            "/configuration/ui",
            "/configuration/security",
            "/configurations/sso",
            "/swagger-ui.html",
            "/webjars/",
            "/configurations",
            "/actuator",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/",
            "/swagger-ui/",
            // - locking
            "/locking-administration/delete",
            // -- Login
            Defines.WEB_ACTIONS.LOGIN,
            "/user/upload",
            "/footprint/log",
            "/batch-install-disconnect/",
            "admin/service-classes/import",
            "/call-activity-admin/upload",
            "/admin-dynamic-pages/xml-parse-parameters",
            "/call-reason/add",
            "/call-reason/check",
            "/call-reason/update",
            "/admin-dynamic-pages/export"
    ));

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        long start = System.currentTimeMillis();
        scheduledTask.incrementTPSCount();
        try {
            CCATLogger.DEBUG_LOGGER.info("Internal Request Filtering Started");
            CCATLogger.DEBUG_LOGGER.debug("Internal Request Filtering Started " + req.getServletPath());

            String servletPath = req.getServletPath();
            boolean permit = AUTH_WHITELIST.stream().anyMatch(path -> servletPath.contains(path));

            if (!permit) {
                HttpRequestWrapper hrw = doAuthenticationFilter(req, res, chain);
                chain.doFilter(hrw, res);
                return;
            }
            chain.doFilter(req, res);

        } catch (GatewayException ex) {
            CCATLogger.DEBUG_LOGGER.error(" Exception thrown with error code" + ex.getErrorCode());
            CCATLogger.ERROR_LOGGER.error(" Exception thrown with error code" + ex.getErrorCode(), ex);
            handleRejectionResponse(res, ex.getErrorCode());
        } catch (IOException | ServletException ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown Error occurred");
            CCATLogger.ERROR_LOGGER.error("Unknown Error occurred", ex);
            handleRejectionResponse(res, ErrorCodes.ERROR.NOT_AUTHORIZED);
        } finally {
            long end = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.debug("Internal Request Filtering Finished in [" + end + "] ms");
            ThreadContext.clearAll();
        }
    }

    protected HttpRequestWrapper doAuthenticationFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, GatewayException {
        CCATLogger.DEBUG_LOGGER.debug("Start authenticate user");
        String username = null;
        String userId = null;
        ArrayList<String> authorizedUrls;
        HttpServletRequest requestToCache = (HttpServletRequest) req;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(requestToCache);
        String authToken = "";

        if (req != null && req.getContentType() != null && req.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            authToken = jwtTokenUtil.getTokenFromMultiPartReq(requestWrapper);
        } else {
            authToken = jwtTokenUtil.getTokenFromBody(requestWrapper);
        }
        if (authToken != null) {
            try {
                CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
                HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(authToken);
                if (!tokendata.isEmpty()) {
                    username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
                    userId = tokendata.get(Defines.SecurityKeywords.USER_ID).toString();
                    authorizedUrls = (ArrayList) tokendata.get(Defines.SecurityKeywords.PROFILE_ROLE);
                    if (!authorizedUrls.isEmpty()) {
                        HashSet<String> authorizedUrlsSet = new HashSet<>(authorizedUrls);
                        CCATLogger.DEBUG_LOGGER.info("Authorized URls: " + authorizedUrlsSet);
                        CCATLogger.DEBUG_LOGGER.info("Requested Url:" + req.getServletPath());
                        if (!authorizedUrlsSet.isEmpty()) {
                            if (req.getServletPath() != null) {
                                if (!(authorizedUrlsSet.contains(req.getServletPath()))) {
                                    throw new GatewayException(ErrorCodes.ERROR.NOT_AUTHORIZED);
                                }
                            }
                        }
                    }
                }
                CCATLogger.DEBUG_LOGGER.debug("username: {}" , username);
            } catch (GatewayException e) {
                CCATLogger.DEBUG_LOGGER.debug("token not valid");
                throw e;
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("no token found");
            throw new GatewayException(ErrorCodes.ERROR.NOT_AUTHORIZED);
        }
        return requestWrapper;
    }

    private void handleRejectionResponse(HttpServletResponse response, Integer errorCode) {
        CCATLogger.DEBUG_LOGGER.info("Handle request rejection");
        try {
            ObjectMapper mapper = new ObjectMapper();
            BaseResponse<String> baseResponse = new BaseResponse();
            baseResponse.setStatusCode(errorCode);
            baseResponse.setStatusMessage(messagesCache.getErrorMsg(errorCode));
            baseResponse.setSeverity(Defines.SEVERITY.ERROR);

            String jsonInString = mapper.writeValueAsString(baseResponse);
            PrintWriter out = response.getWriter();
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            out.print(jsonInString);
            out.flush();

            CCATLogger.DEBUG_LOGGER.info("Rejected request handled successfully");
        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown Error occured in handling rejection response");
            CCATLogger.ERROR_LOGGER.error("Unknown Error occured in handling rejection response", ex);
        }
    }
}
