import {AfterViewChecked, ChangeDetectorRef, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {AccountHistoryService} from 'src/app/core/service/customer-care/account-history.service';
import {Note} from 'src/app/shared/models/note.interface';
import {saveAs} from 'file-saver';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {accountHistory} from 'src/app/shared/models/account-history.interface';
import {MessageService} from 'src/app/shared/services/message.service';
import {FilterMatchMode, PrimeNGConfig} from 'primeng/api';
import {MatchMode} from 'src/app/shared/enum/match-mode';
import {ToastService} from 'src/app/shared/services/toast.service';
import {environment} from 'src/environments/environment';
import {Table} from 'primeng/table';
import {Subscription} from 'rxjs';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {map} from 'rxjs/operators';
const baseURL = environment.url;

@Component({
    selector: 'app-account-history',
    templateUrl: './account-history.component.html',
    styleUrls: ['./account-history.component.scss'],
})
export class AccountHistoryComponent implements OnInit, AfterViewChecked, OnDestroy {
    constructor(
        private fb: FormBuilder,
        private accountHistoryService: AccountHistoryService,
        private notepadService: NotepadService,
        private featuresService: FeaturesService,
        private toastService: ToastService,
        private messageService: MessageService,
        private config: PrimeNGConfig,
        private cdr: ChangeDetectorRef,
        private subscriberService: SubscriberService,
        private loadingService: LoadingService
    ) {}
    selectedMsisdn;
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }
    accountHistoryForm: FormGroup;

    today = new Date();
    // default dates
    todayAfterMonth = new Date();
    dateMonthBefore = new Date(
        new Date().setDate(new Date().getDate() - JSON.parse(sessionStorage.getItem('accountHistorySearchPeriod')))
    );

    loading$ = this.accountHistoryService.loading$;
    allAccountHistory: accountHistory[];
    allAccountHistorys: accountHistory[] = [];
    totalRecords = 0;
    rowsDisplayed = 5;
    allDataLoading = false;
    // dialog
    ReasonDialog = false;
    reason;
    detailsDialog = false;
    exportDetailsFlag = false;
    showDetailsFlag = false;

    rowSelectedData;
    index = 0;
    rowSelectedArray = [];
    clonedRowSelectedArray = [];
    itemToExport;

    notes: Note[] = [];

    // permissions
    GET_ALL_ACCOUNT_HISTORY = false;
    GET_ACCOUNT_HISTORY_DETAILS = false;

    // tab change
    tab = 'Account History';
    value = '';

    // types filter array
    types = [
        {name: 'Activations', value: 'Activations'},
        {name: 'Adjustment', value: 'Adjustment'},
        {name: 'Balance Transfer', value: 'Balance Transfer'},
        {name: 'Bonus Adjustment', value: 'Bonus Adjustment'},
        {name: 'Disconnections', value: 'Disconnections'},
        {name: 'Language Change', value: 'Language Change'},
        {name: 'LifeCycle Change', value: 'LifeCycle Change'},
        {name: 'Service Class Change', value: 'Service Class Change'},
        {name: 'Voucher Refill', value: 'Voucher Refill'},
    ];
    typeValue = '';
    @ViewChild('table')
    private table: Table;
    filtersOff = true;

    filters = {
        accountStatus: '',
        amount: '',
        balance: '',
        subType: '',
        type: '',
        first: 0,
        globalFilter: null,
        multiSortMeta: undefined,
        rows: this.rowsDisplayed,
        sortField: undefined,
        sortOrder: 1,
    };
    getAllData = true;
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    subscriberSearchSubscription: Subscription;

    accountColumns = [];
    isFetchingList$ = this.loadingService.fetching$;
    ngOnInit(): void {
        this.setPermissions();
        this.createForm();
        this.getAllDate();
        this.getAllAccountHistoryColumn();
        this.setFilterModes();
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
        });
        this.subscriberSearchSubscription = this.subscriberService.subscriber$
            .pipe(map((subscriber) => subscriber?.subscriberNumber))
            .subscribe((res) => {
                //this.subscriberNumber = res;
                this.getAllDate();
                this.getAllAccountHistoryColumn();
                this.setFilterModes();
            });
    }
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
        this.subscriberSearchSubscription.unsubscribe();
    }

    setPermissions() {
        let advanced: Map<number, string> = new Map()
            .set(152, 'GET_ALL_ACCOUNT_HISTORY')
            .set(153, 'GET_ACCOUNT_HISTORY_DETAILS');
        this.featuresService.checkUserPermissions(advanced);
        this.GET_ALL_ACCOUNT_HISTORY = this.featuresService.getPermissionValue(152);
        this.GET_ACCOUNT_HISTORY_DETAILS = this.featuresService.getPermissionValue(153);
    }
    createForm() {
        this.accountHistoryForm = this.fb.group({
            dateFrom: [this.dateMonthBefore, Validators.required],
            dateTo: [this.today, Validators.required],
        });
        // this.onSubmit();
    }
    getAllDate() {
        const daysBetween =
            Math.floor((this.accountHistoryForm.value.dateTo - this.accountHistoryForm.value.dateFrom ) / (1000 * 60 * 60 * 24));
            console.log("daysBetween",daysBetween)
        if (daysBetween > JSON.parse(sessionStorage.getItem('accountHistoryMaxSearchPeriod'))) {
            this.toastService.warning(
                `Date Range greater than ${sessionStorage.getItem('accountHistoryMaxSearchPeriod')}`
            );
        } else {
            this.filterAction(this.filters);
        }
    }
    setFilterModes() {
        // configure primeNg filtering menu
        this.config.filterMatchModeOptions = {
            text: [FilterMatchMode.STARTS_WITH, FilterMatchMode.CONTAINS, FilterMatchMode.EQUALS],
            numeric: [
                FilterMatchMode.EQUALS,
                FilterMatchMode.NOT_EQUALS,
                FilterMatchMode.LESS_THAN,
                FilterMatchMode.LESS_THAN_OR_EQUAL_TO,
                FilterMatchMode.GREATER_THAN,
                FilterMatchMode.GREATER_THAN_OR_EQUAL_TO,
            ],
            date: [
                FilterMatchMode.DATE_IS,
                FilterMatchMode.DATE_IS_NOT,
                FilterMatchMode.DATE_BEFORE,
                FilterMatchMode.DATE_AFTER,
            ],
        };
    }
    onRowSelect(event) {
        this.selectedMsisdn = event.data.subscriber;
        if (!event?.target?.id) {
            this.rowSelectedArray = [];
            this.rowSelectedData = event?.data?.details;
            // this.submitReason()
            this.ReasonDialog = true;
            this.showDetailsFlag = true;
        }
    }
    submitReason() {
        let noteObj = {
            entry: this.reason,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Account History',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.reason,
                    },
                ],
            },
        };
        // add notepad reason
        console.log('eslected data', this.selectedMsisdn);
        this.notepadService.addNote(noteObj, this.selectedMsisdn).subscribe((success) => {
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
            // this.toasterService.success('Success', success.statusMessage);
        });
        this.ReasonDialog = false;
        this.reason = '';

        if (this.exportDetailsFlag) {
            let details = this.itemToExport.details;
            let arr = [];
            for (const key in details) {
                arr.push({
                    key: key,
                    data: details[key],
                });
            }
            import('xlsx').then((xlsx) => {
                const worksheet = xlsx.utils.json_to_sheet(arr);
                const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
                const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
                this.saveAsExcelFile(excelBuffer, 'users');
                arr = [];
            });
            this.itemToExport = '';
            this.exportDetailsFlag = false;
            this.showDetailsFlag = false;
        }
        if (this.showDetailsFlag) {
            this.showHistoryDetails();
        }
    }
    showHistoryDetails() {
        this.detailsDialog = true;

        for (const key in this.rowSelectedData) {
            this.rowSelectedArray.push({
                key: key,
                data: this.rowSelectedData[key],
            });
        }

        // open account details dialog
        this.detailsDialog = true;

        // Shallow Copy array for searching
        this.clonedRowSelectedArray = [...this.rowSelectedArray];

        this.showDetailsFlag = false;
    }
    backToHistory() {
        this.tab = 'Account History';
        this.rowSelectedData = '';
        this.rowSelectedArray = [];
        this.detailsDialog = false;
    }
    exportDetailsExcel(item, event) {
        this.ReasonDialog = true;
        this.exportDetailsFlag = true;
        this.itemToExport = item;
        console.log(item.details['Msisdn']);
        this.selectedMsisdn = item.subscriber;
    }

    detailsSearch() {
        this.rowSelectedArray = this.clonedRowSelectedArray.filter((el) =>
            el.key.toLowerCase().includes(this.value.toLowerCase())
        );
    }
    filterAction(event) {
        console.log('this.filterdate', event);
        let formData = this.generateFormData(event);
        this.allAccountHistory = [];
        this.getAccountHistory(formData);
    }

    generateConcatedString(filters) {
        let tempFilters = {};
        for (const key in filters) {
            if (filters[key][0]?.value !== null) {
                if (key === 'balance' || key === 'amount' || key === 'type') {
                    tempFilters[key] = `${filters[key][0]?.value},3`;
                } else {
                    tempFilters[key] = `${filters[key][0]?.value},${MatchMode[filters[key][0]?.matchMode]}`;
                }
            }
        }
        //forming queryString payload
        let concatedString = '';
        let queryString = {
            ...tempFilters,
        };
        for (const key in queryString) {
            let isLast = Object.keys(queryString)[Object.keys(queryString).length - 1] === key;
            concatedString += `${key}=${queryString[key]}${isLast ? '' : '&'}`;
        }
        console.log('concated String is -> ', concatedString);
        return concatedString;
    }

    generateFormData(event) {
        this.filters = event;
        console.log('eventtt', event);
        this.rowsDisplayed = event.rows;
        this.filtersOff = false;
        // forming filters
        console.log('event is -> ', event.filters);
        const concatedString = this.generateConcatedString(event?.filters);
        let formData = {
            queryString: concatedString,
            fetchCount: this.rowsDisplayed,
            offset: event.first,
            order: event?.sortOrder === 1 ? 1 : 2,
            dateFrom: this.accountHistoryForm.value.dateFrom?.getTime(),
            dateTo: this.accountHistoryForm.value.dateTo?.getTime(),
            isGetAll: this.getAllData,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Account History',
                tabName: 'Account History',
                footPrintDetails: [
                    {
                        paramName: 'Date From',
                        oldValue: null,
                        newValue: this.accountHistoryForm.value.dateFrom?.getTime(),
                    },
                    {
                        paramName: 'Date To',
                        oldValue: null,
                        newValue: this.accountHistoryForm.value.dateTo?.getTime(),
                    },
                ],
            },
        };
        if (event?.sortField) {
            formData['sortedBy'] = event?.sortField;
        }
        return formData;
    }
    getAccountHistory(formData) {
        this.allDataLoading = true;
        this.loadingService.startFetchingList();
        this.accountHistoryService.getFilteredAccountHistory(formData).subscribe(
            (resp) => {
                if (resp?.statusCode === 0) {
                    this.allDataLoading = false;
                    this.allAccountHistory = resp?.payload?.subscriberActivityList;
                    this.totalRecords = resp?.payload?.totalNumberOfActivities;
                    this.getAllData = false;
                    this.loadingService.endFetchingList();
                    this.allDataLoading = false;
                } else {
                    this.loadingService.endFetchingList();
                    this.allAccountHistory = [];
                    this.allDataLoading = false;
                }
            },
            (err) => {
                this.loadingService.endFetchingList();
                this.allAccountHistory = [];
            }
        );
    }
    getAllAccountHistoryColumn() {
        this.accountHistoryService.getAllAccountHistoryColumns().subscribe({
            next: (resp) => {
                if (resp?.statusCode === 0) {
                    for (const key in resp?.payload?.odsActivityHeaderMap) {
                        this.accountColumns = this.accountColumns.concat(resp?.payload?.odsActivityHeaderMap[key]);
                    }
                    console.log('return account history column', this.accountColumns);
                }
            },
        });
    }

    exportSubscriberActivities() {
        let data = {
            token: JSON.parse(sessionStorage.getItem('session')).token,
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            footprint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Account History',
                tabName: 'Save Account History',
                footPrintDetails: null,
            },
        };
        fetch(`${baseURL}/ccat/account-history/export/subscriber-activities`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then((response) => {
                // return response.arrayBuffer();
                return response.arrayBuffer();
            })
            .then((result) => {
                console.log(result);
                this.saveAsCSVFile(result, 'Subscriber_Activities');
            });
    }
    resetHistory() {
        this.typeValue = '';
        this.filtersOff = true;
        this.table.reset();
        this.getAllData = true;
        this.filterAction(this.filters);
    }
    pageFilterAction(event) {
        console.log(event);
    }
    sortFilterAction(event) {
        console.log(event);
    }
    filterFilterAction(event) {
        console.log(event);
    }
    exportAccountExcel() {
        let clone = [...this.allAccountHistory];
        for (let index = 0; index < clone.length; index++) {
            delete clone[index].activityId;
            delete clone[index].details;
        }

        if (this.rowSelectedData === '' || this.rowSelectedData === undefined) {
            this.toastService.warning(this.messageService.getMessage(41).message);
            this.tab = 'Account History';
        } else {
            import('xlsx').then((xlsx) => {
                const worksheet = xlsx.utils.json_to_sheet(clone);
                const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
                const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
                this.saveAsExcelFile(excelBuffer, 'users');
            });
        }
    }

    exportSubscriberActivityDetails() {
        let data = {
            token: JSON.parse(sessionStorage.getItem('session')).token,
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            footprint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Account History',
                tabName: 'Save Account History',
                footPrintDetails: null,
            },
        };
        fetch(`${baseURL}/ccat/account-history/export/subscriber-activity-details`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then((response) => {
                // return response.arrayBuffer();
                return response.arrayBuffer();
            })
            .then((result) => {
                console.log(result);
                this.saveAsCSVFile(result, 'Subscriber_Activities_Details');
            });
    }
    saveAsExcelFile(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.csv';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
    saveAsCSVFile(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.csv';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
}
