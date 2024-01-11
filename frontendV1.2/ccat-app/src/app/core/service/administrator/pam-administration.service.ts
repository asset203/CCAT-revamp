import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../http.service';
import {BehaviorSubject} from 'rxjs';
import { indicate } from 'src/app/shared/rxjs/indicate';
@Injectable({
  providedIn: 'root'
})
export class PamAdministrationService {
  loading$ = new BehaviorSubject (false)
  constructor(private http: HttpService) { }

  get getAllPams$(): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/get-all',
    }).pipe(indicate(this.loading$))
  }
  get getAllPamsTypes$(): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/pam-type/get-all',
    }).pipe(indicate(this.loading$))
  }


  addPam$(pam): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/add',
      payload: {
        pam
      },
    }).pipe(indicate(this.loading$))
  }
  getPam$(pamId): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/get',
      payload: {
        id: pamId
      },
    }).pipe(indicate(this.loading$))
  }

  updatePam$(pam): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/update',
      payload: {
        ...pam
      },
    }).pipe(indicate(this.loading$))
  }

  deletePam$(id,pamTypeId): Observable<any> {
    return this.http.request({
      path: '/ccat/pam-administration/delete',
      payload: {
        id,
        pamTypeId
      },
    }).pipe(indicate(this.loading$))
  }
}
