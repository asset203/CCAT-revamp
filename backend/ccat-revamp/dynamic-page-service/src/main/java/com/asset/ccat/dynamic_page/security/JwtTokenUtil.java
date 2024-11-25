/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.dynamic_page.security;

import com.asset.ccat.dynamic_page.configurations.Properties;
import com.asset.ccat.dynamic_page.defines.Defines;
import com.asset.ccat.dynamic_page.defines.ErrorCodes;
import com.asset.ccat.dynamic_page.exceptions.DynamicPageException;
import com.asset.ccat.dynamic_page.logger.CCATLogger;
import com.asset.ccat.dynamic_page.models.requests.BaseRequest;
import com.asset.ccat.dynamic_page.models.security.HttpRequestWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;

/**
 *
 * @author mahmoud.shehab
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private Properties properties;

    public HashMap<String, Object> extractDataFromToken(String token) throws DynamicPageException {
        try {
            HashMap<String, Object> tokenData = new HashMap<>();
            CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
            final Claims claims = getAllClaimsFromToken(token);
            String username = claims.getSubject();
            String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
            String userId = String.valueOf(claims.get(Defines.SecurityKeywords.USER_ID));
            tokenData.put(Defines.SecurityKeywords.USERNAME, username);
            tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
            tokenData.put(Defines.SecurityKeywords.USER_ID, userId);
            return tokenData;
        } catch (SignatureException ex) {
            CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
            CCATLogger.DEBUG_LOGGER.error("Invalid JWT signature");
            CCATLogger.ERROR_LOGGER.error("Invalid JWT signature", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "invalid signature");
        } catch (MalformedJwtException ex) {
            CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
            CCATLogger.DEBUG_LOGGER.error("Malformed JWT Token");
            CCATLogger.ERROR_LOGGER.error("Malformed JWT Token", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "malformed token");
        } catch (ExpiredJwtException ex) {
            CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
            CCATLogger.DEBUG_LOGGER.error("Expired JWT token");
            CCATLogger.ERROR_LOGGER.error("Expired JWT token", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "expired token");
        } catch (UnsupportedJwtException ex) {
            CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
            CCATLogger.DEBUG_LOGGER.error("Unsupported JWT token");
            CCATLogger.ERROR_LOGGER.error("Unsupported JWT token", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "unsupported token");

        } catch (IllegalArgumentException ex) {
            CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
            CCATLogger.DEBUG_LOGGER.error("JWT claims string is empty");
            CCATLogger.ERROR_LOGGER.error("JWT claims string is empty", ex);
            throw new DynamicPageException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "empty claims string");
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
            ObjectMapper mapper = new ObjectMapper();
            final ObjectNode node = new ObjectMapper().readValue(result.toString(), ObjectNode.class);
            if (node.get("token") != null) {
                token = node.get("token").textValue();
            }

        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.debug("failed to generate baseRequest model, token not found ");
        }

        return token;
    }

    public String getTokenFromMultiPartReq(HttpRequestWrapper request) {
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
