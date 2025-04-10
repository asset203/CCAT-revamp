import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { SuperFlexShareService } from 'src/app/core/service/customer-care/super-flex-share.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { SendSmsService } from './../../../../core/service/customer-care/send-sms.service';
import { Subscription } from 'rxjs';
import { SubscriberService } from 'src/app/core/service/subscriber.service';


@Component({
  selector: 'app-super-flex-view',
  templateUrl: './super-flex-view.component.html',
  styleUrls: ['./super-flex-view.component.scss']
})
export class SuperFlexViewComponent implements OnInit , OnDestroy {
  tab = 'mi-add-on';
  types = [1];
  addOnesModel = null;
  addOnForm: FormGroup;
  loading$ = this.superFlexShareService.loading$;
  thresholdModel = null;
  thresholdProductList = [];
  msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
  sendSMS = false;
  sendSMSThreshold = false;
  thresholdDialog = false;
  thresholdValue = null;
  subscriberSearchSubscription  :Subscription
  classNameCon = '';
  isopened: boolean;
  isopenedNav: boolean;
  isOpenedSubscriber: Subscription;
  isOpenedNavSubscriber: Subscription;
  constructor(private superFlexShareService: SuperFlexShareService,
    private fb: FormBuilder,
    private SendSmsService: SendSmsService,
    private footPrintService: FootPrintService,
    private subscriberService : SubscriberService) { }
  ngOnDestroy(): void {
    this.subscriberSearchSubscription.unsubscribe();
    this.isOpenedSubscriber.unsubscribe();
    this.isOpenedNavSubscriber.unsubscribe();
  }

  ngOnInit(): void {
    this.superFlexShareService.getAddOns().subscribe({
      next: (resp) => { this.addOnesModel = resp?.payload; }
    });

    this.createForms();
    // footprint
    let footprintObj: FootPrint = {
      machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
      profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
      pageName: 'Super Flex View',
      msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
    };
    this.footPrintService.log(footprintObj);
    this.subscriberSearchSubscription = this.subscriberService.subscriber$
            .subscribe((res) => {
                //this.subscriberNumber = res;
                this.superFlexShareService.getAddOns().subscribe({
                  next: (resp) => { this.addOnesModel = resp?.payload; }
                });
            });
    this.handelMenusOpen();
  }
  switchTab(tab) {
    this.tab = tab;
    if (this.tab === 'mi-threshold') {
      this.superFlexShareService.getMIThresholds().subscribe({
        next: (resp) => {
          this.thresholdModel = resp.payload;
          for (let i = 0; i < resp?.payload?.productList?.length; i++) {
            this.thresholdProductList.push(...resp?.payload?.productList[i].quotas)

          }
          console.log(this.thresholdProductList)
        }

      })
    }
  }
  createForms() {
    this.addOnForm = this.fb.group({
      packageId: ['', Validators.required]
    });
  }
  activateAddOn() {

    this.superFlexShareService.activateAddOn(this.addOnForm.value.packageId.id);

    if (this.sendSMS) {
      let smsObject = {
        actionName: 'ACTIVATE_OFFER',
        templateParam: {
          offerName: this.addOnForm.value.packageId.name,
          offerDescription: this.addOnForm.value.packageId.description
        },
        footprintModel: {
          machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
          profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
          pageName: 'Super Flex View',
          msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
          footPrintDetails: [
            {
              paramName: 'OptedIn Add-ons',
              oldValue: this.addOnesModel?.currentAddonsList.join(','),
              newValue: this.addOnForm.value.packageId.name,
            }
          ],
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMS = false;
      });
    }
    // reset data 
    this.addOnForm.reset();
  }

  deactivateAddOn() {
    this.superFlexShareService.deactivateAddOn();
    if (this.sendSMS) {
      let smsObject = {
        actionName: 'ACTIVATE_OFFER',
        templateParam: {
          offerName: this.addOnForm.value.packageId.name || '',
          footprintModel: {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Super Flex View',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            footPrintDetails: [
              {
                paramName: 'OptedIn Add-ons',
                oldValue: this.addOnesModel?.currentAddonsList.join(','),
                newValue: this.addOnForm.value.packageId.name || '',
              }
            ],
          },
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMS = false;
      });
    }
  }
  openThresholdDialog() {
    this.thresholdDialog = true;
  }
  activateThreshold() {
    // this.thresholdModel.thresholdAmount
    this.superFlexShareService.activateThreshold(this.thresholdModel.thresholdAmount, this.thresholdValue);
    if (this.sendSMSThreshold) {
      let smsObject = {
        actionName: 'ACTIVATE_THRESHOLD',
        templateParam: {
          thresholdOldAmount: this.thresholdModel.thresholdAmount,
          thresholdNewAmount: this.thresholdValue
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMSThreshold = false;

      });
    }
    this.thresholdDialog = false;
  }
  deactivateThreshold() {
    this.superFlexShareService.deactivateThreshold();
    if (this.sendSMSThreshold) {
      let smsObject = {
        actionName: 'DEACTIVATE_THRESHOLD',
        templateParam: {
        },
        footprintModel: {
          machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
          profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
          pageName: 'Super Flex View',
          msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
          sendsms:this.sendSMS?1:0
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMSThreshold = false;
      });
    }
  }
  stopMI() {
    this.superFlexShareService.stopMI();
    if (this.sendSMSThreshold) {
      let smsObject = {
        actionName: 'STOP_MI',
        templateParam: {
        },
        footprintModel: {
          machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
          profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
          pageName: 'Super Flex View',
          msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
          sendsms:this.sendSMS?1:0
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMSThreshold = false;
      });
    }
  }
  deactivateStopMI() {
    this.superFlexShareService.deactivateStopMI();
    if (this.sendSMSThreshold) {
      let smsObject = {
        actionName: 'DEACTIVATE_STOP_MI',
        templateParam: {
        },
        footprintModel: {
          machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
          profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
          pageName: 'Super Flex View',
          msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
          sendsms:this.sendSMS?1:0
        },
      };
      this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
        this.sendSMSThreshold = false;
      });
    }
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
handelMenusOpen(){
    this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
        this.isopened = isopened;
        this.setResponsiveTableWidth();
    });
    this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
        this.isopenedNav = isopened;
        this.setResponsiveTableWidth();
    });
}
}
