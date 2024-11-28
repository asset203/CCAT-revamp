/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.security;

import com.asset.ccat.user_management.configurations.Properties;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.requests.BaseRequest;
import com.asset.ccat.user_management.models.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.function.Function;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;
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

  public HashMap<String, Object> extractDataFromToken(String token) throws UserManagementException {
    try {
      HashMap<String, Object> tokenData = new HashMap<>();
      CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
      final Claims claims = getAllClaimsFromToken(token);
      String username = claims.getSubject();
      String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
      String userId = String.valueOf(claims.get(Defines.SecurityKeywords.USER_ID));
      String sessionId = String.valueOf(claims.get(Defines.SecurityKeywords.SESSION_ID));
      tokenData.put(Defines.SecurityKeywords.USERNAME, username);
      tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
      tokenData.put(Defines.SecurityKeywords.USER_ID, userId);
      tokenData.put(Defines.SecurityKeywords.SESSION_ID, sessionId);
      return tokenData;
    } catch (MalformedJwtException ex) {
      CCATLogger.DEBUG_LOGGER.error("MalformedJwtException JWT Token");
      CCATLogger.ERROR_LOGGER.error("MalformedJwtException JWT Token", ex);
      throw new UserManagementException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "malformed token");
    } catch (ExpiredJwtException ex) {
      CCATLogger.DEBUG_LOGGER.error("ExpiredJwtException JWT token");
      CCATLogger.ERROR_LOGGER.error("ExpiredJwtException JWT token", ex);
      throw new UserManagementException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "expired token");
    } catch (UnsupportedJwtException ex) {
      CCATLogger.DEBUG_LOGGER.error("UnsupportedJwtException: Unsupported JWT token");
      CCATLogger.ERROR_LOGGER.error("UnsupportedJwtException: Unsupported JWT token", ex);
      throw new UserManagementException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "unsupported token");
    } catch (IllegalArgumentException ex) {
      CCATLogger.DEBUG_LOGGER.error("IllegalArgumentException: JWT claims string is empty");
      CCATLogger.ERROR_LOGGER.error("IllegalArgumentException: JWT claims string is empty", ex);
      throw new UserManagementException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "empty claims string");
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
    // Retrieve the secret key from properties
    String secretKey = properties.getAccessTokenKey().trim();

    Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

    return Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .getBody();
  }

  private Boolean isTokenExpired(String token) {
    final Date expiration = getExpirationDateFromToken(token);
    return expiration.before(new Date());
  }

  public String generateToken(UserModel user, String machineName) throws UserManagementException {
    return doGenerateToken(user, machineName);
  }

  private String doGenerateToken(UserModel user, String machineName) throws UserManagementException {
    String token = "";
    try {
      String profileName = user.getProfileName() != null ? user.getProfileName() : user.getProfileModel().getProfileName();
      CCATLogger.DEBUG_LOGGER.debug("start generating token for user : " + user.getNtAccount());
      UUID uuid = UUID.randomUUID();
      Claims claims = Jwts.claims().setSubject(user.getNtAccount());
      claims.put(Defines.SecurityKeywords.PREFIX, Defines.SecurityKeywords.PREFIX);
      claims.put(Defines.SecurityKeywords.SESSION_ID, uuid.toString());
      claims.put(Defines.SecurityKeywords.USERNAME, user.getNtAccount());
      claims.put(Defines.SecurityKeywords.USER_ID, user.getUserId());
      claims.put(Defines.SecurityKeywords.PROFILE_ID, user.getProfileId());
      claims.put(Defines.SecurityKeywords.PROFILE_NAME, profileName);
      claims.put(Defines.SecurityKeywords.PROFILE_ROLE, user.getProfileModel().getAuthorizedUrls());
      claims.put(Defines.SecurityKeywords.MACHINE_NAME, machineName);

      long accessTokenValidityMilli = properties.getAccessTokenValidity() * 60 * 1000;
      CCATLogger.DEBUG_LOGGER.debug("accessTokenValidityHour : {}  =  accessTokenValidityMilli :  {}", + properties.getAccessTokenValidity() , accessTokenValidityMilli);

      Key key = Keys.hmacShaKeyFor(properties.getAccessTokenKey().trim().getBytes(StandardCharsets.UTF_8));

      token = Jwts.builder()
              .setClaims(claims)
              .setIssuedAt(new Date(System.currentTimeMillis()))
              .setExpiration(new Date(System.currentTimeMillis() + accessTokenValidityMilli))
              .signWith(key, SignatureAlgorithm.HS256)
              .compact();
    } catch (Exception ex) {
      CCATLogger.DEBUG_LOGGER.error("Exception occurred : ", ex);
      CCATLogger.ERROR_LOGGER.error("Exception occurred : ", ex);
      throw new UserManagementException(ErrorCodes.ERROR.CANNOT_GENERATE_TOKEN);
    }
    CCATLogger.DEBUG_LOGGER.debug("token generated");
    return token;
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
