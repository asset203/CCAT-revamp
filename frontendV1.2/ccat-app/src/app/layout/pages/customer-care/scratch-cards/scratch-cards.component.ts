import { MessageService } from 'src/app/shared/services/message.service';
import { Component, OnInit } from '@angular/core';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FootPrintService } from 'src/app/core/service/foot-print.service';

@Component({
    selector: 'app-scratch-cards',
    templateUrl: './scratch-cards.component.html',
    styleUrls: ['./scratch-cards.component.scss'],
})
export class ScratchCardsComponent implements OnInit {
    constructor(
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private toasterService: ToastService,
        private footPrintService: FootPrintService
    ) { }
    businessPlan = ['item1', 'item2', 'item3'];
    permissions = {
        viewVoucherInfo: false,
        viewVoucherBasedRefill: false,
        viewOverScratch: false,
    };
    tab = 'voucher-info';

    ngOnInit(): void {
        this.setPermissions();
        if (
            !this.permissions.viewOverScratch ||
            !this.permissions.viewVoucherBasedRefill ||
            !this.permissions.viewOverScratch
        ) {
            //this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Scratch cards',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn'))
        }
        this.footPrintService.log(footprintObj)
    }
    switchTab(tab) {
        this.tab = tab;
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Scratch cards',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            tabName: tab
        }
        this.footPrintService.log(footprintObj)
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(302, 'viewVoucherInfo')
            .set(304, 'viewVoucherBasedRefill')
            .set(305, 'viewOverScratch');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.viewVoucherInfo = this.featuresService.getPermissionValue(302);
        this.permissions.viewVoucherBasedRefill = this.featuresService.getPermissionValue(304);
        this.permissions.viewOverScratch = this.featuresService.getPermissionValue(305);
    }
}
