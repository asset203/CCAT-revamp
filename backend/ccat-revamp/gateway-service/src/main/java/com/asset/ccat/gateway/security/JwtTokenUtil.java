/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.gateway.security;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.BaseRequest;
import com.asset.ccat.gateway.models.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoud.shehab
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private Properties properties;

    public HashMap<String, Object> extractDataFromToken(String token) throws GatewayException {
        try {
            HashMap<String, Object> tokenData = new HashMap<>();
            CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
            final Claims claims = getAllClaimsFromToken(token);
            String username = claims.getSubject();
            String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
            String userId = String.valueOf(claims.get(Defines.SecurityKeywords.USER_ID));
            String sessionId = String.valueOf(claims.get(Defines.SecurityKeywords.SESSION_ID));
            String profileId = String.valueOf(claims.get(Defines.SecurityKeywords.PROFILE_ID));
            String profileName = String.valueOf(claims.get(Defines.SecurityKeywords.PROFILE_NAME));

            ArrayList<String> profileRole = (ArrayList) claims.get(Defines.SecurityKeywords.PROFILE_ROLE);
            tokenData.put(Defines.SecurityKeywords.USERNAME, username);
            tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
            tokenData.put(Defines.SecurityKeywords.SESSION_ID, sessionId);
            tokenData.put(Defines.SecurityKeywords.USER_ID, userId);
            tokenData.put(Defines.SecurityKeywords.PROFILE_ROLE, profileRole);
            tokenData.put(Defines.SecurityKeywords.PROFILE_ID, profileId);
            tokenData.put(Defines.SecurityKeywords.PROFILE_NAME, profileName);

            return tokenData;
        } catch (IllegalArgumentException e) {
            CCATLogger.DEBUG_LOGGER.error("IllegalArgumentException occurred while extracting data from a token: ", e);
            CCATLogger.ERROR_LOGGER.error("IllegalArgumentException occurred while extracting data from a token: ", e);
            throw new GatewayException(ErrorCodes.ERROR.INVALID_USERNAME_OR_PASSWORD);
        } catch (ExpiredJwtException e) {
            CCATLogger.DEBUG_LOGGER.error("ExpiredJwtException occurred while extracting data from a token: ", e);
            CCATLogger.ERROR_LOGGER.error("ExpiredJwtException occurred while extracting data from a token: ", e);
            throw new GatewayException(ErrorCodes.ERROR.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            CCATLogger.DEBUG_LOGGER.error("SignatureException occurred while extracting data from a token: ", e);
            CCATLogger.ERROR_LOGGER.error("SignatureException occurred while extracting data from a token: ", e);
            throw new GatewayException(ErrorCodes.ERROR.INVALID_TOKEN);
        }
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllClaimsFromToken(String token) {
        //Jwts.
        return Jwts.parser()
                .setSigningKey(properties.getAccessTokenKey().trim())
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    public Boolean validateToken(String token, String username, UserModel userModel) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("start validate Token for user : " + username);
            if (isTokenExpired(token)) {
                CCATLogger.DEBUG_LOGGER.debug("token expired");
                return false;
            }
            if (!username.equals(userModel.getNtAccount())) {
                CCATLogger.DEBUG_LOGGER.debug("wrong user name");
                return false;
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.debug("an error occured : " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.info("an error occured ");
            CCATLogger.ERROR_LOGGER.error("Failed to validate Token " + ex.getMessage(), ex);
            return false;
        }
        return true;
    }

    public String getTokenFromBody(HttpServletRequest request) {
        String token = "";
        try {
            CCATLogger.DEBUG_LOGGER.debug("start get token from request body");
            InputStream inputStream = request.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            final ObjectNode node = new ObjectMapper().readValue(result.toString(), ObjectNode.class);
            if (node.get("token") != null) {
                token = node.get("token").textValue();
            }

        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.debug("failed to generate baseRequest model, token not found ");
        }

        return token;
    }

    public String getTokenFromMultiPartReq(HttpServletRequest request) {
        CCATLogger.DEBUG_LOGGER.debug("start get token from multi-part request");
        StringBuilder result = new StringBuilder();
        try {
            Part tokenPart = request.getPart("token");
            InputStream inputStream = tokenPart.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException | ServletException ex) {
            CCATLogger.DEBUG_LOGGER.debug("failed to generate baseRequest model, token not found ");
        }
        return result.toString();
    }

    public String getTokenFromRequest(BaseRequest baseRequest) {
        return baseRequest.getToken().replace(Defines.SecurityKeywords.PREFIX, "");

    }
}
