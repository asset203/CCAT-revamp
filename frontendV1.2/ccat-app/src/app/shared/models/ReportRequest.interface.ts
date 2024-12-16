export interface ReportRequest {
    pagination: {
        offset: number;
        fetchCount: number;
        queryString?: string;
        sortedBy?: string;
        order?: number;
        isGetAll: boolean;
    };
    dateFrom: number;
    dateTo: number;
    flag?:number;
}
export interface FlagReportRequest{
    pagination: {
        offset: number;
        fetchCount: number;
        queryString?: string;
        sortedBy?: string;
        order?: number;
        isGetAll: boolean;
    };
    dateFrom: number;
    dateTo: number;
    flag : number
}
export interface ContractBillRequest{
    pagination: {
        offset: number;
        fetchCount: number;
        queryString?: string;
        sortedBy?: string;
        order?: number;
        isGetAll: boolean;
    };
    numOfBill : number;
    reportType : number;
}
