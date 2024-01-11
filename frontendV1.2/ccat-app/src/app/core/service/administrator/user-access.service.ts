import { HttpService } from 'src/app/core/service/http.service';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UserAccessService {

  constructor(private http: HttpService) { }

  get allUsers$(): Observable<any> {
    return this.http.request({
      path: '/ccat/user/get-all',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
      },
    })
  }

  addUser$(reqData): Observable<any> {
    return this.http.request({
      path: '/ccat/user/add',
      payload: {

        ...reqData
      },
    })
  }

  getUser$(userId): Observable<any> {
    return this.http.request({
      path: '/ccat/user/get',
      payload: {
        userId
      },
    })
  }

  updateUser$(reqData): Observable<any> {
    const { resetDebitLimit, resetRebateLimit, resetSessionCounter, ...newUser } = reqData.user
    const { ntAccount, profileId, userId, ...restDataOfUser } = reqData.user
    return this.http.request({
      path: '/ccat/user/update',
      payload: {
        ...reqData,
        ...restDataOfUser
      },
    })
  }

  deleteUser$(reqData): Observable<any> {
    return this.http.request({
      path: '/ccat/user/delete',
      payload: {
        ...reqData
      },
    })
  }

}
