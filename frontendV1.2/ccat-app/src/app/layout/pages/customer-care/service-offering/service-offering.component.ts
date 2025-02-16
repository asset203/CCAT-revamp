import {Component, OnInit, OnDestroy} from '@angular/core';
import {Subscription} from 'rxjs';
import {ServiceOfferingPlansService} from 'src/app/core/service/administrator/service-offering-plans.service';
import {ServiceOfferingCCService} from 'src/app/core/service/customer-care/service-offering-cc.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Bit} from 'src/app/shared/models/accountGroupCC.interface';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {ServiceOfferingCC} from 'src/app/shared/models/ServiceOfferingCC.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-service-offering',
    templateUrl: './service-offering.component.html',
    styleUrls: ['./service-offering.component.scss'],
})
export class ServiceOfferingComponent implements OnInit, OnDestroy {
    tableData: Bit[];
    dropDownValue;
    serviceOfferingList: ServiceOfferingCC[];
    orginalServiceOfferingList: ServiceOfferingCC[];
    currentServiceOffering: ServiceOfferingCC;
    updatedServiceOffering: ServiceOfferingCC;
    isFetchingList$ = this.loadingService.fetching$;
    permissions = {
        updateServiceOfferingPer: false,
        getServiceOffering: false,
    };
    constructor(
        private toastrService: ToastService,
        private messageService: MessageService,
        private serviceOfferingService: ServiceOfferingCCService,
        private footPrintService: FootPrintService,
        private subscriberService: SubscriberService,
        private loadingService: LoadingService,
        private serviceOfferingPlans: ServiceOfferingPlansService,
        private featuresService: FeaturesService
    ) {}
    loading$ = this.serviceOfferingService.loading$;
    types = [];
    serviceClassCode;
    serviceClassCodeSubscriber = new Subscription();
    msisdn;
    ngOnInit(): void {
        this.serviceClassCodeSubscriber = this.subscriberService.subscriberSubject.subscribe((subscriber) => {
            if (subscriber?.serviceClass?.code) {
                this.getServiceOfferings(subscriber?.serviceClass?.code);
            }

            this.serviceClassCode = subscriber?.serviceClass?.code;
            this.msisdn = subscriber?.subscriberNumber;
        });

        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Service Offering',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    ngOnDestroy(): void {
        this.serviceClassCodeSubscriber.unsubscribe();
    }
    getServiceOfferings(serviceClassCode) {
        this.loadingService.startFetchingList();
        this.serviceOfferingService.getAllServiceOffering(serviceClassCode).subscribe(
            (res) => {
                this.serviceOfferingList = JSON.parse(JSON.stringify(res?.payload?.serviceOfferings));
                this.orginalServiceOfferingList = JSON.parse(JSON.stringify(res?.payload?.serviceOfferings));
                this.currentServiceOffering = {...this.orginalServiceOfferingList[0]};
                this.updatedServiceOffering = {...this.serviceOfferingList[0]};
                this.tableData = this.updatedServiceOffering.bits;
                this.dropDownValue = this.serviceOfferingList[0].id;
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.loadingService.endFetchingList();
            }
        );
    }
    setTableDate(id: number) {
        this.updatedServiceOffering = this.orginalServiceOfferingList.filter((el) => el.id === id)[0];
        this.tableData = this.orginalServiceOfferingList.filter((el) => el.id === id)[0].bits;
    }
    submit() {
        this.serviceOfferingService
            .updateServiceOffering(this.currentServiceOffering, this.updatedServiceOffering)
            .subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(109).message);
                    this.getServiceOfferings(this.serviceClassCode);
                    this.subscriberService.loadSubscriber(this.msisdn);
                    this.dropDownValue = null;
                }
            });
    }
    setPermissions() {
        let serviceOfferingPermissions: Map<number, string> = new Map()
            .set(284, 'updateServiceOfferingPer')
            .set(17, 'getServiceOffering');
        this.featuresService.checkUserPermissions(serviceOfferingPermissions);
        this.permissions.updateServiceOfferingPer = this.featuresService.getPermissionValue(284);
        //this.permissions.getServiceOffering = this.featuresService.getPermissionValue(23);
    }
}
