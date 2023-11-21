package com.asset.ccat.user_management.file.parser;

import com.asset.ccat.user_management.cache.MessagesCache;
import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.users.UserModel;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class ExcelParser implements Parser {

    @Autowired
    private MessagesCache messagesCache;

    @Override
    public List<UserModel> parse(InputStream inputStream, Boolean requiredAll) throws UserManagementException {
        try {
            List<UserModel> users = null;

            int ntAccountIndex = -1;
            int profileIndex = -1;

            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);

            // reading headers
            Row row = sheet.getRow(0);
            Cell header;
            String headerVal;
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                header = row.getCell(i);
                if (header == null || header.getCellType() == CellType.BLANK) {
                    continue;
                } else if (header.getCellType() != CellType.STRING) {
                    CCATLogger.DEBUG_LOGGER.error("Error while parsing excel file headers, header value is not string");
                    throw new UserManagementException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE, Defines.SEVERITY.ERROR);
                }

                headerVal = header.getStringCellValue();

                if (headerVal.equalsIgnoreCase(Defines.FILE.USER.HEADERS.NT_ACCOUNT)) {
                    ntAccountIndex = i;
                } else if (headerVal.equalsIgnoreCase(Defines.FILE.USER.HEADERS.PROFILE_NAME)) {
                    profileIndex = i;
                } else {
                    CCATLogger.DEBUG_LOGGER.error("Error while parsing excel file headers, [" + headerVal + "] header is invalid");
                    throw new UserManagementException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE, Defines.SEVERITY.ERROR);
                }
            }
            // Headers parsing check
            if (ntAccountIndex == -1 || (profileIndex == -1 && requiredAll)) {
                CCATLogger.DEBUG_LOGGER.error("Invalid users file template, missing headers");
                throw new UserManagementException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE, Defines.SEVERITY.ERROR);
            }

            // Reading users
            users = readUsers(sheet, ntAccountIndex, profileIndex, requiredAll);

            return users;
        } catch (UserManagementException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while parsing users file : [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing users file", ex);
            throw new UserManagementException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private List<UserModel> readUsers(Sheet sheet, int ntAccountIndex, int profileNameIndex, Boolean requiredAll) {
        ArrayList<UserModel> users = new ArrayList<>();
        for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
            UserModel user = new UserModel();
            Row userEntry = sheet.getRow(i);

            //skip empty rows
            if (isRowEmpty(userEntry)) {
                continue;
            }
            try {
                //reading ntAccount
                if (userEntry.getCell(ntAccountIndex) != null
                        && userEntry.getCell(ntAccountIndex).getCellType() == CellType.STRING) {
                    user.setNtAccount(userEntry.getCell(ntAccountIndex).getStringCellValue());
                } else {
                    CCATLogger.DEBUG_LOGGER.error("Invalid nt account value in row number [" + i + "]"); // log index of row
                    throw new UserManagementException(ErrorCodes.ERROR.INVALID_INPUT, Defines.SEVERITY.ERROR, Defines.FILE.USER.HEADERS.NT_ACCOUNT);
                }

                //reading profileName
                if (requiredAll) {
                    if (userEntry.getCell(profileNameIndex) != null
                            && userEntry.getCell(profileNameIndex).getCellType() == CellType.STRING) {
                        user.setProfileName(userEntry.getCell(profileNameIndex).getStringCellValue());
                    } else {
                        CCATLogger.DEBUG_LOGGER.error("Invalid profile name value in row number [" + i + "]");
                        throw new UserManagementException(ErrorCodes.ERROR.INVALID_INPUT, Defines.SEVERITY.ERROR, Defines.FILE.USER.HEADERS.PROFILE_NAME);
                    }
                }

            } catch (UserManagementException ex) {
                user.setStatus("FAILED");
                user.setStatusMessage(messagesCache.replaceArgument(messagesCache.getErrorMsg(ex.getErrorCode()), ex.getArgs()));
            } finally {
                users.add(user);
            }
        }
        return users;
    }

    private boolean isRowEmpty(Row row) {
        boolean isEmpty = true;
        DataFormatter dataFormatter = new DataFormatter();
        if (row != null) {
            for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
                if (dataFormatter.formatCellValue(row.getCell(i)).trim().length() > 0) {
                    isEmpty = false;
                    break;
                }
            }
        }
        return isEmpty;
    }
}
