import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take, tap} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Injectable({
    providedIn: 'root',
})
export class FamilyAndFriendsService {
    loading$ = new BehaviorSubject(false);
    private msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
    allFAFPlansSubject$ = new BehaviorSubject(null);
    FAFPlansLookupSubject$ = new BehaviorSubject(null);
    isFetchingList$ = this.loadingService.fetching$;

    constructor(
        private httpService: HttpService,
        private messageService: MessageService,
        private toastService: ToastService,
        private subscriberService: SubscriberService,
        private loadingService : LoadingService
    ) {}

    getFAFPlansLookup() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/faf-plans/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe(
                (resp) => {
                     this.FAFPlansLookupSubject$.next(resp?.payload?.fafPlans);
                   
                
                    
                
            },err=>{
                    
            });
    }
    getFAFPlans() {
        this.loadingService.startFetchingList()
        // prepare request obj
        let reqData = {
            msisdn: this.msisdn,
        };
        let reqObj: ApiRequest = {
            path: '/ccat/family-and-friends/get-all',
            payload: reqData,
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe((resp) => {
                    this.allFAFPlansSubject$.next(resp?.payload?.familyAndFriendsList);
                    this.loadingService.endFetchingList()
                },err=>{
                    this.allFAFPlansSubject$.next([]);
                    this.loadingService.endFetchingList()
                }
            );
    }
    addFAFPlan(formData) {
        // prepare request obj
        let reqData = {
            msisdn: this.msisdn,
            ...formData,
        };
        let reqObj: ApiRequest = {
            path: '/ccat/family-and-friends/add',
            payload: reqData,
        };
        // get api data
        return this.httpService.request(reqObj).pipe(
            take(1),
            indicate(this.loading$),
            tap((resp) => {
                if (resp?.statusCode === 0) {
                    this.allFAFPlansSubject$.next(resp?.payload?.familyAndFriendsList);
                    this.subscriberService.refresh();
                }
            })
        );
    }
    deleteFAFPlan(plan) {
        // prepare request obj
        let reqData = {
            msisdn: this.msisdn,
            ...plan,
        };
        let reqObj: ApiRequest = {
            path: '/ccat/family-and-friends/delete',
            payload: reqData,
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success(resp?.statusMessage);
                        this.allFAFPlansSubject$.next(resp?.payload?.familyAndFriendsList);
                        this.subscriberService.refresh();
                    }
                },
                error: (err) => {},
            });
    }
    updateFAFPlan(plan) {
        let reqData = {
            msisdn: this.msisdn,
            ...plan,
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/family-and-friends/update',
            payload: reqData,
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toastService.success("Faf Updated Successfully");
                        this.allFAFPlansSubject$.next(resp?.payload?.familyAndFriendsList);
                        this.subscriberService.refresh();
                    }
                },
            });
    }
}
