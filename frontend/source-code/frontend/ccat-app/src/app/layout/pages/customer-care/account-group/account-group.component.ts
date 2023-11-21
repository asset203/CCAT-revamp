import {Component, OnInit ,OnDestroy} from '@angular/core';
import { Subscription } from 'rxjs';
import {AccountGroupCCService} from 'src/app/core/service/customer-care/account-group-cc.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import {AccountGroupCC, Bit} from 'src/app/shared/models/accountGroupCC.interface';
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
    constructor(
        private accountGroupService: AccountGroupCCService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private subscriberService : SubscriberService
    ) {}
    
    loading$ = this.accountGroupService.loading$;
    msisdn;
    serviceClassId;
    subscriberSubscription = new Subscription();
    accountGroupNumber;
    ngOnInit(): void {
        
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
        this.accountGroupService.getAllAccountGroups(msisdn,this.serviceClassId).subscribe((res) => {
            this.accountGroups = JSON.parse(JSON.stringify(res?.payload?.accountGroups));
            this.orginalAccountGroups = JSON.parse(JSON.stringify(res?.payload?.accountGroups));
            this.currentAccountGroup = {...this.accountGroups[0]};
            this.tableData = JSON.parse(JSON.stringify(this.currentAccountGroup.bits));
            //this.dropDownValue=this.accountGroups[0].id
        });
    }
    setTableDate(id: number) {
        this.updatedAccount = this.orginalAccountGroups.filter((el) => el.id === id)[0];
        this.tableData = this.orginalAccountGroups.filter((el) => el.id === id)[0].bits;
    }
    submit() {
        this.accountGroupService.updateAccountGroup(this.currentAccountGroup, this.updatedAccount).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(108).message);
                this.dropDownValue=null;
                this.getAccountGroups(this.msisdn);
                this.subscriberService.loadSubscriber(this.msisdn)
            }
        });
    }
}
