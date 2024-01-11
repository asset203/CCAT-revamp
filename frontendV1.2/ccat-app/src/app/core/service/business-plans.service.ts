import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {BusinessPlan} from 'src/app/shared/models/business-plans.interface';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from './http.service';
@Injectable({
    providedIn: 'root',
})
export class BusinessPlansService {
    private token = JSON.parse(sessionStorage.getItem('session')).token;
    loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}
    get loading$() {
        return this.loading;
    }
    get businessPlans$() {
        return this.getBusinessPlansLookup('/ccat/business-plan/get-all');
    }
    get hlrProfiles$() {
        return this.getBusinessPlansLookup('/ccat/lookup/hlr-profiles/get-all');
    }
    addBusinessPlan(businessPlan: BusinessPlan) {
        return this.updateAndAddBusinessPlan('/ccat/business-plan/add', businessPlan);
    }
    updateBusinessPlan(businessPlan: BusinessPlan) {
        return this.updateAndAddBusinessPlan('/ccat/business-plan/update', businessPlan);
    }
    deleteBusinessPlan(businessPlanId: number) {
        return this.httpService
            .request({
                path: '/ccat/business-plan/delete',
                payload: {
                    token: this.token,
                    businessPlanId,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    getBusinessPlansLookup(path: string) {
        return this.httpService
            .request({
                path,
                payload: {
                    token: this.token,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    updateAndAddBusinessPlan(path: string, businessPlan: BusinessPlan) {
        return this.httpService
            .request({
                path,
                payload: {
                    token: this.token,
                    businessPlan,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
}
