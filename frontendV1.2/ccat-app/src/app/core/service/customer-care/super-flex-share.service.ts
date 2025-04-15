import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BehaviorSubject } from "rxjs";
import { take, tap } from "rxjs/operators";
import { indicate } from "src/app/shared/rxjs/indicate";
import { MessageService } from "src/app/shared/services/message.service";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";
import { SubscriberService } from "../subscriber.service";

@Injectable({
    providedIn: 'root',
})
export class SuperFlexShareService {

    loading$ = new BehaviorSubject(false);
    private msisdn = JSON.parse(sessionStorage.getItem('msisdn'));

    constructor(private httpService: HttpService) {
    }
    getAddOns() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/addons/get',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),

            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
    activateAddOn(id) {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/addons/activate',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                packageId: id
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
    deactivateAddOn() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/addons/deactivate',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
    getMIThresholds() {
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/thresholds/get',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),

            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
    activateThreshold(oldAmount, newAmount) {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/thresholds/activate',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                thresholdAmount: newAmount,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Super Flex View',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    footPrintDetails: [
                        {
                            paramName: 'Flex Threshold',
                            oldValue: oldAmount,
                            newValue: newAmount,
                        }
                    ],
                },
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
    deactivateThreshold() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/thresholds/deactivate',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
    stopMI() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/stop-mi',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
    deactivateStopMI() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/super-flex/stop-mi/deactivate',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
            }
        };
        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe({
                next: (resp) => { }
            })
    }
}