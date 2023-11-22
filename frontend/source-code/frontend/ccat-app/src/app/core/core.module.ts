import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CoreRoutingModule } from './core-routing.module';
import { SharedModule } from '../shared/shared.module';

import { SessionService } from './service/session.service';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { HttpService } from './service/http.service';
import { SubscriberService } from './service/subscriber.service';
import { LoggingInterceptor } from './interceptor/logging.interceptor';
import { RequestInterceptor } from './interceptor/request.interceptor';

const INTERCEPTORS = [
  { provide: HTTP_INTERCEPTORS, useClass: RequestInterceptor, multi: true },
  { provide: HTTP_INTERCEPTORS, useClass: LoggingInterceptor, multi: true },
];
@NgModule({
  declarations: [
  ],
  imports: [CommonModule, CoreRoutingModule, SharedModule, HttpClientModule],
  providers: [HttpService, SessionService, SubscriberService, INTERCEPTORS],
})
export class CoreModule { }
