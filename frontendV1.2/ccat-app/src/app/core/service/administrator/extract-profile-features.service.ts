import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {environment} from 'src/environments/environment';
import {AppConfigService} from '../app-config.service';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class ExtractProfileFeaturesService {
    loading$ = new BehaviorSubject(false);
    constructor(
        private httpService: HttpService,
        private http: HttpClient,
        private appConfigsService: AppConfigService
    ) {}
    getAllProfileFeatures() {
        return this.httpService
            .request({
                path: '/ccat/extract-profile-features/get-all',
            })
            .pipe(indicate(this.loading$));
    }
    getAllProfileUsers(profileId: number) {
        return this.httpService
            .request({
                path: '/ccat/extract-profile-features/users/get-all',
                payload: {profileId},
            })
            .pipe(indicate(this.loading$));
    }
    downloadProfileUsers() {
        let headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json');
        return this.http
            .post(
                `${this.appConfigsService.config.apiBaseUrl}/ccat/extract-profile-features/users-profile/export`,
                {},
                {
                    headers: headers,
                    responseType: 'blob',
                }
            )
            .pipe(indicate(this.loading$));
    }
}
