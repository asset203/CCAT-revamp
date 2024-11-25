/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.nba_service.security;

import com.asset.ccat.nba_service.defines.ErrorCodes;
import com.asset.ccat.nba_service.logger.CCATLogger;
import com.asset.ccat.nba_service.configurations.Properties;
import com.asset.ccat.nba_service.defines.Defines;
import com.asset.ccat.nba_service.exceptions.NBAException;
import com.asset.ccat.nba_service.models.requests.BaseRequest;
import com.asset.ccat.nba_service.models.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

/**
 *
 * @author mahmoud.shehab
 */
@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private Properties properties;

    public HashMap<String, Object> extractDataFromToken(String token) throws NBAException {
        try {
            HashMap<String, Object> tokenData = new HashMap<>();
            CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
            final Claims claims = getAllClaimsFromToken(token);
            String username = claims.getSubject();
            String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
            tokenData.put(Defines.SecurityKeywords.USERNAME, username);
            tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
            return tokenData;
        } catch (ExpiredJwtException e) {
            CCATLogger.DEBUG_LOGGER.info("an error occured ");
            CCATLogger.ERROR_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage());
            throw new NBAException(ErrorCodes.ERROR.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            CCATLogger.DEBUG_LOGGER.info("an error occured ");
            CCATLogger.ERROR_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage());
            throw new NBAException(ErrorCodes.ERROR.INVALID_TOKEN);
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

    public String generateToken(UserModel user) throws AuthenticationException, Exception {
        return doGenerateToken(user);
    }

    private String doGenerateToken(UserModel user) throws AuthenticationException, Exception {
        String token = "";
        try {
            CCATLogger.DEBUG_LOGGER.debug("start generating token for user : " + user.getNtAccount());
            Claims claims = Jwts.claims().setSubject(user.getNtAccount());
            claims.put(Defines.SecurityKeywords.PREFIX, Defines.SecurityKeywords.PREFIX);
            long accessTokenValidityMilli = properties.getAccessTokenValidity() * 60 * 1000;
            CCATLogger.DEBUG_LOGGER.debug("accessTokenValidityHour : " + properties.getAccessTokenValidity() + "  =  accessTokenValidityMilli : " + accessTokenValidityMilli);
            token = Jwts.builder()
                    .setClaims(claims)
                    .setIssuedAt(new Date(System.currentTimeMillis()))
                    .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityMilli))
                    .signWith(SignatureAlgorithm.HS256, properties.getAccessTokenKey().trim())
                    .compact();
        } catch (AuthenticationException ex) {
            CCATLogger.DEBUG_LOGGER.info("an error occured ");
            CCATLogger.DEBUG_LOGGER.error("an error occured : " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("an error occured  " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("an error occured ");
            CCATLogger.DEBUG_LOGGER.debug("an error occured : " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("an error occured  " + ex.getMessage(), ex);
            throw ex;
        }
        CCATLogger.DEBUG_LOGGER.debug("token generated");
        return token;
    }

    public Boolean validateToken(String token, String username) {
        try {
            CCATLogger.DEBUG_LOGGER.debug("start validate Token for user : " + username);
            if (isTokenExpired(token)) {
                CCATLogger.DEBUG_LOGGER.debug("token expired");
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

    public String getTokenFromRequest(BaseRequest baseRequest) {
        return baseRequest.getToken().replace(Defines.SecurityKeywords.PREFIX, "");

    }
}
