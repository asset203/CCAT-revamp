import {Injectable} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {take} from 'rxjs/operators';
import {Paramter} from 'src/app/shared/models/admin-dynamic-page.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../../http.service';

@Injectable()
export class AdminDPProcedureService {
    loading$ = new BehaviorSubject(false);
    sourceStepParameter = new BehaviorSubject<Paramter[]>([]);
    constructor(private httpService: HttpService) {}
    resetParameter() {
        this.sourceStepParameter.next([]);
    }
    initSourceStepParameter(parameters: Paramter[], stepOrder: number) {
        let outputParameters = [];
        if (parameters) {
            outputParameters = parameters.map((el) => {
                return {
                    ...el,
                    stepOrder,
                };
            });
            const oldParameters = this.sourceStepParameter.getValue();
            const newParameters = [...oldParameters, ...outputParameters];
            this.sourceStepParameter.next(newParameters);
        }
    }
    deleteParameters(deletedParameters: Paramter[]) {
        const oldParameters = this.sourceStepParameter.getValue();
        const outputParameter = oldParameters.filter((oldparam) => {
            return !deletedParameters.find((param) => {
                return oldparam.id === param.id;
            });
        });
        this.sourceStepParameter.next(outputParameter);
    }
    deleteParametersFromDeleteStep(deletedParameters: Paramter[], deletedStepOrder: number) {
        const oldParameters = this.sourceStepParameter.getValue();
        const outputParameter = oldParameters.filter((oldparam) => {
            if (oldparam.stepOrder > deletedStepOrder) {
                oldparam.stepOrder = oldparam.stepOrder - 1;
            }
            return !deletedParameters.find((param) => {
                return oldparam.id === param.id;
            });
        });
        this.sourceStepParameter.next(outputParameter);
    }
    testConnectivity(payload) {
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/test-db-connection',
                payload,
            })
            .pipe(indicate(this.loading$), take(1));
    }
    parseParameter(query: string) {
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/parse-query',
                payload: {
                    query,
                },
            })
            .pipe(indicate(this.loading$), take(1));
    }
    checkParameterValidation(parameter: Paramter) {
        if (
            !parameter.displayName ||
            !parameter.parameterName ||
            !parameter.displayOrder ||
            !parameter.parameterType ||
            !parameter.parameterDataType
        )
            return false;
        if (parameter.parameterType === 1 && !parameter.inputMethod) return false;
        if (parameter.parameterType === 1 && parameter.inputMethod === 2 && !parameter.dropdownList) return false;
        return true;
    }
    parseHttpParameter(query: string) {
        return this.httpService
            .request({
                path: '/ccat/admin-dynamic-pages/http-parse-query',
                payload: {query},
            })
            .pipe(indicate(this.loading$));
    }
    parseResponseHttpParameters(reqObj) {
        return this.httpService.request({
            path: '/ccat/admin-dynamic-pages/parse-response-parameters',
            payload: {
                ...reqObj,
            },
        }).pipe(indicate(this.loading$));;
    }
}
