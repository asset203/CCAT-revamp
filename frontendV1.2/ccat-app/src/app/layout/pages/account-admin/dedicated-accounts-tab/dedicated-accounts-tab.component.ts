import {HttpService} from './../../../../core/service/http.service';
import {SubscriberService} from './../../../../core/service/subscriber.service';
import {Component, ElementRef, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {DatePipe} from '@angular/common';
import {Observable} from 'rxjs';
import {map, switchMap, tap, filter, take} from 'rxjs/operators';
import {ToastService} from 'src/app/shared/services/toast.service';
import {NavigationEnd, Router} from '@angular/router';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {Note} from 'src/app/shared/models/note.interface';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {InputNumber} from 'primeng/inputnumber';
import {MessageService} from 'src/app/shared/services/message.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';

export interface DedecatedAccount {
    balance: number;
    balancePT: string;
    description: string;
    description2: string;
    expiryDate: any;
    id: string;
    ratingFactor: any;
    unitType: number;
    unitTypeDesc: string;
    adjustmentAmount?: number;
    adjustmentMethod?: number;
    isDateEdited?: number;
}

@Component({
    selector: 'app-dedicated-accounts-tab',
    templateUrl: './dedicated-accounts-tab.component.html',
    styleUrls: ['./dedicated-accounts-tab.component.scss'],
})
export class DedicatedAccountsTabComponent implements OnInit {
    @Input() selectedType;
    @Input() selectedCode;
    @Input() readOnly: boolean;
    @Output() formSubmited = new EventEmitter <void>()
    @ViewChild('updateDedicatedAccountsBtn') updateDedicatedAccountsBtn: ElementRef;
    loading = true;
    id;
    dedicatedAccounts;
    dedicatedAccountList: any = [];
    editAccountdialog: boolean;
    currentDedicatedBalance;
    selectedAccount: DedecatedAccount;
    accountAddAmount;
    accountSubAmount;
    accountAdjustmentAmount = 0;
    accountAdjustmentMethod;
    accountExpiryDate;
    oldExpiryDate;
    disableSubAmount: boolean;
    disableAddAmount: boolean;
    accountSetAmount;

    todayDate = new Date();
    invalidDate: boolean;
    showEdit = true;
    permissions = {
        editDedicatedAccounts: false,
        addBalance: false,
        deductBalance: false,
    };
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    subscriberNumber;
    subscriberSubject;
    values = [];
    now=new Date ();
    yearRange = `${new Date ().getFullYear()}:2100`
    constructor(
        private datepipe: DatePipe,
        private SubscriberService: SubscriberService,
        private http: HttpService,
        private toasterService: ToastService,
        private router: Router,
        private featuresService: FeaturesService,
        private notepadService: NotepadService,
        private messageService: MessageService,
        private footPrintService: FootPrintService
    ) {}

    ngOnInit(): void {
        this.setPermissions();

        this.SubscriberService.subscriber$
            .pipe(
                map((subscriber) => subscriber?.subscriberNumber),
                take(1)
            )
            .subscribe((res) => (this.subscriberNumber = res));

        if (this.router.url === '/customer-care/voucherless-refill') {
            this.showEdit = false;
        }
        this.subscriberSubject = this.SubscriberService.subscriberSubject.value;
        this.dedicatedAccounts$.subscribe();
    }

    dedicatedAccountsExport() {
        this.updateDedicatedAccountsBtn.nativeElement.click();
    }

    showEditAccountDialog(account: DedecatedAccount) {
        console.log(account);
        this.selectedAccount = {...account};
        this.currentDedicatedBalance = this.values.filter(el=>el.id ==this.selectedAccount.id)[0].balance;
        this.id = account.id;
        this.accountExpiryDate = this.values.filter(el=>el.id ==this.selectedAccount.id)[0].date;
        this.editAccountdialog = true;
    }

    updateAccountList() {
        if (this.accountAddAmount) {
            this.selectedAccount.adjustmentAmount = this.accountAddAmount;
            this.selectedAccount.adjustmentMethod = 1;
            this.setValueId(this.selectedAccount.id, this.selectedAccount.balance + this.accountAddAmount);
        } else if (this.accountSubAmount) {
            this.selectedAccount.adjustmentAmount = this.accountSubAmount;
            this.selectedAccount.adjustmentMethod = 2;
            this.setValueId(this.selectedAccount.id, this.selectedAccount.balance - this.accountSubAmount);
        } else if (this.accountSetAmount) {
            this.selectedAccount.adjustmentAmount = this.accountSetAmount;
            this.selectedAccount.adjustmentMethod = 3;
            this.setValueId(this.selectedAccount.id, this.accountSetAmount);
        } else {
            this.selectedAccount.adjustmentMethod = -1;
        }

        if (this.selectedAccount.expiryDate == this.accountExpiryDate) {
            this.selectedAccount.isDateEdited = 0;
        } else {
            this.selectedAccount.isDateEdited = 1;
        }

        // assigning old expiry date to save it for footprint
        this.oldExpiryDate = this.selectedAccount.expiryDate;

        this.selectedAccount.expiryDate = new Date(this.accountExpiryDate).getTime();
        console.log('this.selectedid', this.selectedAccount.id);
        this.setDateID(this.selectedAccount.id,this.selectedAccount.expiryDate)
        if (!this.dedicatedAccountList.find((el) => el.id == this.selectedAccount.id)) {
            this.dedicatedAccountList.push(this.selectedAccount);
        } else {
            this.dedicatedAccountList = this.dedicatedAccountList.filter((el) => el.id != this.selectedAccount.id);
            this.dedicatedAccountList.push(this.selectedAccount);
        }
        //this.dedicatedAccountList.push(this.selectedAccount);

        if (this.dedicatedAccountList.length > 0 && this.dedicatedAccountList[0].adjustmentMethod == 1) {
            this.disableSubAmount = true;
        }
        if (this.dedicatedAccountList.length > 0 && this.dedicatedAccountList[0].adjustmentMethod == 2) {
            this.disableAddAmount = true;
        }
        this.editAccountdialog = false;
        console.log('detectedADD ', this.dedicatedAccountList);
    }

    updateDedicatedAccount() {
        this.ReasonDialog = true;
    }
    positiveValuesValidation() {
        if (this.accountAddAmount < 0) {
            return true;
        } else if (this.accountSubAmount < 0) {
            return true;
        } else {
            return false;
        }
    }

    dateValidation() {
        if (new Date(this.accountExpiryDate).getTime() < this.todayDate.getTime()) {
            this.invalidDate = true;
        } else {
            this.invalidDate = false;
        }
    }

    get dedicatedAccounts$(): Observable<any> {
        return this.SubscriberService.subscriber$.pipe(
            map((subscriber) => subscriber.subscriberNumber),
            switchMap((msisdn) =>
                this.http.request({
                    path: '/ccat/customer-balances/dedicated-accounts/get',
                    payload: {
                        token: JSON.parse(sessionStorage.getItem('session')).token,
                        msisdn,
                        accountModel: this.subscriberSubject,
                    },
                })
            ),
            map((dedicatedAcc) => dedicatedAcc.payload),
            tap((acc) => {
                this.loading = false;
                this.values = acc.map((el) => {
                    return {
                        id: el.id,
                        balance: el.balance,
                        orginalValue: el.balance,
                        date: el.expiryDate,
                    };
                });
                this.dedicatedAccounts = acc;
            })
        );
    }

    updateDedicatedAccounts$(list, transactionType, transactionCode): Observable<any> {
        const msisdn = this.SubscriberService.subscriberSubject.getValue().subscriberNumber;
        return this.http.request({
            path: '/ccat/customer-balances/dedicated-accounts/update',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
                msisdn,
                list,
                transactionCode,
                transactionType,
            },
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(19, 'editDedicatedAccounts')
            .set(3, 'addBalance')
            .set(4, 'deductBalance');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.editDedicatedAccounts = this.featuresService.getPermissionValue(19);
        this.permissions.addBalance = this.featuresService.getPermissionValue(3);
        this.permissions.deductBalance = this.featuresService.getPermissionValue(4);
    }
    submitReason() {
        console.log(this.dedicatedAccountList);
        if (this.dedicatedAccountList?.length > 0) {
            let noteObj = {
                entry: this.reason,
                footPrint: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Account Admin',
                    footPrintDetails: [
                        {
                            paramName: 'entry',
                            oldValue: '',
                            newValue: this.reason,
                        },
                    ],
                },
            };
            this.ReasonDialog = false;
            this.notepadService.addNote(noteObj, this.subscriberNumber).subscribe((success) => {
                const operator = JSON.parse(sessionStorage.getItem('session')).user;
                this.notes.unshift({
                    note: this.reason,
                    date: new Date().getTime(),
                    operator: operator.ntAccount,
                });
                // this.toasterService.success('Success', success.statusMessage);
            });

            // console.log('new expiry date', new Date(this.selectedAccount.expiryDate));
            this.updateDedicatedAccounts$(
                this.dedicatedAccountList,
                this.selectedType.id,
                this.selectedCode.id
            ).subscribe({
                next: (res) => {
                    console.log('resres', res);
                    this.disableSubAmount = false;
                    this.disableAddAmount = false;
                    this.dedicatedAccountList = [];
                    //this.dedicatedAccounts = this.dedicatedAccounts$;

                    if (res?.statusCode === 0) {
                        this.formSubmited.emit();
                        this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                        this.toasterService.success(this.messageService.getMessage(64).message);
                    }
                    else{
                        //this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn'))); 
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                    //his.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn'))); 
                },
            });
            // footprint
            let footprintObj: FootPrint = {
                machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'account-admin',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                tabName: 'Dedicated Accounts',
                footPrintDetails: [
                    {
                        paramName: 'Transaction Code',
                        oldValue: null,
                        newValue: this.selectedCode.id,
                    },
                    {
                        paramName: 'Transaction Type',
                        oldValue: null,
                        newValue: this.selectedType.id,
                    },
                    {
                        paramName: 'ID',
                        oldValue: this.id,
                        newValue: null,
                    },
                    {
                        paramName: 'Expiry Date',
                        oldValue: this.datepipe.transform(this.oldExpiryDate, 'dd/MM/yyyy'),
                        newValue: this.datepipe.transform(this.selectedAccount?.expiryDate, 'dd/MM/yyyy'),
                    },
                    {
                        paramName: 'Adjustment Amount',
                        oldValue: this.currentDedicatedBalance,
                        newValue: this.currentDedicatedBalance + this.accountAddAmount,
                    },
                    {
                        paramName: 'MSISDN',
                        oldValue: null,
                        newValue: this.subscriberNumber,
                    },
                ],
            };
            this.footPrintService.log(footprintObj);

            // Erasing popup form
            this.selectedAccount = null;
            this.accountAddAmount = null;
            this.accountSubAmount = null;
        } else {
            this.toasterService.warning('Dedicated Accounts is required');
        }
    }
    hideDialog() {
        this.accountAddAmount = null;
        this.accountSubAmount = null;
        this.accountAdjustmentAmount = null;
        this.accountAdjustmentMethod = null;
        this.accountExpiryDate = null;
        this.oldExpiryDate = null;
        this.disableSubAmount = false;
        this.disableAddAmount = false;
        this.editAccountdialog = false;
        this.accountSetAmount = null;
    }
    setValueId(id, balance) {
        const index = this.values.findIndex((el) => el.id == id);
        this.values[index].balance = balance;
    }
    setDateID(id, date) {
        const index = this.values.findIndex((el) => el.id == id);
        this.values[index].date = date;
    }
}
