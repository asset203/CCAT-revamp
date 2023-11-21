import {NgModule} from '@angular/core';
import {SharedModule} from 'src/app/shared/shared.module';
import {AccountGroupComponent} from './account-group/account-group.component';
import {AccountHistoryComponent} from './account-history/account-history.component';
import {AddAccountTabComponent} from './advanced/add-account-tab/add-account-tab.component';
import {AdvancedComponent} from './advanced/advanced.component';
import {ChangeSIMTabComponent} from './advanced/change-sim-tab/change-sim-tab.component';
import {DisconnectTabComponent} from './advanced/disconnect-tab/disconnect-tab.component';
import {LinkSubordinateTabComponent} from './advanced/link-subordinate-tab/link-subordinate-tab.component';
import {ResetSubscriberPasswordTabComponent} from './advanced/reset-subscriber-password-tab/reset-subscriber-password-tab.component';
import {BarringComponent} from './barring/barring.component';
import {CcatBalanceDisputeComponent} from './ccat-balance-dispute/ccat-balance-dispute.component';
import {CommunitiesComponent} from './communities/communities.component';
import {CustomerCareRoutingModule} from './customer-care-routing.module';
import {DynamicPageComponent} from './dynamic-page/dynamic-page.component';
import {FamilyAndFriendsComponent} from './family-and-friends/family-and-friends.component';
import {FlexShareComponent} from './flex-share/flex-share.component';
import {LanguageComponent} from './language/language.component';
import {NotepadComponent} from './notepad/notepad.component';
import {OffersNewComponent} from './offers-new/offers-new.component';
import {AddPamInformationComponent} from './pam-information/add-pam-information/add-pam-information.component';
import {PamInformationComponent} from './pam-information/pam-information.component';
import {PrepaidVBPComponent} from './prepaid-vbp/prepaid-vbp.component';
import {ProductsViewComponent} from './products-view/products-view.component';
import {OverScratchComponent} from './scratch-cards/over-scratch/over-scratch.component';
import {ScratchCardsComponent} from './scratch-cards/scratch-cards.component';
import {VoucherBasedRefillComponent} from './scratch-cards/voucher-based-refill/voucher-based-refill.component';
import {VoucherInfoComponent} from './scratch-cards/voucher-info/voucher-info.component';
import {ServiceClassComponent} from './service-class/service-class.component';
import {ServiceOfferingComponent} from './service-offering/service-offering.component';
import {SubscriberAdminComponent} from './subscriber-admin/subscriber-admin.component';
import {SuperFlexViewComponent} from './super-flex-view/super-flex-view.component';
import {UsageCounterDialogComponent} from './usage-counter/usage-counter-dialog/usage-counter-dialog.component';
import {UsageCounterThresholdDialogComponent} from './usage-counter/usage-counter-threshold-dialog/usage-counter-threshold-dialog.component';
import {UsageCounterThresholdComponent} from './usage-counter/usage-counter-threshold/usage-counter-threshold.component';
import {UsageCounterComponent} from './usage-counter/usage-counter.component';
import {VoucherlessRefillComponent} from './voucherless-refill/voucherless-refill.component';
import {CallActivityComponent} from './call-activity/call-activity.component';
import { BalanceDisputeComponent } from './balance-dispute/balance-dispute.component';

@NgModule({
    declarations: [
        AccountHistoryComponent,
        LanguageComponent,
        NotepadComponent,
        SubscriberAdminComponent,
        ServiceClassComponent,
        AdvancedComponent,
        VoucherlessRefillComponent,
        ScratchCardsComponent,
        OffersNewComponent,
        UsageCounterComponent,
        ProductsViewComponent,
        PamInformationComponent,
        DynamicPageComponent,
        AddAccountTabComponent,
        LinkSubordinateTabComponent,
        DisconnectTabComponent,
        ResetSubscriberPasswordTabComponent,
        ChangeSIMTabComponent,
        AddPamInformationComponent,
        UsageCounterThresholdComponent,
        UsageCounterThresholdDialogComponent,
        BarringComponent,
        UsageCounterDialogComponent,
        VoucherInfoComponent,
        VoucherBasedRefillComponent,
        OverScratchComponent,
        FamilyAndFriendsComponent,
        CommunitiesComponent,
        AccountGroupComponent,
        ServiceOfferingComponent,
        CcatBalanceDisputeComponent,
        SuperFlexViewComponent,
        FlexShareComponent,
        PrepaidVBPComponent,
        CallActivityComponent,
        BalanceDisputeComponent,
    ],
    imports: [CustomerCareRoutingModule, SharedModule],
    exports: [],
})
export class CustomerCareModule {}
