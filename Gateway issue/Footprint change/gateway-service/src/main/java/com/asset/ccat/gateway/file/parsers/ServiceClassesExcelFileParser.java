package com.asset.ccat.gateway.file.parsers;

import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.admin.MigrationModel;
import org.apache.poi.ss.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class ServiceClassesExcelFileParser {

    public List<MigrationModel> parse(InputStream inputStream) throws GatewayException {
        List<MigrationModel> migrationModels = null;
        try {
            Workbook wb = WorkbookFactory.create(inputStream);
            int numberOfSheets = wb.getNumberOfSheets();
            migrationModels = new ArrayList<>(numberOfSheets);
            DataFormatter dataFormatter = new DataFormatter();
            Sheet sheet;
            MigrationModel migrationModel;
            Row row;
            Cell cell;
            for (int i = 0; i < numberOfSheets; i++) {
                sheet = wb.getSheetAt(i);
                migrationModel = new MigrationModel();
                // row0>>tableName
                // row1>>keyIdentifer
                // row2>>headers
                if (sheet.getPhysicalNumberOfRows() <= 3) {
                    throw new GatewayException(ErrorCodes.WARNING.EMPTY_FILE);
                }
                String tableName = sheet.getRow(0).getCell(0).getStringCellValue();
                String keyIdentifier = sheet.getRow(1).getCell(0).getStringCellValue();
                List<String> headers = new ArrayList<>();
                for (int j = 0; j < sheet.getRow(2).getPhysicalNumberOfCells(); j++) {
                    headers.add(sheet.getRow(2).getCell(j).getStringCellValue());
                }

                // take care that if dataRow number of cells > number of headers
                List<HashMap<String, String>> data = new ArrayList<>();
                for (int x = 3; x < sheet.getPhysicalNumberOfRows(); x++) {
                    Row dataRow = sheet.getRow(x);
                    if (dataRow == null) {
                        continue;
                    }
                    HashMap<String, String> dataRowMap = new HashMap<>();
                    for (int y = 0; y < headers.size(); y++) {
                        dataRowMap.put(headers.get(y), dataFormatter.formatCellValue(dataRow.getCell(y)));
                    }
                    data.add(dataRowMap);
                }
                migrationModel.setTableName(tableName);
                migrationModel.setKeyIdentifier(keyIdentifier);
                migrationModel.setHeaders(headers);
                migrationModel.setData(data);
                migrationModels.add(migrationModel);
            }
            return migrationModels;

        } catch (GatewayException ex) {
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while parsing msisdn file : [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing msisdn file", ex);
            throw new GatewayException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}
