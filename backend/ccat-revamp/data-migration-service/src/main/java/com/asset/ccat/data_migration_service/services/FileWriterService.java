package com.asset.ccat.data_migration_service.services;

import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class FileWriterService {

    public void writeToCSVFile(String fileName, List<LinkedHashMap<String, Object>> list) {
        //create a CSV printer
        long startTime = System.currentTimeMillis();
        try (PrintWriter out = new PrintWriter(fileName + ".csv")) {
            list.forEach(map -> {
                StringBuilder row = new StringBuilder();

                map.forEach((columnName, columnVal) -> {
                    row.append(columnVal).append(",");
                });
                row.replace(row.lastIndexOf(","), row.length(), "");
                row.append("\n");
                out.append(row);
            });
            // flushing printer content to output stream
            out.flush();
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]");
            CCATLogger.ERROR_LOGGER.error("Error while writing to csv, [" + ex.getMessage() + "]", ex);
        } finally {
            CCATLogger.DEBUG_LOGGER.debug("Retrieve data from table " + fileName
                    + " in [" + (System.currentTimeMillis() - startTime) + "]");
        }
    }
}
