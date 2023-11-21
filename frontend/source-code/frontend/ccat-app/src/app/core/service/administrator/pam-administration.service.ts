import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../http.service';

@Injectable({
  providedIn: 'root'
})
export class PamAdministrationService {

  constructor(private http: HttpService) { }

  get getAllPams$(): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/get-all',
    })
  }
  get getAllPamsTypes$(): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/pam-type/get-all',
    })
  }


  addPam$(pam): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/add',
      payload: {
        pam
      },
    })
  }
  getPam$(pamId): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/get',
      payload: {
        id: pamId
      },
    })
  }

  updatePam$(pam): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/update',
      payload: {
        ...pam
      },
    })
  }

  deletePam$(id,pamTypeId): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/delete',
      payload: {
        id,
        pamTypeId
      },
    })
  }
}
