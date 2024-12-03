import {NgModule} from '@angular/core';
import {SharedModule} from 'src/app/shared/shared.module';
import {AccountAdminTabComponent} from '../account-admin/account-admin-tab/account-admin-tab.component';
import {AccountAdminComponent} from '../account-admin/account-admin.component';
import {AccumlatorsTabComponent} from '../account-admin/accumlators-tab/accumlators-tab.component';
// import { ViewDynamicPageComponent } from "../customer-care/dynamic-page/view-dynamic-page/view-dynamic-page.component";
import {AccountGroupsBitsDescriptionComponent} from './account-groups-bits-description/account-groups-bits-description.component';
import {AccountGroupsComponent} from './account-groups/account-groups.component';
import {AddDynamicPageComponent} from './admin-dynamic-page-detalis/add-dynamic-page/add-dynamic-page.component';
import {GeneralConfigsComponent} from './admin-dynamic-page-detalis/add-dynamic-page/general-configs/general-configs.component';
import {PageConfigsComponent} from './admin-dynamic-page-detalis/add-dynamic-page/page-configs/page-configs.component';
import {ProcedureConfigsComponent} from './admin-dynamic-page-detalis/add-dynamic-page/procedure-configs/procedure-configs.component';
import {AdminDynamicPageDetalisComponent} from './admin-dynamic-page-detalis/admin-dynamic-page-detalis.component';
import {AdminstratorRoutingModule} from './adminstrator-routing.module';
import {BatchInstallationDisconnectionComponent} from './batch-installation-disconnection/batch-installation-disconnection.component';
import {BusinessPlansDialogComponent} from './business-plans/business-plan-dialog/business-plan-dialog.component';
import {BusinessPlansComponent} from './business-plans/business-plans.component';
import {CallActivityAdminstratorComponent} from './call-activity-adminstrator/call-activity-adminstrator.component';
import {CommunityAdminComponent} from './community-admin/community-admin.component';
import {DisconnectionCodesComponent} from './disconnection-codes/disconnection-codes.component';
import {DisconnectionsCodesDialogComponent} from './disconnection-codes/disconnections-codes-dialog/disconnections-codes-dialog.component';
import {Es2alnyMarqueeFormComponent} from './es2alny-marquee/es2alny-marquee-form/es2alny-marquee-form.component';
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
import {ServiceOfferingPlanComponent} from './service-offering-plan/service-offering-plan.component';
import {SmsTemplateCsComponent} from './sms-template-cs/sms-template-cs.component';
import {SmsTemplateComponent} from './sms-template/sms-template.component';
import {SystemSettingsComponent} from './system-settings/system-settings.component';
import {TransactionAdminComponent} from './transaction-admin/transaction-admin.component';
import {TransactionCodesComponent} from './transaction-admin/transaction-codes/transaction-codes.component';
import {TransactionLinksComponent} from './transaction-admin/transaction-links/transaction-links.component';
import {TransactionTypesComponent} from './transaction-admin/transaction-types/transaction-types.component';
import {UserAccessComponent} from './user-access/user-access.component';
import {AddUserProfileComponent} from './user-profiles/add-user-profile/add-user-profile.component';
import {MenuOrderComponent} from './user-profiles/add-user-profile/menu-order/menu-order.component';
import {ProfileLimitsTable} from './user-profiles/add-user-profile/profile-limits-table/profile-limits-table.component';
import {ProfilePickList} from './user-profiles/add-user-profile/profile-pick-list/profile-pick-list.component';
import {UserProfilesComponent} from './user-profiles/user-profiles.component';
import {ExtractProfileComponent} from './extact-profile-features/extract-profile/extract-profile.component';
import {ExtractFeatureComponent} from './extact-profile-features/extract-feature/extract-feature.component';
import {ExtractFeaturesTableComponent} from './extact-profile-features/extract-feature/extract-features-table/extract-features-table.component';
import {ServiceClassPlanDescriptionComponent} from './service-offering-plan/service-class-plan-description/service-class-plan-description.component';
import {ActivityDirectionComponent} from './call-activity-adminstrator/activity-direction/activity-direction.component';
import {ActivityFamilyComponent} from './call-activity-adminstrator/activity-family/activity-family.component';
import {ActivityReasonTypeComponent} from './call-activity-adminstrator/activity-reason-type/activity-reason-type.component';
import {ActivityReasonComponent} from './call-activity-adminstrator/activity-reason/activity-reason.component';
import {ServiceClassPlansComponent} from './service-classes/add-service-class/service-class-plans/service-class-plans.component';
import {HttpConfigsComponent} from './admin-dynamic-page-detalis/add-dynamic-page/http-configs/http-configs.component';
import {ExternalServiceNodesComponent} from './external-service-nodes/external-service-nodes.component';
import {AirNodesComponent} from './external-service-nodes/air-nodes/air-nodes.component';
import {OdsNodesComponent} from './external-service-nodes/ods-nodes/ods-nodes.component';
import {DssNodesComponent} from './external-service-nodes/dss-nodes/dss-nodes.component';
import {FlexshareNodesComponent} from './external-service-nodes/flexshare-nodes/flexshare-nodes.component';
import {VipMsisdnComponent} from './vip-msisdn/vip-msisdn.component';
import {VipMsisdnListsComponent} from './vip-msisdn/vip-msisdn-lists/vip-msisdn-lists.component';
import {VipAccessRoleComponent} from './vip-msisdn/vip-access-role/vip-access-role.component';
import {ConfirmationService} from 'primeng/api';

@NgModule({
    declarations: [
        AccountAdminComponent,
        AccountAdminTabComponent,
        AccumlatorsTabComponent,
        UserAccessComponent,
        UserProfilesComponent,
        AddUserProfileComponent,
        ServiceClassesComponent,
        AddServiceClassComponent,
        BusinessPlansComponent,
        SystemSettingsComponent,
        Es2alnyMarqueeComponent,
        BatchInstallationDisconnectionComponent,
        PamAdminstrationComponent,
        DisconnectionCodesComponent,
        LockingAdminstrationComponent,
        TransactionAdminComponent,
        TransactionTypesComponent,
        TransactionCodesComponent,
        TransactionLinksComponent,
        ProfilePickList,
        ProfileLimitsTable,
        BusinessPlansDialogComponent,
        Es2alnyMarqueeFormComponent,
        MenuOrderComponent,
        DisconnectionsCodesDialogComponent,
        ExtactProfileFeaturesComponent,
        CommunityAdminComponent,
        ServiceOfferingDescriptionComponent,
        ServiceOfferingPlanComponent,
        AddServiceOfferingPlanComponent,
        AccountGroupsComponent,
        CallActivityAdminstratorComponent,
        FafPlanComponent,
        FootprintReportComponent,
        AccountGroupsBitsDescriptionComponent,
        SmsTemplateComponent,
        SmsTemplateCsComponent,
        AdminDynamicPageDetalisComponent,
        AddDynamicPageComponent,
        // ViewDynamicPageComponent,
        ProcedureConfigsComponent,
        HttpConfigsComponent,
        GeneralConfigsComponent,
        PageConfigsComponent,
        ExtractProfileComponent,
        ExtractFeatureComponent,
        ExtractFeaturesTableComponent,
        ServiceClassPlanDescriptionComponent,
        ActivityDirectionComponent,
        ActivityFamilyComponent,
        ActivityReasonTypeComponent,
        ActivityReasonComponent,
        ServiceClassPlansComponent,
        ExternalServiceNodesComponent,
        AirNodesComponent,
        OdsNodesComponent,
        DssNodesComponent,
        FlexshareNodesComponent,
        VipMsisdnComponent,
        VipMsisdnListsComponent,
        VipAccessRoleComponent,
    ],
    providers: [ConfirmationService],
    imports: [AdminstratorRoutingModule, SharedModule],
    exports: [],
})
export class AdminstratorModule {}
