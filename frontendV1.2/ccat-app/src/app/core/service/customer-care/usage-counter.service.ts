import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { MessageService } from "src/app/shared/services/message.service";
import { ToastService } from 'src/app/shared/services/toast.service';
import { ApiRequest } from '../../interface/api-request.interface';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class UsageCounterService {
    private token = JSON.parse(sessionStorage.getItem('session')).token;
    private loading = new BehaviorSubject(false);

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastService,
        private messageService: MessageService) { }
    get loading$() {
        return this.loading;
    }
    getUsageCountersList(msisdn: string) {
        return this.httpService
            .request({
                path: '/ccat/usage-counters/get-all',
                payload: {
                    token: this.token,
                    msisdn,
                },
            })
            .pipe(
                indicate(this.loading),
                map((res) => {
                    return res.payload.usageCountersList;
                })
            );
    }
    addUsageCounter(reqBody: any) {
        return this.updateOrAddUsageCounter(reqBody, '/ccat/usage-counters/add', 'addUsage');
    }
    updateUsageCounter(reqBody: any) {
        return this.updateOrAddUsageCounter(reqBody, '/ccat/usage-counters/update', 'updateUsage');
    }
    private updateOrAddUsageCounter(reqBody: any, path: string, type: string) {
        const reqObj = {
            path,
            payload: {
                ...reqBody
            },
        };
        return this.httpService.request(reqObj).pipe(indicate(this.loading), take(1));
    }

    addUsageThreshold(reqBody) {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...reqBody
        }
        let reqObj: ApiRequest = {
            path: '/ccat/usage-counters/usage-thresholds/add',
            payload: reqData

        };

        this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$)).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success('', this.messageService.getMessage(60).message)
                                this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                            }
                        }
                    }
                )
    }
    deleteUsageThreshold(ids, counter) {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            usageThresholdsIds: ids,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Usage Counters new',
                footPrintDetails: [
                    {
                        paramName: "Usage Type",
                        oldValue: null,
                        newValue: counter.monetaryValue1 === null ? 'counter value' : 'monetary value'
                    },
                    {
                        paramName: "ID",
                        oldValue: null,
                        newValue: counter.id
                    }
                ]
            }

        }

        let reqObj: ApiRequest = {
            path: '/ccat/usage-counters/usage-thresholds/delete',
            payload: reqData

        };

        this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$)).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success('', this.messageService.getMessage(61).message)
                                this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))

                            }
                        }
                    }
                )
    }

    updateThreshold(reqBody) {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            ...reqBody
        }



        let reqObj: ApiRequest = {
            path: '/ccat/usage-counters/usage-thresholds/update',
            payload: reqData

        };

        this.httpService.request(reqObj)
            .pipe(
                take(1), indicate(this.loading$)).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success('', this.messageService.getMessage(62).message)
                                this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                            }
                        }
                    }
                )
    }
}

