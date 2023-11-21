import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { map, switchMap } from 'rxjs/operators';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';

@Injectable({
  providedIn: 'root'
})
export class ProductsViewService {
  loading$ = new BehaviorSubject(false)
  constructor(private http: HttpService, private subscriberService: SubscriberService) { }

  get getAllProducts$(): Observable<any> {
    return this.subscriberService.subscriber$.pipe(
      
      map(subscriber => subscriber?.subscriberNumber),
      switchMap((msisdn) =>
        this.http.request({
          path: '/ccat/product/get-all',
          payload: {
            msisdn
          },
        })
      )
    )
  }
}
