import {Injectable} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

import {BehaviorSubject, Subject} from 'rxjs';
import {switchMap, take, tap, withLatestFrom} from 'rxjs/operators';
import {Defines} from 'src/app/shared/constants/defines';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {CheckCallReasonService} from './customer-care/check-call-reason.service';
import {HttpService} from './http.service';
import {LockService} from './lock.service';

const FAKE_SUBSCRIBER = {id: '123', user: {username: 'Manar97', name: 'Manar'}};

const subscriberSearch$ = new Subject();

@Injectable({
    providedIn: 'root',
})
export class SubscriberService {
    loading$ = new BehaviorSubject(false);
    giftsCounter = new BehaviorSubject(0);
    giftOpened = new BehaviorSubject (true)
    sidebarOpened = new BehaviorSubject (true)
    // TODO: Adjust subscriber subject to hold the PAYLOAD not the Response.
    subscriberSubject = new BehaviorSubject(null);
    productSubject = new BehaviorSubject(null);
    trackingRouteState = new BehaviorSubject(null);

    // Save URL
    currentURL = '';
    localMsisdn = '';

    // permissions
    permissions = {
        callActivity: false,
    };
    private subscriberAccountApi = (msisdn) =>
        this.http
            .request({
                path: '/ccat/subscriber-account/get',
                payload: {
                    msisdn,
                },
            })
            .pipe(indicate(this.loading$),tap(res=>{
                console.log("reshh",res)
                if (res?.statusCode === 0) {
                    sessionStorage.setItem('msisdn', JSON.stringify(res.payload.subscriberNumber));
                    const visitedUrl = this.trackingRouteState.getValue();
                    console.log('save tracking call reason', visitedUrl);
                    if (!visitedUrl) {
                        if (this.router.url === '/find-subscriber') {
                            this.router.navigate(['customer-care/subscriber-admin']);
                        }
                    } else {
                        this.router.navigate([visitedUrl]);
                        this.trackingRouteState.next(null)
                    }
                } else {
                    // unlock wrong number
                    this.toastService.error('No Data found for this number');
                    this.lockService.deleteLock(this.localMsisdn);
                }
            }));

    private subscriberProductApi = (msisdn) =>
        this.http
            .request({
                path: '/ccat/subscriber-account/main-product/get',
                payload: {
                    msisdn,
                },
            })
            .pipe(indicate(this.loading$));

    private subscriberLoader = subscriberSearch$.pipe(
        switchMap(this.subscriberAccountApi),
        tap(
            (resp) => {
                this.subscriberSubject.next(resp.payload);
            },
            (err) => console.log(' subscriberLoader error ', err)
        )
    );

    private subscriberProductLoader = subscriberSearch$.pipe(
        switchMap(this.subscriberProductApi),
        tap(
            (resp) => this.productSubject.next(resp.payload),
            (err) => console.log(' subscriberProductLoader error ', err)
        )
    );

    private activationSub = this.subscriberLoader
        .pipe(
            withLatestFrom(this.subscriberProductLoader),
            tap((res) => {
                
            })
        )
        .subscribe({next: (resp) => console.log(`activation from shehab`, resp)});

    constructor(
        private router: Router,
        private http: HttpService,
        private lockService: LockService,
        private toastService: ToastService,
        private checkCallActivity: CheckCallReasonService,
        private featuresService: FeaturesService
    ) {
        this.setPermissions();
    }

    get subscriber$() {
        return this.subscriberSubject.asObservable();
    }
    get subscriberProduct$() {
        return this.productSubject.asObservable();
    }

    clearSubscriberReset(ignoreUnlock?) {
        let msisdn = sessionStorage.getItem('msisdn');
        if (msisdn && !ignoreUnlock) {
            this.lockService.deleteLock(JSON.parse(msisdn));
        }
        this.subscriberSubject.next(null);
        this.productSubject.next(null);
        sessionStorage.removeItem('msisdn');
        sessionStorage.setItem('route',"/find-subscriber");
        this.router.navigate(['find-subscriber']);
        
    }
    clearSubscribtionNoredirect(){
        this.subscriberSubject.next(null);
        this.productSubject.next(null);
        sessionStorage.removeItem('msisdn');
    }

    loadSubscriber(msisdn: string) {
        if (sessionStorage.getItem('msisdn')) {
            console.log("11111")
            subscriberSearch$.next(msisdn);
            this.localMsisdn = msisdn;
        } else {
            console.log("2222")
            this.lockService.lockAdminstration(msisdn).subscribe({
                next: (resp) => {
                    if (resp.statusCode == 0) {
                        subscriberSearch$.next(msisdn);
                        this.localMsisdn = msisdn;
                    }
                },
            });
        }
    }

    refresh() {
        /*const hasCallReason = this.checkCallActivity.checkCallReason();
        if(hasCallReason){
            this.router.navigate(['/customer-care/call-activity']);
        }
        else{
            this.clearSubscriberReset();
            this.router.navigate(['find-subscriber']);
        }*/
        let msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        this.loadSubscriber(msisdn)
    }

    private redirectTo(uri: string) {
        this.router.navigateByUrl('/proxy', {skipLocationChange: true}).then(() => this.router.navigate([uri]));
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(349, 'viewCallReasonMenu')
            .set(342, 'addCallReason')
            .set(343, 'updateCallReasonMenu')
            .set(345, 'checkCallReasonMenu');
        //342, 343, 345

        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        let viewCallReasonMenu = this.featuresService.getPermissionValue(349);
        let addCallReason = this.featuresService.getPermissionValue(342);
        let updateCallReasonMenu = this.featuresService.getPermissionValue(343);
        let checkCallReasonMenu = this.featuresService.getPermissionValue(345);
        if (viewCallReasonMenu && addCallReason && updateCallReasonMenu && checkCallReasonMenu) {
            this.permissions.callActivity = true;
        }
    }
}
