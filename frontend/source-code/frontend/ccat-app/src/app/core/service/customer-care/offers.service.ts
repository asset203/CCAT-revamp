import {Injectable} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {BehaviorSubject} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {MessageService} from 'src/app/shared/services/message.service';
import {ApiRequest} from '../../interface/api-request.interface';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class OffersService {
    loading = new BehaviorSubject(false);
    allOffersSubject = new BehaviorSubject(null);
    offersLookupSubject = new BehaviorSubject(null);

    constructor(
        private httpService: HttpService,
        private subscriberService: SubscriberService,
        private toastService: ToastrService,
        private messageService: MessageService
    ) {}
    get loading$() {
        return this.loading;
    }

    getAllOffers() {
        let reqData = {
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/offers/get-all',
            payload: reqData,
        };
        // get api data
        return this.httpService.request(reqObj).pipe(
            take(1),
            indicate(this.loading),
            map((res) =>
                res?.payload?.offers.map((offer) => {
                    return {
                        ...offer,
                        startDate: new Date(offer.startDate),
                        expiryDate: new Date(offer.expiryDate),
                    };
                })
            )
        );
    }
    get allOffers$() {
        return this.allOffersSubject.asObservable();
    }

    getOffersLookup() {
        let reqData = {
            msisdn: this.subscriberService?.subscriberSubject?.value?.subscriberNumber,
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/lookup/offers/get-all',
            payload: reqData,
        };
        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading))
            .subscribe({
                next: (resp) => {
                    this.offersLookupSubject.next(resp.payload?.offers);
                },
            });
    }
    get offersLookup$() {
        return this.offersLookupSubject.asObservable();
    }

    addOffer(offer) {
        let reqData = {
            msisdn: this.subscriberService.subscriberSubject.value.subscriberNumber,
            ...offer,
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/offers/add',
            payload: reqData,
        };
        // get api data
        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading));
    }
    deleteOffer(offer) {
        let reqData = {
            msisdn: this.subscriberService?.subscriberSubject?.value.subscriberNumber,
            ...offer,
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/offers/delete',
            payload: reqData,
        };
        // get api data
        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading));
    }
    updateOffer(offer) {
        let reqData = {
            msisdn: this.subscriberService?.subscriberSubject?.value.subscriberNumber,
            ...offer,
        };
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/offers/update',
            payload: reqData,
        };
        // get api data
        return this.httpService.request(reqObj).pipe(take(1), indicate(this.loading));
    }
}
