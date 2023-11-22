import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { MessageService } from 'src/app/shared/services/message.service';
import { BehaviorSubject } from 'rxjs';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';
import { ApiRequest } from '../../interface/api-request.interface';
import { map, switchMap, take } from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class SendSmsService {
    loading$ = new BehaviorSubject(false);
    languageId;
    constructor(
        private httpService: HttpService,
        private toastService: ToastrService,
        private subscriberService: SubscriberService,
        private messageService: MessageService
    ) {
        this.subscriberService.subscriber$.pipe(take(1)).subscribe((res) => {
            this.languageId = res?.languageId;
        });
    }

    sendSMS(SMS) {
        this.loading$.next(true);

        return this.subscriberService.subscriber$.pipe(
            map((subscriber) => subscriber?.subscriberNumber),take(1),
            switchMap((msisdn) => {
                let reqObj: ApiRequest = {
                    path: '/ccat/send-sms/send',
                    payload: {
                        ...SMS,
                        templateLanguageId: this.languageId,
                        msisdn,
                    },
                };
                return this.httpService.request(reqObj);
            })
        );
    }
}
