import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { ServiceOfferingBitModel } from "src/app/shared/models/service-offering-bit.interface";
import { ServiceOfferingPlanModel } from "src/app/shared/models/ServiceOfferingPlan.model";
import { HttpService } from "../http.service";



@Injectable({
  providedIn: 'root',
})
export class ServiceOfferingPlansService {
  allServiceOfferingPlansSubject = new BehaviorSubject<ServiceOfferingPlanModel[]>([]);
  serviceOfferingPlanWithBitsSubject = new BehaviorSubject<ServiceOfferingPlanModel>(null);
  serviceClassPlanDescSubject = new BehaviorSubject<ServiceOfferingPlanModel>(null);

  constructor(private http: HttpService, private router: Router) { }


  get allServiceOfferingPlans$(): Observable<any> {
    return this.allServiceOfferingPlansSubject.asObservable();
  }
  get serviceOfferingPlanWithBits$(): Observable<any> {
    return this.serviceOfferingPlanWithBitsSubject.asObservable();
  }

  get serviceClassPlanDescription$(): Observable<any> {
    return this.serviceClassPlanDescSubject.asObservable();
  }


  public getAllServiceOfferingBitsDesc() {

    this.http.request({
      path: '/ccat/service-offering-plans/get-all',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
      },
    }).subscribe(res => this.allServiceOfferingPlansSubject.next(res.payload.serviceOfferingPlans)
    )
  }

  public getAllServiceOfferingPlansForServiceClass() {
    return this.http.request({
      path: '/ccat/service-offering-plans/get-all',
    }).pipe(map(res => res.payload.serviceOfferingPlans))
  }

  public getServiceOfferingPlanWithBits(planId: number) {

    this.http.request({
      path: '/ccat/service-offering-plans/get',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        planId
      },
    })
      .subscribe(res => this.serviceOfferingPlanWithBitsSubject.next(res.payload));
  }

  public getServiceClassPlanDescription(planId: number) {

    this.http.request({
      path: '/ccat/service-offering-plans/so-sc-desc/get',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        planId
      },
    })
      .subscribe(res => this.serviceClassPlanDescSubject.next(res.payload));
  }


  public deleteServiceOfferingPlanWithBits(planId: number) {

    this.http.request({
      path: '/ccat/service-offering-plans/delete',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        planId
      },
    })
      .subscribe(res => this.getAllServiceOfferingBitsDesc());
  }
  public addServiceOfferingPlan(serviceOfferingPlan: ServiceOfferingPlanModel) {

    this.http.request({
      path: '/ccat/service-offering-plans/add',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        ...serviceOfferingPlan
      },
    })
      .pipe(
        (tap((res) => {

          if (res.statusCode === 0)
            this.router.navigateByUrl("admin/service-offering-plan")
        }))).subscribe()
  }

  public updateServiceOfferingPlan(serviceOfferingPlan: ServiceOfferingPlanModel) {

    this.http.request({
      path: '/ccat/service-offering-plans/update',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        ...serviceOfferingPlan
      },
    }).pipe(
      (tap((res) => {
        if (res.statusCode === 0)
          this.router.navigateByUrl("admin/service-offering-plan");
      }))).subscribe()

  }

  public addServiceClassPlanDescription(serviceClassPlanDescription) {

    this.http.request({
      path: '/ccat/service-offering-plans/so-sc-desc/add',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        ...serviceClassPlanDescription
      },
    }).pipe(tap(() => this.getServiceClassPlanDescription(serviceClassPlanDescription.planId))).subscribe()
  }
  public updateServiceClassPlanDescription(serviceClassPlanDescription) {

    this.http.request({
      path: '/ccat/service-offering-plans/so-sc-desc/update',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        ...serviceClassPlanDescription
      },
    }).pipe(tap(() => this.getServiceClassPlanDescription(serviceClassPlanDescription.planId))).subscribe()
  }
  public deleteServiceClassPlanDescription(params) {

    this.http.request({
      path: '/ccat/service-offering-plans/so-sc-desc/delete',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        ...params
      },
    }).pipe(tap(() => this.getServiceClassPlanDescription(params.planId))).subscribe()
  }




}