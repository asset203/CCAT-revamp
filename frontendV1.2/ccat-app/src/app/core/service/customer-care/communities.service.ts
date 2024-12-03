import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class CommunitiesService {
    msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
    loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}

    get allCommunities$() {
        let reqData = {
            msisdn: this.msisdn,
        };
        return this.httpService
            .request({
                path: '/ccat/communities/get',
                payload: reqData,
            })
            .pipe(take(1), indicate(this.loading));
    }

    updateCommunity(currentList, newList) {
        let footprintDetails = [];
        newList.forEach((el) => {
            footprintDetails.push({
                paramName: 'communityDescription',
                oldValue: null,
                newValue: el.communityDescription,
            });
        });
        let reqData = {
            msisdn: this.msisdn,
            currentList: currentList,
            newList: newList,
            footprint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Communities',
                footPrintDetails: [...footprintDetails],
            },
        };
        return this.httpService
            .request({
                path: '/ccat/communities/update',
                payload: {
                    ...reqData,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
}
