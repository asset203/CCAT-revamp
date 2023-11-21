import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AddDynamicPageComponent} from './add-dynamic-page/add-dynamic-page.component';
import {AdminDynamicPageDetalisComponent} from './admin-dynamic-page-detalis.component';
const routes: Routes = [
    {path: '', component: AdminDynamicPageDetalisComponent},
    {path: 'add/:id', component: AddDynamicPageComponent},
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class AdminDynamicPageRoutingModule {}
