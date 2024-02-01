package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.logger.CCATLogger;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Autowired
    private Properties properties;


    private Function<String, Claims> getAllClaimsFromToken = (token) -> Jwts.parser()
            .setSigningKey(properties.getAccessTokenKey())
            .parseClaimsJws(token)
            .getBody();

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken.apply(token);
        return claimsResolver.apply(claims);
    }

    public Long getExpiryEpochDateFromToken(String token) {
        CCATLogger.DEBUG_LOGGER.debug("extracting the expiry date from a token..");
        Date expiryDate = getClaimFromToken(token, Claims::getExpiration);
        Long epoch = expiryDate.getTime();
        CCATLogger.DEBUG_LOGGER.debug("token expiry date = {}, epoch = {}", expiryDate, epoch);
        return epoch;
    }


}

