import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BehaviorSubject } from "rxjs";
import { take, tap } from "rxjs/operators";
import { MessageService } from "src/app/shared/services/message.service";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";
import { SubscriberService } from "../subscriber.service";

@Injectable({
    providedIn: 'root',
})
export class VoucherService {

    loading$ = new BehaviorSubject(false);
    allVouchersSubject = new BehaviorSubject(null);

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService) {
    }

    //  VoucherLess Refill APIs
    getAllVouchers() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/voucherless-refill/payment-profile/get-all'
        };

        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1)).subscribe(
                {
                    next: (resp) => {
                        this.allVouchersSubject.next(resp?.payload?.paymentProfiles);
                        // close loading
                        this.loading$.next(false);
                    }
                }
            )
    }

    get allVouchers$() {
        return this.allVouchersSubject.asObservable();
    }

    updateVoucher(updateObj: any) {

        let reqData = {
            msisdn: this.subscriberService?.subscriberSubject?.value?.subscriberNumber,
            amount: updateObj.amount,
            paymentProfileId: updateObj.payment.profileId
        }
        let reqObj: ApiRequest = {
            path: '/ccat/voucherless-refill/submit',
            payload: reqData

        };
        this.httpService.request(reqObj)
            .pipe(
                take(1), tap((resp) => {

                })).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success(this.messageService.getMessage(50).message)
                                this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                            }
                        }
                    }
                )
    }
}