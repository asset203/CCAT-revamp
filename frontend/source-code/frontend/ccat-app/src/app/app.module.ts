import { APP_INITIALIZER, NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CoreModule } from './core/core.module';
import { SharedModule } from './shared/shared.module';
import { LayoutModule } from './layout/layout.module';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { PrimeOneModules } from './constant/prime.constant';
import { CarouselModule } from 'ngx-owl-carousel-o';
import { NgxSmartModalModule } from 'ngx-smart-modal';
import { LOADER_CONFIG, TOAST_CONFIG } from './constant/config.constant';
import { CommonModule } from '@angular/common';
import { AppConfigService } from './core/service/app-config.service';
const appInitializerFn = (appConfig: AppConfigService) => {
    return () => {
        return appConfig.loadAppConfig();
    }
};
// import { NgxSmartModalModule } from 'ngx-smart-modal';
@NgModule({
    declarations: [AppComponent],
    imports: [
        BrowserModule,
        BrowserAnimationsModule,
        AppRoutingModule,
        CoreModule,
        SharedModule,
        CommonModule,
        LayoutModule,
        NgbModule,

    ],
    providers: [
        AppConfigService,
        {
            provide: APP_INITIALIZER,
            useFactory: appInitializerFn,
            multi: true,
            deps: [AppConfigService]
        }],
    bootstrap: [AppComponent],
})
export class AppModule { }
