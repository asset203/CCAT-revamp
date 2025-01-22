import {NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {SharedRoutingModule} from './shared-routing.module';
import {FormsErrorsComponent} from './components/forms-errors/forms-errors.component';
import {TextCarouselComponent} from './components/text-carousel/text-carousel.component';
import {CarouselModule} from 'ngx-owl-carousel-o';
import {NbaGiftsComponent} from './components/nba-gifts/nba-gifts.component';
import {DateTimeComponent} from './components/date-time/date-time.component';
import {ToastrModule} from 'ngx-toastr';
import {LOADER_CONFIG, TOAST_CONFIG} from '../constant/config.constant';
import {NgxSmartModalModule} from 'ngx-smart-modal';
import {PrimeOneModules} from '../constant/prime.constant';
import {NavigatorComponent} from './components/navigator/navigator.component';
import {TimestampPipe} from './pipes/timestamp.pipe';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {MsisdnSearchPopupComponent} from './components/msisdn-search-popup/msisdn-search-popup.component';
import {NotAuthorizedComponent} from './components/not-authorized/not-authorized.component';
import {NoDataFoundComponent} from './components/no-data-found/no-data-found.component';
import {FocusDirective} from './directives/focus.directive';
import {MergeProducts} from './pipes/mergeProducts';
import {OnlyNumber} from './directives/only-number.directive';
import {AppFindPipe} from './pipes/search.pipe';

import {FontAwesomeModule} from '@fortawesome/angular-fontawesome';
import {NgxLoadingModule} from 'ngx-loading';
import {NgCircleProgressModule} from 'ng-circle-progress';
import {DedicatedAccountsTabComponent} from '../layout/pages/account-admin/dedicated-accounts-tab/dedicated-accounts-tab.component';
import {ChechedIconComponent} from './components/cheched-icon/cheched-icon.component';
import {MultiSelectModule} from 'primeng/multiselect';
import {SlideMenuModule} from 'primeng/slidemenu';
import {MenuItem} from 'primeng/api';
import {MenuModule} from 'primeng/menu';
import {AutoCompleteModule} from 'primeng/autocomplete';
import {ProgressBarModule} from 'primeng/progressbar';
import { CamelToTitlePipe } from './pipes/camelToTitle.pipe';
import { ThreeDotsPipe } from './pipes/threeDots.pipe';
import { NgSelectModule } from '@ng-select/ng-select';
import { SubmitOnEnterDirective } from './directives/submit-on-enter.directive';

@NgModule({
    declarations: [
        FormsErrorsComponent,
        TextCarouselComponent,
        NbaGiftsComponent,
        DateTimeComponent,
        NavigatorComponent,
        TimestampPipe,
        MsisdnSearchPopupComponent,
        NotAuthorizedComponent,
        NoDataFoundComponent,
        FocusDirective,
        MergeProducts,
        OnlyNumber,
        AppFindPipe,
        DedicatedAccountsTabComponent,
        ChechedIconComponent,
        CamelToTitlePipe,
        ThreeDotsPipe,
        SubmitOnEnterDirective
    ],
    imports: [
        CommonModule,
        SharedRoutingModule,
        CarouselModule,
        ProgressBarModule,
        MultiSelectModule,
        ToastrModule.forRoot(TOAST_CONFIG),
        ReactiveFormsModule,
        FormsModule,
        ...PrimeOneModules,
        FontAwesomeModule,
        NgxLoadingModule.forRoot(LOADER_CONFIG),
        NgCircleProgressModule.forRoot({
            // set defaults here
            radius: 100,
            outerStrokeWidth: 16,
            innerStrokeWidth: 8,
            outerStrokeColor: '#78C000',
            innerStrokeColor: '#C7E596',
            animationDuration: 300,
        }),
        NgxSmartModalModule.forRoot(),
        SlideMenuModule,
        MenuModule,
        NgSelectModule
    ],
    exports: [
        ReactiveFormsModule,
        FormsModule,
        FormsErrorsComponent,
        TextCarouselComponent,
        NbaGiftsComponent,
        DateTimeComponent,
        NavigatorComponent,
        TimestampPipe,
        MsisdnSearchPopupComponent,
        NotAuthorizedComponent,
        NoDataFoundComponent,
        FocusDirective,
        MergeProducts,
        OnlyNumber,
        AppFindPipe,
        ...PrimeOneModules,
        FontAwesomeModule,
        NgxLoadingModule,
        NgCircleProgressModule,
        NgxSmartModalModule,
        DedicatedAccountsTabComponent,
        ChechedIconComponent,
        MultiSelectModule,
        SlideMenuModule,
        MenuModule,
        AutoCompleteModule,
        ProgressBarModule,
        CamelToTitlePipe,
        ThreeDotsPipe,
        NgSelectModule,
        SubmitOnEnterDirective
    ],
})
export class SharedModule {}
