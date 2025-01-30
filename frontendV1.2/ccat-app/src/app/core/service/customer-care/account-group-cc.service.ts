import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import { AccountGroupCC } from 'src/app/shared/models/accountGroupCC.interface';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class AccountGroupCCService {
    loading$ = new BehaviorSubject(false);
    constructor(private httpService: HttpService, private subscriberService: SubscriberService) {}
    getAllAccountGroups(msisdn,serviceClassId) {
        //const serviceClassId = this.subscriberService.subscriberSubject.getValue().serviceClass.code;
        return this.httpService
            .request({
                path: '/ccat/account-groups/get-all',
                payload: {
                    msisdn,
                    serviceClassId,
                },
            })
            .pipe(indicate(this.loading$));
    }
    updateAccountGroup(currentAccountGroup : AccountGroupCC , newAccountGroup : AccountGroupCC){
        console.log("currentAccountGroup",currentAccountGroup)
        console.log("newAccountGroup",newAccountGroup)
        const msisdn = this.subscriberService.subscriberSubject.getValue().subscriberNumber;
        return this.httpService.request({
            path : "/ccat/account-groups/update",
            payload:{
                msisdn,
                currentAccountGroup,
                newAccountGroup
            }
        }).pipe(indicate(this.loading$))
    }
}
