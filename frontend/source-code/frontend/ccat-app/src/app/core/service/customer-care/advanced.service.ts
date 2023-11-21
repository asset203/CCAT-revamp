import {Injectable, ViewChild} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {FindSubscriberComponent} from 'src/app/layout/pages/find-subscriber/find-subscriber.component';
import {ServiceClassService} from '../customer-care/service-class.service';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';
import {ToastrService} from 'ngx-toastr';
import {MessageService} from 'src/app/shared/services/message.service';

@Injectable({
    providedIn: 'root',
})
export class AdvancedService {
    @ViewChild(FindSubscriberComponent) findSubscriber: FindSubscriberComponent;

    allCommunityGroupsSubject = new BehaviorSubject(null);
    allAccountGroupSubject = new BehaviorSubject(null);
    allServiceOfferingSubject = new BehaviorSubject(null);
    allDisconnectionReasonsSubject = new BehaviorSubject(null);
    loading$ = new BehaviorSubject(false);

    constructor(
        private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService
    ) {}

    getAllCommunityGroups() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/communities/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    this.allCommunityGroupsSubject.next(resp?.payload?.communities);
                },
            });
    }
    get allCommunityGroups$() {
        return this.allCommunityGroupsSubject.asObservable();
    }
    getAllAccountGroups() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/account-groups/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    this.allAccountGroupSubject.next(resp?.payload?.accountGroups);
                },
            });
    }
    get allAccountGroups$() {
        return this.allAccountGroupSubject.asObservable();
    }
    getAllServiceOffering() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/service-offering/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    this.allServiceOfferingSubject.next(resp?.payload?.serviceOffering);
                },
            });
    }
    get ServiceOffering$() {
        return this.allServiceOfferingSubject.asObservable();
    }
    getAllDisconnectReasons() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/disconnection-code/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    this.allDisconnectionReasonsSubject.next(resp?.payload?.disconnectionCodes);
                },
            });
    }
    get DisconnectionReasons$() {
        return this.allDisconnectionReasonsSubject.asObservable();
    }
    submitSubscriber(formData) {
        let reqData = formData;

        let reqObj: ApiRequest = {
            path: '/ccat/advanced/add',
            payload: reqData,
        };

        this.httpService
            .request(reqObj)
            .pipe(indicate(this.loading$), take(1))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success(this.messageService.getMessage(48).message);
                        this.subscriberService.loadSubscriber(reqData?.subscriberMsisdn);
                        
                    }
                },
            });
    }
    submitDisconnect(formData) {
        let reqData = formData;

        let reqObj: ApiRequest = {
            path: '/ccat/advanced/delete',
            payload: reqData,
        };

        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    if (resp?.body?.statusCode === 0) {
                        this.toastService.success(this.messageService.getMessage(49).message);
                    }
                },
            });
    }
}
