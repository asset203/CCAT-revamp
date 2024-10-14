import {Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {NgxSmartModalService} from 'ngx-smart-modal';
import {ToastrService} from 'ngx-toastr';
import {Table} from 'primeng/table';
import {Observable, Subscription} from 'rxjs';
import {switchMap, take, tap} from 'rxjs/operators';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {UsageCounterService} from 'src/app/core/service/customer-care/usage-counter.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {UsageCounter} from 'src/app/shared/models/usage-counter.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';

@Component({
    selector: 'app-usage-counter',
    templateUrl: './usage-counter.component.html',
    styleUrls: ['./usage-counter.component.scss'],
})
export class UsageCounterComponent implements OnInit , OnDestroy{
    constructor(
        private toastService: ToastrService,
        private usageCounterService: UsageCounterService,
        private subscriberService: SubscriberService,
        private messageService: MessageService,
        private featuresService: FeaturesService,
        private notepadService: NotepadService,
        private router: Router,
        private footPrintService: FootPrintService,
        private loadingService: LoadingService
    ) {}
    ngOnDestroy(): void {
        this.subscriberSearchSubscription.unsubscribe()
    }
    usageCountersList: UsageCounter[];
    loading$: Observable<boolean> = this.usageCounterService.loading$;
    selectedUsage: UsageCounter;
    modalIsOpen: boolean;
    editMode = false;
    reasonModal = false;
    reason: string;
    subscriberNumber = '';
    reqObject: any;
    permissions = {
        getAllUsages: false,
        addUsage: false,
        updateUsage: false,
    };
    search = false;
    searchText: string;
    isFetchingList$ = this.loadingService.fetching$;
    subscriberSearchSubscription :Subscription
    ngOnInit(): void {
        this.setPermissions();
        // foot print load
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Usage Counters',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
        this.subscriberSearchSubscription = this.subscriberService.subscriber$
            .subscribe((res) => {
                this.getUsageCountersList();
            });
    }
    getUsageCountersList() {
        if (this.permissions.getAllUsages) {
            this.loadingService.startFetchingList();
            this.usageCounterService.getUsageCountersList(JSON.parse(sessionStorage.getItem('msisdn'))).subscribe(
                (usageList) => {
                    this.loadingService.endFetchingList();
                    this.usageCountersList = usageList;
                },
                (err) => {
                    this.loadingService.endFetchingList();
                    this.usageCountersList = [];
                }
            );
        } else {
            this.toastService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }

    editUsageCounter(usageCounter: UsageCounter) {
        console.log('usageCounter', usageCounter);

        this.editMode = true;
        this.selectedUsage = usageCounter;
        this.modalIsOpen = true;
    }
    openAddDialog() {
        this.editMode = false;
        this.modalIsOpen = true;
    }
    submitUsageCounter(usageCounter: UsageCounter) {
        this.modalIsOpen = false;
        const reqBody = {
            ...usageCounter,
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Usage Counters new',
                footPrintDetails: [
                    {
                        paramName: 'Usage Type',
                        oldValue: null,
                        newValue: usageCounter.usageTypeId === 1 ? 'counter value' : 'monetary value',
                    },
                    {
                        paramName: 'ID',
                        oldValue: null,
                        newValue: usageCounter.id,
                    },
                    {
                        paramName: 'VALUE',
                        oldValue: null,
                        newValue: usageCounter.value,
                    },
                ],
            },
        };
        this.reqObject = reqBody;
        this.reasonModal = true;
    }
    submitReason() {
        let noteObj = {
            entry: this.reason,
            footprintModel: {
                msisdn:JSON.parse(sessionStorage.getItem('msisdn')),
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Usage Counter',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.reason,
                    },
                ],
            },
        };
        console.log("noteObj",noteObj)
        this.reasonModal = false;
        this.notepadService.addNote(noteObj, JSON.parse(sessionStorage.getItem('msisdn'))).subscribe(
            (success) => {
                this.sendAddOrEditUsageRequest();
            },
            (error) => {
                this.sendAddOrEditUsageRequest();
            }
        );
        this.reason = '';
    }
    sendAddOrEditUsageRequest() {
        if (!this.editMode) {
            this.usageCounterService.addUsageCounter(this.reqObject).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastService.success(this.messageService.getMessage(18).message, 'Success');
                    this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                }
            });
        } else {
            this.reqObject.counterValue = this.reqObject.value;
            delete this.reqObject['value'];
            this.usageCounterService.updateUsageCounter(this.reqObject).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastService.success(this.messageService.getMessage(19).message, 'Success');
                    this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                }
            });
        }
    }
    hideModal() {
        this.modalIsOpen = false;
        this.selectedUsage = null;
    }
    setPermissions() {
        let usageCounterPermissions: Map<number, string> = new Map()
            .set(66, 'getAllUsages')
            .set(69, 'addUsage')
            .set(68, 'updateUsage');
        this.featuresService.checkUserPermissions(usageCounterPermissions);
        this.permissions.getAllUsages = this.featuresService.getPermissionValue(66);
        this.permissions.addUsage = this.featuresService.getPermissionValue(69);
        this.permissions.updateUsage = this.featuresService.getPermissionValue(68);
    }
    selectRow(counter) {
        // save threshold in localstorage
        localStorage.setItem('usageCounterData', JSON.stringify(counter));
        // navigate to threshold page
        this.router.navigate([`customer-care/usage-counter-threshold/${counter.id}`]);
    }
    clear(table: Table) {
        table.clear();
    }
}
