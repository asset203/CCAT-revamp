import {AfterViewChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {map, take} from 'rxjs/operators';
import {UssdReportsService} from 'src/app/core/service/customer-care/ussd-reports.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-ussd-reports',
    templateUrl: './ussd-reports.component.html',
    styleUrls: ['./ussd-reports.component.scss'],
})
export class USSDReportsComponent implements OnInit, AfterViewChecked {
    constructor(
        private ussdService: UssdReportsService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef
    ) {}
    headers;
    reports;
    fromDate;
    toDate;
    dateTime = new Date();

    permissions = {
        GET_USSD_REPORT: false,
    };
    ngOnInit(): void {
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
        let findSubscriberPermissions: Map<number, string> = new Map().set(156, 'GET_USSD_REPORT');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.GET_USSD_REPORT = this.featuresService.getPermissionValue(156);
    }
}
