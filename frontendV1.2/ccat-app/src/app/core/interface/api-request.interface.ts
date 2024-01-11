import { HttpParams } from "@angular/common/http";

export interface ApiRequest {
    path: string,
    method?: string,
    payload?: any,
    headers?: Map<string, string>,
    params?: HttpParams,
    responseType ?: "arraybuffer" | "blob" | "text" | "json"
}