import {AfterViewChecked, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {UssdReportsService} from 'src/app/core/service/customer-care/ussd-reports.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-ussd-reports',
    templateUrl: './ussd-reports.component.html',
    styleUrls: ['./ussd-reports.component.scss'],
})
export class USSDReportsComponent implements OnInit, AfterViewChecked, OnDestroy {
    constructor(
        private ussdService: UssdReportsService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef,
        private subscriberService:SubscriberService
    ) {}
    headers;
    reports;
    fromDate;
    toDate;
    dateTime = new Date();
    classNameCon = '';
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    permissions = {
        GET_USSD_REPORT: false,
    };
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
    ngOnInit(): void {
        this.handelMenusOpen();
        this.setPermissions();
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
    getUSSD() {
        const trafficFromtDate = new Date(this.fromDate).getTime();
        const trafficToDate = new Date(this.toDate).getTime();
        this.ussdService
            .getUSSD$(trafficFromtDate, trafficToDate)
            .pipe(
                take(1),
            )
            .subscribe({
                next: (res) => {
                    if(res?.statusCode===0){
                        this.headers = res.payload.headers;
                        this.reports = res.payload.data;
                    }
                    else{
                        this.headers = res.payload.headers;
                        this.reports = [];
                    }
                    
                },
                error: (err) => {
                    this.reports = [];
                    this.toasterService.error('Error', err);
                },
            });
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(156, 'GET_USSD_REPORT');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.GET_USSD_REPORT = this.featuresService.getPermissionValue(156);
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
    handelMenusOpen() {
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
            this.setResponsiveTableWidth();
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
            this.setResponsiveTableWidth();
        });
    }
}
