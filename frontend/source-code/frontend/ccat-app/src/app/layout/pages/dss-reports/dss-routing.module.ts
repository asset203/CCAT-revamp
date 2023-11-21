import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { BTIVRComponent } from "./btivr/btivr.component";
import { ComplaintViewComponent } from "./complaint-view/complaint-view.component";
import { ContractBalanceTransferComponent } from "./contract-balance-transfer/contract-balance-transfer.component";
import { ContractBalanceComponent } from "./contract-balance/contract-balance.component";
import { ContractBillComponent } from "./contract-bill/contract-bill.component";
import { EtopupTransactionsComponent } from "./etopup-transactions/etopup-transactions.component";
import { OutgoingViewComponent } from "./outgoing-view/outgoing-view.component";
import { TrafficBehaviourComponent } from "./traffic-behaviour/traffic-behaviour.component";
import { USSDReportsComponent } from "./ussd-reports/ussd-reports.component";
import { VisitedUrlsComponent } from "./visited-urls/visited-urls.component";
import { VodafoneOneProfileComponent } from "./vodafone-one-profile/vodafone-one-profile.component";
import { VodafoneOneRedeemComponent } from "./vodafone-one-redeem/vodafone-one-redeem.component";
import { VodafoneOneComponent } from "./vodafone-one/vodafone-one.component";
const routes: Routes = [
    {
        path: `contract-bill`,
        component: ContractBillComponent,
    },
    {
        path: `complaint-view`,
        component: ComplaintViewComponent,
    },
    {
        path: `outgoing-view`,
        component: OutgoingViewComponent,
    },
    {
        path: `vodafone-one`,
        component: VodafoneOneComponent,
    },
    {
        path: `contract-balance`,
        component: ContractBalanceComponent,
    },
    {
        path: `contract-balance-transfer`,
        component: ContractBalanceTransferComponent,
    },
    {
        path: `visited-urls`,
        component: VisitedUrlsComponent,
    },
    {
        path: `bt-ivr`,
        component: BTIVRComponent,
    },
    {
        path: `traffic-behaviour`,
        component: TrafficBehaviourComponent,
    },
    {
        path: `ussd`,
        component: USSDReportsComponent,
    },
    {
        path: `vodafone-one-profile`,
        component: VodafoneOneProfileComponent,
    },
    {
        path: `vodafone-one-redeem`,
        component: VodafoneOneRedeemComponent,
    },
    {
        path: `etopup-transactions`,
        component: EtopupTransactionsComponent,
    },
    
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DssRoutingModule { }