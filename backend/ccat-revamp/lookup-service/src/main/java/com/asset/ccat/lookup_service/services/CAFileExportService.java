package com.asset.ccat.lookup_service.services;


import com.asset.ccat.lookup_service.defines.Defines;
import com.asset.ccat.lookup_service.defines.ErrorCodes;
import com.asset.ccat.lookup_service.exceptions.LookupException;
import com.asset.ccat.lookup_service.logger.CCATLogger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
public class CAFileExportService {

    public byte[] exportAsExcel(ArrayList<String> headers, String fileName, List<String> list) throws LookupException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            XSSFSheet sheet = (XSSFSheet) workbook.createSheet(fileName);
            Row row = sheet.createRow(0);

            CellStyle style = workbook.createCellStyle();
            XSSFFont font = (XSSFFont) workbook.createFont();
            font.setBold(false);
            font.setFontHeight(10);
            style.setFont(font);
            int columnCount = 0;
            for (String header : headers) {
                createCell(row, columnCount++, header, style);
            }


            int rowNumber = 1;

            for (String names : list) {
                columnCount = 0;
                row = sheet.createRow(rowNumber);
                int length = names.split(",").length - 1;
                for (int i = length; i >= 0; i--) {
                    String name = names.split(",")[i];
                    createCell(row, columnCount++, name, style);

                }
                rowNumber++;

            }

            workbook.write(out);
            return out.toByteArray();

        } catch (IOException ex) {
            CCATLogger.DEBUG_LOGGER.debug("Export call activities failed");
            throw new LookupException(ErrorCodes.ERROR.EXPORT_FILE_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private void createCell(Row row, int columnCount, Object value, CellStyle style) {
        //  sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (value instanceof Integer) {
            cell.setCellValue((Integer) value);
        } else if (value instanceof Boolean) {
            cell.setCellValue((Boolean) value);
        } else {
            cell.setCellValue((String) value);
        }
        cell.setCellStyle(style);
    }

}
