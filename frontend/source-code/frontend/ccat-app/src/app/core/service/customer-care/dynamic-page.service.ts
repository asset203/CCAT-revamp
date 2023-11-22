import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { take } from 'rxjs/operators';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ApiRequest } from '../../interface/api-request.interface';
import { HttpService } from '../http.service';

@Injectable({
    providedIn: 'root'
})
export class DynamicPageService {
    constructor(private httpService: HttpService) { }
    loading$ = new BehaviorSubject(false);


    getDynamicPage(pageID) {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/customer-dynamic-page/view',
            payload: {
                privilegeId: pageID
            }
        };
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }

    executeDynamicPage(reqData) {
        // prepare request obj
        let reqObj: ApiRequest = {
            path: '/ccat/customer-dynamic-page/step/execute',
            payload: { ...reqData }
        };
        return this.httpService
            .request(reqObj)
            .pipe(take(1), indicate(this.loading$))
    }
}