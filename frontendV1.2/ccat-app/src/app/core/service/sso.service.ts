import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take, tap} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {ApiRequest} from '../interface/api-request.interface';
import {HttpService} from './http.service';

@Injectable({
    providedIn: 'root',
})
export class SSOService {
    loading$ = new BehaviorSubject(false);

    constructor(private httpService: HttpService, private validationService: ValidationService) {}

    getSSOConfig() {
        let reqObj: ApiRequest = {
            path: '/ccat/configurations/sso',
            method: 'get',
        };

        return this.httpService.request(reqObj).pipe(
            take(1),
            indicate(this.loading$),
            tap((res) => {
                const msisdnPattern = res.payload.msisdnPattern;
                const passwordPattern = res.payload.passwordPattern;
                this.validationService.setRegex(msisdnPattern, passwordPattern);
                this.setRegaxLocally(msisdnPattern, passwordPattern);
                this.setReportSearchPeriod(res.payload.reportDefaultSearchPeriod, res.payload.reportMaxSearchPeriod);
                this.setReportAccountHistorySearchPeriod(res.payload.accountHistorySearchPeriod, res.payload.accountHistoryMaxSearchPeriod);
                this.setNBAInterfaceSelection(res.payload.nbaInterfaceSelector);
            })
        );
    }
    setRegaxLocally(msisdnPattern, passwordPattern) {
        sessionStorage.setItem('msisdnPattern', msisdnPattern);
        sessionStorage.setItem('passwordPattern', passwordPattern);
    }
    setReportSearchPeriod(reportDefaultSearchPeriod, reportMaxSearchPeriod) {
        sessionStorage.setItem('reportDefaultSearchPeriod', reportDefaultSearchPeriod);
        sessionStorage.setItem('reportMaxSearchPeriod', reportMaxSearchPeriod);
    }
    setReportAccountHistorySearchPeriod(accountHistorySearchPeriod, accountHistoryMaxSearchPeriod) {
        sessionStorage.setItem('accountHistoryMaxSearchPeriod', accountHistoryMaxSearchPeriod);
        sessionStorage.setItem('accountHistorySearchPeriod', accountHistorySearchPeriod);
    }
    setNBAInterfaceSelection(nbaInterfaceSelector: any) {
        sessionStorage.setItem('nbaInterfaceSelector', nbaInterfaceSelector);
    }
}
