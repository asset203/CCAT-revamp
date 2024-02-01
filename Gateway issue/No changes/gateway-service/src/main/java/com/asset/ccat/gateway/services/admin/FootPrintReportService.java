package com.asset.ccat.gateway.services.admin;

import com.asset.ccat.gateway.constants.FootprintReportHeaders;
import com.asset.ccat.gateway.defines.Defines;
import com.asset.ccat.gateway.defines.ErrorCodes;
import com.asset.ccat.gateway.exceptions.GatewayException;
import com.asset.ccat.gateway.logger.CCATLogger;
import com.asset.ccat.gateway.models.requests.admin.foot_print.GetFootPrintReportRequest;
import com.asset.ccat.gateway.models.responses.admin.foot_print.GetFootPrintReportResponse;
import com.asset.ccat.gateway.proxy.admin.FootPrintReportProxy;
import com.asset.ccat.rabbitmq.models.FootprintDetailsModel;
import com.asset.ccat.rabbitmq.models.FootprintModel;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Service
public class FootPrintReportService {

    @Autowired
    private FootPrintReportProxy footPrintReportProxy;


    public GetFootPrintReportResponse getFootPrintReport(GetFootPrintReportRequest footPrintReportRequest) throws GatewayException {

        return footPrintReportProxy.getFootPrintReport(footPrintReportRequest);
    }


    public ByteArrayInputStream exportFootPrintReport(GetFootPrintReportRequest request) throws GatewayException {
        CCATLogger.DEBUG_LOGGER.info("Start serving export FootPrintReport request");

        CCATLogger.DEBUG_LOGGER.debug("Fetching FootPrintReport from lookup service");
        GetFootPrintReportResponse res = footPrintReportProxy.exportFootprintReport(request);
        CCATLogger.DEBUG_LOGGER.debug("Finished fetching FootPrintReport from lookup service successfully");

        CCATLogger.DEBUG_LOGGER.debug("Start writing FootPrintReport to file");
        ByteArrayInputStream fileInputStream = export(new ArrayList<>(res.getFootprints().values()));
        CCATLogger.DEBUG_LOGGER.debug("Finished writing FootPrintReport to file");
        CCATLogger.DEBUG_LOGGER.info("Finished serving export FootPrintReport request successfully");

        return fileInputStream;
    }

    private ByteArrayInputStream export(List<FootprintModel> footprintList) throws GatewayException {

        //Blank workbook
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("FootPrintReport");
            FootprintReportHeaders[] headers = FootprintReportHeaders.values();
            Row headerRow = sheet.createRow(0);

            for (int col = 0; col < headers.length; col++) {
                Cell cell = headerRow.createCell(col);
                cell.setCellValue(headers[col].getHeader());
            }

            int rowIdx = 1;
            for (FootprintModel footprint : footprintList) {

                if (Objects.nonNull(footprint.getFootPrintDetails())) {

                    for (FootprintDetailsModel detailsModel : footprint.getFootPrintDetails()) {
                        Row row = sheet.createRow(rowIdx++);
                        row.createCell(0).setCellValue(footprint.getId());
                        row.createCell(1).setCellValue(footprint.getPageName());
                        row.createCell(2).setCellValue(footprint.getTabName());
                        row.createCell(3).setCellValue(footprint.getActionName());
                        row.createCell(4).setCellValue(footprint.getActionType());
                        row.createCell(5).setCellValue(footprint.getActionTime());
                        row.createCell(6).setCellValue(footprint.getUserName());
                        row.createCell(7).setCellValue(footprint.getProfileName());
                        row.createCell(8).setCellValue(footprint.getMsisdn());
                        row.createCell(9).setCellValue(footprint.getStatus());
                        row.createCell(10).setCellValue(footprint.getErrorMessage());
                        row.createCell(11).setCellValue(footprint.getErrorCode());
                        row.createCell(12).setCellValue(footprint.getSessionId());
                        row.createCell(13).setCellValue(footprint.getMachineName());
                        row.createCell(14).setCellValue(footprint.getRequestId());

                        row.createCell(15).setCellValue(detailsModel.getParamName());
                        row.createCell(16).setCellValue(detailsModel.getOldValue());
                        row.createCell(17).setCellValue(detailsModel.getNewValue());
                    }
                } else {
                    Row row = sheet.createRow(rowIdx++);
                    row.createCell(0).setCellValue(footprint.getId());
                    row.createCell(1).setCellValue(footprint.getPageName());
                    row.createCell(2).setCellValue(footprint.getTabName());
                    row.createCell(3).setCellValue(footprint.getActionName());
                    row.createCell(4).setCellValue(footprint.getActionType());
                    row.createCell(5).setCellValue(footprint.getActionTime());
                    row.createCell(6).setCellValue(footprint.getUserName());
                    row.createCell(7).setCellValue(footprint.getProfileName());
                    row.createCell(8).setCellValue(footprint.getMsisdn());
                    row.createCell(9).setCellValue(footprint.getStatus());
                    row.createCell(10).setCellValue(footprint.getErrorMessage());
                    row.createCell(11).setCellValue(footprint.getErrorCode());
                    row.createCell(12).setCellValue(footprint.getSessionId());
                    row.createCell(13).setCellValue(footprint.getMachineName());
                    row.createCell(14).setCellValue(footprint.getRequestId());
                }
            }
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        } catch (IOException e) {
            throw new GatewayException(ErrorCodes.ERROR.EXPORT_FAILED, Defines.SEVERITY.ERROR);
        }
    }
}
