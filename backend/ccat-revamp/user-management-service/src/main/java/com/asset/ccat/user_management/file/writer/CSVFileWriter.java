package com.asset.ccat.user_management.file.writer;

import com.asset.ccat.user_management.defines.Defines;
import com.asset.ccat.user_management.defines.ErrorCodes;
import com.asset.ccat.user_management.exceptions.UserManagementException;
import com.asset.ccat.user_management.logger.CCATLogger;
import com.asset.ccat.user_management.models.dtoWrappers.ExtractAllUsersProfilesWrapper;
import com.asset.ccat.user_management.models.dtoWrappers.ProfileWrapper;
import com.asset.ccat.user_management.models.shared.UsersProfilesModel;
import com.asset.ccat.user_management.models.users.UserModel;
import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.output.ByteArrayOutputStream;
import org.springframework.stereotype.Component;

/**
 *
 * @author marwa.elshawarby
 */
@Component
public class CSVFileWriter implements FileWriter {

    @Override
    public byte[] writeToFile(List<UserModel> users) throws UserManagementException {
        String[] headers = {"USER_NAME", "STATUS", "STATUS_MESSAGE"};
        String[][] data = new String[users.size()][3];
        for (int i = 0; i < users.size(); i++) {
            UserModel user = users.get(i);
//            data[i] = {user.getNtAccount(), user.getStatus(), user.getStatusMessage()};
            data[i][0] = user.getNtAccount();
            data[i][1] = user.getStatus();
            data[i][2] = user.getStatusMessage();
        }
        return writeToCSV(headers, data);
    }

    @Override
    public byte[] writeUsersProfilesFile(ExtractAllUsersProfilesWrapper extractAllUsersProfilesWrapper) throws UserManagementException {
        LinkedList<String> rowsHeader = extractAllUsersProfilesWrapper.getProfilesName();
        List<UsersProfilesModel> data = extractAllUsersProfilesWrapper.getAllUsersProfilesList();
        List<List<String>> rows = new ArrayList<>();
        for(UsersProfilesModel usersProfilesModel : data){
            List<String> currentLine = new ArrayList<>();
            currentLine.add(usersProfilesModel.getUserName());
            for(String profile : rowsHeader){
                if(usersProfilesModel.getProfileName().equals(profile)){
                    currentLine.add("X");
                }else {
                    currentLine.add("-");
                }
            }
           rows.add(currentLine);
        }

        rowsHeader.addFirst("");
        return  writeToCSVByList(rowsHeader, rows);
    }

    private byte[] writeToCSV(String[] headers, String[][] lines) throws UserManagementException {
        //create a CSV printer
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
            // create headers row
            printer.printRecord(headers);

            // create data rows
            for (String[] line : lines) {
                printer.printRecord(line);
            }
            
            // flushing printer content to output stream
            printer.flush();
            
            // return content of output stream
            return out.toByteArray();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]", ex);
            throw new UserManagementException(ErrorCodes.ERROR.WRITING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private byte[] writeToCSVByList(List<String> headers, List<List<String>> lines) throws UserManagementException {
        //create a CSV printer
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
            // create headers row
            printer.printRecord(headers);

            // create data rows
            for (List<String> line : lines) {
                printer.printRecord(line);
            }

            // flushing printer content to output stream
            printer.flush();

            // return content of output stream
            return out.toByteArray();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]", ex);
            throw new UserManagementException(ErrorCodes.ERROR.WRITING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}
