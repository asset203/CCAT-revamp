import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subscription} from 'rxjs';
import {HttpService} from 'src/app/core/service/http.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {map} from 'rxjs/operators';
import {PrepaidService} from 'src/app/core/service/customer-care/prepaid.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SendSmsService} from 'src/app/core/service/customer-care/send-sms.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';

@Component({
    selector: 'app-prepaid-vbp',
    templateUrl: './prepaid-vbp.component.html',
    styleUrls: ['./prepaid-vbp.component.scss'],
})
export class PrepaidVBPComponent implements OnInit , OnDestroy {
    codes;
    prepaidForm: FormGroup;
    permissions = {
        checkPrepaidVbp: false,
        unsubscribePrepaidVbp: false,
        subscribePrepaidVbp: false,
        viewPrepaidVbp: false,
    };

    constructor(
        private http: HttpService,
        private fb: FormBuilder,
        private prepaidService: PrepaidService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService,
        private sendSmsService: SendSmsService,
        private subscriberService : SubscriberService
    ) {}
    
    types: any;
    isSubscribed;
    loading$ = this.prepaidService.loading$;
    sendSms=true;
    subscriberSearchSubscription : Subscription;
    checkSubscription(){
        this.prepaidService.checkSubscription().subscribe((res) => {
            this.isSubscribed = res.payload.isSubscribed;
        });
    }
    ngOnDestroy(): void {
        this.subscriberSearchSubscription.unsubscribe()
    }
    ngOnInit(): void {
        this.setPermissions();
        this.transactionsType$();

        if (this.permissions.checkPrepaidVbp) {
           // this.checkSubscription()
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
        this.createForm();

        // footprint
        let footprintObj = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Prepaid VBP',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            sendSms:this.sendSms?1:0,
        };
        this.footPrintService.log(footprintObj);
        this.subscriberSearchSubscription = this.subscriberService.subscriber$
            .subscribe((res) => {
                if (this.permissions.checkPrepaidVbp) {
                    this.checkSubscription()
                }
            });
    }

    createForm() {
        this.prepaidForm = this.fb.group({
            transactionType: [null, [Validators.required]],
            transactionCode: [null, Validators.required],
            transactionAmount: [null, Validators.required],
        });
    }
    transactionsType$() {
        if (this.permissions.viewPrepaidVbp) {
            this.types = this.http
                .request({
                    path: '/ccat/transactions/type/get',
                    payload: {
                        token: JSON.parse(sessionStorage.getItem('session')).token,
                        featureId: 333,
                    },
                })
                .pipe(map((types) => types.payload));
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    getTransactionsCode(event) {
        console.log("event",event)
        this.codes = this.transactionsCode$(event.value.id);
    }

    transactionsCode$(typId): Observable<any> {
        return this.http
            .request({
                path: '/ccat/transactions/code/get',
                payload: {
                    token: JSON.parse(sessionStorage.getItem('session')).token,
                    typeId: typId,
                },
            })
            .pipe(map((code) => code.payload));
    }
    unSubscribe() {
            this.prepaidService.prepaidUnSubscribe().subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(89).message);
                    if (this.sendSms) {
                        const smsObj = {
                            actionName: 'Unsubscribe',
                            transactionType: this.prepaidForm.value?.transactionType?.id,
                            transactionCode: this.prepaidForm.value?.transactionCode?.id,
                            transactionAmount: this.prepaidForm?.value?.transactionAmount,
                        };
                        this.sendSmsService.sendSMS(smsObj).subscribe();  
                    }
                    this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                }
            }); 
    }
    subscribe() {
        if (this.prepaidForm.valid) {
            console.log("formValue",this.prepaidForm.value)
            let reqObj = {
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Prepaid VBP',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                },
                ...this.prepaidForm.value,
            };
            this.prepaidService.prepaidSubscribe(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(88).message);
                    this.checkSubscription()
                    if (this.sendSms) {
                        const smsObj = {
                            actionName: 'Subscribe',
                            transactionType: this.prepaidForm.value?.transactionType?.id,
                            transactionCode: this.prepaidForm.value?.transactionCode?.id,
                            transactionAmount: this.prepaidForm.value?.transactionAmount,
                        };
                        this.sendSmsService.sendSMS(smsObj).subscribe();
                    }
                    this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
                }
            });
        } else {
            this.toastrService.error(this.messageService.getMessage(91).message);
        }
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(310, 'checkPrepaidVbp')
            .set(309, 'subscribePrepaidVbp')
            .set(308, 'unsubscribePrepaidVbp')
            .set(333, 'viewPrepaidVbp');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.checkPrepaidVbp = this.featuresService.getPermissionValue(310);
        this.permissions.subscribePrepaidVbp = this.featuresService.getPermissionValue(309);
        this.permissions.unsubscribePrepaidVbp = this.featuresService.getPermissionValue(308);
        this.permissions.viewPrepaidVbp = this.featuresService.getPermissionValue(333);
    }
}
