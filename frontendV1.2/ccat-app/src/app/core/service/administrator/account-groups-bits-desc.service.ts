import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {AccountGroupBitModel} from 'src/app/shared/models/account-group-bit.model';
import {HttpService} from '../http.service';
import {indicate} from 'src/app/shared/rxjs/indicate';

@Injectable({
    providedIn: 'root',
})
export class AccountGroupsBitsDescService {
    allAccountGroupsBitsDescSubject = new BehaviorSubject<AccountGroupBitModel[]>([]);
    loading = new BehaviorSubject(false);
    constructor(private http: HttpService) {}

    public getAllAccountGroupsBitsDesc() {
        return this.http
            .request({
                path: '/ccat/account-groups-bits-description/get-all',
                payload: {
                    token: JSON.parse(sessionStorage.getItem('session')).token,
                },
            })
            .pipe(indicate(this.loading));
    }

    public UpdateAccountGroupsBitsDesc(accountGroupBit: AccountGroupBitModel): Observable<any> {
        return this.http
            .request({
                path: '/ccat/account-groups-bits-description/update',
                payload: {
                    token: JSON.parse(sessionStorage.getItem('session')).token,
                    accountGroupBit,
                },
            })
            .pipe(
                tap((res) => {
                    this.getAllAccountGroupsBitsDesc();
                }),
                indicate(this.loading)
            );
    }
}
