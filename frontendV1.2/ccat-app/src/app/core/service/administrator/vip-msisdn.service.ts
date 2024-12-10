import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {HttpService} from '../http.service';
import {indicate} from 'src/app/shared/rxjs/indicate';

@Injectable({
    providedIn: 'root',
})
export class VipMsisdnService {
    loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}
    get allVipMsisdn$() {
        return this.httpService
            .request({
                path: '/ccat/vip/get-all',
            })
            .pipe(indicate(this.loading));
    }
    addVipNumber(data) {
        return this.httpService
            .request({
                path: '/ccat/vip/msisdn/add',
                payload: {
                    ...data,
                },
            })
            .pipe(indicate(this.loading));
    }

    deleteVipNumber(reqData) {
        return this.httpService
            .request({
                path: '/ccat/vip/msisdn/delete',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading));
    }

    updateVipPages(newList) {
        let reqData = {
            menuIds: newList,
        };
        return this.httpService
            .request({
                path: '/ccat/vip/page/update',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading));
    }
}
