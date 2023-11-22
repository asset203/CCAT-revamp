import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { ServiceOfferingBitModel } from "src/app/shared/models/service-offering-bit.interface";
import { HttpService } from "../http.service";



@Injectable({
    providedIn: 'root',
})
export class ServiceOfferingBitsDescService {
   allServiceOfferingBitsDescSubject = new  BehaviorSubject<ServiceOfferingBitModel[]> ([]);
    constructor(private http: HttpService) { }
    

    get allServiceOfferingBitsDesc$(): Observable<any> {
      return this.allServiceOfferingBitsDescSubject.asObservable();
      }


    public getAllServiceOfferingBitsDesc(){

        this.http.request({
            path: '/ccat/service-offering-description/get-all',
            payload: {
              token: JSON.parse(sessionStorage.getItem('session')).token,
            },
          }).subscribe(res=> this.allServiceOfferingBitsDescSubject.next(res.payload.serviceOfferingBitList)
          )
    }

    public updateServiceOfferingBitDesc(serviceOffering:ServiceOfferingBitModel){

        this.http.request({
            path: '/ccat/service-offering-description/update',
            payload: {
              token: JSON.parse(sessionStorage.getItem('session')).token,
              serviceOffering
            },
          }).pipe(tap(res=>  this.getAllServiceOfferingBitsDesc()))
            .subscribe();
    }


}