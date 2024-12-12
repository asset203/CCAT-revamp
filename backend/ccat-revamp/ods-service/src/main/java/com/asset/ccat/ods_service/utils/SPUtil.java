package com.asset.ccat.ods_service.utils;

import com.asset.ccat.ods_service.cache.DSSCache;
import com.asset.ccat.ods_service.models.DssInterfaceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Mayar Ezz el-Din
 */
@Service
public class SPUtil {
    private final DSSCache dssCache;

    @Autowired
    public SPUtil(DSSCache dssCache) {
        this.dssCache = dssCache;
    }

    /**
     * @return List of SQL input parameters.
     * */
    public List<SqlParameter> getSPInputParams(String spName, String[] paramValues) {
        DssInterfaceModel spInterface = Optional.ofNullable(dssCache.getDssInterfaceModelMap().get(spName))
                .orElseThrow(() -> new IllegalArgumentException("Stored procedure not found: " + spName));

        List<String> paramNames = getParamNames(spInterface.getSpInputParams());

        if (paramValues == null || paramNames == null || paramValues.length != paramNames.size())
            throw new IllegalArgumentException("Parameter names and values must be non-null and have the same length.");

//        String inputsAfterReplacement = replacePlaceholders(spInterface.getSpInputParams(), paramNames, paramValues);

        // Convert input string into a map
        Map<String, String> paramsMap = Arrays.stream(spInterface.getSpInputParams().split(",\\s*"))
                .map(pair -> pair.split("=", 2))
                .filter(keyValue -> keyValue.length == 2)
                .collect(Collectors.toMap(
                        keyValue -> keyValue[0],
                        keyValue -> keyValue[1],
                        (existing, replacement) -> existing, // Keep the first occurrence
                        LinkedHashMap::new // Use LinkedHashMap to preserve order
                ));
        return new StoredProcedureParamsBuilder().buildInputParameters(paramsMap);
    }

    public List<SqlOutParameter> getSPOutParams(String spName){
        DssInterfaceModel spInterface = Optional.ofNullable(dssCache.getDssInterfaceModelMap().get(spName))
                .orElseThrow(() -> new IllegalArgumentException("Stored procedure not found: " + spName));
        String[] outParams = spInterface.getSpOutputParams().split(",");
        return new StoredProcedureParamsBuilder().buildOutputParameters(outParams);
    }


    /**
     * MSISDN=$MSISDN$, FROM_DATE=$FROM_DATE$, TO_DATE=$TO_DATE$
     * @return MSISDN, FROM_DATE, TO_DATE
     * */
    public List<String> getParamNames(String spInputParamsString){
        return Arrays.stream(spInputParamsString.split(",\\s*"))
                .map(param -> param.split("=")[1]) // Extract the right-hand side (e.g., $MSISDN$)
                .map(placeholder -> placeholder.replace("$", ""))
                .toList();
    }

    public String replacePlaceholders(String inputString, List<String> paramNames, String[] paramValues){
        ReplacePlaceholdersUtil replacementUtil = new ReplacePlaceholdersUtil();
        IntStream.range(0, paramNames.size())
                .forEach(i -> replacementUtil.addPlaceholder("$" + paramNames.get(i) + "$", paramValues[i]));
        return replacementUtil.buildUrl(inputString);
    }

}
