import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs/internal/Observable';
import {ApiRequest} from '../interface/api-request.interface';
import {environment} from '../../../environments/environment';
import { AppConfigService } from './app-config.service';

@Injectable({
    providedIn: 'root',
})
export class HttpService {
    // base URL stored in environment.ts
    baseURL;

    // baseURL = 'http:localhost:8080/';

    constructor(
        private http: HttpClient,
        private appConfigsService : AppConfigService
    ) {
        
    }

    public request(req: ApiRequest): Observable<any> {
        const absolute = req.path.match(
            '^(http://|https://|http://|https://)[a-z0-9]+([-.]{1}[a-z0-9]+)*(:[0-9]{1,5})?(/.*)?$'
        );
        const apiUrl: string = absolute ? req.path : `${this.appConfigsService.config.apiBaseUrl}${req.path}`;
        const httpOptions = {
            headers: new HttpHeaders(),
        };
        if (req.headers) {
            // Handle Headers
            req.headers.forEach((value: string, key: string) => {
                httpOptions.headers = httpOptions.headers.set(key, value);
            });
        }

        // Handle Request
        let obs: Observable<any>;

        // Backend workaround
        req.payload = req.payload == null ? {} : req.payload;
        if (req.method) {
            obs = this.http.request(req.method, apiUrl, {
                body: req.payload,
                headers: httpOptions.headers,
                observe: 'body',
                params: req.params,
                responseType : req.responseType
                
            });
        } else {
            if (req.payload) {
                obs = this.http.post(apiUrl, req.payload, httpOptions);
            } else {
                obs = this.http.get(apiUrl, httpOptions);
            }
        }
        return obs;
    }
}
