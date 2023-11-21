import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';
import { StorageService } from '../service/storage.service';
import { SessionService } from '../service/session.service';
import { Router } from '@angular/router';
import { ToastService } from 'src/app/shared/services/toast.service';

@Injectable()
export class RequestInterceptor implements HttpInterceptor {
    constructor(
        private storageService: StorageService,
        private toastService: ToastService,
        private sessionService: SessionService,
        private router: Router
    ) { }

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(this.prework(request)).pipe(
            tap({
                next: (response: any) => {
                    if (response?.body?.statusCode === -101) {
                        this.sessionService.logout();
                        this.toastService.error('error', response.body.statusMessage);
                    } else {
                        this.severityHandling(
                            response?.body?.severity,
                            response?.body?.statusMessage,
                            response?.body?.requestId
                        );
                    }
                    if (response instanceof HttpResponse) {
                        this.postwork(request, response);
                    }
                },
            }),
            catchError((error: any) => {
                throw error;
            })
        );
    }
    private prework(request: HttpRequest<any>): HttpRequest<any> {
        let reqHeaders: HttpHeaders = request.headers;

        if (!reqHeaders.has('Accept')) {
            reqHeaders = reqHeaders.set('Accept', 'application/json');
        }
        reqHeaders = reqHeaders.set('Access-Control-Allow-Origin', '*');
        const req = request.clone({
            headers: reqHeaders,
            body: { ...request.body, token: this.storageService.getItem('session')?.token },
        });
        return req;
    }

    private postwork(request: HttpRequest<any>, response: HttpResponse<any>): void { }

    severityHandling(num: number, msg: string, requestId: string) {
        const reqId = requestId ? `<br> 
                                    <span class="roboto-light">Tracking ID:</span>
                                    <span class="roboto-light heading-13" > ${requestId} </span>` : '';
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
                break;
        }
    }

    copy(value) {
        navigator.clipboard.writeText(value);
    }
}
