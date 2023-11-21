import {AfterViewChecked, ChangeDetectorRef, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {AdminDPProcedureService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-procedure.service';
import {AdminDPStepService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-step.service';
import {Paramter} from 'src/app/shared/models/admin-dynamic-page.model';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {HTTP_TYPE, OUTPUT_PARAMETER_TYPE_VALUE, PROCEDURE_TYPE} from '../../admin-dynamic-page-constants';

@Component({
    selector: 'app-general-configs',
    templateUrl: './general-configs.component.html',
    styleUrls: ['./general-configs.component.scss'],
    providers: [ConfirmationService],
})
export class GeneralConfigsComponent implements OnInit, AfterViewChecked {
    loading$ = this.stepService.loading$;
    loadingProced$ = this.procedureService.loading$;
    @Input() stepType: number;
    @Input() step: any;
    stepForm: FormGroup;
    httpStepForm: FormGroup;
    @Input() stepIndex: number;
    @Output() deleteStepEmitter = new EventEmitter<number>();
    constructor(
        private fb: FormBuilder,
        private stepService: AdminDPStepService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private procedureService: AdminDPProcedureService,
        private cdr: ChangeDetectorRef
    ) {}
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }

    ngOnInit(): void {
        this.initStepForm();
    }
    initHttpForm() {
        this.httpStepForm = this.fb.group({});
    }
    initStepForm() {
        this.stepForm = this.fb.group({
            id: [this.step.id],
            pageId: [this.step.pageId],
            stepType: [this.stepType],
            stepName: [this.step.stepName, [Validators.required]],
            stepOrder: [{value: this.step.stepOrder, disabled: true}],
            isHidden: [this.step.isHidden],
            stepConfiguration:
                this.stepType === PROCEDURE_TYPE
                    ? this.stepConfigrationProcedureFormGroup()
                    : this.stepConfigrationHttpFormGroup(),
        });
    }
    stepConfigrationProcedureFormGroup() {
        let extraConfigurations;
        if (this.step?.stepConfiguration?.extraConfigurations) {
            extraConfigurations = this.step?.stepConfiguration?.extraConfigurations;
            extraConfigurations = JSON.parse(extraConfigurations);
        } else {
            extraConfigurations = [];
        }
        return this.fb.group({
            stepId: [this.step.id],
            id: [this.step?.stepConfiguration?.id],
            stepType: [this?.stepType],
            procedureName: [this.step?.stepConfiguration?.procedureName, [Validators.required]],
            databaseURL: [this.step?.stepConfiguration?.databaseURL, [Validators.required]],
            databaseUsername: [this?.step?.stepConfiguration?.databaseUsername, [Validators.required]],
            databasePassword: [null],
            schema: [this?.step?.stepConfiguration?.schema],
            successCode: [this?.step?.stepConfiguration?.successCode, [Validators.required]],
            maxRecords: [this?.step?.stepConfiguration?.maxRecords, [Validators.required]],
            extraConfigurations: this.fb.array(extraConfigurations.map((config) => this.fb.group(config))),
        });
    }
    stepConfigrationHttpFormGroup() {
        let headers;
        if (this.step?.stepConfiguration?.headers) {
            headers = this.step?.stepConfiguration?.headers;
            headers = JSON.parse(headers);
        } else {
            headers = [];
        }
        return this.fb.group({
            id: [this.step?.stepConfiguration?.id],
            stepId: [this.step?.id],
            stepType: [this?.stepType],
            httpURL: [this.step?.stepConfiguration?.httpURL, [Validators.required]],
            httpMethod: [this.step?.stepConfiguration?.httpMethod, [Validators.required]],
            headers: this.fb.array(headers.map((header) => this.fb.group(header))),
            successCode: [this?.step?.stepConfiguration?.successCode, [Validators.required]],
            maxRecords: [this?.step?.stepConfiguration?.maxRecords, [Validators.required]],
            requestContentType: [this?.step?.stepConfiguration?.requestContentType],
            responseContentType: [this?.step?.stepConfiguration?.responseContentType, [Validators.required]],
            responseForm: [this?.step?.stepConfiguration?.responseForm],
            requestBody: [this?.step?.stepConfiguration?.requestBody],
            mainDelimiter: [this?.step?.stepConfiguration?.mainDelimiter],
            keyValueDelimiter: [this?.step?.stepConfiguration?.mainDelimiter],
        });
    }
    submitStep() {
        const step = this.stepForm.getRawValue();
        if (step.stepType === HTTP_TYPE) {
            if (step?.stepConfiguration?.headers?.length === 0) {
                step.stepConfiguration.headers = null;
            } else {
                step.stepConfiguration.headers = JSON.stringify(step.stepConfiguration.headers);
            }
        } else {
            if (
                !step?.stepConfiguration?.extraConfigurations ||
                step?.stepConfiguration?.extraConfigurations?.length === 0
            ) {
                step.stepConfiguration.extraConfigurations = null;
            } else {
                step.stepConfiguration.extraConfigurations = JSON.stringify(step.stepConfiguration.extraConfigurations);
            }
        }
        step.stepConfiguration.parameters = this.step.stepConfiguration.parameters;
        if (!this.formStepId) {
            this.stepService.addStep(step, this.stepIndex).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(68).message);
                    const paramters: Paramter[] = res?.payload?.addedStep?.stepConfiguration?.parameters;
                    this.procedureService.initSourceStepParameter(
                        paramters.filter((params) => params.parameterType === OUTPUT_PARAMETER_TYPE_VALUE),
                        step.stepOrder
                    );
                }
            });
        } else {
            this.stepService.editStep(step, this.stepIndex).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(69).message);
                    const paramters: Paramter[] = res?.payload?.updatedStep?.stepConfiguration?.parameters;
                    this.procedureService.deleteParameters(
                        this.step.stepConfiguration.parameters.filter(
                            (param) => param.parameterType === OUTPUT_PARAMETER_TYPE_VALUE
                        )
                    );
                    this.procedureService.initSourceStepParameter(
                        paramters.filter((param) => param.parameterType === OUTPUT_PARAMETER_TYPE_VALUE),
                        res?.payload?.updatedStep.stepOrder
                    );
                }
            });
        }
    }
    deleteStep() {
        this.stepService.deleteStep(this.stepIndex, this.step.id).subscribe((res) => {
            if (res.statusCode === 0) {
                this.procedureService.deleteParametersFromDeleteStep(
                    this.step.stepConfiguration.parameters.filter(
                        (param) => param.parameterType === OUTPUT_PARAMETER_TYPE_VALUE
                    ),
                    this.step.stepOrder
                );

                this.deleteStepEmitter.emit(this.stepIndex);
                this.toastrService.success(this.messageService.getMessage(70).message);
            }
        });
    }
    confirmDeleteStep() {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(71).message,
            accept: () => {
                this.deleteStep();
            },
        });
    }
    get formStepId() {
        return this.stepForm.get('id').value;
    }
    get enabled() {
        return (
            this.stepForm.valid &&
            this.step.stepConfiguration.parameters.length > 0 &&
            this.checkParametersValidation(this.step.stepConfiguration.parameters)
        );
    }
    checkParametersValidation(parameters: Paramter[]) {
        for (let param of parameters) {
            if (!param.valid) {
                return false;
            }
        }
        return true;
    }
}
