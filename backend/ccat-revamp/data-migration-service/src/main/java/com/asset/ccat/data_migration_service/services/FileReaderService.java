package com.asset.ccat.data_migration_service.services;


import com.asset.ccat.data_migration_service.configurations.Properties;
import com.asset.ccat.data_migration_service.defines.Defines;
import com.asset.ccat.data_migration_service.defines.ErrorCodes;
import com.asset.ccat.data_migration_service.exceptions.DataMigrationException;
import com.asset.ccat.data_migration_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Assem.Hassan
 */
@Service
public class FileReaderService {


    @Autowired
    private Properties properties;

    public List<File> loadAllFiles() throws DataMigrationException {

        File baseDirectory = new File(properties.getReadTablesPath());
        List<File> files = listFilesForFolder(baseDirectory);
        if (files.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No Files to read in " + baseDirectory.getAbsolutePath());
            throw new DataMigrationException(ErrorCodes.ERROR.NO_FILES_FOUND);
        } else {
            return files;
        }
    }

    public List<Object[]> readCSVFile(File csvFile) {
        BufferedReader bufferedReader;
        List<Object[]> records = new ArrayList<>();

        try {
            FileReader fr = new FileReader(csvFile);
            bufferedReader = new BufferedReader(fr);
            //line variable will point to the buffered line
            String line = "";

            Object[] objects;
            //read each line of the file and buffer it
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                //split the line by delimiter
                objects = line.split(Defines.delimiter);
                records.add(objects);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            CCATLogger.ERROR_LOGGER.error("IOException while reading content from [" + csvFile.getName() + "]", ex);
        }
        return records;
    }

    public List<Object[]> readAdmSystemPropertiesCSVFile(File csvFile) {
        BufferedReader bufferedReader;
        List<Object[]> records = new ArrayList<>();

        try {
            FileReader fr = new FileReader(csvFile);
            bufferedReader = new BufferedReader(fr);
            //line variable will point to the buffered line
            String line = "";

            Object[] objects;
            //read each line of the file and buffer it
            while (Objects.nonNull(line = bufferedReader.readLine())) {
                //split the line by delimiter
                objects = line.split(Defines.delimiter, 2);
                records.add(objects);
            }
            bufferedReader.close();
        } catch (IOException ex) {
            CCATLogger.ERROR_LOGGER.error("IOException while reading content from [" + csvFile.getName() + "]", ex);
        }
        return records;
    }

    private List<File> listFilesForFolder(final File folder) {
        List<File> fList = null;
        if (folder != null) {
            fList = new ArrayList<>();
            File[] directory = folder.listFiles();
            if (directory != null) {
                for (final File fileEntry : directory) {
                    if (!fileEntry.isDirectory()) {
                        if (fileEntry.getName().contains(".csv")) {
                            fList.add(fileEntry);
                        }
                    }
                }
            }
        }
        return fList;
    }
}
