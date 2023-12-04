package com.asset.ccat.user_management.file.parser;

import com.asset.ccat.user_management.configurations.constants.FileType;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class ParserFactory {

    private final ExcelParser excelParser;

    @Autowired
    public ParserFactory(ExcelParser excelParser) {
        this.excelParser = excelParser;
    }

    public Parser getParser(String fileExt) throws UserManagementException {
        if (fileExt != null && !fileExt.equals("")) {
            if (fileExt.equalsIgnoreCase(FileType.EXCEL_2007.ext)
                    || fileExt.equalsIgnoreCase(FileType.EXCEL_2003.ext)) {
                return excelParser;
            } else {
                CCATLogger.DEBUG_LOGGER.error("Unsupported file type : [" + fileExt + "]");
                throw new UserManagementException(ErrorCodes.ERROR.UNSUPPORTED_FILE_TYPE, Defines.SEVERITY.ERROR);
            }
        } else {
            CCATLogger.DEBUG_LOGGER.error("Missing file extension");
            throw new UserManagementException(ErrorCodes.ERROR.MISSING_FIELD, Defines.SEVERITY.ERROR, "file extention");
        }
    }

}
