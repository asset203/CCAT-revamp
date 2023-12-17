import { HttpClient, HttpHeaders } from '@angular/common/http';
import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {indicate} from 'src/app/shared/rxjs/indicate';
import { environment } from 'src/environments/environment';
import { AppConfigService } from '../../app-config.service';
import {HttpService} from '../../http.service';
@Injectable()
export class AdminDynamicPageService {
    constructor(private httpService: HttpService , private http : HttpClient,private appConfigsService : AppConfigService ) {}
    loading = new BehaviorSubject(false);
    private disableAddStep = new BehaviorSubject(true);
    steps: any[] = [];
    public get allDynamicPages$() {
        return this.httpService
            .request({path: '/ccat/admin-dynamic-pages/get-all'})
            .pipe(take(1), indicate(this.loading));
    }
    get disabledAddStep$() {
        return this.disableAddStep;
    }
    setDisabledAddStep(disabled: boolean) {
        this.disableAddStep.next(disabled);
    }
    downloadDynamicPage(pageId :number) {
        let headers = new HttpHeaders().set('Accept', '*/*').set('Content-Type', 'application/json');
        return this.http.post(
            `${this.appConfigsService.config.apiBaseUrl}/ccat/admin-dynamic-pages/import`,
            {pageId},
            {
                headers: headers,
                responseType: 'blob',
            }
        ).pipe(indicate(this.loading));
    }
}
