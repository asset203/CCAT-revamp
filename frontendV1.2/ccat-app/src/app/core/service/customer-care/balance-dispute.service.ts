import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {AppConfigService} from '../app-config.service';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class BalanceDisputeService {
    loading$ = new BehaviorSubject(false);
    constructor(
        private httpService: HttpService,
        private appConfigsService: AppConfigService,
        private http: HttpClient
    ) {}
    getBalance(reportQuery: any) {
        return this.httpService
            .request({
                path: '/ccat/balance-dispute/get',
                payload: {
                    ...reportQuery,
                },
            })
            .pipe(indicate(this.loading$));
    }
    exportDailyBalance() {
        const url = '/ccat/balance-dispute/get/today-data-usage';
        return this.exportSheet(url);
    }
    exportBalanceSheet(filterObj) {
        const url = '/ccat/balance-dispute/export';
        return this.exportSheet(url,filterObj);
    }
    balanceDisputeCheck(){
        const testObj = {
            "msisdn": "1066710216",
            "actionType": 0,
            "date1": 1676470884629,
            "date2": 1676470884629,
            "fetchCount": 100,
            "offset": 10,
            "order": 1,
            "isGetAll": true,
            "sortedBy": "",
            "queryString": ""
        }
        return this.httpService
            .request({
                path: '/ccat/balance-dispute/check',
                payload: {
                    ...testObj,
                },
            })
            .pipe(indicate(this.loading$));
    }
    private exportSheet(url,filterObj ?:any) {
        let headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json');
        return this.http
            .post(
                `${this.appConfigsService.config.apiBaseUrl}${url}`,
                {
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    ...(filterObj && {...filterObj}), 
                },
                {
                    headers: headers,
                    responseType: 'blob',
                }
            )
            .pipe(indicate(this.loading$))
            .toPromise();
    }
}
