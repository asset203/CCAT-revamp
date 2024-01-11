import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { take } from 'rxjs/operators';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ApiRequest } from '../interface/api-request.interface';
import { AppConfigService } from './app-config.service';
import { HttpService } from './http.service';
import { SubscriberService } from './subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class FootPrintService {
    loading$ = new BehaviorSubject(false)
    constructor(private httpService: HttpService,
        private http : HttpClient , private appConfigService : AppConfigService) { }

    log(reqData) {
        let reqObj: ApiRequest = {
            path: '/ccat/footprint/log',
            payload: {
                footprintModel: { ...reqData }
            }
        };

        this.httpService.request(reqObj)
            .pipe(
                take(1)).subscribe(
                    {
                        next: (resp) => {
                            console.log(resp)
                        }
                    })
    }
    get(reqData) {
        let reqObj: ApiRequest = {
            path: '/ccat/admin-footprint-report/get',
            payload: {
                // msisdn: '1099900306',
                searchFootPrintReport: { ...reqData }
            }
        };

        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
    export(reqData){
        let requestBody={
            searchFootPrintReport : reqData
        }
        let headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json');
        return this.http.post(
            `${this.appConfigService.config.apiBaseUrl}/ccat/admin-footprint-report/export`,
            {...requestBody},
            {
                headers: headers,
                responseType: 'blob',
            }
        ).pipe(indicate(this.loading$));
    }
}