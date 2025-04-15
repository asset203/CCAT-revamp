import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BehaviorSubject } from "rxjs";
import { take } from "rxjs/operators";
import { indicate } from "src/app/shared/rxjs/indicate";
import { MessageService } from "src/app/shared/services/message.service";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";

import { SubscriberService } from "../subscriber.service";

@Injectable({
    providedIn: 'root',
})
export class FlexShareService {

    loading$ = new BehaviorSubject(false);
    private msisdn = JSON.parse(sessionStorage.getItem('msisdn'));

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService) {
    }

    getFlexShareEligibility(formData) {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/flex-share/eligibility/get',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                ...formData
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
    getFlexShareState() {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),

        }
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/flex-share/states/get?type=1',
            payload: reqData
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }

    getFlexShareHistory(formData) {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...formData
        }
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/flex-share/history/get',
            payload: reqData
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }

    updateFlexShareState(data) {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...data
        }
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/flex-share/states/update',
            payload: { ...reqData }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: () => { }
            })
    }

}