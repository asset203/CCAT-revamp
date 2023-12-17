import {NgModule} from '@angular/core';
import {AdminDpPageConfigrationService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-page-configrations.service';
import {AdminDPProcedureService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-procedure.service';
import {AdminDPStepService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-step.service';
import {AdminDynamicPageService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dynamic-page.service';
import {AdminDynamicPageRoutingModule} from './admin-dynamic-page.routing.module';

@NgModule({
    declarations: [],
    imports: [AdminDynamicPageRoutingModule],
    providers: [AdminDynamicPageService, AdminDpPageConfigrationService, AdminDPProcedureService, AdminDPStepService],
})
export class AdminDynamicPageModule {}
