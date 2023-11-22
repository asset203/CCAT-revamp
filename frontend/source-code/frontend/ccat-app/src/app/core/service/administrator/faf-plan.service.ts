import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { take } from 'rxjs/operators';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { environment } from 'src/environments/environment';

import { ApiRequest } from '../../interface/api-request.interface';
import { HttpService } from '../http.service';

const baseURL = environment.url;
@Injectable({
  providedIn: 'root'
})


export class FafPlanService {
  loading$ = new BehaviorSubject(false);
  allFAFPlanSubject$ = new BehaviorSubject(null);
  FAFPlanIndicatorsLookupSubject$ = new BehaviorSubject(null);
  constructor(private httpService: HttpService, private toasterService: ToastService, private messageService: MessageService) { }
  getFAFPlansLookup() {
    // prepare request obj
    let reqObj: ApiRequest = {
      path: '/ccat/lookup/faf-indicators/get-all',
    };
    // get api data
    this.httpService
      .request(reqObj)
      .pipe(take(1), indicate(this.loading$))
      .subscribe({
        next: (resp) => {
          this.FAFPlanIndicatorsLookupSubject$.next(resp?.payload?.fafIndicators)
        },
      });
  }
  getAllFAFPlans() {
    // prepare request obj
    let reqObj: ApiRequest = {
      path: '/ccat/admin-faf-plan/get-all',
    };
    // get api data
    this.httpService
      .request(reqObj)
      .pipe(take(1), indicate(this.loading$))
      .subscribe({
        next: (resp) => {
          this.allFAFPlanSubject$.next(resp?.payload?.plans)
        },
      });
  }
  addFAFPlan(formData): Observable<any> {
    // prepare request obj
    let addedPlan = {
      ...formData
    }
    return this.httpService
      .request({
        path: '/ccat/admin-faf-plan/add',
        payload: {
          addedPlan,
        },
      })
      .pipe(take(1), indicate(this.loading$))

  }
  deleteFAFPlan(planId) {
    // prepare request obj
    let reqData = {
      fafPlanId: planId,
    }
    // get api data
    return this.httpService
      .request({
        path: '/ccat/admin-faf-plan/delete',
        payload: reqData
      })
      .pipe(take(1), indicate(this.loading$))
  }
  updateFAFPlan(formData): Observable<any> {
    let updatedPlan = {
      ...formData
    }
    // get api data
    return this.httpService
      .request({
        path: '/ccat/admin-faf-plan/update',
        payload: {
          updatedPlan
        }
      })
      .pipe(take(1), indicate(this.loading$))
  }
}
