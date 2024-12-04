package com.asset.ccat.gateway.util;

import com.asset.ccat.gateway.configurations.Properties;
import com.asset.ccat.gateway.logger.CCATLogger;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Component
public class JwtUtil {
    @Autowired
    private Properties properties;


    private final Function<String, Claims> getAllClaimsFromToken = token -> {
        String secretKey = properties.getAccessTokenKey().trim();

        Key key = Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));

        // Parse the token to retrieve claims
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    };

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

