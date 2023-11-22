import { NgModule } from "@angular/core";
import { SharedModule } from "src/app/shared/shared.module";
import { BTIVRComponent } from "./btivr/btivr.component";
import { ComplaintViewComponent } from "./complaint-view/complaint-view.component";
import { ContractBalanceTransferComponent } from "./contract-balance-transfer/contract-balance-transfer.component";
import { ContractBalanceComponent } from "./contract-balance/contract-balance.component";
import { ContractBillComponent } from "./contract-bill/contract-bill.component";
import { DssRoutingModule } from "./dss-routing.module";
import { OutgoingViewComponent } from "./outgoing-view/outgoing-view.component";
import { TrafficBehaviourComponent } from "./traffic-behaviour/traffic-behaviour.component";
import { USSDReportsComponent } from "./ussd-reports/ussd-reports.component";
import { VisitedUrlsComponent } from "./visited-urls/visited-urls.component";
import { VodafoneOneComponent } from "./vodafone-one/vodafone-one.component";
import { VodafoneOneProfileComponent } from './vodafone-one-profile/vodafone-one-profile.component';
import { VodafoneOneRedeemComponent } from './vodafone-one-redeem/vodafone-one-redeem.component';
import { EtopupTransactionsComponent } from './etopup-transactions/etopup-transactions.component';

@NgModule({
    declarations: [
        BTIVRComponent,
        TrafficBehaviourComponent,
        USSDReportsComponent,
        ContractBillComponent,
        ComplaintViewComponent,
        OutgoingViewComponent,
        VodafoneOneComponent,
        ContractBalanceComponent,
        ContractBalanceTransferComponent,
        VisitedUrlsComponent,
        VodafoneOneProfileComponent,
        VodafoneOneRedeemComponent,
        EtopupTransactionsComponent,
    ],
    imports: [DssRoutingModule, SharedModule],
    exports: []
})
export class DssModule { }