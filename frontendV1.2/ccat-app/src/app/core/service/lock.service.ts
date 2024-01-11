import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs";
import { take } from "rxjs/operators";
import { indicate } from "src/app/shared/rxjs/indicate";
import { ApiRequest } from "../interface/api-request.interface";
import { HttpService } from "./http.service";

@Injectable({
    providedIn: 'root',
})
export class LockService {
    constructor(private httpService: HttpService) { }
    loading$ = new BehaviorSubject(false);

    lockAdminstration(msisdn) {
        let reqData = {
            msisdn: msisdn,
            date: new Date().getTime()
        }
        let reqObj: ApiRequest = {
            path: '/ccat/locking-administration/add',
            payload: reqData
        };

        // get api data
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
    deleteLock(msisdn) {
        let reqData = {
            msisdn: msisdn,
            date: new Date().getTime()
        }
        let reqObj: ApiRequest = {
            path: '/ccat/locking-administration/delete',
            payload: reqData
        };

        // get api data
        this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$)).subscribe(
                {
                    next: (resp) => {

                    }
                }
            )
    }
}