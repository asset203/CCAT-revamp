import {Injectable} from '@angular/core';
import {HttpService} from './http.service';
import {filter, take, tap} from 'rxjs/operators';
import {BehaviorSubject} from 'rxjs';
import {NavigationEnd, Router} from '@angular/router';
import {ApiRequest} from '../interface/api-request.interface';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {StorageService} from './storage.service';
import {LockService} from './lock.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {CallActivityService} from './customer-care/call-activity.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from './foot-print.service';
import {CheckCallReasonService} from './customer-care/check-call-reason.service';
import {Defines} from 'src/app/shared/constants/defines';

const FAKE_SESSION: any = {token: '1234jdjlf'};

const LOGGED_OUT = {token: null, user: null, userProfile: null};

@Injectable({
    providedIn: 'root',
})
export class SessionService {
    sessionSubject = new BehaviorSubject(LOGGED_OUT);
    loading = new BehaviorSubject(false);
    allMarquessSubject = new BehaviorSubject(null);
    constructor(
        private httpService: HttpService,
        private router: Router,
        private storageService: StorageService,
        private lockService: LockService,
        private toastService: ToastService,
        private checkCallActivity: CheckCallReasonService,
        private footPrintService: FootPrintService
    ) {
        // Check localstorage for session data.
        // retrieve session from localstorage.
        // load session data into sessionSubject.
        // navigate, app.
        if (this.storageService.getItem('session')) {
            // const sessionData = localStorage.getItem('session');
            // const sessionObject = JSON.parse(sessionData);
            const sessionObject = this.storageService.getItem('session');
            this.sessionSubject.next(sessionObject);
            let currentUrl = window.location.pathname;
            if (currentUrl === '/login') {
                this.router.navigate(['find-subscriber']);
            } else {
                this.router.navigate([currentUrl]);
            }
        }

        this.router.events
            .pipe(
                filter((event) => event instanceof NavigationEnd),
                // tap((e) => console.log(`router event`, e)),
                tap((e: NavigationEnd) => {
                    // if (e.url === '/login' && JSON.parse(localStorage.getItem('session'))) {
                    if (e.url === '/login' && this.storageService.getItem('session')) {
                        // console.log(`redirecting`);
                        this.router.navigate(['find-subscriber']);
                    }
                })
            )
            .subscribe();
    }

    get session$() {
        return this.sessionSubject.asObservable();
    }

    get loading$() {
        return this.loading.asObservable();
    }

    login(reqData) {
        console.log(reqData);
        let reqObj: ApiRequest = {
            path: '/ccat/login',
            payload: {
                ...reqData,
                footprintModel: {
                    machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: null,
                    pageName: 'Login',
                    msisdn: null,
                    footPrintDetails: [
                        {
                            paramName: 'username',
                            oldValue: null,
                            newValue: '',
                        },
                    ],
                },
            },
        };

        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading))
            .subscribe({
                next: (resp) => {
                    if (resp.statusCode === 0) {
                        this.checkCallActivity.setPermissions();
                        this.storageService.changeFlag(reqData.staylogged);
                        this.storageService.setItem('session', resp.payload);
                        this.sessionSubject.next(resp.payload);
                        this.router.navigate(['/find-subscriber']);
                        this.logfootPrint();
                    }
                },
                complete: () => {},
            });
    }

    async logout() {
        /*
        if (this.permissions.callActivity) {
            console.log('Call reason ');
            if (!sessionStorage.getItem('callReason')) {
                sessionStorage.setItem('pageRediected', 'logout');
                this.checkCallActivity.checkCallReason().subscribe({
                    next: (resp) => {
                        if (resp.payload && resp.payload.userId && resp.payload.msisdn) {
                            sessionStorage.setItem('callReason', JSON.stringify(resp.payload));
                            this.toastService.warning('Please add call reason');
                            this.router.navigate(['/customer-care/call-activity']);
                        } else {
                            this.cleanAllCachedData();
                        }
                    },
                });
            } else {
                console.log('there is a Call reason in session ', sessionStorage.getItem('callReason'));
                this.toastService.warning('Please add call reason');
                sessionStorage.setItem('pageRediected', 'logout');
                this.router.navigate(['/customer-care/call-activity']);
                
            }
        } else {
            this.cleanAllCachedData();
        }*/
        
        const hasCallReason = this.checkCallActivity.checkCallReason('logout');
        if(hasCallReason){
            this.router.navigate(['/customer-care/call-activity']);
        }
        else{
            this.cleanAllCachedData();  
        }
        /*this.checkCallActivity.action.pipe(take(1)).subscribe((action) => {
            console.log(action);
            if (
                action === Defines.callReasonActions.CALL_ACTIVITY_HAS_NO_PERMISSION ||
                action === Defines.callReasonActions.CALL_ACTIVITY_CHECKED_HAS_NO_REASON
            ) {
                this.cleanAllCachedData();
            } else if (action === Defines.callReasonActions.CALL_ACTIVITY_HAS_NO_LOCAL_REASON) {
                this.toastService.warning('Please add call reason');
                sessionStorage.setItem('pageRediected', 'logout');
                this.router.navigate(['/customer-care/call-activity']);
            }
        });*/
    }

    getAllMarquees() {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/marquees/get-all',
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (resp) => {
                    this.allMarquessSubject.next(resp?.payload?.marquees);
                },
            });
    }
    get allMarquess$() {
        return this.allMarquessSubject.asObservable();
    }

    cleanAllCachedData() {
        let msisdn = sessionStorage.getItem('msisdn');
        if (msisdn) {
            this.lockService.deleteLock(JSON.parse(msisdn));
        }
        this.loading.next(true);
        this.loading.next(false);
        this.sessionSubject.next(LOGGED_OUT);
        this.storageService.clearItem('session');
        this.storageService.clearItem('msisdn');
        this.router.navigate(['/login']);

    }

    logfootPrint() {
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: null,
            pageName: 'Login',
            msisdn: null,
        };
        this.footPrintService.log(footprintObj);
    }
    /*setSessionTimeOut(tokenExpiryDate){
        const sessionTimeOut=tokenExpiryDate-new Date().getTime()
        console.log("sessionTimeOut",tokenExpiryDate-new Date().getTime() )
        this.sessionTimeOutTimer=setTimeout(() => {
            console.log("logout ....")
            this.logout();
        }, sessionTimeOut);
    }
    clearTimout(){
        clearTimeout(this.sessionTimeOutTimer);
    }*/
}
