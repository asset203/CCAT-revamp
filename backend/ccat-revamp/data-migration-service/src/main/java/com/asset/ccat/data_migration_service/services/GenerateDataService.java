package com.asset.ccat.data_migration_service.services;

import com.asset.ccat.data_migration_service.configurations.Properties;
import com.asset.ccat.data_migration_service.database.dao.DataMigrationReadDao;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GenerateDataService {

    @Autowired
    Properties properties;

    @Autowired
    DataMigrationReadDao dataMigrationReadDao;

    @Autowired
    FileWriterService fileWriterService;

    public void generateDataTables() {
        String tablesNamesCSV = properties.getTablesNames();
        String[] tables = tablesNamesCSV.split(",");
        ConcurrentHashMap<String, List<LinkedHashMap<String, Object>>> allData = new ConcurrentHashMap<>();

        CCATLogger.DEBUG_LOGGER.debug(tables);
        Arrays.stream(tables).parallel().forEach(
                (table) -> {
                    allData.put(table, dataMigrationReadDao.extractGenericData(table));
                }
        );

        allData.forEach((key, value) -> {
            CCATLogger.DEBUG_LOGGER.info("table is [" + key + "] table size " + value.size());
            fileWriterService.writeToCSVFile(properties.getWriteTablesPath() + key, value);
        });
    }
}
