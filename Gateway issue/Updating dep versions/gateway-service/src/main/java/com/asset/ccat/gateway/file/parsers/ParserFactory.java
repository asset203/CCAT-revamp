package com.asset.ccat.gateway.file.parsers;

import com.asset.ccat.gateway.constants.FileType;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class ParserFactory {

    private final MsisdnExcelFileParser msisdnExcelFileParser;
    private final MsisdnTextFileParser msisdnTextFileParser;

    @Autowired
    public ParserFactory(MsisdnExcelFileParser msisdnExcelFileParser, MsisdnTextFileParser msisdnTextFileParser) {
        this.msisdnExcelFileParser = msisdnExcelFileParser;
        this.msisdnTextFileParser = msisdnTextFileParser;
    }

    public Parser getMsisdnFileParser(String fileExt) throws GatewayException {
        if (fileExt != null && !fileExt.equals("")) {
            if (fileExt.equalsIgnoreCase(FileType.EXCEL_2007.ext) || fileExt.equalsIgnoreCase(FileType.EXCEL_2003.ext)) {
                return msisdnExcelFileParser;
            } else if (fileExt.equalsIgnoreCase(FileType.TXT.ext)) {
                return msisdnTextFileParser;
            } else if (fileExt.equalsIgnoreCase(FileType.CSV.ext)) {
                return msisdnTextFileParser;
            } else {
                CCATLogger.DEBUG_LOGGER.error("Unsupported file type : [" + fileExt + "]");
                throw new GatewayException(ErrorCodes.ERROR.UNSUPPORTED_FILE_TYPE, Defines.SEVERITY.ERROR);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.error("Missing file extension");
            throw new GatewayException(ErrorCodes.WARNING.MISSING_FIELD, null, "file extention");
        }
    }
}
