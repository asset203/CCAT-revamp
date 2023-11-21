import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take, tap} from 'rxjs/operators';
import {Step} from 'src/app/shared/models/admin-dynamic-page.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../../http.service';
import {AdminDynamicPageService} from './admin-dynamic-page.service';

@Injectable()
export class AdminDPStepService {
    constructor(private httpService: HttpService, private dynamicPageService: AdminDynamicPageService) {}
    loading$ = new BehaviorSubject(false);
    pageId: number;
    steps$ = new BehaviorSubject<Step[]>([]);
    private lastStepOrder: number = 0;
    setSteps(steps: Step[]) {
        this.steps$.next(steps);
    }
    addStep(step: Step, stepIndex: number) {
        const payload = {
            pageId: this.pageId,
            step: {...step, pageId: this.pageId},
        };

        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/step/add',
                payload,
            })
            .pipe(
                take(1),
                indicate(this.loading$),
                tap((res) => {
                    if (res.statusCode === 0) {
                        const steps: Step[] = this.steps$.getValue();
                        steps[stepIndex] = res.payload.addedStep;
                        this.steps$.next(steps);
                        this.dynamicPageService.setDisabledAddStep(false);
                        this.lastStepOrder = this.lastStepOrder + 1;
                    }
                })
            );
    }
    editStep(step: Step, stepIndex: number) {
        const payload = {
            pageId: this.pageId,
            step: {...step, pageId: this.pageId},
        };
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/step/update',
                payload,
            })
            .pipe(
                take(1),
                indicate(this.loading$),
                tap((res) => {
                    if (res.statusCode === 0) {
                        const steps: Step[] = this.steps$.getValue();
                        steps[stepIndex] = res.payload.updatedStep;
                        this.steps$.next(steps);
                    }
                })
            );
    }
    deleteStep(index: number, stepId: number) {
        const deletedStepPageId = this.pageId;
        console.log(deletedStepPageId);
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/step/delete',
                payload: {
                    pageId: deletedStepPageId,
                    stepId,
                },
            })
            .pipe(
                indicate(this.loading$),
                take(1),
                tap((res) => {
                    if (res.statusCode === 0) {
                        let steps: Step[] = this.steps$.getValue();
                        steps.splice(index, 1);
                        steps = steps.map((step, index) => {
                            if (step.stepOrder > index + 1) {
                                const newStep: Step = {
                                    ...step,
                                    stepOrder: step.stepOrder - 1,
                                };
                                if (newStep.id) {
                                    this.httpService
                                        .request({
                                            path: '/ccat/admin-dynamic-pages/step/update',
                                            payload: {
                                                pageId: deletedStepPageId,
                                                step: newStep,
                                            },
                                        })
                                        .subscribe();
                                }
                                return {
                                    ...newStep,
                                };
                            } else {
                                return step;
                            }
                        });
                        this.steps$.next(steps);
                    }
                })
            );
    }
    addHttpStepsToSteps() {
        const steps = this.steps$.getValue();
        steps.push({
            pageId: this.pageId,
            stepOrder: this.lastStepOrder + 1,
            stepName: null,
            isHidden : false,
            stepType: 2,
            stepConfiguration: {
                httpURL: null,
                httpMethod: null,
                maxRecords: null,
                successCode: null,
                headers: null,
                requestContentType: null,
                responseContentType: null,
                responseForm: null,
                requestBody: null,
                mainDelimiter: null,
                keyValueDelimiter: null,
                parameters: [],
            },
        });
        this.steps$.next(steps);
    }
    addProcedureSteptoSteps() {
        const steps = this.steps$.getValue();
        steps.push({
            pageId: this.pageId,
            stepOrder: this.lastStepOrder + 1,
            stepName: null,
            stepType: 1,
            isHidden : false,
            stepConfiguration: {
                databasePassword: null,
                databaseURL: null,
                databaseUsername: null,
                datasourceName: null,
                maxRecords: null,
                procedureName: null,
                successCode: null,
                schema : null,
                parameters: [],
                extraConfigurations : null
            },
        });
        this.steps$.next(steps);
    }
    setStepOrder(order: number) {
        this.lastStepOrder = order;
    }
    decreaseLastOrder() {
        this.lastStepOrder -= 1;
    }
    setPageId(id: number) {
        this.pageId = id;
    }
}
