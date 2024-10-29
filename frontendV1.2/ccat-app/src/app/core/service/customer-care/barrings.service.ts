import { Injectable } from "@angular/core";
import { MessageService } from "src/app/shared/services/message.service";
import { BehaviorSubject } from "rxjs";
import { take } from "rxjs/operators";
import { indicate } from "src/app/shared/rxjs/indicate";
import { ToastService } from "src/app/shared/services/toast.service";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";
import { SubscriberService } from "../subscriber.service";


@Injectable({
    providedIn: 'root',
})
export class BarringsService {
    loading$ = new BehaviorSubject(false);
    barringsReasoneSubject = new BehaviorSubject(null);
    private msisdn = JSON.parse(sessionStorage.getItem('msisdn'));

    constructor(private httpService: HttpService,
        private toastService: ToastService,
        private subscriberService: SubscriberService) { }

    barTempBlocked(formData) {
        // prepare request obj
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...formData,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Barrings',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                footPrintDetails: [
                    {
                        paramName: 'barringReason',
                        oldValue: '',
                        newValue: formData.barringReason
                    },
                    {
                        paramName: 'requestedBy',
                        oldValue: '',
                        newValue: formData.requestedBy
                    }
                ]
            }
        }
        let reqObj: ApiRequest = {
            path: '/ccat/barring/bar',
            payload: reqData
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success("Bared Successfully");
                        this.subscriberService.refresh();
                    }
                },
            });
    }
    unbarTempBlocked(formData) {
        // prepare request obj
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...formData,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Barrings',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                footPrintDetails: [
                    {
                        paramName: 'barringReason',
                        oldValue: '',
                        newValue: formData.barringReason
                    },
                    {
                        paramName: 'requestedBy',
                        oldValue: '',
                        newValue: formData.requestedBy
                    }
                ]
            }
        }
        let reqObj: ApiRequest = {
            path: '/ccat/barring/unbar',
            payload: reqData
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success(resp?.statusMessage);
                        this.subscriberService.refresh();
                    }
                },
            });
    }
    unbarRefillBarring(formData) {
        // prepare request obj
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...formData,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Barrings',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                footPrintDetails: [
                    {
                        paramName: 'barringReason',
                        oldValue: '',
                        newValue: formData.barringReason
                    },
                    {
                        paramName: 'requestedBy',
                        oldValue: '',
                        newValue: formData.requestedBy
                    }
                ]
            }
        }
        let reqObj: ApiRequest = {
            path: '/ccat/barring/unbar-refill-barring',
            payload: reqData
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success("Unbared Successfully");
                        this.subscriberService.refresh();
                    }
                },
            });
    }

    getBarringReason(TempBlocked) {
        // prepare request obj
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            isTempBlocked: TempBlocked
        }
        let reqObj: ApiRequest = {
            path: '/ccat/barring/reason/get',
            payload: reqData
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    this.barringsReasoneSubject.next(resp?.payload?.reason);
                    // close loading
                    this.loading$.next(false);
                },
            });
    }
    get allServiceClasses$() {
        return this.barringsReasoneSubject.asObservable();
    }
}