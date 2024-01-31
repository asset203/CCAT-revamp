package com.asset.ccat.gateway.file.parsers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class MsisdnTextFileParser implements Parser {

    @Override
    public List<String> parse(InputStream inputStream) throws GatewayException {
        try ( BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));) {

            String msisdn = "";
            List<String> msisdns = new ArrayList<>();

            while ((msisdn = br.readLine()) != null) {
                msisdn = msisdn.trim();
                if (!msisdn.isEmpty()) {
                    msisdns.add(msisdn);
                }
            }
            return msisdns;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while parsing msisdn file : [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing msisdn file", ex);
            throw new GatewayException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }

    }
}
