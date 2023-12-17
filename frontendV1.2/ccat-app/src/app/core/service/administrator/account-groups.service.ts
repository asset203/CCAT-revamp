import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { AccountGroup } from 'src/app/shared/models/AccountGroup.interface';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ToastService } from 'src/app/shared/services/toast.service';
import { HttpService } from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class AccountGroupsService {
    loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) { }
    get allAccountGroups$() {
        return this.httpService
            .request({
                path: '/ccat/account-groups-admin/get-all',
            })
            .pipe(indicate(this.loading));
    }
    addAccountGroup(addedAccountGroup: AccountGroup, currentAccountGroups: AccountGroup[]) {


        return this.httpService
            .request({
                path: '/ccat/account-groups-admin/add',
                payload: {
                    addedAccountGroup,
                },
            })
            .pipe(indicate(this.loading));
    }
    updateAccountGroup(reqData, currentAccountGroups: AccountGroup[]) {

        return this.httpService
            .request({
                path: '/ccat/account-groups-admin/update',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading));
    }
    deleteAccountGroup(reqData) {
        return this.httpService
            .request({
                path: '/ccat/account-groups-admin/delete',
                payload: {
                    ...reqData,
                },
            })
            .pipe(indicate(this.loading));
    }


}
