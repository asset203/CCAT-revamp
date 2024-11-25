package com.asset.ccat.data_migration_service.services;

import com.asset.ccat.data_migration_service.constants.Tables;
import com.asset.ccat.data_migration_service.database.dao.BaseMigrationWriteDao;
import com.asset.ccat.data_migration_service.exceptions.DataMigrationException;
import com.asset.ccat.data_migration_service.factories.MigrationWriteDaoFactory;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * @author Assem.Hassan
 */
@Service
public class ConsumeDataService {

    @Autowired
    FileReaderService fileReaderService;

    @Autowired
    MigrationWriteDaoFactory migrationWriteDaoFactory;

    public void consumeCSVFiles() throws DataMigrationException {
        List<File> csvFilesNames = fileReaderService.loadAllFiles();
        try {
            String fileName;
            CCATLogger.DEBUG_LOGGER.debug(csvFilesNames);
            for (File file : csvFilesNames) {
                fileName = file.getName().replace(".csv", "");
                BaseMigrationWriteDao dao = migrationWriteDaoFactory.getDao(fileName);
                if (!Tables.ADM_SYSTEM_PROPERTIES.toString().equals(fileName)) {
                    dao.insertData(fileName, fileReaderService.readCSVFile(file));
                } else {
                    dao.insertData(fileName, fileReaderService.readAdmSystemPropertiesCSVFile(file));
                }
            }
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("ERROR while consuming files !! " + ex);
            CCATLogger.ERROR_LOGGER.error("ERROR consuming files !! ", ex);
        }
    }

}
