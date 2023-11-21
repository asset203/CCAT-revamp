import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { take } from "rxjs/operators";
import { indicate } from "src/app/shared/rxjs/indicate";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";
import { SubscriberService } from "../subscriber.service";

@Injectable({
    providedIn: 'root',
})
export class AccountHistoryService {

    loading$ = new BehaviorSubject(false);
    allAccountHistorySubject = new BehaviorSubject(null);

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService) { }

    getAllAccountHistory(formData) {
        console.log(this.subscriberService?.subscriberSubject?.value)
        let reqData = formData;
        reqData.msisdn = JSON.parse(sessionStorage.getItem('msisdn'));

        let reqObj: ApiRequest = {
            path: '/ccat/account-history/get-all',
            payload: reqData

        };

        return this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$))
    }

    getAllAccountHistoryColumns() {
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/activities-headers/get-all',
        };

        return this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$))
    }
    getFilteredAccountHistory(formData) {
        let reqData = formData;
        reqData.msisdn = JSON.parse(sessionStorage.getItem('msisdn'));

        let reqObj: ApiRequest = {
            path: '/ccat/account-history/search',
            payload: reqData

        };

        return this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$))
    }
}