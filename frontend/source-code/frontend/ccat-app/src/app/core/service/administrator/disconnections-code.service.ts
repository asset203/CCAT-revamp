import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {DisconnectionCode} from 'src/app/shared/models/disconnection-code.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class DisconnectionCodesService {
    private token = JSON.parse(sessionStorage.getItem('session')).token;
    private loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}
    get loading$() {
        return this.loading;
    }
    getAllDisconnections() {
        return this.httpService
            .request({
                path: '/ccat/disconnection-code/get-all',
                payload: {
                    token: this.token,
                },
            })
            .pipe(
                indicate(this.loading),
                take(1),
                map((res) => res?.payload?.disconnectionCodes)
            );
    }
    addDisconnectionCode(payload: DisconnectionCode) {
        return this.crudDisconnectionCode(payload,'/ccat/disconnection-code/add')
    }
    updateDisconnectionCode(payload: DisconnectionCode) {
        return this.crudDisconnectionCode(payload , '/ccat/disconnection-code/update')
    }
    deleteDisconnectionCode(payload: DisconnectionCode) {
        return this.crudDisconnectionCode(payload , '/ccat/disconnection-code/delete')
    }
    private crudDisconnectionCode(payload: DisconnectionCode, path: string) {
        return this.httpService
            .request({
                path,
                payload: {
                    token: this.token,
                    ...payload,
                },
            })
            .pipe(indicate(this.loading), take(1));
    }
}
