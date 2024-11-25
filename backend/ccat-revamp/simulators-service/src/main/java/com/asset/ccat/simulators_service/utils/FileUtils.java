/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.simulators_service.utils;


import com.asset.ccat.simulators_service.logger.CCATLogger;
import com.asset.ccat.simulators_service.models.UCIPModel;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Objects;

/**
 *
 * @author mahmoud.shehab
 */
public class FileUtils {

    public static String readFileAsString(String filePath, UCIPModel model)
        throws IOException {
        StringBuilder fileData = new StringBuilder(2000);
        String generatedFilePath = "";
        BufferedReader reader = null;
        boolean haveFileTemplate = false;

        try {
            try {
                generatedFilePath = filePath + "_" + model.getMsisdn() + ".xml";
                reader = new BufferedReader(new FileReader(generatedFilePath));
                haveFileTemplate = true;
            } catch (IOException ex) {
                CCATLogger.DEBUG_LOGGER.info(generatedFilePath + " Not found");
            }
            if (!haveFileTemplate) {
                try {
                    generatedFilePath = filePath + ".xml";
                    reader = new BufferedReader(new FileReader(generatedFilePath));
                    haveFileTemplate = true;
                } catch (IOException ex) {
                    CCATLogger.DEBUG_LOGGER.info(generatedFilePath + " Not found");
                    CCATLogger.DEBUG_LOGGER.error(generatedFilePath + " Not found");
                }
            }
            if (!haveFileTemplate) {
                CCATLogger.DEBUG_LOGGER.info("please add a response file , file should be named like this $methodName$_$MSISDN$");
                CCATLogger.DEBUG_LOGGER.info("add this file " + generatedFilePath + " at this location ");
                CCATLogger.DEBUG_LOGGER.info("****************************************************  End   **************************************");
            } else {

                char[] buf = new char[1024];

                int numRead = 0;

                while ((numRead = reader.read(buf)) != -1) {
                    String readData = String.valueOf(buf, 0, numRead);

                    fileData.append(readData);

                    buf = new char[1024];
                }
            }
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.info("Error in FileUtils: " + filePath);
            CCATLogger.DEBUG_LOGGER.error("Error in FileUtils: " + filePath);
        } finally {
            if (reader != null) {
                reader.close();
            }
        }

        String resultFile = fileData.toString();

        if(Objects.nonNull(model.getLanguageId())){
            resultFile = resultFile.replace("$LANGUAGE_ID$",model.getLanguageId());
        }else {
            resultFile = resultFile.replace("$LANGUAGE_ID$","1");
        }

        if(Objects.nonNull(model.getServiceClassId())){
            resultFile = resultFile.replace("$SERVICE_CLASS_ID$", model.getServiceClassId());
        }else {
            resultFile = resultFile.replace("$SERVICE_CLASS_ID$", "1");
        }

        if(Objects.nonNull(model.getAdjustmentAmount())){
            String balance = model.getAdjustmentAmount();
            resultFile = resultFile.replace("$BALANCE$", balance);
        }else {
            resultFile = resultFile.replace("$BALANCE$", "0");

        }



        return resultFile;
    }
}
