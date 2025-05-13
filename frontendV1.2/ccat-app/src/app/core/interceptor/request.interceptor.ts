import { Injectable } from '@angular/core';
import {
    HttpRequest,
    HttpHandler,
    HttpEvent,
    HttpInterceptor,
    HttpResponse,
    HttpHeaders,
    HttpErrorResponse,
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { StorageService } from '../service/storage.service';
import { SessionService } from '../service/session.service';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/shared/services/toast.service';
import { SubscriberService } from '../service/subscriber.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
    dssReportsFootprintModel = {
        machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
        profileName: JSON.parse(sessionStorage.getItem('session'))?.userProfile?.profileName,
        pageName: '',
        msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
    }

    constructor(
        private storageService: StorageService,
        private toastService: ToastService,
        private sessionService: SessionService,
        private router: Router,
        private subscriberService: SubscriberService
    ) {

    }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(this.prework(request)).pipe(
            tap({
                next: (response: any) => {
                    if (response?.body?.statusCode !== 0) {
                        if (response?.body?.statusCode === -101 || response?.body?.statusCode === -102) {
                            this.sessionService.logout();
                            this.toastService.error('error', response.body.statusMessage);
                        } else if (response?.body?.statusCode === -120 || response?.body?.statusCode === -123) {
                            this.subscriberService.clearSubscriberReset(true);
                            this.toastService.error('error', response.body.statusMessage);
                        }
                        if (response.status === 200 && response?.body?.statusCode) {
                            this.severityHandling(
                                response?.body?.severity,
                                response?.body?.statusMessage,
                                response?.body?.requestId,
                                request.url
                            );
                        }

                        /*if (response.status === 200 && response?.body?.statusCode)
                            throw new HttpErrorResponse({
                                error: 'your error',
                                status: response?.body?.statusCode,
                                statusText: response.body.statusMessage,
                            });*/
                    }

                    if (response instanceof HttpResponse) {
                        this.postwork(request, response);
                    }
                },
            }),
            catchError((error: HttpErrorResponse) => {
                if (error.status === 504) {
                    this.toastService.error('Timeout', 'The server took too long to respond. Please try again later.');
                } else {
                    this.toastService.error('Error', 'There was an error communicating with the server, please try again.');
                }
                throw error;
            })
        );
    }
    private prework(request: HttpRequest<any>): HttpRequest<any> {
        let reqHeaders: HttpHeaders = request.headers;
        let req: any;
        if (!reqHeaders.has('Accept')) {
            reqHeaders = reqHeaders.set('Accept', 'application/json');
        }
        reqHeaders = reqHeaders.set('Access-Control-Allow-Origin', '*');


        if (this.router.url.includes('dss-reports') || this.router.url.includes('admin')) {
            console.log('%cdss-reports request', 'color: red');
            const url = window.location.href;
            const lastSegment = url.substring(url.lastIndexOf('/') + 1);
            console.log('Last segment:', lastSegment); // "vodafone-one-redeem"
            req = request.clone({
                headers: reqHeaders,
                body: {
                    ...request.body, token: this.storageService.getItem('session')?.token,
                    footprintModel: { ...this.dssReportsFootprintModel, pageName: lastSegment }
                },
            });
        } else {
            req = request.clone({
                headers: reqHeaders,
                body: { ...request.body, token: this.storageService.getItem('session')?.token },
            });
        }

        console.log('request', req);
        return req;
    }

    private postwork(request: HttpRequest<any>, response: HttpResponse<any>): void { }

    severityHandling(num: number, msg: string, requestId: string, url) {
        const reqId = requestId
            ? `<br> 
                                    <span class="roboto-light">Tracking ID:</span> 
                                    <span class="roboto-light heading-13" > ${requestId} </span> <br> 
                                    <span class="roboto-light heading-13" > In: ${url} </span>`
            : '';
        const errorMessage = `<span >${msg}</span> ${reqId} `;
        switch (num) {
            case 0:
                break;
            case 1:
                this.toastService.warning('', errorMessage);
                break;
            case 2:
                this.toastService.error('', errorMessage);
                break;
            default:
                this.toastService.error('', errorMessage);;
        }
    }

    copy(value) {
        navigator.clipboard.writeText(value);
    }
}
