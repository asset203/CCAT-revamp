package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.constants.FileType;
import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import com.asset.ccat.lookup_service.models.ReasonActivityFlatModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Service;


import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;


import org.springframework.web.multipart.MultipartFile;

@Service
public class CAFileUploadService {

    public ArrayList<ReasonActivityFlatModel> uploadCallActivityFile(MultipartFile file, String fileExt) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CAFileUploadService - uploadCallActivityFile");
        CCATLogger.DEBUG_LOGGER.info("Start parsing call activities file");
        ArrayList<ReasonActivityFlatModel> activityReasonsList;
        if (fileExt.equalsIgnoreCase(FileType.EXCEL_2007.ext)
                || fileExt.equalsIgnoreCase(FileType.EXCEL_2003.ext)) {
            CCATLogger.DEBUG_LOGGER.debug("Parsing call activities excel file");
            activityReasonsList = uploadCallActivityReasonExcelFile(file);
        } else if (fileExt.equalsIgnoreCase(FileType.CSV.ext)) {
            CCATLogger.DEBUG_LOGGER.debug("Parsing call activities csv file");
            activityReasonsList = uploadCallActivityReasonCsvFile(file);
        } else {
            CCATLogger.DEBUG_LOGGER.info("Unsupported file type : [" + fileExt + "]");
            throw new LookupException(ErrorCodes.ERROR.UNSUPPORTED_FILE_TYPE, Defines.SEVERITY.ERROR);
        }

        CCATLogger.DEBUG_LOGGER.info("Finished parsing call activities file");
        CCATLogger.DEBUG_LOGGER.debug("Finished CAFileUploadService - uploadCallActivityFile");
        return activityReasonsList;
    }

    private ArrayList<ReasonActivityFlatModel> uploadCallActivityReasonExcelFile(MultipartFile file) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CAFileUploadService - uploadCallActivityReasonExcelFile");
        ArrayList<ReasonActivityFlatModel> activityReasonsList = new ArrayList<>();

        Workbook workbook = null;
        Sheet sheet = null;
        Cell[] dataRow = new Cell[4];
        Cell[] headers = null;
        boolean fileEmpty = true;

        try {

            workbook = WorkbookFactory.create(file.getInputStream());
            sheet = workbook.getSheetAt(0);

            if (sheet.getPhysicalNumberOfRows() <= 0) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in FileUploadService - uploadCallActivityReasonExcelFile: file is empty");
                CCATLogger.ERROR_LOGGER.error("Failure in FileUploadService - uploadCallActivityReasonExcelFile: file is empty");
                throw new LookupException(ErrorCodes.ERROR.FILE_IS_EMPTY);
            }

            Row row = sheet.getRow(0);
            headers = new Cell[4];


            Iterator iterator = row.iterator();
            for (int i = 0; iterator.hasNext(); i++) {
                headers[i] = row.getCell(i);
                iterator.next();
            }
            // get headers

            //validate headers
            if (headers[0].getStringCellValue() == null || headers[0].getStringCellValue().equals("")
                    || !headers[0].getStringCellValue().equalsIgnoreCase("direction")) {
                //Invalid direction header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile: "
                        + "Invalid file template | Direction header should be in first column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers[1].getStringCellValue() == null || headers[1].getStringCellValue().equals("")
                    || !headers[1].getStringCellValue().equalsIgnoreCase("family")) {
                //Invalid family header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile: "
                        + "Invalid file template | Family header should be in second column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers[2].getStringCellValue() == null || headers[2].getStringCellValue().equals("")
                    || !headers[2].getStringCellValue().equalsIgnoreCase("type")) {
                //Invalid type header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile: "
                        + "Invalid file template | Type header should be in third column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers[3].getStringCellValue() == null || headers[3].getStringCellValue().equals("")
                    || !headers[3].getStringCellValue().equalsIgnoreCase("reason")) {
                //Invalid reason header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile: "
                        + "Invalid file template | Reason header should be in fourth column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            }

            //extract data
            CCATLogger.DEBUG_LOGGER.debug("Number of data rows --> " + sheet.getPhysicalNumberOfRows());
            for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
                fileEmpty = false;
                row = sheet.getRow(i);
                dataRow = new Cell[4];
                Iterator rowIterator = sheet.getRow(i).iterator();
                for (int j = 0; rowIterator.hasNext(); j++) {
                    dataRow[j] = row.getCell(j);
                    rowIterator.next();
                }
                if (dataRow != null && dataRow.length != 0) {
                    ReasonActivityFlatModel reasonActivity = new ReasonActivityFlatModel();

                    if (dataRow[0] == null || dataRow[0].getStringCellValue() == null || dataRow[0].getStringCellValue().equals("")) {
                        //no direction present | skip row
                        continue;
                    }

                    // set direction

                    reasonActivity.setDirectionName(dataRow[0].getStringCellValue().trim());

                    if (dataRow[1] == null || dataRow[1].getStringCellValue() == null
                            || dataRow[1].getStringCellValue().equals("")) {
                        //no family present | skip rest of the row
                        continue;
                    }
                    //  set family
                    reasonActivity.setFamilyName(dataRow[1].getStringCellValue().trim());

                    if (dataRow[2] == null || dataRow[2].getStringCellValue() == null
                            || dataRow[2].getStringCellValue().equals("")) {
                        //no type present | skip rest of the row
                        continue;
                    }
                    //  set type
                    reasonActivity.setTypeName(dataRow[2].getStringCellValue().trim());

                    if (dataRow[3] == null || dataRow[3].getStringCellValue() == null
                            || dataRow[3].getStringCellValue().equals("")) {
                        //no reason present | skip rest of the row
                        continue;
                    }
                    //  set reason
                    reasonActivity.setReasonName(dataRow[3].getStringCellValue().trim());
                    activityReasonsList.add(reasonActivity);
                }
            }

            // file containing only headers
            if (fileEmpty) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile: file is empty");
                throw new LookupException(ErrorCodes.ERROR.FILE_IS_EMPTY);
            }

            // File contains invalid data structure
            if (activityReasonsList.size() <= 0) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonExcelFile : Invalid file structure");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_STRUCTURE);
            }

            return activityReasonsList;
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to parse call activties excel file");
            CCATLogger.ERROR_LOGGER.error("Failed to parse call activties excel file", e);
            throw new LookupException(ErrorCodes.ERROR.PARSING_FAILED);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Ended CAFileUploadService - uploadCallActivityReasonExcelFile");
        }
    }

    public ArrayList<ReasonActivityFlatModel> uploadCallActivityReasonCsvFile(MultipartFile file) throws LookupException {
        CCATLogger.DEBUG_LOGGER.debug("Starting CAFileUploadService - uploadCallActivityReasonCsvFile");

        String headersLine = "";
        String dataLine = "";
        String[] data;
        boolean fileEmpty = true;
        ArrayList<ReasonActivityFlatModel> activityReasonsList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()));) {

            if ((headersLine = br.readLine()) == null) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: file is empty");
                throw new LookupException(ErrorCodes.ERROR.FILE_IS_EMPTY);
            }

            // parsing headers
            String[] headers = headersLine.trim().split(",");

            //validate headers
            if (headers.length < 1 || headers[0].equals("")
                    || !headers[0].equalsIgnoreCase("direction")) {
                //Invalid direction header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: "
                        + "Invalid file template | Direction header should be in first column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers.length < 2 || headers[1].equals("")
                    || !headers[1].equalsIgnoreCase("family")) {
                //Invalid family header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: "
                        + "Invalid file template | Family header should be in second column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers.length < 3 || headers[2].equals("")
                    || !headers[2].equalsIgnoreCase("type")) {
                //Invalid type header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: "
                        + "Invalid file template | Type header should be in third column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            } else if (headers.length < 4 || headers[3].equals("")
                    || !headers[3].equalsIgnoreCase("reason")) {
                //Invalid reason header
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: "
                        + "Invalid file template | Reason header should be in fourth column");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_TEMPLATE);
            }

            while ((dataLine = br.readLine()) != null) {
                fileEmpty = false;
                data = dataLine.trim().split(",");

                if (data != null && data.length != 0) {
                    ReasonActivityFlatModel reasonActivity = new ReasonActivityFlatModel();

                    if (data[0].trim().equals("")) {
                        //no direction present | skip line
                        continue;
                    }
                    activityReasonsList.add(reasonActivity);
                    // set direction
                    reasonActivity.setDirectionName(data[0].trim());

                    if (data.length == 1 || data[1].trim().equals("")) {
                        //no family present | skip rest of the line
                        continue;
                    }
                    //  set family
                    reasonActivity.setFamilyName(data[1].trim());

                    if (data.length == 2 || data[2].trim().equals("")) {
                        //no type present | skip rest of the line
                        continue;
                    }
                    //  set type
                    reasonActivity.setTypeName(data[2].trim());

                    if (data.length == 3 || data[3].trim().equals("")) {
                        //no reason present | skip rest of the line
                        continue;
                    }
                    //  set reason
                    reasonActivity.setReasonName(data[3].trim());
                }
            }

            // file containing only headers
            if (fileEmpty) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: file is empty");
                throw new LookupException(ErrorCodes.ERROR.FILE_IS_EMPTY);
            }

            // File contains invalid data structure
            if (activityReasonsList.size() <= 0) {
                CCATLogger.DEBUG_LOGGER.debug("Failure in CAFileUploadService - uploadCallActivityReasonCsvFile: Invalid file structure");
                throw new LookupException(ErrorCodes.ERROR.INVALID_FILE_STRUCTURE);
            }

            return activityReasonsList;
        } catch (LookupException e) {
            throw e;
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.debug("Failed to parse call activties excel file");
            CCATLogger.ERROR_LOGGER.error("Failed to parse call activties excel file", e);
            throw new LookupException(ErrorCodes.ERROR.PARSING_FAILED);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Ending CAFileUploadService - uploadCallActivityReasonCsvFile");
        }
    }
}
