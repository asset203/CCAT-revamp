import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';

@Injectable({
  providedIn: 'root'
})
export class TrafficBehaviorService {

  constructor(private http: HttpService, private subscriberService: SubscriberService) { }
  getTrafficBehavior$(requestData): Observable<any> {
    return this.subscriberService.subscriber$.pipe(
      map(subscriber => subscriber.subscriberNumber),
      switchMap((msisdn) =>
        this.http.request({
          path: '/ccat/dss-report/traffic-behavior/get-all',
          payload: {
            ...requestData,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
          },
        })
      ),
    )
  }
}
