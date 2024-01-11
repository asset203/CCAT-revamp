export interface TransactionType {
    id?: number;
    name?: string;
    value?:string;
    ccFeatures ?: number[];
}
export interface TransactionCode {
    id?: number;
    name?: string;
    value?: string;
    description?: string;
}
