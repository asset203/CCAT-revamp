import { HttpService } from 'src/app/core/service/http.service';
import { EventEmitter, Injectable, Output } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { HttpHeaders } from '@angular/common/http';
import { ToastService } from 'src/app/shared/services/toast.service';
import { SubscriberService } from '../subscriber.service';
import { indicate } from 'src/app/shared/rxjs/indicate';

const baseURL = environment.url;
@Injectable({
  providedIn: 'root'
})

export class ServiceClassesService {

  loading$ = new BehaviorSubject(false)
  constructor(private http: HttpService, private toasterService: ToastService,
    private subscriberService: SubscriberService) { }

  get allServiceClasses$(): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/get-all',
    })
  }

  allEligibleServiceClass$(id): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/eligible',
      payload: {
        id
      },
    })
  }

  get dedicatedAccountSequence$(): Observable<any> {
    return this.http.request({
      path: '/ccat/sequence/ded-account',
    })
  }
  get accumulatorSequence$(): Observable<any> {
    return this.http.request({
      path: '/ccat/sequence/accumulator',
    })
  }

  addServiceClass$(serviceClass): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/add',
      payload: {
        serviceClass
      },
    }).pipe(indicate(this.loading$))
  }

  getServiceClass$(code): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/get',
      payload: {
        code
      },
    })
  }
  updateServiceClass$(serviceClass): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/update',
      payload: {
        serviceClass: serviceClass
      }


    }).pipe(indicate(this.loading$))
  }

  deleteClass$(code): Observable<any> {
    return this.http.request({
      path: '/ccat/admin/service-classes/delete',
      payload: {
        code
      },
    }).pipe(indicate(this.loading$))
  }

  importServiceClass(reqHeaders, fileFormData) {

    const options = {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
      reportProgress: true
    }
    fetch(`${baseURL}/ccat/admin/service-classes/import`, {
      method: "POST",
      body: fileFormData
    }).then((response: any) => {
      this.toasterService.success('Success', 'Imported Successfully');
    }).catch(err => {
      this.toasterService.error('Error', err);
    }).finally(() => {
      this.subscriberService.refresh();
    })


  }

}
