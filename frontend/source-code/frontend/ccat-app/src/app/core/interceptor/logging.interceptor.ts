import {Injectable} from '@angular/core';
import {HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpResponse} from '@angular/common/http';
import {Observable} from 'rxjs';
import {tap} from 'rxjs/operators';

@Injectable({
    providedIn: 'root',
})
export class LoggingInterceptor implements HttpInterceptor {
    private matcher = '(?:https?://)?(?:[^?/s]+[?/])(.*)';
    constructor() {}

    intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
        return next.handle(this.prework(request)).pipe(
            tap({
                next: (response) => {
                    if (response instanceof HttpResponse) {
                        this.postwork(request, response);
                    }
                },
                error: (error) => {
                    throw error;
                },
            })
        );
    }

    private prework(request: HttpRequest<any>): HttpRequest<any> {
        const match: RegExpMatchArray = request.urlWithParams.match(this.matcher) ?? [];
        console.log(`%c[${request.method}] %c[${match[1]}]`, 'font-weight: 600;color:#ae2727', 'font-weight: 600;', {
            ...request,
        });
        return request;
    }

    private postwork(request: HttpRequest<any>, response: HttpResponse<any>): void {
        if (response && response.url) {
            const match: RegExpMatchArray = response.url.match(this.matcher) ?? [];
            console.log(
                `%c[${request.method}] %c[${match[1]}]`,
                'font-weight: 600;color:#27ae60',
                'font-weight: 600;',
                {
                    ...response,
                }
            );
        } else {
            console.log('%cRESPONSE ERROR%c', 'color: #e60000; font-weight: 600; font-size: 1.2em;');
        }
    }
}
