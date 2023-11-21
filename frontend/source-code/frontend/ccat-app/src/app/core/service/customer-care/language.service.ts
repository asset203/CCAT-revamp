import { Injectable } from "@angular/core";
import { ToastrService } from "ngx-toastr";
import { BehaviorSubject } from "rxjs";
import { take, tap } from "rxjs/operators";
import { language } from "src/app/shared/models/language.interface";
import { MessageService } from "src/app/shared/services/message.service";
import { ApiRequest } from "../../interface/api-request.interface";
import { HttpService } from "../http.service";
import { StorageService } from "../storage.service";
import { SubscriberService } from "../subscriber.service";

@Injectable({
    providedIn: 'root',
})
export class LanguageService {

    loading$ = new BehaviorSubject(false);
    allLanguagesSubject = new BehaviorSubject(null);

    constructor(private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService) {
    }


    // Language APIs
    getCurrentLanguage() {
        let lang = this.subscriberService?.subscriberSubject?.value?.language;
        return lang;
    }

    getAllLanguages() {

        // open loading
        this.loading$.next(true);

        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/language/get-all'
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1)).subscribe(
                {
                    next: (resp) => {
                        let languageArray: language = resp.payload?.langaugeModel?.map(this.mapLanguageElement);
                        this.allLanguagesSubject.next(languageArray);

                        // close loading
                        this.loading$.next(false);
                    }
                }
            )
    }
    mapLanguageElement(el) {
        return { name: el.value, value: el.key }
    }
    get allLanguages$() {
        return this.allLanguagesSubject.asObservable();
    }
    updateLanguage(data,subscriberNumber) {

        let reqData = {
            msisdn: subscriberNumber,
            languageId: data.languageId,
            footPrint: data.footprint,
            username: JSON.parse(sessionStorage.getItem('session')).user.ntAccount
        }


        let reqObj: ApiRequest = {
            path: '/ccat/language/update',
            payload: reqData

        };

        this.httpService.request(reqObj)
            .pipe(
                take(1)).subscribe(
                    {
                        next: (resp) => {
                            if (resp?.statusCode === 0) {
                                this.toastService.success(this.messageService.getMessage(42).message)
                                this.subscriberService.refresh();
                            }

                        }
                    }
                )
    }
}