import {AfterViewChecked, ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {BtivrService} from 'src/app/core/service/customer-care/btivr.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Defines} from 'src/app/shared/constants/defines';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-btivr',
    templateUrl: './btivr.component.html',
    styleUrls: ['./btivr.component.scss'],
})
export class BTIVRComponent implements OnInit, AfterViewChecked, OnDestroy {
    constructor(
        private btivrService: BtivrService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef,
        private subscriberService: SubscriberService
    ) {}
    headers;
    reports;
    fromDate;
    toDate;
    permissions = {
        GET_BTIVR_REPORT: false,
    };
    flag;
    btivrFlagTypes = [];
    dateTime = new Date();
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    classNameCon = '';
    rows = 5;
    totalRecords;
    globalFilters;
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
        const dates = this.getLongDates();
        const reportDataReq: any = {
            pagination: {
                fetchCount: this.rows,
                offset: 0,
                isGetAll: true,
            },
            dateFrom: dates.dateFrom,
            dateTo: dates.dateTo,
            flag: this.flag,
        };
        this.setDataOfTable(true, reportDataReq);
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(155, 'GET_BTIVR_REPORT');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.GET_BTIVR_REPORT = this.featuresService.getPermissionValue(155);
    }
    getBtivrFlags() {
        this.btivrService.getBTIVRFlags().subscribe((res) => {
            this.btivrFlagTypes = res?.payload?.BTIVR;
        });
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
    handleMenuObservable() {
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
            const reportDataReq: any = {
                dateFrom: dates.dateFrom,
                dateTo: dates.dateTo,
                flag: this.flag,
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
    setDataOfTable(isFirstRequest: boolean, reportDataReq: any) {
        this.btivrService.getBTIVR$(reportDataReq).subscribe(
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
    getLongDates() {
        const longDate = {
            dateFrom: new Date(this.fromDate).getTime(),
            dateTo: new Date(this.toDate).getTime(),
            //flag: this.datesForm.value.flag,
        };
        return longDate;
    }
}
