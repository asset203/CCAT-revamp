import { NgModule } from "@angular/core";
import { RouterModule, Routes } from "@angular/router";
import { SubscriberGuard } from "src/app/core/guard/subscriber.guard";
import { AccountAdminComponent } from "../account-admin/account-admin.component";
import { ServiceClassesComponent } from "../adminstrator/service-classes/service-classes.component";
import { AccountGroupComponent } from "./account-group/account-group.component";
import { AccountHistoryComponent } from "./account-history/account-history.component";
import { AdvancedComponent } from "./advanced/advanced.component";
import { BalanceDisputeComponent } from "./balance-dispute/balance-dispute.component";
import { BarringComponent } from "./barring/barring.component";
import { CallActivityComponent } from "./call-activity/call-activity.component";
import { CcatBalanceDisputeComponent } from "./ccat-balance-dispute/ccat-balance-dispute.component";
import { CommunitiesComponent } from "./communities/communities.component";
// import { ViewDynamicPageComponent } from "./dynamic-page/view-dynamic-page/view-dynamic-page.component";
import { FamilyAndFriendsComponent } from "./family-and-friends/family-and-friends.component";
import { FlexShareComponent } from "./flex-share/flex-share.component";
import { LanguageComponent } from "./language/language.component";
import { NotepadComponent } from "./notepad/notepad.component";
import { OffersNewComponent } from "./offers-new/offers-new.component";
import { AddPamInformationComponent } from "./pam-information/add-pam-information/add-pam-information.component";
import { PamInformationComponent } from "./pam-information/pam-information.component";
import { PrepaidVBPComponent } from "./prepaid-vbp/prepaid-vbp.component";
import { ProductsViewComponent } from "./products-view/products-view.component";
import { ScratchCardsComponent } from "./scratch-cards/scratch-cards.component";
import { ServiceClassComponent } from "./service-class/service-class.component";
import { ServiceOfferingComponent } from "./service-offering/service-offering.component";
import { SubscriberAdminComponent } from "./subscriber-admin/subscriber-admin.component";
import { SuperFlexViewComponent } from "./super-flex-view/super-flex-view.component";
import { UsageCounterThresholdComponent } from "./usage-counter/usage-counter-threshold/usage-counter-threshold.component";
import { UsageCounterComponent } from "./usage-counter/usage-counter.component";
import { VoucherlessRefillComponent } from "./voucherless-refill/voucherless-refill.component";
const customer = 'customer-care/';

const routes: Routes = [
    {
        path: `account-admin`,
        component: AccountAdminComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `subscriber-admin`,
        component: SubscriberAdminComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `service-classes`,
        component: ServiceClassesComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `account-history`,
        component: AccountHistoryComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `language`,
        component: LanguageComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `notepad`,
        component: NotepadComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `service-class`,
        component: ServiceClassComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `advanced`,
        component: AdvancedComponent,
    },
    {
        path: `voucherless-refill`,
        component: VoucherlessRefillComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `scratch-cards`,
        component: ScratchCardsComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `offers-new`,
        component: OffersNewComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `usage-counter`,
        component: UsageCounterComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `usage-counter-threshold/:id`,
        component: UsageCounterThresholdComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `products-view`,
        component: ProductsViewComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `pam-information`,
        component: PamInformationComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `add-pam-information`,
        component: AddPamInformationComponent,
        canActivate: [SubscriberGuard]
    },
    // {
    //     path: `dynamic-page/:id`,
    //     component: ViewDynamicPageComponent,
    // },
    {
        path: `barrings`,
        component: BarringComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `family-and-friends`,
        component: FamilyAndFriendsComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `communities`,
        component: CommunitiesComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `account-group`,
        component: AccountGroupComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `service-offering`,
        component: ServiceOfferingComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `ccat-balance-dispute`,
        component: CcatBalanceDisputeComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `super-flex-view`,
        component: SuperFlexViewComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `flex-share`,
        component: FlexShareComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `prepaid-VBP`,
        component: PrepaidVBPComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `balance-dispute`,
        component: BalanceDisputeComponent,
        canActivate: [SubscriberGuard]
    },
    {
        path: `call-activity`,
        component: CallActivityComponent,
        canActivate: [SubscriberGuard]
    },
]
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CustomerCareRoutingModule { }