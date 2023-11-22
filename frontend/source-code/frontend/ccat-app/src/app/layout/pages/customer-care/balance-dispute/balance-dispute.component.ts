import {Component, OnInit, ViewChild} from '@angular/core';
import {Calendar} from 'primeng/calendar';
import {Table} from 'primeng/table';
import {Subscription} from 'rxjs';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {BalanceDisputeService} from 'src/app/core/service/customer-care/balance-dispute.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Defines} from 'src/app/shared/constants/defines';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-balance-dispute',
    templateUrl: './balance-dispute.component.html',
    styleUrls: ['./balance-dispute.component.scss'],
})
export class BalanceDisputeComponent implements OnInit {
    tab = 'summary';
    @ViewChild('dt') tableSummary: Table;
    constructor(
        private balanceDisputeService: BalanceDisputeService,
        private notepadService: NotepadService,
        private subscriberService: SubscriberService,
        private toastrService: ToastService
    ) {}
    newMatchModeOptions = [
        {label: 'Starts With', value: 'startsWith'},
        {label: 'Contains', value: 'contains'},
        {label: 'Equals', value: 'equals'},
    ];
    getAll = true;
    reasonDialog: boolean = false;
    reason: string = null;
    balanceSheetSummary;
    usageSheetSummary;
    details;
    dateTo;
    dateFrom = new Date (new Date().setDate(
        new Date().getDate() - JSON.parse(sessionStorage.getItem('reportDefaultSearchPeriod'))
    ));
    search = false;
    searchText: string;
    loading$ = this.balanceDisputeService.loading$;
    detailsCols;
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    classNameCon = '';
    totalRecords;
    offset = 0;
    fetchCount = 5;
    queryString = '';
    order = null;
    orderBy = null;
    dateTime = new Date();
    calSummarysheetRecordTotal(record: any[], detailName) {
        let totalRecord = {};
        let totalDebit = 0;
        let totalCredit = 0;
        record.forEach((entity) => {
            totalDebit += entity.Debit ? +entity.Debit : 0;
            totalCredit += entity.Credit ? +entity.Credit : 0;
        });
        totalRecord = {
            type: 'Total ' + Defines.balanceDisputeSummaryLables[detailName],
            Debit: totalDebit,
            Credit: totalCredit,
            isTotal: true,
        };
        return [...record, totalRecord];
    }
    addGrandBalances(balanceSheetSummary, details) {
        return [
            ...balanceSheetSummary,
            {
                name: 'Main balance grand total',
                data: [
                    {
                        type: ' ',
                        Debit: details.totalMBDebit,
                        Credit: details.totalMBCredit,
                        isGrandTotal: true,
                    },
                ],
            },
            {
                name: 'Dedicated account grand total',
                data: [
                    {
                        type: ' ',
                        Debit: details.totalDADebit,
                        Credit: details.totalDACredit,
                        isGrandTotal: true,
                    },
                ],
            },
        ];
    }
    shapeBalanceSheetSummary(balanceSheetSummary, isbalanceSheet: boolean, details?: any) {
        let finalBalanceSheet = [];
        for (let detail in balanceSheetSummary) {
            if (balanceSheetSummary[detail]?.length > 0 ) {
                let data = [...balanceSheetSummary[detail]];
                if (isbalanceSheet) {
                    data = this.calSummarysheetRecordTotal(balanceSheetSummary[detail], detail);
                }
                finalBalanceSheet.push({
                    name: isbalanceSheet ? Defines.balanceDisputeSummaryLables[detail] : detail,
                    data,
                });
            } else {
                if(typeof balanceSheetSummary[detail] !=='number'){
                    finalBalanceSheet.push({
                        name: isbalanceSheet ? Defines.balanceDisputeSummaryLables[detail] : detail,
                        data: [{Credit: null, Debit: null, type: null}],
                    });
                }
                
            }
        }

        if (isbalanceSheet) {
            finalBalanceSheet = this.addGrandBalances(finalBalanceSheet, details);
        }
        console.log('finalBalanceSheet', finalBalanceSheet);

        return finalBalanceSheet;
    }
    ngOnInit(): void {
        /*let intialFilter = {
            offset: 0,
            fetchCount: 5,
            isGetAll: true,
        };
        this.getReport(intialFilter);*/
        /*this.balanceDisputeService.balanceDisputeCheck().subscribe((res) => {
            console.log(res);
            this.extractDetailsColumnsordered(res.payload.Details.columnsOrderMap);
        });*/
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
            this.setResponsiveTableWidth();
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
            this.setResponsiveTableWidth();
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
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
    getReport(filterObj) {
        const currentDate = new Date();
        this.balanceDisputeService
            .getBalance({
                ...filterObj,
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                dateFrom: this.dateFrom
                    ? this.dateFrom.getTime()
                    : currentDate.setDate(
                          currentDate.getDate() - JSON.parse(sessionStorage.getItem('reportDefaultSearchPeriod'))
                      ),
                dateTo: this.dateTo ? this.dateTo.getTime() : new Date().getTime(),
            })
            .subscribe((res) => {
                this.totalRecords = res.payload.totalCount;
                this.balanceSheetSummary = this.shapeBalanceSheetSummary(
                    res.payload.balanceSummarySheet,
                    true,
                    res.payload['details']
                );
                this.usageSheetSummary = this.shapeBalanceSheetSummary(res.payload.usageSummarySheet, false);
                this.extractDetailsColumns(res.payload.details.columnNames);
                this.details = res.payload.details.transactionDetailsList;
            });
    }
    addReason() {
        let noteObj = {
            entry: this.reason,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Balance Dispute',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.reason,
                    },
                ],
            },
        };
        this.reasonDialog = false;
        this.notepadService.addNote(noteObj, JSON.parse(sessionStorage.getItem('msisdn'))).subscribe((success) => {
            this.offset = 0;
            this.fetchCount = 5;
            const offset = this.offset;
            const fetchCount = this.fetchCount;
            if (this.details) {
                this.tableSummary.lazy = false;
                this.tableSummary.reset();
                this.tableSummary._sortOrder = 1;
                this.tableSummary.lazy = true;
            }
            let filterObj = {
                offset,
                fetchCount,
                isGetAll: true,
            };
            this.tab = 'summary';
            this.getReport(filterObj);
            this.reason = null;

            this.getAll = false;
        });
    }

    switchTab(tab) {
        this.tab = tab;
    }

    extractDetailsColumns(data: any) {
        this.detailsCols = data;
    }
    getQueryString(event) {
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
        return filterQueryString;
    }
    loadBalanceDisputeData(event) {
        console.log('event', event);
        this.fetchCount = event.rows;
        this.offset = event.first;
        this.queryString = this.getQueryString(event);
        this.orderBy = event.sortField;
        this.order = event.sortOrder === 1 ? 1 : 2;
        let filterObj = {
            offset: event.first,
            fetchCount: event.rows,
            isGetAll: false,
            queryString: this.queryString,
            order: event.sortOrder === 1 ? 1 : 2,
            sortedBy: event.sortField,
        };
        this.getReport(filterObj);
    }
    exportBalanaceDisputeSheet() {
        let filterObject = {
            offset: this.offset,
            fetchCount: this.fetchCount,
            queryString: this.queryString,
            order: this.order,
            sortedBy: this.orderBy,
        };
        this.balanceDisputeService.exportBalanceSheet(filterObject).then(
            async (res) => {
                if (res.type === 'application/octet-stream') {
                    const a = document.createElement('a');
                    document.body.appendChild(a);
                    const blob: any = new Blob([res], {type: 'octet/stream'});
                    const url = window.URL.createObjectURL(blob);
                    a.href = url;
                    a.download = 'Usage Balance Dispute.xlsm';
                    a.click();
                    window.URL.revokeObjectURL(url);
                } else {
                    let errorResponse = JSON.parse(await res.text());
                    this.toastrService.error(errorResponse.statusMessage);
                }
            },
            (err) => {
                console.log(err);
            }
        );
    }
    exportDailySheet() {
        this.balanceDisputeService.exportDailyBalance().then(
            async (res) => {
                if (res.type === 'application/octet-stream') {
                    const a = document.createElement('a');
                    document.body.appendChild(a);
                    const blob: any = new Blob([res], {type: 'octet/stream'});
                    const url = window.URL.createObjectURL(blob);
                    a.href = url;
                    a.download = 'Usage Daily Report.csv';
                    a.click();
                    window.URL.revokeObjectURL(url);
                } else {
                    let errorResponse = JSON.parse(await res.text());
                    this.toastrService.error(errorResponse.statusMessage);
                }
            },
            (err) => {
                console.log(err);
            }
        );
    }
    clearFilters() {
        this.queryString = null;
        this.orderBy = null;
        this.order = null;
        this.tableSummary.lazy = false;
        this.tableSummary.reset();
        this.tableSummary._sortOrder = 1;
        this.tableSummary.lazy = true;
        this.getReport({
            offset: this.offset,
            fetchCount: this.fetchCount,
            queryString: this.queryString,
            order: this.order,
            sortedBy: this.orderBy,
            isGetAll: false,
        });
    }
    extractDetailsColumnsordered(columnsObj) {
        let colsArr = [];
        for (let col in columnsObj) {
            colsArr.push({[col]: columnsObj[col]});
        }
        console.log('colsArr', colsArr);
    }
    onAddDateTo(event, lastDate: Calendar) {
        if (!this.dateFrom) {
            this.dateTo = event;
        } else {
            const daysBetween = Math.round(event - this.dateFrom.getTime()) / (1000 * 60 * 60 * 24);
            if (daysBetween <= JSON.parse(sessionStorage.getItem('reportMaxSearchPeriod'))) {
                this.dateTo = event;
            } else {
                lastDate.value = null;
                this.toastrService.warning(
                    `Date Range greater than ${sessionStorage.getItem('reportMaxSearchPeriod')}`
                );
            }
        }
    }
    onAddDateFrom(event, firstDate: Calendar) {
        if (!this.dateTo) {
            this.dateFrom = event;
        } else {
            const daysBetween = Math.round(this.dateTo - event) / (1000 * 60 * 60 * 24);
            if (daysBetween <= JSON.parse(sessionStorage.getItem('reportMaxSearchPeriod'))) {
                this.dateFrom = event;
            } else {
                firstDate.value = null;
                this.toastrService.warning(
                    `Date Range greater than ${sessionStorage.getItem('reportMaxSearchPeriod')}`
                );
            }
        }
    }
}
