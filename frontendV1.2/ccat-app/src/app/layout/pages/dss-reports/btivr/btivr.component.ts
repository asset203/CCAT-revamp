import {AfterViewChecked, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import { Subscription } from 'rxjs';
import {map, take} from 'rxjs/operators';
import {BtivrService} from 'src/app/core/service/customer-care/btivr.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-btivr',
    templateUrl: './btivr.component.html',
    styleUrls: ['./btivr.component.scss'],
})
export class BTIVRComponent implements OnInit, AfterViewChecked , OnDestroy {
    constructor(
        private btivrService: BtivrService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef,
        private subscriberService:SubscriberService
    ) {}
    headers;
    reports;
    fromDate;
    toDate;
    permissions = {
        GET_BTIVR_REPORT: false,
    };
    flag;
    btivrFlagTypes=[];
    dateTime = new Date();
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    classNameCon = '';
    ngOnInit(): void {
        this.setPermissions();
        this.getBtivrFlags();
        this.handleMenuObservable();
    }
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }
    onDateSelectFromDate(event: any) {
        const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.fromDate = correctedDate;
    }
    onDateSelectToDate(event: any) {
        const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.toDate = correctedDate;
    }
    getBTIVR() {
        const trafficFromtDate = new Date(this.fromDate).getTime();
        const trafficToDate = new Date(this.toDate).getTime();
        
        this.btivrService
            .getBTIVR$(trafficFromtDate, trafficToDate,this.flag)
            .pipe(
                take(1),
                map((res) => res.payload)
            )
            .subscribe({
                next: (res) => {
                    this.headers = res.headers;
                    this.reports = res.data;
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(155, 'GET_BTIVR_REPORT');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.GET_BTIVR_REPORT = this.featuresService.getPermissionValue(155);
    }
    getBtivrFlags(){
        this.btivrService.getBTIVRFlags().subscribe(res=>{
            this.btivrFlagTypes = res?.payload?.BTIVR;
        })
    }
    setResponsiveTableWidth() {
        if (this.isopened && this.isopenedNav) {
            this.classNameCon = 'table-width';
        } else if (this.isopened && !this.isopenedNav) {
            this.classNameCon = 'table-width-1';
        } else if (!this.isopened && this.isopenedNav) {
            this.classNameCon = 'table-width-3';
        } else {
            this.classNameCon = 'table-width-2';
        }
    }
    handleMenuObservable(){
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
            this.setResponsiveTableWidth();
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
            this.setResponsiveTableWidth();
        });
    }
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
}
