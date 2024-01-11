export interface FailedUploadError{
    failedMsisdns ?: [{
        errorCode : number,
        errorMessage : string,
        msisdn : string
    }];
    numberOfFailedMsisdns ?: number;
    numberOfSucessMsisdns?: number;
    totalNumberOfMsisdns?: number;
}