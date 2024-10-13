package com.asset.ccat.balancedisputemapperservice.utils;

import com.asset.ccat.balancedisputemapperservice.configurations.Properties;
import com.asset.ccat.balancedisputemapperservice.defines.Defines.SecurityKeywords;
import com.asset.ccat.balancedisputemapperservice.defines.ErrorCodes;
import com.asset.ccat.balancedisputemapperservice.exceptions.BalanceDisputeException;
import com.asset.ccat.balancedisputemapperservice.loggers.CCATLogger;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import java.io.Serializable;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Assem.Hassan
 */
@Component
public class JwtTokenUtil implements Serializable {

  @Autowired
  private Properties properties;

  public Integer extractDataFromToken(String token)
      throws BalanceDisputeException {
    try {
      CCATLogger.DEBUG_LOGGER.debug("Start extracting profile ID from the token.");
      HashMap<String, Object> tokenData = new HashMap<>();
      CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
      final Claims claims = getAllClaimsFromToken(token);

      return (Integer) claims.get(SecurityKeywords.PROFILE_ID);
    } catch (SignatureException ex) {
      CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
      CCATLogger.DEBUG_LOGGER.error("Invalid JWT signature");
      CCATLogger.ERROR_LOGGER.error("Invalid JWT signature", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "invalid signature");
    } catch (MalformedJwtException ex) {
      CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
      CCATLogger.DEBUG_LOGGER.error("Malformed JWT Token");
      CCATLogger.ERROR_LOGGER.error("Malformed JWT Token", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "malformed token");
    } catch (ExpiredJwtException ex) {
      CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
      CCATLogger.DEBUG_LOGGER.error("Expired JWT token");
      CCATLogger.ERROR_LOGGER.error("Expired JWT token", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "expired token");
    } catch (UnsupportedJwtException ex) {
      CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
      CCATLogger.DEBUG_LOGGER.error("Unsupported JWT token");
      CCATLogger.ERROR_LOGGER.error("Unsupported JWT token", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "unsupported token");

    } catch (IllegalArgumentException ex) {
      CCATLogger.DEBUG_LOGGER.info("An error occured during extracting claims from token");
      CCATLogger.DEBUG_LOGGER.error("JWT claims string is empty");
      CCATLogger.ERROR_LOGGER.error("JWT claims string is empty", ex);
      throw new BalanceDisputeException(ErrorCodes.ERROR.INVALID_TOKEN, 0, "empty claims string");
    }
  }


  private Claims getAllClaimsFromToken(String token) {
    //Jwts.
    return Jwts.parser()
        .setSigningKey(properties.getAccessTokenKey().trim())
        .parseClaimsJws(token)
        .getBody();
  }
}
