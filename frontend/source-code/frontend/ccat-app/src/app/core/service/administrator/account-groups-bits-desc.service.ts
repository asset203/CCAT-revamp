import { Injectable } from "@angular/core";
import { BehaviorSubject, Observable } from "rxjs";
import { tap } from "rxjs/operators";
import { AccountGroupBitModel } from "src/app/shared/models/account-group-bit.model";
import { HttpService } from "../http.service";



@Injectable({
    providedIn: 'root',
})
export class AccountGroupsBitsDescService {
   allAccountGroupsBitsDescSubject = new  BehaviorSubject<AccountGroupBitModel[]> ([]);
    constructor(private http: HttpService) { }
    

    get allAccountGroupsBitsDesc$(): Observable<any> {
      return this.allAccountGroupsBitsDescSubject.asObservable();
      }


    public getAllAccountGroupsBitsDesc(){

        this.http.request({
            path: '/ccat/account-groups-bits-description/get-all',
            payload: {
              token: JSON.parse(sessionStorage.getItem('session')).token,
            },
          }).subscribe(res=> this.allAccountGroupsBitsDescSubject.next(res.payload.accountGroupsList)
          )
    }

    public UpdateAccountGroupsBitsDesc(accountGroupBit:AccountGroupBitModel){

        this.http.request({
            path: '/ccat/account-groups-bits-description/update',
            payload: {
              token: JSON.parse(sessionStorage.getItem('session')).token,
              accountGroupBit
            },
          }).pipe(tap(res=>  this.getAllAccountGroupsBitsDesc()))
            .subscribe();
    }


}