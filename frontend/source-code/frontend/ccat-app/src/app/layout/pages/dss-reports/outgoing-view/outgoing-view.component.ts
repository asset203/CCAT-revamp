import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReportsService } from 'src/app/core/service/reports.service';
import { Defines } from 'src/app/shared/constants/defines';
import { FlagReportRequest } from 'src/app/shared/models/ReportRequest.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-outgoing-view',
    templateUrl: './outgoing-view.component.html',
    styleUrls: ['./outgoing-view.component.scss'],
})
export class OutgoingViewComponent implements OnInit {
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
    getOutgoingViewPermission: boolean;
    contractTypesFlags = Defines.OUTGOING_VIEW_FLAGS;
    constructor(
        private reportsService: ReportsService,
        private fb: FormBuilder,
        private featuresService: FeaturesService
    ) {}

    ngOnInit(): void {
        this.setPermissions();
        this.datesForm = this.fb.group({
            dateFrom: [null, [Validators.required]],
            dateTo: [null, [Validators.required]],
            flag: [null, [Validators.required]],
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
        if (dates.dateFrom && dates.dateTo) {
            const reportDataReq: FlagReportRequest = {
                dateFrom: dates.dateFrom,
                dateTo: dates.dateTo,
                flag: dates.flag,
                pagination: {
                    fetchCount: event.rows,
                    offset: event.first,
                    isGetAll: true,
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
                fetchCount: 5,
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
        this.reportsService.allOutgoingView$(reportDataReq).subscribe((res) => {
            this.globalFilters = this.extractFilters(res?.payload?.headers);
            this.reportsHeaders = res?.payload?.headers;
            this.reportData = res?.payload?.data;
            if (isFirstRequest) {
                this.totalRecords = res?.payload?.totalNumberOfActivities;
            }
        });
    }
    extractFilters(headers: any) {
        return Object.values(headers);
    }
    setPermissions() {
        let viewReportPermission: Map<number, string> = new Map().set(288, 'outgoing');
        this.featuresService.checkUserPermissions(viewReportPermission);
        this.getOutgoingViewPermission = this.featuresService.getPermissionValue(288);
    }
}
