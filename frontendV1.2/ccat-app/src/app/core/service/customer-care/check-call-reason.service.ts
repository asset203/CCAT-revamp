import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {BehaviorSubject, of, Subject} from 'rxjs';
import {take} from 'rxjs/operators';
import {Defines} from 'src/app/shared/constants/defines';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class CheckCallReasonService {
    loading$ = new BehaviorSubject(false);
    action = new BehaviorSubject(0);
    constructor(
        private httpService: HttpService,
        private featuresService: FeaturesService,
        private toastService: ToastService,
    ) {
        this.setPermissions();
    }
    permissions = {
        callActivity: false,
    };
    checkCallReason(page?: string) {
        // prepare request obj
        let result: boolean = false;
        this.setPermissions();
        console.log('permissiion', this.permissions.callActivity);
        if (this.permissions.callActivity) {
            if (!sessionStorage.getItem('callReason')) {
                
                this.checkCallReasonApi().subscribe((resp) => {
                    if (resp.payload && resp.payload.userId && resp.payload.msisdn) {
                        sessionStorage.setItem('callReason', JSON.stringify(resp.payload));
                        if(JSON.parse(sessionStorage.getItem('session')).token){
                            this.toastService.warning('Please add call reason');
                        }
                        sessionStorage.setItem('pageRediected', page ? 'logout' : '');
                        result = true;
                        
                    }
                });
            } else {
                sessionStorage.setItem('pageRediected', page ? 'logout' : '');
                result = true;
            }
        }
        return result;

    }
    checkCallReasonApi() {
        let reqObj: ApiRequest = {
            path: '/ccat/call-reason/check',
            payload: {
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Call Reason',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                },
            },
        };
        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading$));
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
        console.log(viewCallReasonMenu && addCallReason && updateCallReasonMenu && checkCallReasonMenu);
        if (viewCallReasonMenu && addCallReason && updateCallReasonMenu && checkCallReasonMenu) {
            this.permissions.callActivity = true;
        } else {
            this.permissions.callActivity = false;
        }
    }
}
