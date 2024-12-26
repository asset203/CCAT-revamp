import {AfterViewChecked, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {UssdReportsService} from 'src/app/core/service/customer-care/ussd-reports.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Defines} from 'src/app/shared/constants/defines';
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
        private subscriberService: SubscriberService
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
    rows = 5;
    totalRecords;
    globalFilters;
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
        const dates = this.getLongDates();
        const reportDataReq = {
            pagination: {
                fetchCount: this.rows,
                offset: 0,
                isGetAll: true,
            },
            dateFrom: dates.dateFrom,
            dateTo: dates.dateTo,
        };
        this.setDataOfTable(true, reportDataReq);
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
    loadReport(event) {
        let filterQueryString = '';
        const dates = this.getLongDates();
        for (let filter in event.filters) {
            let filterObj = event.filters[filter][0];
            if (filterObj.value) {
                filterQueryString += `${filter}=${filterObj.value},${
                    filterObj.matchMode === 'startsWith'
                        ? Defines.FILTERS_TYPES.START_WITH
                        : filterObj.matchMode === 'contains'
                        ? Defines.FILTERS_TYPES.CONTAINS
                        : Defines.FILTERS_TYPES.EQUALS
                }&`;
            }
        }
        filterQueryString = filterQueryString.slice(0, -1);
        this.rows = event.rows;
        if (dates.dateFrom && dates.dateTo) {
            const reportDataReq = {
                dateFrom: dates.dateFrom,
                dateTo: dates.dateTo,
                pagination: {
                    fetchCount: event.rows,
                    offset: event.first,
                    isGetAll: false,
                    sortedBy: this.headers[event.sortField],
                    order: event.sortOrder === 1 ? 1 : 2,
                    queryString: filterQueryString,
                },
            };
            this.setDataOfTable(false, reportDataReq);
        }
    }
    getLongDates() {
        const longDate = {
            dateFrom: new Date(this.fromDate).getTime(),
            dateTo: new Date(this.toDate).getTime(),
        };
        return longDate;
    }
    setDataOfTable(isFirstRequest: boolean, reportDataReq: any) {
        this.ussdService.getUSSD$(reportDataReq).subscribe(
            (res) => {
                if (res.statusCode === 0) {
                    this.globalFilters = this.extractFilters(res?.payload?.headers);
                    this.headers = res?.payload?.headers;
                    this.reports = res?.payload?.data;
                    if (isFirstRequest) {
                        this.totalRecords = res?.payload?.totalNumberOfActivities;
                    }
                } else {
                    this.reports = [];
                }
            },
            (err) => {
                this.reports = [];
            }
        );
    }
    extractFilters(headers: any) {
        return Object.values(headers);
    }
}
