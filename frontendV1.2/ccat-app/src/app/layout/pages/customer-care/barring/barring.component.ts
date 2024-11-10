import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {BarringsService} from 'src/app/core/service/customer-care/barrings.service';
import {ServiceClassService} from 'src/app/core/service/customer-care/service-class.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-barring',
    templateUrl: './barring.component.html',
    styleUrls: ['./barring.component.scss'],
})
export class BarringComponent implements OnInit, OnDestroy {
    constructor(
        private barringsService: BarringsService,
        private fb: FormBuilder,
        private serviceClassService: ServiceClassService,
        private messageService: MessageService,
        private toastService: ToastService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService,
        private subscriberService: SubscriberService
    ) {}
    ngOnDestroy(): void {
        this.subscriberSubscribtion.unsubscribe();
    }

    currentSubscriber;
    barringsReason;
    tab = 'barring';
    barringForm: FormGroup;
    refillChecked = true;
    isTempBlocked = false;
    isNegativeBalBarring = false;
    barFlag = false;
    unbarFlag = false;
    types = [
        {id: 1, name: 'Customer'},
        {id: 2, name: 'Service Provider'},
    ];
    loading$ = this.barringsService.loading$;
    permissions = {
        unbarRefillBarring: false,
        unbarTemporaryBlocked: false,
        barTemporaryBlocked: false,
    };
    subscriberSubscribtion: Subscription;
    ngOnInit(): void {
        this.setPermissions();
        this.createForm();
        this.subscriberSubscribtion = this.subscriberService.subscriber$.subscribe((res) => {
            this.baringInit();
        });
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Barrings',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    baringInit() {
        this.currentSubscriber = this.serviceClassService.getCurrentSubscriber();
        this.isTempBlocked = this.currentSubscriber?.isTempBlocked;
        this.isNegativeBalBarring = this.currentSubscriber?.isNegativeBalBarring;

        if (this.currentSubscriber) {
            this.barringsService.getBarringReason(this.currentSubscriber?.isTempBlocked);
            this.barringsReason = this.barringsService?.barringsReasoneSubject?.value;
        }
        if (this.currentSubscriber?.isTempBlocked == true) {
            this.barFlag = false;
            this.unbarFlag = true;
        }
        // else if (this.currentSubscriber?.isTempBlocked == false && this.currentSubscriber?.isNegativeBalBarring == true) {
        //   this.barFlag = false;
        //   this.unbarFlag = true;
        // }
        // else if (this.currentSubscriber?.isTempBlocked == false && this.currentSubscriber?.isNegativeBalBarring == false) {
        //   this.barFlag = true;
        //   this.unbarFlag = false;
        // }
        else {
            this.barFlag = true;
            this.unbarFlag = false;
        }
    }
    createForm() {
        this.barringForm = this.fb.group({
            barringReason: ['', [Validators.required]],
            requestedBy: ['', Validators.required],
        });
    }

    switchTab(tab) {
        this.tab = tab;
        this.createForm();
        this.refillChecked = this.currentSubscriber.refillBarredUntil !== null ? true : false;
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Barrings',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            tabName: tab,
        };
        this.footPrintService.log(footprintObj);
    }
    barTemp() {
        if (this.barringForm.valid) {
            this.barringsService.barTempBlocked(this.barringForm.value);
        } else {
            this.toastService.error(this.messageService.getMessage(91).message);
        }
    }
    unbarTemp() {
        console.log('unbartemp');
        if (this.barringForm.valid) {
            this.barringsService.unbarTempBlocked(this.barringForm.value);
        } else {
            this.toastService.error(this.messageService.getMessage(91).message);
        }
    }
    unbarRefill() {
        if (this.barringForm.valid) {
            this.barringsService.unbarRefillBarring(this.barringForm.value);
        } else {
            this.toastService.error(this.messageService.getMessage(91).message);
        }
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(214, 'unbarRefillBarring')
            .set(213, 'unbarTemporaryBlocked')
            .set(212, 'barTemporaryBlocked');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.barTemporaryBlocked = this.featuresService.getPermissionValue(214);
        this.permissions.unbarRefillBarring = this.featuresService.getPermissionValue(213);
        this.permissions.barTemporaryBlocked = this.featuresService.getPermissionValue(212);
    }
}
