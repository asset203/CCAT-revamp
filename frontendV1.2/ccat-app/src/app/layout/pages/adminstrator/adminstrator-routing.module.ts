import {ExternalServiceNodesComponent} from './external-service-nodes/external-service-nodes.component';
import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationGuard} from 'src/app/core/guard/authentication.guard';
import {AccountGroupsBitsDescriptionComponent} from './account-groups-bits-description/account-groups-bits-description.component';
import {AccountGroupsComponent} from './account-groups/account-groups.component';
import {BatchInstallationDisconnectionComponent} from './batch-installation-disconnection/batch-installation-disconnection.component';
import {BusinessPlansComponent} from './business-plans/business-plans.component';
import {CallActivityAdminstratorComponent} from './call-activity-adminstrator/call-activity-adminstrator.component';
import {CommunityAdminComponent} from './community-admin/community-admin.component';
import {DisconnectionCodesComponent} from './disconnection-codes/disconnection-codes.component';
import {Es2alnyMarqueeComponent} from './es2alny-marquee/es2alny-marquee.component';
import {ExtactProfileFeaturesComponent} from './extact-profile-features/extact-profile-features.component';
import {FafPlanComponent} from './faf-plan/faf-plan.component';
import {FootprintReportComponent} from './footprint-report/footprint-report.component';
import {LockingAdminstrationComponent} from './locking-adminstration/locking-adminstration.component';
import {PamAdminstrationComponent} from './pam-adminstration/pam-adminstration.component';
import {AddServiceClassComponent} from './service-classes/add-service-class/add-service-class.component';
import {ServiceClassesComponent} from './service-classes/service-classes.component';
import {ServiceOfferingDescriptionComponent} from './service-offering-description/service-offering-description.component';
import {AddServiceOfferingPlanComponent} from './service-offering-plan/add-service-offering-plan/add-service-offering-plan.component';
import {ServiceClassPlanDescriptionComponent} from './service-offering-plan/service-class-plan-description/service-class-plan-description.component';
import {ServiceOfferingPlanComponent} from './service-offering-plan/service-offering-plan.component';
import {SmsTemplateCsComponent} from './sms-template-cs/sms-template-cs.component';
import {SmsTemplateComponent} from './sms-template/sms-template.component';
import {SystemSettingsComponent} from './system-settings/system-settings.component';
import {TransactionAdminComponent} from './transaction-admin/transaction-admin.component';
import {UserAccessComponent} from './user-access/user-access.component';
import {AddUserProfileComponent} from './user-profiles/add-user-profile/add-user-profile.component';
import {UserProfilesComponent} from './user-profiles/user-profiles.component';
import {VipMsisdnComponent} from './vip-msisdn/vip-msisdn.component';

const routes: Routes = [
    {
        path: `user-access`,
        component: UserAccessComponent,
    },
    {
        path: `user-profiles`,
        component: UserProfilesComponent,
    },
    {
        path: `add-profile/:id`,
        component: AddUserProfileComponent,
    },
    {
        path: `service-classes`,
        component: ServiceClassesComponent,
    },
    {
        path: `service-classes/add-service-class/:id`,
        component: AddServiceClassComponent,
    },
    {
        path: `business-plans`,
        component: BusinessPlansComponent,
    },
    {
        path: `es2alny-marquee`,
        component: Es2alnyMarqueeComponent,
    },
    {
        path: `batch-installation`,
        component: BatchInstallationDisconnectionComponent,
    },
    {
        path: `disconnection-codes`,
        component: DisconnectionCodesComponent,
    },
    {
        path: `pam-adminstration`,
        component: PamAdminstrationComponent,
    },
    {
        path: `locking-adminstration`,
        component: LockingAdminstrationComponent,
    },
    {
        path: `transaction-admin`,
        component: TransactionAdminComponent,
    },
    {
        path: `system-settings`,
        component: SystemSettingsComponent,
    },
    {
        path: `extract-profile-features`,
        component: ExtactProfileFeaturesComponent,
    },
    {
        path: `community-admin`,
        component: CommunityAdminComponent,
    },
    {
        path: `service-offering-description`,
        component: ServiceOfferingDescriptionComponent,
    },
    {
        path: `service-offering-plan`,
        component: ServiceOfferingPlanComponent,
    },
    {
        path: `service-offering-plan/update-plan/:id`,
        component: AddServiceOfferingPlanComponent,
    },
    {
        path: `service-offering-plan/add-plan`,
        component: AddServiceOfferingPlanComponent,
    },
    {
        path: `service-offering-plan/update-class-plan-desc/:id`,
        component: ServiceClassPlanDescriptionComponent,
    },
    {
        path: `account-groups`,
        component: AccountGroupsComponent,
    },
    {
        path: `call-activity-adminstration`,
        component: CallActivityAdminstratorComponent,
    },
    {
        path: `faf-plan`,
        component: FafPlanComponent,
    },
    {
        path: `footprint-report`,
        component: FootprintReportComponent,
    },
    {
        path: `account-groups-bits-description`,
        component: AccountGroupsBitsDescriptionComponent,
    },
    {
        path: `sms-template`,
        component: SmsTemplateComponent,
    },
    {
        path: `sms-template-cs`,
        component: SmsTemplateCsComponent,
    },
    {
        path: `external-service-nodes`,
        component: ExternalServiceNodesComponent,
    },
    {
        path: `vip`,
        component: VipMsisdnComponent,
    },
    {
        path: 'admin-dynamic-page',
        loadChildren: () =>
            import('./admin-dynamic-page-detalis/admin-dynamic-page.module').then((m) => m.AdminDynamicPageModule),
    },
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AdminstratorRoutingModule {}
