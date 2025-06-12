import { Component, Input, OnDestroy, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import {
    CursorParameterMapping,
    ParameterDropdown,
    Paramter,
    StepConfigurationProceduce,
} from 'src/app/shared/models/admin-dynamic-page.model';
import { faDatabase, faSignalPerfect, faCircleCheck, faXmarkCircle } from '@fortawesome/free-solid-svg-icons';
import {
    CURSOR_DATA_TYPE,
    cursorDataTypes,
    DATE_DATA_TYPE,
    INPUT_PARAMETER_TYPE_VALUE,
    MENU_INPUT_METHOD,
    OUTPUT_FROM_OTHER_STEP_INPUT_METHOD,
    OUTPUT_PARAMETER_TYPE_VALUE,
    procedureDataTypes,
    procedureParamInputMethod,
    procedureParamsTypes,
} from '../../admin-dynamic-page-constants';
import { AdminDPProcedureService } from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-procedure.service';
import { Subscription } from 'rxjs';
@Component({
    selector: 'app-procedure-configs',
    templateUrl: './procedure-configs.component.html',
    styleUrls: ['./procedure-configs.component.scss'],
})
export class ProcedureConfigsComponent implements OnInit, OnDestroy {
    faDatabase = faDatabase;
    faSignal = faSignalPerfect;
    faCircleCheck = faCircleCheck;
    faXmarkCircle = faXmarkCircle;
    INPUT_PARAMETER_TYPE_VALUE = INPUT_PARAMETER_TYPE_VALUE;
    MENU_INPUT_METHOD = MENU_INPUT_METHOD;
    OUTPUT_FROM_OTHER_STEP_INPUT_METHOD = OUTPUT_FROM_OTHER_STEP_INPUT_METHOD;
    DATE_DATA_TYPE = DATE_DATA_TYPE;
    OUTPUT_PARAMETER_TYPE_VALUE = OUTPUT_PARAMETER_TYPE_VALUE;
    CURSOR_DATA_TYPE = CURSOR_DATA_TYPE;
    dataTypes = [...procedureDataTypes];
    paramTypes = [...procedureParamsTypes];
    displayMethods = [...procedureParamInputMethod];
    procedureForm: FormGroup;
    parameterForm: FormGroup;
    dialogVisiabilty: boolean = false;
    addParameterMode: boolean;
    @Input() stepConfiguration: StepConfigurationProceduce;
    @Input() stepForm: FormGroup;
    @Input() stepOrder: number;
    editParameterIndex: number;
    constructor(private fb: FormBuilder, private procedureService: AdminDPProcedureService) { }
    parseParameterDialog: boolean = false;
    parsedQuery: string;
    sourceStepParameters: Paramter[] = [];
    stepParameterSubscription = new Subscription();
    connectivityMessage: string;
    connectivityStatus: number;
    loading$ = this.procedureService.loading$;
    selectedOutputSourceParameter: Paramter;
    isProcedureSourceParameter = false;
    sourceParameterNames;
    cursorDataTypes = [...cursorDataTypes];
    cursorMappingDateFormate = false;
    ngOnInit(): void {
        this.checkIfParamemterHasCursorOutput();
        this.initProcedureForm();
        this.initParameterForm();
        this.stepParameterSubscription = this.procedureService.sourceStepParameter.subscribe((sourcePara) => {
            this.sourceStepParameters = sourcePara.filter((el) => el.stepOrder < this.stepForm.get('stepOrder').value);
            if (this.sourceStepParameters.length === 0) {
                this.displayMethods[3].disable = true;
            } else {
                this.displayMethods[3].disable = false;
            }
        });
        this.stepConfiguration.parameters.forEach((parameter) => {
            parameter.valid = this.procedureService.checkParameterValidation(parameter);
        });
        this.stepConfiguration.parameters = this.stepConfiguration.parameters.sort((a, b) =>
            a.parameterOrder > b.parameterOrder ? 1 : -1
        );
    }
    // init component forms
    initProcedureForm() {
        this.procedureForm = this.stepForm.get('stepConfiguration') as FormGroup;
    }
    initParameterForm() {
        this.parameterForm = this.fb.group({
            id: [null],
            parameterName: [null, [Validators.required]],
            displayName: [null, [Validators.required]],
            parameterType: [null, [Validators.required]],
            parameterDataType: [null, [Validators.required]],
            inputMethod: [null],
            displayOrder: [null, [Validators.required]],
            dropdownList: this.fb.array([]),
            cursorParameterMappings: this.fb.array([]),
            configId: [null],
            sourceStepParameterId: [null],
            parameterOrder: [
                { value: this.stepConfiguration.parameters.length + 1, disabled: true },
                [Validators.required],
            ],
            defaultValue: [null],
            responseCode: [false],
            responseDescription: [false],
            hidden: [false],
            dateFormat: [null],
            sourceStepParameterName: [null],
        });
    }
    // form getter for validations
    get formParamType() {
        return (this.parameterForm.get('parameterType') as FormControl).value;
    }
    get formDisplayMethod() {
        return (this.parameterForm.get('inputMethod') as FormControl).value;
    }
    get dropDownControls() {
        return (<FormArray>this.parameterForm.get('dropdownList')).controls;
    }
    get cursorMappinControls() {
        return (<FormArray>this.parameterForm.get('cursorParameterMappings')).controls;
    }
    get parameterDataType() {
        return (this.parameterForm.get('parameterDataType') as FormControl).value;
    }
    get dbUrl() {
        return this.procedureForm.get('databaseURL').value;
    }
    get dbUsername() {
        return this.procedureForm.get('databaseUsername').value;
    }
    get dbPassword() {
        return this.procedureForm.get('databasePassword').value;
    }
    get enabledTestConnectivity() {
        return this.dbUrl && this.dbUsername && this.dbPassword;
    }
    // drop down and cursor mapping form arrays
    addDropDownToFormArray(dropdownItem: ParameterDropdown) {
        (this.parameterForm.get('dropdownList') as FormArray).push(
            this.fb.group({
                label: [dropdownItem.label, [Validators.required]],
                value: [dropdownItem.value, [Validators.required]],
            })
        );
    }
    addCursorMappingToFormArray(cursorMappingItem: CursorParameterMapping) {
        (this.parameterForm.get('cursorParameterMappings') as FormArray).push(
            this.fb.group({
                actualColumnName: [cursorMappingItem.actualColumnName],
                displayColumnName: [cursorMappingItem.displayColumnName],
                id: [cursorMappingItem.id],
                parameterId: [cursorMappingItem.parameterId],
                displayOrder: [cursorMappingItem.displayOrder || 0],
                dataType: [cursorMappingItem.dataType],
                dateFormat: [cursorMappingItem.dateFormat]
            })
        );
    }
    getDropDownValidity(index) {
        return (<FormArray>this.parameterForm.get('dropdownList')).controls[index] as FormGroup;
    }
    deleteFormDropDownFormArray(index: number) {
        (<FormArray>this.parameterForm.get('dropdownList')).removeAt(index);
    }
    deleteFormCursorMappingFormArray(index: number) {
        (<FormArray>this.parameterForm.get('cursorParameterMappings')).removeAt(index);
    }
    resetParamDropDownForm() {
        while ((<FormArray>this.parameterForm.get('dropdownList')).length !== 0) {
            (<FormArray>this.parameterForm.get('dropdownList')).removeAt(0);
        }
    }
    resetCursorMapping() {
        while ((<FormArray>this.parameterForm.get('cursorParameterMappings')).length !== 0) {
            (<FormArray>this.parameterForm.get('cursorParameterMappings')).removeAt(0);
        }
    }
    resetParamForm() {
        this.initParameterForm();
        this.dialogVisiabilty = false;
    }
    setMenuFormArray(parameter: Paramter) {
        parameter.dropdownList.forEach((dropdownItem) => {
            this.addDropDownToFormArray(dropdownItem);
        });
    }
    setCursorMappinFormArray(parameter: Paramter) {
        parameter.cursorParameterMappings.forEach((mapping) => {
            this.addCursorMappingToFormArray(mapping);
        });
    }
    // add and edit
    addOrEditParameter() {
        const parameter: Paramter = this.parameterForm.getRawValue();
        parameter.valid = true;
        if (this.addParameterMode) {
            this.stepConfiguration.parameters.push(parameter);
            this.reorderTable();
        } else {
            this.stepConfiguration.parameters[this.editParameterIndex] = parameter;
        }
        this.dialogVisiabilty = false;
        this.checkIfParamemterHasCursorOutput();
    }
    switchAddMode() {
        this.initParameterForm();
        this.addParameterMode = true;
        this.dialogVisiabilty = true;
    }
    switchEditMode(parameter: Paramter, editedIndex: number) {
        this.addParameterMode = false;
        this.parameterForm.patchValue(parameter);
        this.editParameterIndex = editedIndex;
        if (this.parameterForm.controls['parameterType'].value === INPUT_PARAMETER_TYPE_VALUE) {
            this.setParaInputMethodValidation(1);
        } else {
            this.setParaInputMethodValidation(2);
        }
        this.dialogVisiabilty = true;
        if (parameter?.inputMethod === MENU_INPUT_METHOD) {
            this.setMenuFormArray(parameter);
        }
        if (parameter.parameterDataType === CURSOR_DATA_TYPE) {
            this.setCursorMappinFormArray(parameter);
        }
    }
    // delete
    deleteParameter(parameterIndex: number) {
        this.stepConfiguration.parameters.splice(parameterIndex, 1);
        this.checkIfParamemterHasCursorOutput();
        this.reorderTable();
    }
    // set form validation
    setParaInputMethodValidation(value) {
        if (value === INPUT_PARAMETER_TYPE_VALUE) {
            this.resetCursorMapping();
            this.parameterForm.get('inputMethod').setValidators([Validators.required]);
            if (this.parameterForm.get('parameterDataType').value === 3) {
                this.parameterForm.get('parameterDataType').setValue(null);
                this.changeParameterDataType(null);
            }
        } else {
            this.resetParamDropDownForm();
            this.parameterForm.get('inputMethod').clearValidators();
            this.parameterForm.get('inputMethod').setValue(null);
            this.parameterForm.get('defaultValue').setValue(null);
            this.resetParamDropDownForm();
            this.changeOldParameterStepSourceValidity(2);
        }
        this.parameterForm.get('inputMethod').updateValueAndValidity();
        this.parameterForm.get('defaultValue').updateValueAndValidity();
    }
    changeParameterDataType(value) {
        this.cursorMappingDateFormate = false;
        if (!this.addParameterMode) {
            this.stepConfiguration.parameters[this.editParameterIndex].parameterDataType = value;
        }
        if (value === CURSOR_DATA_TYPE) {
            this.parameterForm.get('parameterType').setValue(2);
            this.setParaInputMethodValidation(2);
        }

        if (value !== CURSOR_DATA_TYPE) this.resetCursorMapping();
        if (value === DATE_DATA_TYPE) {
            this.changeDateFormatValidty(true);
        }
        if (value !== DATE_DATA_TYPE) {
            this.changeDateFormatValidty(false);
        }
    }
    changeDateFormatValidty(enable: boolean) {
        if (enable) {
            this.parameterForm.get('dateFormat').setValidators([Validators.required]);
        } else {
            this.parameterForm.get('dateFormat').clearValidators();
            this.parameterForm.get('dateFormat').setValue(null);
        }
        this.parameterForm.get('dateFormat').updateValueAndValidity();
    }
    onInputMethodChangeValue(value: number) {
        if (value === MENU_INPUT_METHOD) {
            this.parameterForm.get('defaultValue').setValue(null);
            this.parameterForm.get('defaultValue').updateValueAndValidity();
            this.parameterForm.get('dropdownList').setValidators([Validators.required]);
            this.parameterForm.get('dropdownList').updateValueAndValidity();
        } else {
            this.resetParamDropDownForm();
            this.parameterForm.get('dropdownList').clearValidators();
            this.parameterForm.get('dropdownList').updateValueAndValidity();
        }
        this.changeOldParameterStepSourceValidity(value);
    }
    reorderTable() {
        this.stepConfiguration.parameters = this.stepConfiguration.parameters.map((el, index) => {
            return {
                ...el,
                parameterOrder: index + 1,
            };
        });
    }
    changeOldParameterStepSourceValidity(value: number) {
        if (value === OUTPUT_FROM_OTHER_STEP_INPUT_METHOD) {
            this.parameterForm.get('sourceStepParameterId').setValidators([Validators.required]);
        } else {
            this.parameterForm.get('sourceStepParameterId').clearValidators();
            this.parameterForm.get('sourceStepParameterId').setValue(null);
        }
        this.parameterForm.get('sourceStepParameterId').updateValueAndValidity();
    }
    checkIfParamemterHasCursorOutput() {
        if (this?.stepConfiguration?.parameters) {
            for (let param of this.stepConfiguration.parameters) {
                if (param.parameterDataType === 3) {
                    this.dataTypes[2].disable = true;
                    return;
                }
            }
            this.dataTypes[2].disable = false;
        }
    }

    //parse parameters
    parseParameter() {
        let parametersLength: number = this.stepConfiguration.parameters.length;
        this.parseParameterDialog = false;
        this.procedureService.parseParameter(this.parsedQuery).subscribe((res) => {
            if (res.statusCode === 0) {
                res.payload.parameters.forEach((parameter: Paramter) => {
                    parameter.parameterOrder = parametersLength + 1;
                    const notFound: boolean =
                        this.stepConfiguration.parameters.filter(
                            (param) => param.parameterName === parameter.parameterName
                        ).length === 0;
                    if (notFound) {
                        parameter.valid = this.procedureService.checkParameterValidation(parameter);
                        this.stepConfiguration.parameters.push(parameter);
                        parametersLength += 1;
                    }
                });
            }
            this.parsedQuery = null
        });
    }
    testConnectivity() {
        this.connectivityMessage = null;
        this.connectivityStatus = null;
        const payload = {
            dbUrl: this.dbUrl,
            dbUsername: this.dbUsername,
            dbPassword: this.dbPassword,
        };
        this.procedureService.testConnectivity(payload).subscribe((res) => {
            if (res.statusCode === 0) {
                this.connectivityMessage = 'Connection Success';
                this.connectivityStatus = 1;
            } else {
                this.connectivityMessage = res.statusMessage;
                this.connectivityStatus = 2;
            }
        });
    }
    addExtraCofigsToFormArray(configs: any) {
        (this.procedureForm.get('extraConfigurations') as FormArray).push(
            this.fb.group({
                name: [configs.name, [Validators.required]],
                value: [configs.value, [Validators.required]],
            })
        );
    }
    get extraConfigsControl() {
        return (<FormArray>this.procedureForm.get('extraConfigurations')).controls;
    }
    deleteFormExtraConfigsFormArray(index: number) {
        (<FormArray>this.procedureForm.get('extraConfigurations')).removeAt(index);
    }
    ngOnDestroy(): void {
        this.stepParameterSubscription.unsubscribe();
    }
    selectSourceStepParameter(value) {
        const selectedOutputSourceParameter = this.sourceStepParameters.filter((el) => el.id === value)[0];
        if (selectedOutputSourceParameter.parameterDataType === 3) {
            this.selectedOutputSourceParameter = selectedOutputSourceParameter;
            if (this.selectedOutputSourceParameter?.cursorParameterMappings?.length > 0) {
                this.isProcedureSourceParameter = true;
                this.sourceParameterNames = this.selectedOutputSourceParameter?.cursorParameterMappings;
            } else if (this.selectedOutputSourceParameter?.httpResponseMappingModels?.length > 0) {
                this.sourceParameterNames = selectedOutputSourceParameter.httpResponseMappingModels;
                this.isProcedureSourceParameter = false;
            } else {
                this.sourceParameterNames = null;
            }
        } else {
            this.selectedOutputSourceParameter = null;
            this.sourceParameterNames = null;
        }
    }

    addDateFormateInput(value) {

        if (value === DATE_DATA_TYPE) {
            this.cursorDateFormatValidty(true);
        }
        if (value !== DATE_DATA_TYPE) {
            this.cursorDateFormatValidty(false);
        }
    }

    cursorDateFormatValidty(enable: boolean) {
        if (enable) {
            this.cursorMappingDateFormate = true;
            const cursorMappings = this.parameterForm.get('cursorParameterMappings') as FormArray;
            cursorMappings.controls.forEach(control => {
                control.get('dateFormat').setValidators([Validators.required]);
                control.get('dateFormat').updateValueAndValidity();
            });
        } else {
            this.cursorMappingDateFormate = false;
            const cursorMappings = this.parameterForm.get('cursorParameterMappings') as FormArray;
            cursorMappings.controls.forEach(control => {
                control.get('dateFormat').clearValidators();
                control.get('dateFormat').setValue(null);
                control.get('dateFormat').updateValueAndValidity();
            });
        }
    }
}
