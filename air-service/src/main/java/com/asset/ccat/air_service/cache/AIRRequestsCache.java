/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.air_service.cache;

import com.asset.ccat.air_service.configurations.Properties;
import com.asset.ccat.air_service.defines.ErrorCodes;
import com.asset.ccat.air_service.exceptions.AIRServiceException;
import com.asset.ccat.air_service.logger.CCATLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author mahmoud.shehab
 */
@Component
public class AIRRequestsCache {

    @Autowired
    Properties propertiesCache;

    private Map<String, String> airRequestsCache;

    public void init() throws AIRServiceException {
        airRequestsCache = new HashMap<>();
        File baseDirectory = new File(propertiesCache.getAirCommandsPath());
        List<File> files = listFilesForFolder(baseDirectory);

        if (files.isEmpty()) {
            CCATLogger.DEBUG_LOGGER.info("No Requests templates to read in " + baseDirectory.getAbsolutePath());
            throw new AIRServiceException(ErrorCodes.ERROR.NO_COMMANDS_FOUND);
        }

        for (File file : files) {
            try {
                CCATLogger.DEBUG_LOGGER.info("Start reading content from [" + file.getName() + "]");
                String filePath = file.getAbsolutePath();
                String fileName = file.getName().replace(".xml", "");
                String content = new String(Files.readAllBytes(Paths.get(filePath)));
                CCATLogger.DEBUG_LOGGER.info("Finish reading content from [" + file.getName() + "]");
                airRequestsCache.put(fileName, content);
            } catch (IOException ex) {
                CCATLogger.ERROR_LOGGER.error("IOException while reading content from [" + file.getName() + "]", ex);
                throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR, "IOException while reading requests templates");
            } catch (Exception ex) {
                CCATLogger.ERROR_LOGGER.error("Exception while reading content from [" + file.getName() + "]", ex);
                throw new AIRServiceException(ErrorCodes.ERROR.UNKNOWN_ERROR, "IOException while reading requests templates");
            }
        }
    }

    private List<File> listFilesForFolder(final File folder) {
        List<File> fList = null;
        if (folder != null) {
            fList = new ArrayList();
            File[] directory = folder.listFiles();
            if (directory != null) {
                for (final File fileEntry : directory) {
                    if (!fileEntry.isDirectory()) {
                        if (fileEntry.getName().contains(".xml")) {
                            fList.add(fileEntry);
                        }
                    }
                }
            }
        }
        return fList;
    }

    public Map<String, String> getAirRequestsCache() {
        return airRequestsCache;
    }
}
