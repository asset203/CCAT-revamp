package com.asset.ccat.procedureservice.security;


import com.asset.ccat.procedureservice.configrations.Properties;
import com.asset.ccat.procedureservice.defines.Defines;
import com.asset.ccat.procedureservice.defines.ErrorCodes;
import com.asset.ccat.procedureservice.exceptions.ProcedureException;
import com.asset.ccat.procedureservice.logger.CCATLogger;
import com.asset.ccat.procedureservice.dto.requests.BaseRequest;
import com.asset.ccat.procedureservice.dto.users.UserModel;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;


@Component
public class JwtTokenUtil implements Serializable {

    @Autowired
    private Properties properties;

    public HashMap<String, Object> extractDataFromToken(String token) throws ProcedureException {
        try {
            HashMap<String, Object> tokenData = new HashMap<>();
            CCATLogger.DEBUG_LOGGER.debug("extracting data from token");
            final Claims claims = getAllClaimsFromToken(token);
            CCATLogger.DEBUG_LOGGER.debug("Claims are extracted successfully");
            String username = claims.getSubject();
            String prefix = String.valueOf(claims.get(Defines.SecurityKeywords.PREFIX));
            tokenData.put(Defines.SecurityKeywords.USERNAME, username);
            tokenData.put(Defines.SecurityKeywords.PREFIX, prefix);
            return tokenData;
        } catch (ExpiredJwtException e) {
            CCATLogger.DEBUG_LOGGER.info("an error occurred ");
            CCATLogger.ERROR_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage());
            throw new ProcedureException(ErrorCodes.ERROR.EXPIRED_TOKEN);
        } catch (SignatureException e) {
            CCATLogger.DEBUG_LOGGER.info("an error occurred ");
            CCATLogger.ERROR_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage(), e);
            CCATLogger.DEBUG_LOGGER.error("the token is expired and not valid anymore: " + e.getMessage());
            throw new ProcedureException(ErrorCodes.ERROR.INVALID_TOKEN);
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
        CCATLogger.DEBUG_LOGGER.debug("getAllClaimsFromToken Started");
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
            CCATLogger.DEBUG_LOGGER.info("an error occurred ");
            CCATLogger.DEBUG_LOGGER.error("an error occurred : " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("an error occurred  " + ex.getMessage(), ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("an error occurred ");
            CCATLogger.DEBUG_LOGGER.debug("an error occurred : " + ex.getMessage());
            CCATLogger.ERROR_LOGGER.error("an error occurred  " + ex.getMessage(), ex);
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
            CCATLogger.DEBUG_LOGGER.debug("an error occurred : " + ex.getMessage());
            CCATLogger.DEBUG_LOGGER.info("an error occurred ");
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
