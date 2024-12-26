import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {ReportsService} from 'src/app/core/service/reports.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Defines} from 'src/app/shared/constants/defines';
import {ReportRequest} from 'src/app/shared/models/ReportRequest.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-visited-urls',
    templateUrl: './visited-urls.component.html',
    styleUrls: ['./visited-urls.component.scss'],
})
export class VisitedUrlsComponent implements OnInit, OnDestroy {
    reportData;
    reportsHeaders;
    loading$ = this.reportsService.loading;
    datesForm: FormGroup;
    totalRecords;
    rows = 5;
    globalFilters = [];
    newMatchModeOptions = [
        {label: 'Starts With', value: 'startsWith'},
        {label: 'Contains', value: 'contains'},
        {label: 'Equals', value: 'equals'},
    ];
    //types = {};
    dateTime = new Date();
    classNameCon = '';
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    getVisitedUrlReportPermission: boolean;
    visitedUrlFlagTypes;
    
    constructor(
        private reportsService: ReportsService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private subscriberService: SubscriberService
    ) {}

    ngOnInit(): void {
        this.setPermissions();
        this.datesForm = this.fb.group({
            dateFrom: [null, [Validators.required]],
            dateTo: [null, [Validators.required]],
        });
        this.handelMenusOpen();
        //this.getVisitedUrlFlags();
    }
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
    onDateSelect(event: any, formControl: string) {
        const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.datesForm.controls[formControl].setValue(correctedDate);
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
        this.rows = event.rows
        if (dates.dateFrom && dates.dateTo) {
            const reportDataReq: ReportRequest = {
                dateFrom: dates.dateFrom,
                dateTo: dates.dateTo,
                pagination: {
                    fetchCount: this.rows,
                    offset: event.first,
                    isGetAll: false,
                    sortedBy: this.reportsHeaders[event.sortField],
                    order: event.sortOrder === 1 ? 1 : 2,
                    queryString: filterQueryString,
                },
            };
            this.setDataOfTable(false, reportDataReq);
        }
    }
    getLongDates() {
        const longDate = {
            dateFrom: new Date(this.datesForm.value.dateFrom).getTime(),
            dateTo: new Date(this.datesForm.value.dateTo).getTime(),
        };
        return longDate;
    }
    submitDates() {
        const dates = this.getLongDates();
        const reportDataReq: ReportRequest = {
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
    setDataOfTable(isFirstRequest: boolean, reportDataReq: ReportRequest) {
        this.reportsService.allVisitedUrls$(reportDataReq).subscribe(
            (res) => {
                if (res.statusCode === 0) {
                    this.globalFilters = this.extractFilters(res?.payload?.headers);
                    this.reportsHeaders = res?.payload?.headers;
                    this.reportData = res?.payload?.data;
                    if (isFirstRequest) {
                        this.totalRecords = res?.payload?.totalNumberOfActivities;
                    }
                } else {
                    this.reportData = [];
                }
            },
            (err) => {
                this.reportData = [];
            }
        );
    }
    extractFilters(headers: any) {
        return Object.values(headers);
    }
    setPermissions() {
        let viewReportPermission: Map<number, string> = new Map().set(255, 'visitedUrl');
        this.featuresService.checkUserPermissions(viewReportPermission);
        this.getVisitedUrlReportPermission = this.featuresService.getPermissionValue(255);
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
    getVisitedUrlFlags() {
        this.reportsService.getFlags().subscribe((res) => {
            this.visitedUrlFlagTypes = res?.payload['Visited URLs'];
        });
    }
}
