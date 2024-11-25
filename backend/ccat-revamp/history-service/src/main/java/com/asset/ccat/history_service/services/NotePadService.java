package com.asset.ccat.history_service.services;

import com.asset.ccat.history_service.configurations.Properties;
import com.asset.ccat.history_service.database.dao.NotePadDao;
import com.asset.ccat.history_service.defines.ErrorCodes;
import com.asset.ccat.history_service.exceptions.HistoryException;
import com.asset.ccat.history_service.logger.CCATLogger;
import com.asset.ccat.history_service.models.NotePadModel;
import com.asset.ccat.history_service.models.requests.notepad.AddNotePadRequest;
import com.asset.ccat.history_service.models.responses.notepad.NotePadResponse;
import java.util.HashMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import com.asset.ccat.history_service.mappers.IMapper;

/**
 * @author wael.mohamed
 */
@Service
public class NotePadService {

    @Autowired
    NotePadDao notePadDao;
    @Autowired
    Properties properties;
    @Autowired
    @Qualifier("addNotePadRequest")
    IMapper iRequestMapper;
    @Autowired
    @Qualifier("NotePadModelResponse")
    IMapper notePadModelResponseMapper;

    public NotePadResponse getAllNotePad(String msisdn) throws HistoryException {
        try {
            List<NotePadModel> notePadModels = notePadDao.getAllNotePad(msisdn, getOppositeMsisdnFormat(msisdn));
            if (notePadModels.isEmpty()) {
                throw new HistoryException(ErrorCodes.ERROR.NO_DATA_FOUND);
            }
            return (NotePadResponse) notePadModelResponseMapper.mapTo(notePadModels);

        } catch (DataAccessException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.NO_COMMANDS_FOUND);
        } catch (HistoryException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public boolean addNotePad(AddNotePadRequest notePad) throws HistoryException {
        try {
            NotePadModel notePadModel = (NotePadModel) iRequestMapper.mapTo(notePad);
            notePadModel.setMsisdnModX(getMsisdnLastDigit(notePad.getMsisdn()));
            return notePadDao.addNotePad(notePadModel);
        } catch (DataAccessException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.NO_COMMANDS_FOUND);
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public boolean deleteNotePadEntries(String msisdn) throws HistoryException {
        try {
            return notePadDao.deleteNotePadEntries(msisdn, getOppositeMsisdnFormat(msisdn));
        } catch (DataAccessException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.NO_COMMANDS_FOUND);
        } catch (HistoryException ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw ex;
        } catch (Exception ex) {
            CCATLogger.DEBUG_LOGGER.info("Error while calling History ");
            CCATLogger.ERROR_LOGGER.error("Error while calling History ", ex);
            throw new HistoryException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }

    public int getMsisdnLastDigit(String msisdn) {
        CCATLogger.DEBUG_LOGGER.debug("getSubPartirions " + properties.getSubpartitions());
        return (int) (Long.parseLong(msisdn) % properties.getSubpartitions());
    }

    private String getActivePartition(String msisdn) {
        CCATLogger.DEBUG_LOGGER.debug("Starting getActivePartition ");
        int mod = getMsisdnLastDigit(msisdn);
        String partitionName = "P" + mod;
        CCATLogger.DEBUG_LOGGER.debug("ActivePartition = " + partitionName);
        return partitionName;
    }

    public String getOppositeMsisdnFormat(String msisdn) throws HistoryException {

        CCATLogger.DEBUG_LOGGER.debug("Starting Utilities - getOppositeMsisdnFormat with msisdn = " + msisdn);

        try {
            String oldMsisdn = msisdn;
            String newMsisdn = msisdn;

            String oldPrefix = "";
            String newPrefix = "";

            String prefixMappingsStr = properties.getAirPrefixesMappings();

            HashMap msisdnPrefixes = new HashMap();

            String[] tmp2 = null;
            String[] tmp3 = null;

            if (prefixMappingsStr != null) {
                tmp2 = prefixMappingsStr.split(";");
            }

            if (tmp2 != null) {
                for (String tmp21 : tmp2) {
                    if (tmp21 != null && !tmp21.isBlank()) {
                        tmp3 = tmp21.trim().split(",");
                        if (tmp3[0] != null && !tmp3[0].isBlank() && tmp3[1] != null && !tmp3[1].isBlank()) {
                            msisdnPrefixes.put(tmp3[0].trim(), tmp3[1].trim());
                        }
                    }
                }
            }

            if (oldMsisdn.length() == 9) {
                oldPrefix = oldMsisdn.substring(0, 2);
                oldMsisdn = oldMsisdn.substring(2);

            } else if (oldMsisdn.length() == 10) {
                oldPrefix = oldMsisdn.substring(0, 3);
                oldMsisdn = oldMsisdn.substring(3);
            }

            if (msisdnPrefixes.get(oldPrefix) != null) {
                newPrefix = (String) msisdnPrefixes.get(oldPrefix);
            }
            //replace
            newMsisdn = newPrefix + oldMsisdn;

            CCATLogger.DEBUG_LOGGER.debug("Ending Utilities - getOppositeMsisdnFormat with msisdn = " + newMsisdn);
            return newMsisdn;
        } catch (ArrayIndexOutOfBoundsException e) {
            CCATLogger.DEBUG_LOGGER.error("ArrayIndexOutOfBoundsException --> " + e);
            CCATLogger.ERROR_LOGGER.error("ArrayIndexOutOfBoundsException --> ", e);
            throw new HistoryException(ErrorCodes.ERROR.MISSING_FIELD);
        } catch (Exception e) {
            CCATLogger.DEBUG_LOGGER.error("Exception --> " + e);
            CCATLogger.ERROR_LOGGER.error("Exception --> ", e);
            throw new HistoryException(ErrorCodes.ERROR.UNKNOWN_ERROR);
        }
    }
}
