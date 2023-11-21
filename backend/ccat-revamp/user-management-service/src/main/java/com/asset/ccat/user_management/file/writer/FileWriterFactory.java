/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.user_management.file.writer;

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
public class FileWriterFactory {
    
    private final CSVFileWriter csvWriter;

    @Autowired
    public FileWriterFactory(CSVFileWriter csvWriter) {
        this.csvWriter = csvWriter;
    }

    public FileWriter getFileWriter(String fileExt) throws UserManagementException {
        if (fileExt != null && !fileExt.equals("")) {
            if (fileExt.equalsIgnoreCase(FileType.CSV.ext)) {
                return csvWriter;
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
