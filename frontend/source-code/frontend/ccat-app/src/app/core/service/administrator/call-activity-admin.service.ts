import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {CallActivityAdmin} from 'src/app/shared/models/call-activity-admin.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {environment} from 'src/environments/environment';
import { AppConfigService } from '../app-config.service';
import {HttpService} from '../http.service';
@Injectable({
    providedIn: 'root',
})
export class CallActivityAdminService {
    constructor(
        private httpService: HttpService,
        private http: HttpClient,
        private appConfigsService: AppConfigService
    ) {}
    loading$ = new BehaviorSubject(false);
    getCallActivities(activityType: number, parentId: number) {
        return this.httpService
            .request({
                path: '/ccat/call-activity-admin/get-all',
                payload: {
                    activityType,
                    parentId,
                },
            })
            .pipe(indicate(this.loading$));
    }
    addActivity(reqData) {
        return this.httpService
            .request({
                path: '/ccat/call-activity-admin/add',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading$));
    }
    updateActivity(reqData) {
        return this.httpService
            .request({
                path: '/ccat/call-activity-admin/update',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading$));
    }
    deleteActivity(reqData) {
        return this.httpService
            .request({
                path: '/ccat/call-activity-admin/delete',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading$));
    }
    downloadCallActivity() {
        let headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json');
        return this.http
            .post(
                `${this.appConfigsService.config.apiBaseUrl}/ccat/call-activity-admin/download`,
                {},
                {
                    headers: headers,
                    responseType: 'blob',
                }
            )
            .pipe(indicate(this.loading$));
    }
}
