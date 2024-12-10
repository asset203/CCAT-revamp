/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.asset.ccat.lookup_service.defines;

/**
 * @author Mahmoud Shehab
 */
public class ErrorCodes {

    public static class SUCCESS {

        public static final int SUCCESS = 0;
    }

    public static class ERROR {

        public static final int TOKEN_EXPIRED = -701;
        public static final int INVALID_PARAMETER = -702;
        public static final int DATABASE_ERROR = -703;
        public static final int GENERAL_ERROR = -704;
        public static final int NO_DATA_FOUND = -705;
        public static final int HAS_CHILD = -706;       //Can't Delete this Record since a Child Found
        public static final int EMPTY_ERSPONSE = -707;
        public static final int SERVICE_NOT_FOUND = -708;
        public static final int DUPLICATED_DATA = -709;
        public static final int NOT_DELETED = -710;     //Can't Delete this Record.
        public static final int ADD_FAILED = -711;
        public static final int UPDATE_FAILED = -712;
        public static final int DELETE_FAILED = -713;

        public static final int DATA_NOT_FOUND = -714;  //No Data Found for $1'ServiceClass'
        public static final int NOT_LINKED_TO_UNLINK = -715;//A link doesn't exist for this Transaction Type and Transaction Code
        public static final int ALREADY_LINKED = -716;//Transaction Type and Transaction Code already Linked
        public static final int IS_LINKED = -717;//Can't Delete this Record since it's linked to transaction code
        public static final int TRANSACTION_TYPE_HAS_CHILD = -718;//Transaction Type has valid links and may not be deleted
        public static final int TRANSACTION_CODE_HAS_CHILD = -719;//Transaction Code has valid links and may not be deleted
        public static final int IMPORT_FAILED = -720;//Import service classes failed
        public static final int INVALID_FILE_TEMPLATE = -721; //Upload file failed because of Invalid file templated

        public static final int FILE_IS_EMPTY = -722; //  file is empty

        public static final int INVALID_FILE_STRUCTURE = -723;
        public static final int EXPORT_FILE_FAILED = -724;
        public static final int UNSUPPORTED_FILE_TYPE = -725;
        public static final int PARSING_FAILED = -726;
        public static final int DECRYPTION_FAILED = -727;
        public static final int ENCRYPTION_FAILED = -728;

        public static final int ALREADY_EXIST = -729;
        public static final int PAGE_IS_NOT_EXIST = -730;


    }

}
