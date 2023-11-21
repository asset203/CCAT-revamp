import { footprintDetails } from "./footprint-details";

export interface footprintReport {
    actionName?: string,
    actionTime?: string,
    actionType?: string,
    errorCode?: string,
    errorMessage?: string,
    footprintDetails?: footprintDetails[],
    id?: number,
    machineName?: string,
    msisdn?: string,
    pageName?: string,
    profileName?: string,
    requestId?: string,
    sessionId?: string,
    status?: string,
    tabName?: string,
    userName?: string
}