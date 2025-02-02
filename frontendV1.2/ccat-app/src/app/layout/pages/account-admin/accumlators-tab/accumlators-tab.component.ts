import {HttpService} from './../../../../core/service/http.service';
import {SubscriberService} from './../../../../core/service/subscriber.service';
import {Component, ElementRef, EventEmitter, Input, OnDestroy, OnInit, Output, ViewChild} from '@angular/core';
import {DatePipe} from '@angular/common';
import {Observable, Subscription} from 'rxjs';
import {map, switchMap, take, tap} from 'rxjs/operators';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {Note} from 'src/app/shared/models/note.interface';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {InputNumber} from 'primeng/inputnumber';
import {MessageService} from 'src/app/shared/services/message.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
export interface Accumlator {
    description: string;
    id: string;
    resetDate: any;
    startDate: any;
    value: any;
    adjustmentAmount?: number;
    adjustmentMethod?: number;
    isDateEdited?: number;
    isReset: boolean;
}
@Component({
    selector: 'app-accumlators-tab',
    templateUrl: './accumlators-tab.component.html',
    styleUrls: ['./accumlators-tab.component.scss'],
})
export class AccumlatorsTabComponent implements OnInit, OnDestroy {
    @Input() selectedType;
    @Input() selectedCode;
    @Output() formSubmited = new EventEmitter<void>();
    @ViewChild('updateAccumalotrsBtn') updateAccumalotrsBtn: ElementRef;
    loading = true;
    id;
    accumulatorsList: any = [];
    selectedAccumulator: Accumlator;
    accumulatorAddAmount;
    accumulatorSubAmount;
    accumulatorAdjustmentAmount;
    accumulatorAdjustmentMethod;
    accumulatorStartDate;
    accumulatorDialog: boolean;
    todayDate = new Date();
    invalidDate: boolean;

    accumulators;
    disableSubAmount: boolean;
    disableAddAmount: boolean;

    permissions = {
        editAccumulators: false,
        addBalance: false,
        deductBalance: false,
    };
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    subscriberNumber;
    currentAccBalance;
    values = [];
    oldAccumlator;
    accumaltorSubscriber = new Subscription();
    constructor(
        private SubscriberService: SubscriberService,
        private http: HttpService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private notepadService: NotepadService,
        private messageService: MessageService
    ) {}
    ngOnDestroy(): void {
        this.accumaltorSubscriber.unsubscribe();
    }

    ngOnInit(): void {
        this.setPermissions();
        this.SubscriberService.subscriber$
            .pipe(
                map((subscriber) => subscriber.subscriberNumber),
                take(1)
            )
            .subscribe((res) => (this.subscriberNumber = res));
        this.accumaltorSubscriber = this.accumulators$.subscribe();
    }
    accumlatorsExport() {
        this.updateAccumalotrsBtn.nativeElement.click();
    }
    ShowAccumulatorDialog(accumulator) {
        this.oldAccumlator = {...accumulator};
        this.currentAccBalance = this.values.filter((el) => el.id == this.oldAccumlator.id)[0].value;
        this.accumulatorDialog = true;
        this.selectedAccumulator = {...accumulator};
        if (!this.selectedAccumulator.isReset) {
            this.selectedAccumulator.isReset = false;
        }
        this.accumulatorStartDate = new Date(accumulator.startDate);
        this.id = accumulator.id;
    }
    updateAccumulatorList(isReset: boolean) {
        if (isReset) {
            this.selectedAccumulator.adjustmentAmount = 0;
            this.selectedAccumulator.adjustmentMethod = 0;
            this.setValueId(this.selectedAccumulator.id, 0);
        } else {
            if (this.accumulatorAddAmount) {
                this.selectedAccumulator.adjustmentAmount = this.accumulatorAddAmount;
                this.selectedAccumulator.adjustmentMethod = 1;
                this.setValueId(
                    this.selectedAccumulator.id,
                    this.selectedAccumulator.value + this.accumulatorAddAmount
                );
            } else if (this.accumulatorSubAmount) {
                this.selectedAccumulator.adjustmentAmount = this.accumulatorSubAmount;
                this.selectedAccumulator.adjustmentMethod = 2;
                this.setValueId(
                    this.selectedAccumulator.id,
                    this.selectedAccumulator.value - this.accumulatorSubAmount
                );
            } else {
                this.selectedAccumulator.adjustmentMethod = -1;
                //this.setValueId(this.selectedAccumulator.id,0)
            }
        }

        console.log('inside updateAccumlatorList', this.accumulatorsList, this.selectedAccumulator);

        if (!this.accumulatorsList.find((el) => el.id == this.selectedAccumulator.id)) {
            this.accumulatorsList.push(this.selectedAccumulator);
            console.log('inside updateAccumlatorList if', this.accumulatorsList, this.selectedAccumulator);
        } else {
            this.accumulatorsList = this.accumulatorsList.filter((el) => el.id != this.selectedAccumulator.id);
            this.accumulatorsList.push(this.selectedAccumulator);
            console.log('inside updateAccumlatorList elese', this.accumulatorsList, this.selectedAccumulator);
        }
        //this.accumulatorsList.push(this.selectedAccumulator);
        if (this.accumulatorsList.length > 0 && this.accumulatorsList[0].adjustmentMethod == 1) {
            this.disableSubAmount = true;
        }
        if (this.accumulatorsList.length > 0 && this.accumulatorsList[0].adjustmentMethod == 2) {
            this.disableAddAmount = true;
        }
        this.accumulatorDialog = false;
    }

    updateAccumulators() {
        this.ReasonDialog = true;
    }

    positiveValuesValidation() {
        if (this.accumulatorAddAmount < 0) {
            return true;
        } else if (this.accumulatorSubAmount < 0) {
            return true;
        } else {
            return false;
        }
    }
    dateValidation() {
        if (new Date(this.accumulatorStartDate).getTime() < this.todayDate.getTime()) {
            this.invalidDate = true;
        } else {
            this.invalidDate = false;
        }
    }

    get accumulators$(): Observable<any> {
        return this.SubscriberService.subscriber$.pipe(
            map((subscriber) => subscriber.subscriberNumber),
            switchMap((msisdn) =>
                this.http.request({
                    path: '/ccat/customer-balances/accumulators/get',
                    payload: {
                        token: JSON.parse(sessionStorage.getItem('session')).token,
                        msisdn,
                    },
                })
            ),
            map((accumulators) => accumulators.payload),
            tap((acc) => {
                this.loading = false;
                this.values = acc.map((el) => {
                    return {
                        id: el.id,
                        value: el.value,
                        orginalValue: el.value,
                        date: el.startDate,
                        resetDate: el.resetDate,
                        orginalResetDate: el.resetDate,
                    };
                });
                this.accumulators = acc;
            })
        );
    }
    updateAccumulators$(list, transactionType, transactionCode): Observable<any> {
        let accumlatorNewValue = 0;
        if (this.selectedAccumulator?.adjustmentMethod === 1) {
            accumlatorNewValue = this.oldAccumlator?.value + this.selectedAccumulator?.adjustmentAmount;
        } else {
            accumlatorNewValue = this.oldAccumlator?.value - this.selectedAccumulator?.adjustmentAmount;
        }
        const msisdn = this.SubscriberService.subscriberSubject.getValue().subscriberNumber;
        return this.http.request({
            path: '/ccat/customer-balances/accumulators/update',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
                msisdn,
                list,
                transactionType,
                transactionCode,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'account-admin',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    tabName: 'Accumlators',
                    footPrintDetails: [
                        {
                            paramName: 'Transaction Code',
                            oldValue: null,
                            newValue: transactionCode,
                        },
                        {
                            paramName: 'Transaction Type',
                            oldValue: null,
                            newValue: transactionType,
                        },
                        {
                            paramName: 'ID',
                            oldValue: null,
                            newValue: transactionType,
                        },
                        {
                            paramName: 'Start Date',
                            oldValue: new Date(this.oldAccumlator?.startDate),
                            newValue: new Date(this.selectedAccumulator?.startDate),
                        },
                        {
                            paramName: 'Adjustment Amount',
                            oldValue: this.oldAccumlator?.value,
                            newValue: accumlatorNewValue,
                        },
                    ],
                },
            },
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(25, 'editAccumulators')
            .set(3, 'addBalance')
            .set(4, 'deductBalance');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.editAccumulators = this.featuresService.getPermissionValue(25);
        this.permissions.addBalance = this.featuresService.getPermissionValue(3);
        this.permissions.deductBalance = this.featuresService.getPermissionValue(4);
    }
    submitReason(enterClick?: boolean) {
        if ((enterClick && this.reason) || !enterClick) {
            if (this.accumulatorsList?.length === 0) {
                this.toasterService.warning('Accumlator List is required');
            } else {
                console.log('Accumlator List', this.accumulatorsList);

                let noteObj = {
                    entry: this.reason,
                    footprintModel: {
                        machineName: sessionStorage.getItem('machineName')
                            ? sessionStorage.getItem('machineName')
                            : null,
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
                });
                this.updateAccumulators$(this.accumulatorsList, this.selectedType?.id, this.selectedCode?.id).subscribe(
                    {
                        next: (res) => {
                            if (res?.statusCode === 0) {
                                this.formSubmited.emit();
                                this.toasterService.success(this.messageService.getMessage(64).message);
                                this.accumulatorsList = [];
                                this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                            } else {
                                this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                            }
                            this.disableSubAmount = false;
                            this.disableAddAmount = false;
                        },
                        error: () => {
                            this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                        },
                    }
                );

                // erasing popup form
                this.selectedAccumulator = null;
                this.accumulatorAddAmount = null;
                this.accumulatorSubAmount = null;
            }
        }
    }
    hideDialog() {
        this.accumulatorAddAmount = null;
        this.accumulatorSubAmount = null;
        this.accumulatorAdjustmentAmount = null;
        this.accumulatorAdjustmentMethod = null;
        this.accumulatorStartDate = null;
        this.accumulatorDialog = false;
        this.disableSubAmount = false;
        this.disableAddAmount = false;
    }
    addReset(accumulator) {
        this.oldAccumlator = {...accumulator};
        //this.selectedAccumulator = {...accumulator};

        this.selectedAccumulator = JSON.parse(JSON.stringify(accumulator));
        console.log(' this.selectedAccumulator', this.selectedAccumulator);
        if (!this.selectedAccumulator.isReset) {
            console.log('if isreset', !this.selectedAccumulator.isReset);

            this.selectedAccumulator.isReset = false;
            const index = this.values.findIndex((el) => el.id == this.selectedAccumulator.id);
            this.values[index].resetDate = this.values[index].orginalResetDate;
            this.values[index].value = this.values[index].orginalValue;
            this.accumulatorsList = this.accumulatorsList.filter((el) => el.id !== this.selectedAccumulator.id);
        } else {
            console.log('else isreset', !this.selectedAccumulator.isReset, this.selectedAccumulator);
            this.accumulatorStartDate = new Date(accumulator.startDate);
            this.id = accumulator.id;
            const index = this.values.findIndex((el) => el.id == this.selectedAccumulator.id);
            this.values[index].resetDate = new Date();
            this.selectedAccumulator.resetDate = new Date().getTime();
            console.log('else isreset', !this.selectedAccumulator.isReset, this.selectedAccumulator);
            this.updateAccumulatorList(this.selectedAccumulator.isReset);
        }
    }
    setValueId(id, value) {
        const index = this.values.findIndex((el) => el.id == id);
        this.values[index].value = value;
    }
    setDateID(id, date) {
        const index = this.values.findIndex((el) => el.id == id);
        this.values[index].date = date;
    }
    clearSub() {
        this.accumulatorSubAmount = null;
    }
    clearAdd() {
        this.accumulatorAddAmount = null;
    }
}
