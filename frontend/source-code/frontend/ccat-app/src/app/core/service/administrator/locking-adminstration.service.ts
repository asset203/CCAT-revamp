import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../http.service';

@Injectable({
  providedIn: 'root'
})
export class LockingAdminstrationService {

  constructor(private http: HttpService) { }

  get getAllLocking$(): Observable<any> {
    return this.http.request({
      path: '/ccat/locking-administration/get-all',
    })
  }

  unLock$(msisdn, username): Observable<any> {
    return this.http.request({
      path: '/ccat/locking-administration/delete',
      payload: {
        msisdn,
        username
      },
    })
  }
}
