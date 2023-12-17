import { HttpService } from 'src/app/core/service/http.service';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { ProfileRequest } from 'src/app/shared/models/ProfileRequest.interface';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { take } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class ProfileService {
    private token = JSON.parse(sessionStorage.getItem('session')).token;
    private loading = new BehaviorSubject(false);
    constructor(private http: HttpService) { }
    get loading$() {
        return this.loading;
    }
    getProfileByID(id: number) {
        return this.http.request({
            path: '/ccat/profile/get',
            payload: {
                token: this.token,
                profileId: id,
            },
        }).pipe(indicate(this.loading));
    }
    addProfile(profile: ProfileRequest) {
        return this.http
            .request({
                path: '/ccat/profile/add',
                payload: {
                    token: this.token,
                    profile: { ...profile },
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    deleteProfile(reqData) {
        return this.http
            .request({
                path: '/ccat/profile/delete',
                payload: {
                    ...reqData
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    updateProfile(profile: ProfileRequest) {
        return this.http
            .request({
                path: '/ccat/profile/update',
                payload: {
                    profile: { ...profile },
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    get allProfiles$() {
        return this.getProfileLookup('/ccat/profile/get-all');
    }
    get servicesClasses$() {
        return this.getProfileLookup('/ccat/admin/service-classes/get-all');
    }
    get menus$() {
        return this.getProfileLookup('/ccat/lookup/menus/get-all');
    }
    get features$() {
        return this.getProfileLookup('/ccat/lookup/features/get-all');
    }
    get monetaryLimit$() {
        return this.getProfileLookup('/ccat/lookup/monetary-limits/get-all');
    }
    getProfileLookup(path: string) {
        return this.http
            .request({
                path,
                payload: {
                    token: this.token,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
}
