import {EventEmitter, Injectable, Output} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {environment} from 'src/environments/environment';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';

const baseURL = environment.url;
@Injectable({
    providedIn: 'root',
})
export class SystemSettingsService {
    loading$ = new BehaviorSubject(false);
    allSettingsSubject = new BehaviorSubject(null);

    constructor(
        private httpService: HttpService,
        private toastService: ToastService,
        private messageService: MessageService
    ) {}

    getAllSystemSettings() {
        let reqObj: ApiRequest = {
            path: '/ccat/system-configurations/get-all',
        };

        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading$));
    }

    updateSystemSettings(reqData) {
        let reqObj: ApiRequest = {
            path: '/ccat/system-configurations/update',
            payload: {
                configurations: reqData,
            },
        };

        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
            .subscribe((resp) => {
                if (resp.statusCode == 0) {
                    this.toastService.success(this.messageService.getMessage(65).message);
                    this.httpService
                        .request({path: '/ccat/actuator/busrefresh', payload: {}})
                        .pipe(take(1))
                        .subscribe();
                }
            });
    }
}
