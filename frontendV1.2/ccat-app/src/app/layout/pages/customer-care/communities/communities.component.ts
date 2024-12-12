import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subscription} from 'rxjs';
import {CommunitiesService} from 'src/app/core/service/customer-care/communities.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-communities',
    templateUrl: './communities.component.html',
    styleUrls: ['./communities.component.scss'],
})
export class CommunitiesComponent implements OnInit, OnDestroy {
    constructor(
        private toastrService: ToastService,
        private messageService: MessageService,
        private communityService: CommunitiesService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService,
        private subscriberService: SubscriberService
    ) {}
    ngOnDestroy(): void {
        this.subscriberSearchSubscription.unsubscribe();
    }
    communityList: any[] = [];
    targetlist: any[] = [];
    loading$ = this.communityService.loading;
    permissions = {
        getCommunities: false,
        updateCommunities: false,
    };
    subscriberSearchSubscription: Subscription;
    selectedList
    ngOnInit(): void {
        this.setPermissions();

        if (this.permissions.getCommunities) {
            this.getCommunites();
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'communities',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
        this.subscriberSearchSubscription = this.subscriberService.subscriber$.subscribe((res) => {
            this.getCommunites();
        });
    }
    getCommunites() {
        this.communityService.allCommunities$.subscribe((res) => {
            this.communityList = res?.payload?.unSelectedCommunities;
            this.targetlist = res?.payload?.selectedCommunities;
            this.selectedList = res?.payload?.selectedCommunities
        });
    }
    updateCommuinty() {
        this.communityService.updateCommunity(this.communityList, this.targetlist,this.selectedList).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success('Communities updated successfully');
                this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
            }
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(268, 'getCommunities')
            .set(215, 'updateCommunities');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getCommunities = this.featuresService.getPermissionValue(268);
        this.permissions.updateCommunities = this.featuresService.getPermissionValue(215);
    }
}
