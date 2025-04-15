import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { take } from 'rxjs/operators';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ApiRequest } from '../../interface/api-request.interface';
import { HttpService } from '../http.service';

@Injectable({
  providedIn: 'root'
})
export class PrepaidService {

  msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
  constructor(private httpService: HttpService) { }
  loading$ = new BehaviorSubject(false);
  

  checkSubscription() {
    let reqData = {
      msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
    }
    // get api data
    return this.httpService
      .request({
        path: '/ccat/prepaid-vbp/check',
        payload: reqData
      })
      .pipe(take(1), indicate(this.loading$))
  }
  prepaidUnSubscribe() {
    let reqData = {
      msisdn: this.msisdn,

      footprintModel: {
        machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
        profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
        pageName: 'Prepaid VBP',
        msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
      },
    }
    // get api data
    return this.httpService
      .request({
        path: '/ccat/prepaid-vbp/unsubscribe',
        payload: reqData
      })
      .pipe(take(1), indicate(this.loading$))
  }
  prepaidSubscribe(formData) {
    let reqData = {
      msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
      transactionType: formData.transactionType.name,
      transactionCode: formData.transactionCode.id,
      transactionCodeName: formData.transactionCode.name,
      transactionAmount: formData.transactionAmount
    }
    // get api data
    return this.httpService
      .request({
        path: '/ccat/prepaid-vbp/subscribe',
        payload: reqData
      })
      .pipe(take(1), indicate(this.loading$))
  }

}
