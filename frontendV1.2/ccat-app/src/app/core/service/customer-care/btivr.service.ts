import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';

@Injectable({
  providedIn: 'root'
})
export class BtivrService {

  constructor(private http: HttpService, private subscriberService: SubscriberService) { }
  getBTIVR$(dateFrom, dateTo, flag): Observable<any> {
    return this.subscriberService.subscriber$.pipe(
      map(subscriber => subscriber.subscriberNumber),
      switchMap((msisdn) =>
        this.http.request({
          path: '/ccat/dss-report/btivr/get-all',
          payload: {
            msisdn,
            dateFrom,
            dateTo,
            btivr: flag
          },
        })
      ),
    )
  }
  getBTIVRFlags(){
    return this.http.request({
      path : '/ccat/dss-flags/get-all'
    })
  }
}
