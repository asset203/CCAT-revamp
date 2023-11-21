import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {map} from 'rxjs/operators';
import {SmsTemplateCs} from 'src/app/shared/models/SmsTemplate.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class SmsTemplateCsService {
    loading$ = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}
    getAllTemplates() {
        return this.httpService
            .request({
                path: '/ccat/sms-template-cs/get-all',
            })
            .pipe(indicate(this.loading$));
    }
    get languages() {
        return this.httpService
            .request({
                path: '/ccat/language/get-all',
            })
            .pipe(
                map((res) => res?.payload?.langaugeModel),
                map((langs) =>
                    langs.map((lang) => {
                        return {
                            ...lang,
                            key: +lang.key,
                        };
                    })
                ),
                indicate(this.loading$)
            );
    }
    get smsActions() {
        return this.httpService
            .request({
                path: '/ccat/lookup/sms-actions/get-all',
            })
            .pipe(
                map((res) => res?.payload?.smsActions),
                indicate(this.loading$)
            );
    }
    get actionParamMap() {
        return this.httpService
            .request({
                path: '/ccat/lookup/sms-action-param-map/get',
            })
            .pipe(
                map((res) => res?.payload?.actionParamMap),
                indicate(this.loading$)
            );
    }
    addSmsTemplate(smsTemplate: SmsTemplateCs) {
        return this.httpService
            .request({
                path: '/ccat/sms-template-cs/add',
                payload: {
                    smsTemplate,
                },
            })
            .pipe(indicate(this.loading$));
    }
    editSmsTemplate(smsTemplate: SmsTemplateCs) {
        return this.httpService
            .request({
                path: '/ccat/sms-template-cs/update',
                payload: {
                    smsTemplate,
                },
            })
            .pipe(indicate(this.loading$));
    }
    deleteSmsTemplate(id: number, templateId: number) {
        return this.httpService.request({
            path: '/ccat/sms-template-cs/delete',
            payload: {
                id,
                templateId,
            },
        });
    }
}
