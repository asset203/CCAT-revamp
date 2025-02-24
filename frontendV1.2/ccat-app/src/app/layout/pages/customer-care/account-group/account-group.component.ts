import {Component, OnInit ,OnDestroy} from '@angular/core';
import { Subscription } from 'rxjs';
import {AccountGroupCCService} from 'src/app/core/service/customer-care/account-group-cc.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import {AccountGroupCC, Bit} from 'src/app/shared/models/accountGroupCC.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-account-group',
    templateUrl: './account-group.component.html',
    styleUrls: ['./account-group.component.scss'],
})
export class AccountGroupComponent implements OnInit ,OnDestroy{
    dropDownValue;
    tableData: Bit[];
    accountGroups: AccountGroupCC[];
    orginalAccountGroups: AccountGroupCC[];
    currentAccountGroup: AccountGroupCC;
    updatedAccount: AccountGroupCC;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private accountGroupService: AccountGroupCCService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private subscriberService : SubscriberService,
        private loadingService : LoadingService,
        private featuresService : FeaturesService
    ) {}
    
    loading$ = this.accountGroupService.loading$;
    msisdn;
    serviceClassId;
    subscriberSubscription = new Subscription();
    accountGroupNumber;
    permissions = {
        updateAccountGroup: false,
    };
    ngOnInit(): void {
        this.setPermissions();
        this.subscriberSubscription=this.subscriberService.subscriberSubject.subscribe(subscriber=>{
            this.msisdn = subscriber?.subscriberNumber
            this.serviceClassId = subscriber?.serviceClass?.code
            this.accountGroupNumber = subscriber?.accountGroupNumber
            if(this.msisdn &&this.serviceClassId ){
                this.getAccountGroups(this.msisdn);
            }
            
        })
        
    }
    ngOnDestroy(): void {
        this.subscriberSubscription.unsubscribe()
    }
    getAccountGroups(msisdn) {
        this.loadingService.startFetchingList();
        this.accountGroupService.getAllAccountGroups(msisdn,this.serviceClassId).subscribe((res) => {
            this.accountGroups = JSON.parse(JSON.stringify(res?.payload?.accountGroups));
            this.orginalAccountGroups = JSON.parse(JSON.stringify(res?.payload?.accountGroups));
            if(this.accountGroupNumber){
                this.currentAccountGroup = this.accountGroups.filter(acc=>acc.id===this.accountGroupNumber)[0]
                this.updatedAccount = this.accountGroups.filter(acc=>acc.id===this.accountGroupNumber)[0]
            }
            else{
                this.currentAccountGroup = {...this.accountGroups[0]};
                this.updatedAccount = this.orginalAccountGroups[0];
            }
            this.tableData = JSON.parse(JSON.stringify(this.updatedAccount.bits));
            this.loadingService.endFetchingList();
            
        },err=>{
            this.loadingService.endFetchingList();
        });
    }
    setTableDate(id: number) {
        this.updatedAccount = this.orginalAccountGroups.filter((el) => el.id === id)[0];
        this.tableData = this.orginalAccountGroups.filter((el) => el.id === id)[0].bits;
    }
    submit() {
        this.updatedAccount={...this.updatedAccount,bits:this.tableData}
        this.accountGroupService.updateAccountGroup(this.currentAccountGroup, this.updatedAccount).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(108).message);
                this.dropDownValue=null;
                this.getAccountGroups(this.msisdn);
                this.subscriberService.loadSubscriber(this.msisdn)
            }
        });
    }
    setPermissions() {
        let accountGroupPermissions: Map<number, string> = new Map()
            .set(350, 'updateAccountGroup')
        this.featuresService.checkUserPermissions(accountGroupPermissions);
        this.permissions.updateAccountGroup = this.featuresService.getPermissionValue(350);
    }
}
