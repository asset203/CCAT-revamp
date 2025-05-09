/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.security;

import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.responses.BaseResponse;
import com.asset.ccat.user_management.models.security.HttpRequestWrapper;
import com.asset.ccat.user_management.tasks.ScheduledTask;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 *
 * @author mahmoud.shehab
 */
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final ArrayList<String> AUTH_WHITELIST = new ArrayList<>(Arrays.asList(
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/",
            "/actuator",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/",
            "/swagger-ui/",
            // -- Login
            Defines.ContextPaths.LOGIN,
            "/user/upload",
            "/call-reason/add",
            "/call-reason/check",
            "/call-reason/update"
    ));

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    MessagesCache messagesCache;

    @Autowired
    private ScheduledTask scheduledTask;

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

        } catch (UserManagementException ex) {
            CCATLogger.DEBUG_LOGGER.error(" Exception thrown with error code" + ex.getErrorCode());
            CCATLogger.DEBUG_LOGGER.info(" Exception thrown with error code" + ex.getErrorCode());
            CCATLogger.ERROR_LOGGER.error(" Exception thrown with error code" + ex.getErrorCode(), ex);
            handleRejectionResponse(res, ex.getErrorCode());
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Unknown Error occured");
            CCATLogger.ERROR_LOGGER.error("Unkown Error occured", ex);
            handleRejectionResponse(res, ErrorCodes.ERROR.NOT_AUTHORIZED);
        } finally {
            long end = System.currentTimeMillis() - start;
            CCATLogger.DEBUG_LOGGER.debug("Internal Request Filtering Finished in [" + end + "] ms");
            ThreadContext.clearAll();
        }
    }

    protected HttpRequestWrapper doAuthenticationFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException, UserManagementException {
        CCATLogger.DEBUG_LOGGER.debug("Start authenticate user");
        String username = null;
        HttpServletRequest requestToCache = (HttpServletRequest) req;
        HttpRequestWrapper requestWrapper = new HttpRequestWrapper(requestToCache);
        String authToken = "";

        if (req.getContentType().contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
            authToken = jwtTokenUtil.getTokenFromMultiPartReq(requestWrapper);
        } else {
            authToken = jwtTokenUtil.getTokenFromBody(requestWrapper);
        }
        if (authToken != null) {
            try {
                CCATLogger.DEBUG_LOGGER.debug("Validating and extracting data from token");
                HashMap<String, Object> tokendata = jwtTokenUtil.extractDataFromToken(authToken);
                username = tokendata.get(Defines.SecurityKeywords.USERNAME).toString();
                CCATLogger.DEBUG_LOGGER.debug("user name: " + username);
            } catch (UserManagementException e) {
                String msg = messagesCache.getErrorMsg(e.getErrorCode());
                if (e.getArgs() != null && !e.getArgs().equals("")) {
                    msg = messagesCache.replaceArgument(msg, e.getArgs());
                }
                CCATLogger.DEBUG_LOGGER.error("Token validation failed, " + msg);
                throw new UserManagementException(ErrorCodes.ERROR.NOT_AUTHORIZED);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.debug("no token found");
            throw new UserManagementException(ErrorCodes.ERROR.NOT_AUTHORIZED);
        }
//        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//            CCATLogger.debug("getting user details");
//            UserModel userModel = new UserModel();
//            userModel.setNtAccount("admin");
//            CCATLogger.debug("starting validateToken ");
//            if (jwtTokenUtil.validateToken(authToken, username, userModel)) {
//                CCATLogger.debug("token is valid");
//                UsernamePasswordAuthenticationToken authentication
//                        = new UsernamePasswordAuthenticationToken(userModel, null, null);
//                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(requestWrapper));
//                CCATLogger.info("authenticated user " + username + ", setting security context");
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//            } else {
//                CCATLogger.debug("token not valid ");
//                throw new UserManagementException(ErrorCodes.ERROR.NOT_AUTHORIZED);
//            }
//        }
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
