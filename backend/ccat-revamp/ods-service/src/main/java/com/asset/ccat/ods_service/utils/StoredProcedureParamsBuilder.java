package com.asset.ccat.ods_service.utils;

import oracle.jdbc.OracleTypes;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Mayar Ezz el-Din
 */
public class StoredProcedureParamsBuilder {

    /**
     * Builds a list of input parameters for a stored procedure based on the given parameter map.
     * Dynamically sets the SQL type for each parameter.
     *
     * @param parameterMap A map containing parameter names and their corresponding values.
     * @return A list of SqlParameter objects for the input parameters.
     */
    public List<SqlParameter> buildInputParameters(Map<String, String> parameterMap) {
        List<SqlParameter> inputParameters = new ArrayList<>();

        for (Map.Entry<String, String> entry : parameterMap.entrySet()) {
            String paramName = entry.getKey();
            int sqlType = switch (paramName.toUpperCase()) {
                case "FLAG", "INPUT_TYPE", "NUMBER_OF_BILLS", "REPORT_TYPE" -> OracleTypes.NUMBER;
                default -> OracleTypes.VARCHAR;
            };
            inputParameters.add(new SqlParameter(paramName, sqlType));
        }

        return inputParameters;
    }
    /**
     * Builds a list of output parameters for a stored procedure based on its metadata.
     * Fixed parameters are ERROR_CODE (NUMBER) and ERROR_DESCRIPTION (VARCHAR).
     * All other parameters are treated as SYS_REFCURSOR.
     *
     * @param outputParamNames A list of output parameter names.
     * @return A list of SqlOutParameter objects for the output parameters.
     */
    public List<SqlOutParameter> buildOutputParameters(String[] outputParamNames) {
        List<SqlOutParameter> outputParameters = new ArrayList<>();

        for (String paramName : outputParamNames) {
            int sqlType;
            switch (paramName.toUpperCase()) {
                case "ERROR_CODE" -> sqlType = OracleTypes.NUMERIC;
                case "ERROR_DESCRIPTION" -> sqlType = OracleTypes.VARCHAR;
                default -> sqlType = OracleTypes.REF_CURSOR;
            }
            outputParameters.add(new SqlOutParameter(paramName, sqlType));
        }

        return outputParameters;
    }
}
