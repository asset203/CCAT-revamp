import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class PamInformationService {
    constructor(private http: HttpService, private subscriberService: SubscriberService) {}

    get getAllPams$(): Observable<any> {
        return this.subscriberService.subscriber$.pipe(
            map((subscriber) => subscriber.subscriberNumber),
            switchMap((msisdn) =>
                this.http.request({
                    path: '/ccat/lookup/pams/get-all',
                    payload: {
                        msisdn,
                    },
                })
            )
        );
    }

    addPam$(pam): Observable<any> {
        let msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        return this.http.request({
            path: '/ccat/pam-information/add',
            payload: {
                msisdn,
                ...pam,
            },
        });
    }

    deletePam$(pamId): Observable<any> {
        let msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        return this.http.request({
            path: '/ccat/pam-information/delete',
            payload: {
                msisdn,
                ...pamId,
            },
        })
    }
    evaluatePam$(pamId): Observable<any> {
        let msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        return this.http.request({
            path: '/ccat/pam-information/update',
            payload: {
                msisdn,
                ...pamId,
            },
        })
    }
}
