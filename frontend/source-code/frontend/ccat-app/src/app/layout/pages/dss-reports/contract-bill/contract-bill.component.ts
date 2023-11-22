import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ReportsService} from 'src/app/core/service/reports.service';
import {Defines} from 'src/app/shared/constants/defines';
import {ContractBillRequest} from 'src/app/shared/models/ReportRequest.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-contract-bill',
    templateUrl: './contract-bill.component.html',
    styleUrls: ['./contract-bill.component.scss'],
})
export class ContractBillComponent implements OnInit {
    reportData;
    reportsHeaders;
    loading$ = this.reportsService.loading;
    contractBillForm: FormGroup;
    totalRecords;
    rows = 5;
    globalFilters = [];
    numOfBillValues = Defines.CONTRACT_BILL_NUM_OF_BILLS;
    reportTypesValues = Defines.CONTRACT_BILL_REPORT_TYPES;
    newMatchModeOptions = [
        {label: 'Starts With', value: 'startsWith'},
        {label: 'Contains', value: 'contains'},
        {label: 'Equals', value: 'equals'},
    ];
    getContractBillReportPermission: boolean;

    constructor(
        private reportsService: ReportsService,
        private fb: FormBuilder,
        private featuresService: FeaturesService
    ) {}

    ngOnInit(): void {
        this.setPermissions();
        this.contractBillForm = this.fb.group({
            numOfBill: [null, [Validators.required]],
            reportType: [null, [Validators.required]],
        });
    }
    loadReport(event) {
        let filterQueryString = '';
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
        const contactBillForm = this.contractBillForm.value;
        if (contactBillForm.numOfBill && contactBillForm.reportType) {
            const reportDataReq: ContractBillRequest = {
                reportType: contactBillForm.reportType,
                numOfBill: contactBillForm.numOfBill,
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

    submitDates() {
        const contactBillFormValue = this.contractBillForm.value;
        const reportDataReq: ContractBillRequest = {
            pagination: {
                fetchCount: 5,
                offset: 0,
                isGetAll: true,
            },
            numOfBill: contactBillFormValue.numOfBill,
            reportType: contactBillFormValue.reportType,
        };
        this.setDataOfTable(true, reportDataReq);
    }
    setDataOfTable(isFirstRequest: boolean, reportDataReq: ContractBillRequest) {
        this.reportsService.allContractBill$(reportDataReq).subscribe((res) => {
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
        let viewReportPermission: Map<number, string> = new Map().set(289, 'contractBill');
        this.featuresService.checkUserPermissions(viewReportPermission);
        this.getContractBillReportPermission = this.featuresService.getPermissionValue(289);
    }
}
