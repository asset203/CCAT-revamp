import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {ReportsService} from 'src/app/core/service/reports.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Defines} from 'src/app/shared/constants/defines';
import {FlagReportRequest} from 'src/app/shared/models/ReportRequest.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-complaint-view',
    templateUrl: './complaint-view.component.html',
    styleUrls: ['./complaint-view.component.scss'],
})
export class ComplaintViewComponent implements OnInit, OnDestroy {
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
    dateTime = new Date();
    getComplaintViewRepPermission: boolean;
    complaintViewflagTypes = [];
    classNameCon = '';
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    getVisitedUrlReportPermission: boolean;
    constructor(
        private reportsService: ReportsService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private subscriberService: SubscriberService
    ) {}
    fetchCount=5;
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
    ngOnInit(): void {
        this.setPermissions();
        this.datesForm = this.fb.group({
            dateFrom: [null, [Validators.required]],
            dateTo: [null, [Validators.required]],
            flag: [null, [Validators.required]],
        });
        this.handelMenusOpen();
        this.getFlags()
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
        this.fetchCount = event.rows;
        if (dates.dateFrom && dates.dateTo) {
            const reportDataReq: FlagReportRequest = {
                dateFrom: dates.dateFrom,
                dateTo: dates.dateTo,
                flag: dates.flag,
                pagination: {
                    fetchCount: this.fetchCount,
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
            flag: this.datesForm.value.flag,
        };
        return longDate;
    }
    submitDates() {
        const dates = this.getLongDates();
        const reportDataReq: FlagReportRequest = {
            pagination: {
                fetchCount: this.fetchCount,
                offset: 0,
                isGetAll: true,
            },
            dateFrom: dates.dateFrom,
            dateTo: dates.dateTo,
            flag: dates.flag,
        };
        this.setDataOfTable(true, reportDataReq);
    }
    setDataOfTable(isFirstRequest: boolean, reportDataReq: FlagReportRequest) {
        this.reportsService.allComplaintView$(reportDataReq).subscribe(
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
        let viewReportPermission: Map<number, string> = new Map().set(313, 'compView');
        this.featuresService.checkUserPermissions(viewReportPermission);
        this.getComplaintViewRepPermission = this.featuresService.getPermissionValue(313);
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
    getFlags() {
        this.reportsService.getFlags().subscribe((res) => {
            this.complaintViewflagTypes = res?.payload['Complaint View'];
        });
    }
}
