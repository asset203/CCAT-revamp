import {SendSmsService} from './../../../../core/service/customer-care/send-sms.service';
import {HttpService} from './../../../../core/service/http.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {DatePipe} from '@angular/common';
import {map, switchMap, take, tap} from 'rxjs/operators';
import {Observable, Subscription} from 'rxjs';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {Note} from 'src/app/shared/models/note.interface';
import {MessageService} from 'src/app/shared/services/message.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';

interface BalanceAndDate {
    supervisionFeePeriodExpiryDate: any;
    supervisionFeePeriod: any;
    serviceFeePeriod: any;
    serviceFeePeriodExpiryDate: any;
}
@Component({
    selector: 'app-account-admin-tab',
    templateUrl: './account-admin-tab.component.html',
    styleUrls: ['./account-admin-tab.component.scss'],
})
export class AccountAdminTabComponent implements OnInit, OnDestroy {
    @Input() selectedType;
    @Input() selectedCode;
    @Output() formSubmited = new EventEmitter <void>()
    @ViewChild('updateBalanceAndDateBtn') updateBalanceAndDateBtn: ElementRef;
    loading = false;

    customerBalances = this.SubscriberService.subscriber$;
    accountAdminSubscription: Subscription;
    newBalance;
    oldBalance;
    addAmount: number;
    subAmount: number;
    adjustmentAmount;
    adjustmentMethod;
    serviceFeePeriodOld;
    supervisionFeePeriodOld;

    serviceFeeDialog: boolean;
    serviceFeePeriodDialog: boolean;
    superVisionFeeDialog: boolean;
    superVisionFeePeriodDialog: boolean;
    permissions = {
        addBalance: false,
        deductBalance: false,
    };
    sendSMS: boolean = true;
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    subscriberNumber;
    subscriberSubscribtion: Subscription;
    newBalanceToDisplay;
    
    constructor(
        private datepipe: DatePipe,
        private SubscriberService: SubscriberService,
        private http: HttpService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private notepadService: NotepadService,
        private messageService: MessageService,
        private footPrintService: FootPrintService,
        private SendSmsService: SendSmsService
    ) {}
    ngOnDestroy(): void {
        this.subscriberSubscribtion.unsubscribe();
    }

    ngOnInit(): void {
        this.setPermissions();
        this.subscriberSubscribtion = this.SubscriberService.subscriber$
            .pipe(
                map((subscriber) => subscriber?.subscriberNumber),
                take(1)
            )
            .subscribe((res) => (this.subscriberNumber = res));
    }

    balanceAndDateExport() {
        this.updateBalanceAndDateBtn.nativeElement.click();
    }
    calculateAddBalance(balance) {
        // this.oldBalance = balance;

        this.subAmount = null;
        this.newBalanceToDisplay = balance + this.addAmount;
    }
    calculateSubBalance(balance) {
        this.addAmount = null;
        this.newBalanceToDisplay = balance - this.subAmount;
    }

    toggleReasonDialog() {
        this.ReasonDialog = true;
    }

    // updateBalanceAndDate(balanceAndDate: BalanceAndDate) {

    // }

    showServiceFeeDialog() {
        this.serviceFeeDialog = true;
    }
    showServiceFeePeriodDialog(old) {
        this.serviceFeePeriodDialog = true;
        this.serviceFeePeriodOld = old;
    }
    showSuperVisionFeeDialog() {
        this.superVisionFeeDialog = true;
    }
    showSuperVisionFeePeriodDialog(old) {
        this.superVisionFeePeriodDialog = true;
        this.supervisionFeePeriodOld = old;
    }

    positiveValuesValidation() {
        if (this.addAmount < 0) {
            return true;
        } else if (this.subAmount < 0) {
            return 1;
        } else {
            return false;
        }
    }

    updateBalanceAndDate$(balanceAndDate, transactionType, transactionCode): Observable<any> {
        const msisdn = this.SubscriberService.subscriberSubject.getValue().subscriberNumber;
        return this.http.request({
            path: '/ccat/customer-balances/balance-and-date/update',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
                msisdn,
                transactionType,
                transactionCode,
                ...balanceAndDate,
            },
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(3, 'addBalance').set(4, 'deductBalance');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.addBalance = this.featuresService.getPermissionValue(3);
        this.permissions.deductBalance = this.featuresService.getPermissionValue(4);
    }

    submitReason(balanceAndDate: BalanceAndDate) {
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
        this.notepadService.addNote(noteObj, JSON.parse(sessionStorage.getItem('msisdn'))).subscribe((success) => {
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
            // this.toasterService.success('Success', success.statusMessage);
        });

        this.reason = null;

        if (this.addAmount) {
            this.adjustmentAmount = this.addAmount;
            this.adjustmentMethod = 1;
        } else if (this.subAmount) {
            this.adjustmentAmount = this.subAmount;
            this.adjustmentMethod = 2;
        } else {
            this.adjustmentMethod = 0;
            this.addAmount = null;
        }

        if (!this.serviceFeePeriodOld) {
            this.serviceFeePeriodOld = null;
        }
        if (!this.supervisionFeePeriodOld) {
            this.supervisionFeePeriodOld = null;
        }

        const {supervisionFeePeriodExpiryDate, supervisionFeePeriod, serviceFeePeriod, serviceFeePeriodExpiryDate} =
            balanceAndDate;

        const updatedBalanceAndDate = {
            supervisionFeeDate: new Date(supervisionFeePeriodExpiryDate).getTime(),
            supervisionFeePeriod,
            serviceFeePeriod,
            serviceFeeDate: new Date(serviceFeePeriodExpiryDate).getTime(),
            adjustmentAmount: this.adjustmentAmount,
            adjustmentMethod: this.adjustmentMethod,
            serviceFeePeriodOld: this.serviceFeePeriodOld,
            supervisionFeePeriodOld: this.supervisionFeePeriodOld,
        };

        // this.balanceAndDate.supervisionFeeDate = this.datepipe.transform(this.superVisionFeeDate, 'dd/MM/yyyy');

        this.accountAdminSubscription = this.updateBalanceAndDate$(
            updatedBalanceAndDate,
            this.selectedType.id,
            this.selectedCode.id
        ).subscribe({
            next: (res) => {
                if (res?.statusCode === 0) {
                    this.formSubmited.emit()
                    this.addAmount = null;
                    this.subAmount = null;
                    this.toasterService.success(this.messageService.getMessage(64).message);
                    if (this.sendSMS) {
                        let smsObject = {
                            actionName: 'Change_Language',
                            templateParam: {
                                oldValue: this.oldBalance,
                                newValue: this.newBalance,
                                adjustmentAmount: updatedBalanceAndDate.adjustmentAmount,
                            },
                        };
                        if (updatedBalanceAndDate.adjustmentMethod == 1) {
                            smsObject.actionName = 'ADD_BALANCE';
                            smsObject.templateParam.newValue =
                                this.oldBalance + updatedBalanceAndDate?.adjustmentAmount;
                        } else {
                            smsObject.actionName = 'DEDUCT_BALANCE';
                            smsObject.templateParam.newValue =
                                this.oldBalance - updatedBalanceAndDate?.adjustmentAmount;
                        }

                        this.SendSmsService.sendSMS(smsObject).subscribe();

                    }
                    this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                    this.newBalanceToDisplay=null
                }
                else{
                    this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                }
            },
            error: (err) => {
                this.toasterService.error('Error', err);
                this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
            },
        });
        // footprint
        let footprintObj = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'account-admin',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            tabName: 'Account Administration',
            sendSms:this.sendSMS?1:0,
            footPrintDetails: [
                {
                    paramName: 'Adjustment Amount',
                    oldValue: balanceAndDate['balance'],
                    newValue: +balanceAndDate['balance'] + this.adjustmentAmount,
                },
            ],
        };
        this.footPrintService.log(footprintObj);
    }
}
