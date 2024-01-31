package com.asset.ccat.gateway.services;

import com.asset.ccat.gateway.constants.FileType;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.file.parsers.ServiceClassesExcelFileParser;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.MigrationModel;
import com.asset.ccat.gateway.models.shared.ServiceClassesMigrationSummary;
import com.asset.ccat.gateway.redis.model.SubscriberActivityModel;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

/**
 * @author assem.hassan
 */
@Service
public class MigrationService {

    @Autowired
    ServiceClassesExcelFileParser serviceClassesExcelFileParser;

    public ByteArrayInputStream exportServiceClasses(List<MigrationModel> serviceClasses) throws GatewayException {

        //Blank workbook
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            for (MigrationModel model : serviceClasses) {
                //Create a blank sheet
                Sheet sheet = workbook.createSheet(model.getTableName());

                //write tableName
                int rowNum = 0;
                Row tableNameRow = sheet.createRow(rowNum++);
                Cell tableNameCell = tableNameRow.createCell(0);
                tableNameCell.setCellValue(model.getTableName());

                //write keyIdentifier
                Row keyIdentifierRow = sheet.createRow(rowNum++);
                Cell keyIdentifierCell = keyIdentifierRow.createCell(0);
                keyIdentifierCell.setCellValue(model.getKeyIdentifier());

                //write headers
                Row tableHeadersRow = sheet.createRow(rowNum++);
                int tableHeadersRowCellNum = 0;
                for (String header : model.getHeaders()
                ) {
                    Cell cell = tableHeadersRow.createCell(tableHeadersRowCellNum++);
                    cell.setCellValue(header);
                }
                //Iterate over data and write to sheet
                for (var map : model.getData()) {
                    Row row = sheet.createRow(rowNum++);
                    int cellNum = 0;
                    for (String header : model.getHeaders()) {
                        Cell cell = row.createCell(cellNum++);
                        cell.setCellValue(map.get(header));
                    }
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new GatewayException(ErrorCodes.ERROR.EXPORT_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    public List<MigrationModel> importServiceClasses(MultipartFile file, String fileExtension) throws GatewayException {
        try {
            if (fileExtension.equalsIgnoreCase(FileType.EXCEL_2007.ext)
                    || fileExtension.equalsIgnoreCase(FileType.EXCEL_2003.ext)) {
                return serviceClassesExcelFileParser.parse(file.getInputStream());
            } else {
                CCATLogger.DEBUG_LOGGER.error("Unsupported file type : [" + fileExtension + "]");
                throw new GatewayException(ErrorCodes.ERROR.UNSUPPORTED_FILE_TYPE, Defines.SEVERITY.ERROR);
            }
        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Failed to parse file: [" + ex.getMessage() + "]", ex);
            throw new GatewayException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    public byte[] writeServiceClassesMigrationSummary(List<ServiceClassesMigrationSummary> summary) throws GatewayException {

        CCATLogger.DEBUG_LOGGER.debug("Start writing service classes migration summary to CSV file ");

        String[] headers = {"TABLE_NAME, ID, ACTION, STATUS, STATUS_MESSAGE"};
        String[][] data = new String[summary.size()][5];
        for (int i = 0; i < summary.size(); i++) {
            ServiceClassesMigrationSummary summaryItem = summary.get(i);

            data[i][0] = summaryItem.getTableName();
            data[i][1] = summaryItem.getIdentifier();
            data[i][2] = summaryItem.getAction();
            data[i][3] = summaryItem.getStatus();
            data[i][4] = summaryItem.getStatusMessage();
        }
        //create a CSV printer
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();
             CSVPrinter printer = new CSVPrinter(new PrintWriter(out), CSVFormat.DEFAULT);) {
            // create headers row
            printer.printRecord(headers);

            // create data rows
            for (String[] line : data) {
                printer.printRecord(line);
            }

            // flushing printer content to output stream
            printer.flush();

            // return content of output stream
            CCATLogger.DEBUG_LOGGER.info("Finished writing service classes migration summary successfully");
            return out.toByteArray();

        } catch (IOException e) {
            throw new GatewayException(ErrorCodes.ERROR.SUMMARY_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}