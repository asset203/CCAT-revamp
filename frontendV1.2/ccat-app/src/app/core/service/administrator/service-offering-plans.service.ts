import { Injectable } from "@angular/core";
import { Router } from "@angular/router";
import { BehaviorSubject, Observable } from "rxjs";
import { map, tap } from "rxjs/operators";
import { ServiceOfferingBitModel } from "src/app/shared/models/service-offering-bit.interface";
import { ServiceOfferingPlanModel } from "src/app/shared/models/ServiceOfferingPlan.model";
import { HttpService } from "../http.service";
import { LoadingService } from "src/app/shared/services/loading.service";
import { indicate } from "src/app/shared/rxjs/indicate";



@Injectable({
  providedIn: 'root',
})
export class ServiceOfferingPlansService {
  allServiceOfferingPlansSubject = new BehaviorSubject<ServiceOfferingPlanModel[]>([]);
  serviceOfferingPlanWithBitsSubject = new BehaviorSubject<ServiceOfferingPlanModel>(null);
  serviceClassPlanDescSubject = new BehaviorSubject<ServiceOfferingPlanModel>(null);
  isFetchingList$ = this.loadingService.fetching$;
  loading$ = new BehaviorSubject(false);
  constructor(private http: HttpService, private router: Router,private loadingService : LoadingService) { }


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
    this.loadingService.startFetchingList()
    this.http.request({
      path: '/ccat/service-offering-plans/get-all',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
      },
    }).pipe(indicate(this.loading$)).subscribe(res =>{ this.loadingService.endFetchingList(); 
      this.allServiceOfferingPlansSubject.next(res.payload.serviceOfferingPlans)
    },err=>{
      this.allServiceOfferingPlansSubject.next([])
      this.loadingService.endFetchingList(); 

    }
      
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
    }).pipe(indicate(this.loading$))
      .subscribe(res => this.serviceOfferingPlanWithBitsSubject.next(res.payload));
  }

  public getServiceClassPlanDescription(planId: number) {

    this.http.request({
      path: '/ccat/service-offering-plans/so-sc-desc/get',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        planId
      },
    }).pipe(indicate(this.loading$))
      .subscribe(res => this.serviceClassPlanDescSubject.next(res.payload));
  }


  public deleteServiceOfferingPlanWithBits(planId: number) {

    this.http.request({
      path: '/ccat/service-offering-plans/delete',
      payload: {
        token: JSON.parse(sessionStorage.getItem('session')).token,
        planId
      },
    }).pipe(indicate(this.loading$))
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
      .pipe(indicate(this.loading$),
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
    }).pipe(indicate(this.loading$),
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
    }).pipe(indicate(this.loading$),tap(() => this.getServiceClassPlanDescription(serviceClassPlanDescription.planId))).subscribe()
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
    }).pipe(indicate(this.loading$),tap(() => this.getServiceClassPlanDescription(params.planId))).subscribe()
  }




}