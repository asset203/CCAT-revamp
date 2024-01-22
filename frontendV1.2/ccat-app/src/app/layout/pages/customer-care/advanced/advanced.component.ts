import { AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit, ViewChild } from '@angular/core';
import { AdvancedService } from 'src/app/core/service/customer-care/advanced.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { AddAccountTabComponent } from './add-account-tab/add-account-tab.component';
import { DisconnectTabComponent } from './disconnect-tab/disconnect-tab.component';

@Component({
    selector: 'app-advanced',
    templateUrl: './advanced.component.html',
    styleUrls: ['./advanced.component.scss'],
})
export class AdvancedComponent implements OnInit, AfterViewChecked {
    constructor(
        private advancedService: AdvancedService,
        private cdr: ChangeDetectorRef,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService
    ) { }

    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }

    tab = 'Add Subscriber or Account';

    // Permissions Booleans
    INSTALL_SUBSCRIBER = false;
    DISCONNECT_SUBSCRIBER = false;

    @ViewChild(AddAccountTabComponent) addAccountTab: AddAccountTabComponent;
    @ViewChild(DisconnectTabComponent) disconnectTab: DisconnectTabComponent;
    loading$ = this.advancedService.loading$;

    switchTab(tab) {
        this.tab = tab;
    }

    ngOnInit(): void {
        let advanced: Map<number, string> = new Map().set(119, 'INSTALL_SUBSCRIBER').set(120, 'DISCONNECT_SUBSCRIBER');
        this.featuresService.checkUserPermissions(advanced);
        this.INSTALL_SUBSCRIBER = this.featuresService.getPermissionValue(119);
        this.DISCONNECT_SUBSCRIBER = this.featuresService.getPermissionValue(120);

        // foot print load
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Advanced',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    submitAddAccount() {
        let formData = this.addAccountTab.addAccountForm.value;
        let formObj = {
            subscriberMsisdn: formData.msisdn,
            languageId: formData.language ? +formData.language.value : null,
            organization: 'HOST',
            temporeryBlocked: +formData.tempBlock,
            accountGroupId: formData.accountGroup ? formData.accountGroup.groupId : null,
            serviceOfferingId: formData.serviceOffering ? formData.serviceOffering.planId : null,
            serviceClassId: formData.serviceClass.code,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Advanced',
                footPrintDetails: [
                    {
                        paramName: 'SIM',
                        oldValue: '',
                        newValue: formData.msisdn,
                    },
                    {
                        paramName: 'Temporary Blocked',
                        oldValue: '',
                        newValue: +formData.tempBlock,
                    },
                    {
                        paramName: 'Service Offering ID',
                        oldValue: '',
                        newValue: formData.serviceOffering ? formData.serviceOffering.planId : null,
                    },
                ],
            },
        };
        this.advancedService.submitSubscriber(formObj).subscribe(res=>{
            if(res.statusCode===0){
                this.addAccountTab.addAccountForm.reset();
            }
        });
    }
    submitDisconnection() {
        let formData = this.disconnectTab.disconnectForm.value;

        let formObj = {
            subscriberMsisdn: formData.msisdn,
            disconnectFromCharging: +formData.diconnect,
            disconnectReasonId: formData.reason.id,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Advanced',
                footPrintDetails: [
                    {
                        paramName: 'Subscriber Msisdn',
                        oldValue: '',
                        newValue: formData.msisdn,
                    },
                    {
                        paramName: 'Disconnect From Charging System',
                        oldValue: !+formData.diconnect,
                        newValue: +formData.diconnect,
                    },
                    {
                        paramName: 'Disconnection Reason Id',
                        oldValue: '',
                        newValue: formData.reason.id,
                    },
                ],
            },
        };
        this.advancedService.submitDisconnect(formObj).subscribe(res=>{
            if(res.statusCode===0){
                this.disconnectTab.disconnectForm.reset()
            }
        });
    }
}
