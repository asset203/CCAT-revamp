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

        public final static int SUCCESS = 0;
    }

    public static class ERROR {

        public final static int TOKEN_EXPIRED = -701;
        public final static int INVALID_PARAMETER = -702;
        public final static int DATABASE_ERROR = -703;
        public final static int GENERAL_ERROR = -704;
        public final static int NO_DATA_FOUND = -705;
        public final static int HAS_CHILD = -706;       //Can't Delete this Record since a Child Found
        public final static int EMPTY_ERSPONSE = -707;
        public final static int SERVICE_NOT_FOUND = -708;
        public final static int DUPLICATED_DATA = -709;
        public final static int NOT_DELETED = -710;     //Can't Delete this Record.
        public final static int ADD_FAILED = -711;
        public final static int UPDATE_FAILED = -712;
        public final static int DELETE_FAILED = -713;

        public final static int DATA_NOT_FOUND = -714;  //No Data Found for $1'ServiceClass'
        public final static int NOT_LINKED_TO_UNLINK = -715;//A link doesn't exist for this Transaction Type and Transaction Code
        public final static int ALREADY_LINKED = -716;//Transaction Type and Transaction Code already Linked
        public final static int IS_LINKED = -717;//Can't Delete this Record since it's linked to transaction code
        public final static int TRANSACTION_TYPE_HAS_CHILD = -718;//Transaction Type has valid links and may not be deleted
        public final static int TRANSACTION_CODE_HAS_CHILD = -719;//Transaction Code has valid links and may not be deleted
        public final static int IMPORT_FAILED = -720;//Import service classes failed
        public final static int INVALID_FILE_TEMPLATE = -721; //Upload file failed because of Invalid file templated

        public final static int FILE_IS_EMPTY = -722; //  file is empty

        public final static int INVALID_FILE_STRUCTURE = -723;
        public final static int EXPORT_FILE_FAILED = -724;
        public final static int UNSUPPORTED_FILE_TYPE = -725;
        public final static int PARSING_FAILED = -726;
        public final static int DECRYPTION_FAILED = -727;
        public final static int ENCRYPTION_FAILED = -728;

        public final static int ALREADY_EXIST = -729;

    }

}
