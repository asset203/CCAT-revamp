import { Injectable } from '@angular/core';
import { Observable , BehaviorSubject } from 'rxjs';
import { HttpService } from '../http.service';
import { indicate } from 'src/app/shared/rxjs/indicate';

@Injectable({
  providedIn: 'root'
})
export class LockingAdminstrationService {
  loading = new BehaviorSubject(false)
  constructor(private http: HttpService) { }

  get getAllLocking$(): Observable<any> {
    return this.http.request({
      path: '/ccat/locking-administration/get-all',
    }).pipe(indicate(this.loading))
  }

  unLock$(msisdn, username): Observable<any> {
    return this.http.request({
      path: '/ccat/locking-administration/delete',
      payload: {
        msisdn,
        username
      },
    }).pipe(indicate(this.loading))
  }
}
