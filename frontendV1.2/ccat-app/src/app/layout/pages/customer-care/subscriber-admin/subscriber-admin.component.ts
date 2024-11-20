import {AfterViewChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {merge, Observable} from 'rxjs';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {subscriberAccount} from 'src/app/shared/models/subscriber-account.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-subscriber-admin',
    templateUrl: './subscriber-admin.component.html',
    styleUrls: ['./subscriber-admin.component.scss'],
})
export class SubscriberAdminComponent implements OnInit, AfterViewChecked {
    subscriberSearch = this.subscriberService.subscriber$;
    //subscriberProduct = this.subscriberService.subscriberProduct$;
    canViewUsage = true;
    //canViewSubscriberProduct = true;
    canViewServiceClass = true;
    loading$ = this.subscriberService.loading$;

    ///////
    tabIndex = 0;
    currentBucketDropdown = [];
    currentBucketIndex = 0;
    constructor(
        private subscriberService: SubscriberService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef,
        private footPrintService: FootPrintService
    ) {}
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }

    ngOnInit(): void {
        // this.subscriberService.loadSubscriber("01099900306");

        let subscriberAdminFeatures: Map<number, string> = new Map()
            .set(22, 'viewUsageConsumption')
            //.set(11, 'viewSubscriberProduct')
            .set(26, 'viewServiceClass');
        this.featuresService.checkUserPermissions(subscriberAdminFeatures);
        this.canViewUsage = this.featuresService.getPermissionValue(22);
        // this.canViewSubscriberProduct = this.featuresService.getPermissionValue(11);
        this.canViewServiceClass = this.featuresService.getPermissionValue(26);

        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'subscriber-admin',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }

    adjustBucketDropdown(menuItems) {
        this.currentBucketDropdown = [];
        for (let i = 0; i < menuItems.length; i++) {
            this.currentBucketDropdown.push({
                label: `<p class="${this.currentBucketIndex === i ? 'p-title selected' : 'p-title'}">${
                    menuItems[i].product.name
                }</p>`,
                escape: false,
                command: () => {
                    this.currentBucketIndex = i;
                },
                id: i,
            });
        }
    }
    mergeProducts(arr) {
        let merged = [];
        for (let index = 0; index < arr.length; index++) {
            for (let index2 = 0; index < arr[index].bucket.length; index2++) {
                const element = arr[index].bucket[index2];
                merged.push(element);
            }
        }
        return merged;
    }
}
