import { DatePipe } from '@angular/common';
import { NgModule } from '@angular/core';
import { CoreModule } from '../core/core.module';
import { SharedModule } from '../shared/shared.module';
import { LayoutRoutingModule } from './layout-routing.module';
import { LoginComponent } from './login/login.component';
import { NavbarComponent } from './navbar/navbar.component';
import { HeaderComponent } from './header/header.component';
import { ProxyComponent } from './pages/proxy/proxy.component';
import { FindSubscriberComponent } from './pages/find-subscriber/find-subscriber.component';
import { SubscriberInfoComponent } from './subscriber-info/subscriber-info.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';



@NgModule({
    declarations: [
        LoginComponent,
        NavbarComponent,
        HeaderComponent,
        FindSubscriberComponent,
        SubscriberInfoComponent,
        ProxyComponent,
        NotFoundComponent,
     
    ],
    imports: [
        LayoutRoutingModule,
        CoreModule,
        SharedModule,
    ],
    providers: [DatePipe],
    exports: [NavbarComponent, HeaderComponent, SubscriberInfoComponent],
})
export class LayoutModule { }
