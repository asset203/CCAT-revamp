package com.asset.ccat.gateway.file.handlers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.file.parsers.Parser;
import com.asset.ccat.gateway.file.parsers.ParserFactory;
import com.asset.ccat.gateway.logger.CCATLogger;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class MsisdnFileHandler {

    @Autowired
    private ParserFactory parserFactory;

    public List<String> handleFileParsing(MultipartFile file, String fileExtension) throws GatewayException {
        try {
            Parser fileParser = parserFactory.getMsisdnFileParser(fileExtension);
            List<String> msisdns = fileParser.parse(file.getInputStream());
            return msisdns;
        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]", ex);
            throw new GatewayException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}