package com.asset.ccat.gateway.file.parsers;

import com.asset.ccat.gateway.cache.MessagesCache;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author marwa.elshawarby
 */
@Component
public class MsisdnExcelFileParser implements Parser {

    @Autowired
    private MessagesCache messagesCache;

    @Override
    public List<String> parse(InputStream inputStream) throws GatewayException {
        try {

            Workbook wb = WorkbookFactory.create(inputStream);
            Sheet sheet = wb.getSheetAt(0);
            List<String> msisdns = readMsisdn(sheet);
            return msisdns;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while parsing msisdn file : [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while parsing msisdn file", ex);
            throw new GatewayException(ErrorCodes.ERROR.PARSING_FAILED, Defines.SEVERITY.ERROR);
        }
    }

    private List<String> readMsisdn(Sheet sheet) {
        String msisdn = "";
        List<String> msisdns = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        for (int i = 0; i < sheet.getPhysicalNumberOfRows(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                continue;
            }
            msisdn = dataFormatter.formatCellValue(row.getCell(0)).trim();
            if (msisdn.length() > 0) {
                msisdns.add(msisdn);
            }
        }
        return msisdns;
    }
}
