package com.asset.ccat.balance_dispute_service.cache;

import com.asset.ccat.balance_dispute_service.configrations.Properties;
import com.asset.ccat.balance_dispute_service.defines.ErrorCodes;
import com.asset.ccat.balance_dispute_service.exceptions.BalanceDisputeException;
import com.asset.ccat.balance_dispute_service.logger.CCATLogger;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BalanceDisputeTemplatesCache {

  @Autowired
  Properties propertiesCache;

  private Map<String, File> balanceDisputeReportsCache;


  public void init() throws BalanceDisputeException {
    balanceDisputeReportsCache = new HashMap<>();
//    File baseDirectory = new File(propertiesCache.getBdTemplatesPath());
//    List<File> files = listFilesForFolder(baseDirectory);
//
//    if (files.isEmpty()) {
//      CCATLogger.DEBUG_LOGGER.info("No Reports to read in " + baseDirectory.getAbsolutePath());
//      throw new BalanceDisputeException(ErrorCodes.ERROR.NO_REPORTS_FOUND);
//    }
//
//    for (File file : files) {
//      try {
//        CCATLogger.DEBUG_LOGGER.info("Start reading content from [" + file.getName() + "]");
//        CCATLogger.DEBUG_LOGGER.info("Finish reading content from [" + file.getName() + "]");
//        balanceDisputeReportsCache.put(file.getName(), file);
//      } catch (Exception ex) {
//        CCATLogger.ERROR_LOGGER.error(
//            "Exception while reading content from [" + file.getName() + "]", ex);
//        throw new BalanceDisputeException(ErrorCodes.ERROR.UNKNOWN_ERROR,
//            "IOException while reading requests templates");
//      }
//    }
  }

  private List<File> listFilesForFolder(final File folder) {
    List<File> fList = null;
    if (Objects.nonNull(folder)) {
      fList = new ArrayList<>();
      File[] directory = folder.listFiles();
      if (directory != null) {
        for (final File fileEntry : directory) {
          if (!fileEntry.isDirectory()) {
            if (fileEntry.getName().contains(".xlsm")) {
              fList.add(fileEntry);
            }
          }
        }
      }
    }
    return fList;
  }

  public Map<String, File> getBalanceDisputeReportsCache() {
    return balanceDisputeReportsCache;
  }
}
