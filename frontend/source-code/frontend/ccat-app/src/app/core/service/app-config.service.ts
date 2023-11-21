import { HttpClient } from '@angular/common/http';
import { Injectable, Injector } from '@angular/core';
import { AppConfig } from 'src/app/shared/models/AppConfigs';
import { retry } from 'rxjs/operators';
@Injectable({
    providedIn: 'root',
})
export class AppConfigService {
    private appConfig;

    constructor(private http: HttpClient) { }

    loadAppConfig() {
        //let http = this.injector.get(HttpClient);

        return this.http.get('./assets/app.config.json')
            .toPromise()
            .then(data => {
                this.appConfig = data;
            })
    }

    get config() {
        return this.appConfig;
    }
}
