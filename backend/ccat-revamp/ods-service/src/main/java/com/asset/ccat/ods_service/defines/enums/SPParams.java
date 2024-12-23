package com.asset.ccat.ods_service.defines.enums;

public enum SPParams {
    MSISDN("MSISDN"),
    FROM_DATE("FROM_DATE"),
    TO_DATE("TO_DATE"),
    INPUT_IDENTIFIER("INPUT_IDENTIFIER"),
    INPUT_TYPE("INPUT_TYPE"),
    NUMBER_OF_BILLS("NUMBER_OF_BILLS"),
    REPORT_TYPE("REPORT_TYPE"),
    FLAG("FLAG"),
    OUTPUT_CURSOR("OUTPUT_CURSOR"),
    CONTRACT_BILLS("CONTRACT_BILLS"),
    ERROR_CODE("ERROR_CODE"),
    ERROR_DESCRIPTION("ERROR_DESCRIPTION");
    final String paramName;

    SPParams(String paramName) {
        this.paramName = paramName;
    }
    public String getParamName() {
        return paramName;
    }
}
