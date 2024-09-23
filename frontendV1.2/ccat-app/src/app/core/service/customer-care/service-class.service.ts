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
export class ServiceClassService {

    loading$ = new BehaviorSubject(false);
    allServiceClassesSubject = new BehaviorSubject(null);

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService) {
    }

    // Service Class APIs
    getCurrentSubscriber() {
        let serviceClass = this.subscriberService?.subscriberSubject?.value;
        return serviceClass;
    }
    getAllServiceClasses() {
        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/service-classes/get-all'
        };

        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1)).subscribe(
                {
                    next: (resp) => {
                        this.allServiceClassesSubject.next(resp?.payload?.serviceClasses);

                        // close loading
                        this.loading$.next(false);
                    }
                }
            )

    }
    get allServiceClasses$() {
        return this.allServiceClassesSubject.asObservable();
    }
    updateServiceClass(data) {

        let reqData = {
            msisdn: this.subscriberService?.subscriberSubject?.value?.subscriberNumber,
            mainBalance: data.currentService.balanace,
            currentServiceClass: data.currentService,
            newServiceClass: data.newService,
            footprintModel: data.footprint,
        }


        let reqObj: ApiRequest = {
            path: '/ccat/service-classes/update',
            payload: reqData

        };

        this.httpService.request(reqObj)
            .pipe(
                take(1), tap((resp) => {

                })).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success(this.messageService.getMessage(43).message);
                                this.subscriberService.refresh();
                            }
                        }
                    }
                )

    }

}