import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';
import {LockService} from '../lock.service';
import {SessionService} from '../session.service';
import {StorageService} from '../storage.service';
import {CheckCallReasonService} from './check-call-reason.service';

@Injectable({
    providedIn: 'root',
})
export class CallActivityService {
    loading$ = new BehaviorSubject(false);

    constructor(
        private httpService: HttpService,
        private router: Router,
        private sessionService: SessionService,
        private checkCallRason: CheckCallReasonService
    ) {}

    getCallActivities(activityType, parentId) {
        // open loading
        this.loading$.next(true);

        let reqData = {
            activityType,
            parentId,
        };

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/call-activities/get-all',
            payload: reqData,
        };

        // get api data
        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading$));
    }

    updateCallReason(formData) {
        // open loading
        this.loading$.next(true);
        // footprint

        let reqData = {
            ...formData,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Call Reason',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            },
        };

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/call-reason/update',
            payload: reqData,
        };

        // request api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe({
                next: (resp) => {
                    sessionStorage.removeItem('callReason');
                    if (sessionStorage.getItem('pageRediected') === 'logout') {
                        console.log(sessionStorage.getItem('pageRediected'))
                        sessionStorage.removeItem('pageRediected');
                        this.sessionService.cleanAllCachedData();
                    } else {
                        this.router.navigate(['/find-subscriber']);
                    }
                },
            });
    }

    addCallReason(msisdn) {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/call-reason/add',
            payload: {
                msisdn:JSON.parse(sessionStorage.getItem('msisdn')),
            },
        };

        // request api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (resp) => {
                    this.checkCallRason.setPermissions();
                    const permission =this.checkCallRason.permissions.callActivity
                    if(permission){
                        sessionStorage.setItem('callReason', JSON.stringify(resp?.payload?.callReason));
                    }
                    
                },
            });
    }
}
