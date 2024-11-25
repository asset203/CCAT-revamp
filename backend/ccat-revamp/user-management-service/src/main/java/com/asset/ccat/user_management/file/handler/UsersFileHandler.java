package com.asset.ccat.user_management.file.handler;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.file.parser.Parser;
import com.asset.ccat.user_management.file.parser.ParserFactory;
import com.asset.ccat.user_management.file.writer.FileWriter;
import com.asset.ccat.user_management.file.writer.FileWriterFactory;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.users.UserModel;
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
public class UsersFileHandler {

    @Autowired
    private ParserFactory parserFactory;

    @Autowired
    private FileWriterFactory writerFactory;

    public List<UserModel> handleFileParsing(MultipartFile file, String fileExtension, Boolean requiredAll) throws UserManagementException {
        try {
            Parser fileParser = parserFactory.getParser(fileExtension);
            List<UserModel> users = fileParser.parse(file.getInputStream(), requiredAll);
            return users;
        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]", ex);
            throw new UserManagementException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    public byte[] handleFileWriting(List<UserModel> users, String fileExtension) throws UserManagementException {
        try {
            FileWriter fileWriter = writerFactory.getFileWriter(fileExtension);
            byte[] content = fileWriter.writeToFile(users);
            return content;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to write file: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to write file: [" + ex.getMessage() + "]", ex);
            throw new UserManagementException(ErrorCodes.ERROR.WRITING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
    public byte[] handleFileWritingForUsersProfiles(ExtractAllUsersProfilesWrapper data, String fileExtension) throws UserManagementException {
        try {
            CCATLogger.DEBUG_LOGGER.debug("Start Writing the UsersProfiles with extension={}", fileExtension);
            FileWriter fileWriter = writerFactory.getFileWriter(fileExtension);
            byte[] content = fileWriter.writeUsersProfilesFile(data);
            CCATLogger.DEBUG_LOGGER.debug("End Writing the UsersProfiles File.csv Successfully ");
            return content;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Exception occurred while writing users-file. ", ex);
            CCATLogger.ERROR_LOGGER.error("Exception occurred while writing users-file. ", ex);
            throw new UserManagementException(ErrorCodes.ERROR.WRITING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}
