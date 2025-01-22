import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthenticationGuard} from '../core/guard/authentication.guard';
import {LoginComponent} from './login/login.component';
import {FindSubscriberComponent} from './pages/find-subscriber/find-subscriber.component';
import {SubscriberGuard} from '../core/guard/subscriber.guard';
import {ProxyComponent} from './pages/proxy/proxy.component';
import {NotFoundComponent} from './pages/not-found/not-found.component';
import {DynamicPageComponent} from './pages/customer-care/dynamic-page/dynamic-page.component';
import { DelayResolver } from '../shared/reslovers/delay.resolver';

const dss = 'dss-reports/';
const customer = 'customer-care/';
const routes: Routes = [
    {
        path: 'login',
        component: LoginComponent,
        resolve: { delay: DelayResolver }
    },
    {
        path: 'find-subscriber',
        component: FindSubscriberComponent,
        canActivate: [AuthenticationGuard],
    },
    {
        path: 'proxy',
        component: ProxyComponent,
        canActivate: [AuthenticationGuard, SubscriberGuard],
    },

    {
        path: '404',
        component: NotFoundComponent,
    },
    // Routes that differs with their modules in Guards
    {
        path: `customer-care/dynamic-page/:id`,
        component: DynamicPageComponent,
        canActivate: [AuthenticationGuard],
    },
    {
        path: 'customer-care',
        loadChildren: () => import('./pages/customer-care/customer-care.module').then((m) => m.CustomerCareModule),
        canActivate: [AuthenticationGuard],
    },
    {
        path: 'admin',
        loadChildren: () => import('./pages/adminstrator/adminstrator.module').then((m) => m.AdminstratorModule),
        // canActivate: [AuthenticationGuard]
    },
    {
        path: 'dss-reports',
        loadChildren: () => import('./pages/dss-reports/dss.module').then((m) => m.DssModule),
        canActivate: [AuthenticationGuard, SubscriberGuard],
    },
    {
        path: '',
        redirectTo: '/login',
        pathMatch: 'full',
    },
    {
        path: '**',
        redirectTo: '/login',
        pathMatch: 'full',
    },
];
@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule],
})
export class LayoutRoutingModule {}
