import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {map, take, tap} from 'rxjs/operators';
import {AdminDynamicPageInfo} from 'src/app/shared/models/admin-dynamic-page.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../../http.service';
import {AdminDPStepService} from './admin-dp-step.service';

@Injectable()
export class AdminDpPageConfigrationService {
    constructor(private httpService: HttpService, private stepService: AdminDPStepService) {}
    loading = new BehaviorSubject(false);
    page$ = new BehaviorSubject<AdminDynamicPageInfo>({});
    setPageInfo(pageInfo: AdminDynamicPageInfo) {
        this.page$.next(pageInfo);
    }
    addPage(pageInfo: AdminDynamicPageInfo) {
        this.setPageInfo(pageInfo);
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/add',
                payload: {
                    ...pageInfo,
                },
            })
            .pipe(
                take(1),
                indicate(this.loading),
                tap((res) => {
                    const pageInfo = this.page$.getValue();
                    pageInfo.pageId = res.payload.pageId;
                    pageInfo.privilegeId=res.payload.privilegeId;
                    this.stepService.pageId = res.payload.pageId;
                    this.page$.next(pageInfo);
                })
            );
    }
    editPage(pageName: string,requireSubscriber) {
        const page:AdminDynamicPageInfo = this.page$.getValue()
        page.pageName=pageName
        page.requireSubscriber=requireSubscriber;
        this.page$.next(page);
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/update',
                payload: {
                    ...this.page$.getValue(),
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    deletePage(pageId) {
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/delete',
                payload: {
                    pageId,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    getEditedPage(pageId: number) {
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/get',
                payload: {
                    pageId,
                },
            })
            .pipe(
                map((res) => res?.payload?.dynamicPage),
                take(1),
                indicate(this.loading),
                tap((res) => {
                    const pageInfo: AdminDynamicPageInfo = {
                        pageName: res.pageName,
                        requireSubscriber: res.requireSubscriber,
                        privilegeName: res.privilegeName,
                        pageId: res.id,
                        privilegeId : res.privilegeId,
                    };
                    this.setPageInfo(pageInfo);
                    this.stepService.setStepOrder(res?.step?.length)
                })
            );
    }
}
