import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormArray, FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {
    ParameterDropdown,
    Paramter,
    StepConfigurationHttp,
    HttpResponseMappingModels,
} from 'src/app/shared/models/admin-dynamic-page.model';
import {
    contentTypes,
    DATE_DATA_TYPE,
    fileExtentions,
    GET_HTTP_METHOD,
    httpDataTypes,
    httpMethods,
    httpResponseForms,
    INPUT_PARAMETER_TYPE_VALUE,
    JSON_CONTENT_TYPE,
    LIST_DATA_TYPE,
    MENU_INPUT_METHOD,
    OUTPUT_FROM_OTHER_STEP_INPUT_METHOD,
    OUTPUT_PARAMETER_TYPE_VALUE,
    POST_HTTP_METHOD,
    procedureDataTypes,
    procedureParamInputMethod,
    procedureParamsTypes,
    TEXT_CONTENT_TYPE,
    XML_CONTENT_TYPE,
} from '../../admin-dynamic-page-constants';
import {faDatabase, faSignalPerfect, faCircleCheck, faXmarkCircle, faCode} from '@fortawesome/free-solid-svg-icons';
import {Subscription} from 'rxjs';
import {AdminDPProcedureService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-procedure.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {FileUpload} from 'primeng/fileupload';
@Component({
    selector: 'app-http-configs',
    templateUrl: './http-configs.component.html',
    styleUrls: ['./http-configs.component.scss'],
})
export class HttpConfigsComponent implements OnInit {
    @Input() stepConfiguration: StepConfigurationHttp;
    @Input() stepForm: FormGroup;
    @Input() stepOrder: number;
    @ViewChild('resquestPostFile') requestFileUpload: FileUpload;
    @ViewChild('responsePostFile') responseFileUpload: FileUpload;
    httpForm: FormGroup;
    faCode = faCode;
    faDatabase = faDatabase;
    faSignal = faSignalPerfect;
    faCircleCheck = faCircleCheck;
    faXmarkCircle = faXmarkCircle;
    POST_HTTP_METHOD = POST_HTTP_METHOD;
    GET_HTTP_METHOD = GET_HTTP_METHOD;
    TEXT_CONTENT_TYPE = TEXT_CONTENT_TYPE;
    INPUT_PARAMETER_TYPE_VALUE = INPUT_PARAMETER_TYPE_VALUE;
    MENU_INPUT_METHOD = MENU_INPUT_METHOD;
    OUTPUT_FROM_OTHER_STEP_INPUT_METHOD = OUTPUT_FROM_OTHER_STEP_INPUT_METHOD;
    DATE_DATA_TYPE = DATE_DATA_TYPE;
    OUTPUT_PARAMETER_TYPE_VALUE = OUTPUT_PARAMETER_TYPE_VALUE;
    XML_CONTENT_TYPE = XML_CONTENT_TYPE;
    JSON_CONTENT_TYPE = JSON_CONTENT_TYPE;
    LIST_DATA_TYPE = LIST_DATA_TYPE;
    httpMethods = [...httpMethods];
    contentTypes = [...contentTypes];
    dataTypes = [...httpDataTypes];
    paramTypes = [...procedureParamsTypes];
    displayMethods = [...procedureParamInputMethod];
    httpResponseForms = [...httpResponseForms];
    fileExtentions = {...fileExtentions};
    parameterForm: FormGroup;
    dialogVisiabilty: boolean = false;
    addParameterMode: boolean;
    editParameterIndex: number;
    sourceStepParameters: Paramter[] = [];
    stepParameterSubscription = new Subscription();
    selectedOutputSourceParameter: Paramter;
    isProcedureSourceParameter = false;
    sourceParameterNames = [];
    parsePostRequestParametersDialog: boolean = false;
    installedRequestPostFile;
    isInstalledRequestPostFile: boolean;
    parsedRequestQuery: string;
    paths;
    xPathsSuggestions;
    parsePostResponseParametersDialog: boolean = false;
    responseParseExtensionFile;
    rootName;
    constructor(
        private fb: FormBuilder,
        private procedureService: AdminDPProcedureService,
        private toastrService: ToastService,
        private messageService: MessageService
    ) {}
    ngOnInit() {
        this.initHttpForm();
        this.initParameterForm();
        this.stepParameterSubscription = this.procedureService.sourceStepParameter.subscribe((sourcePara) => {
            console.log(this.stepForm.get('stepOrder').value);
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
            httpResponseMappingModels: this.fb.array([]),
            configId: [null],
            sourceStepParameterId: [null],
            parameterOrder: [
                {value: this.stepConfiguration?.parameters?.length + 1, disabled: true},
                [Validators.required],
            ],
            defaultValue: [null],
            responseCode: [false],
            responseDescription: [false],
            hidden: [false],
            dateFormat: [null],
            sourceStepParameterName: [null],
            xPath: [null],
        });
    }
    get sourceParameterName(){
        return this.parameterForm.get("sourceStepParameterName").value
    }
    initHttpForm() {
        this.httpForm = this.stepForm.get('stepConfiguration') as FormGroup;
    }
    addHeaderToFormArray(header: any) {
        (this.httpForm.get('headers') as FormArray).push(
            this.fb.group({
                key: [header.key, [Validators.required]],
                value: [header.value, [Validators.required]],
            })
        );
    }
    //getter for form
    get headersControl() {
        console.log();
        return (<FormArray>this.httpForm.get('headers')).controls;
    }
    get formInputMethod() {
        return this.httpForm.get('httpMethod').value;
    }
    get formUrl() {
        return this.httpForm.get('httpURL').value;
    }
    get reqBody() {
        return this.httpForm.get('requestBody').value;
    }
    get formResponseContentType() {
        return this.httpForm.get('responseContentType').value;
    }
    get formRequestContentType() {
        return this.httpForm.get('requestContentType').value;
    }
    get formParamType() {
        return (this.parameterForm.get('parameterType') as FormControl).value;
    }
    get formDisplayMethod() {
        return (this.parameterForm.get('inputMethod') as FormControl).value;
    }
    get dropDownControls() {
        return (<FormArray>this.parameterForm.get('dropdownList')).controls;
    }
    get httpResponseMappingModelsControls() {
        return (<FormArray>this.parameterForm.get('httpResponseMappingModels')).controls;
    }
    get parameterDataType() {
        return (this.parameterForm.get('parameterDataType') as FormControl).value;
    }
    deleteFormHeaderFormArray(index: number) {
        (<FormArray>this.httpForm.get('headers')).removeAt(index);
    }
    onSelectHttpMethod(httpMethodValue: number) {
        if (httpMethodValue === GET_HTTP_METHOD) {
            this.setRequestBodyAndContentValidty(false);
        } else {
            this.setRequestBodyAndContentValidty(true);
        }
    }
    // set get method validations

    setRequestBodyAndContentValidty(enable: boolean) {
        if (enable) {
            this.httpForm.get('requestContentType').setValidators([Validators.required]);
        } else {
            this.httpForm.get('requestContentType').clearValidators();
            this.httpForm.get('requestContentType').setValue(null);
            this.httpForm.get('requestBody').setValue(null);
        }
        this.httpForm.get('requestContentType').updateValueAndValidity();
        this.httpForm.get('requestBody').updateValueAndValidity();
    }
    onSelectResponseContentType(responseContentType: number) {
        this.xPathsSuggestions = [];
        if (responseContentType === TEXT_CONTENT_TYPE) {
            this.setDelimitersFieldsValidity(true);
            this.setResponceFormValidity(true);
        } else {
            this.setDelimitersFieldsValidity(false);
            this.setResponceFormValidity(false);
        }
    }
    setResponceFormValidity(enable: boolean) {
        if (enable) {
            this.httpForm.get('responseForm').setValidators([Validators.required]);
        } else {
            this.httpForm.get('responseForm').clearValidators();
            this.httpForm.get('responseForm').setValue(null);
        }
        this.httpForm.get('responseForm').updateValueAndValidity();
    }
    setDelimitersFieldsValidity(enable: boolean) {
        if (enable) {
            this.httpForm.get('mainDelimiter').setValidators([Validators.required]);
            this.httpForm.get('keyValueDelimiter').setValidators([Validators.required]);
        } else {
            this.httpForm.get('mainDelimiter').clearValidators();
            this.httpForm.get('keyValueDelimiter').clearValidators();
            this.httpForm.get('mainDelimiter').setValue(null);
            this.httpForm.get('keyValueDelimiter').setValue(null);
        }
        this.httpForm.get('mainDelimiter').updateValueAndValidity();
        this.httpForm.get('keyValueDelimiter').updateValueAndValidity();
    }
    addDropDownToFormArray(dropdownItem: ParameterDropdown) {
        (this.parameterForm.get('dropdownList') as FormArray).push(
            this.fb.group({
                label: [dropdownItem.label, [Validators.required]],
                value: [dropdownItem.value, [Validators.required]],
            })
        );
    }
    addHttpResponseMappingModelsFormArray(HttpResponseMappingModels: HttpResponseMappingModels) {
        (this.parameterForm.get('httpResponseMappingModels') as FormArray).push(
            this.fb.group({
                id: [HttpResponseMappingModels.id],
                parameterId: [HttpResponseMappingModels.parameterId],
                headerName: [HttpResponseMappingModels.headerName, [Validators.required]],
                headerDisplayName: [HttpResponseMappingModels.headerDisplayName, [Validators.required]],
            })
        );
    }
    deleteFormResponseMappingModelsFormArray(index: number) {
        (<FormArray>this.parameterForm.get('httpResponseMappingModels')).removeAt(index);
    }
    resetResponseMappingModelsFormArray() {
        while ((<FormArray>this.parameterForm.get('httpResponseMappingModels')).length !== 0) {
            (<FormArray>this.parameterForm.get('httpResponseMappingModels')).removeAt(0);
        }
    }
    setResponseMappingModelsFormArray(parameter: Paramter) {
        parameter.httpResponseMappingModels.forEach((mapping) => {
            this.addHttpResponseMappingModelsFormArray(mapping);
        });
    }
    getDropDownValidity(index) {
        return (<FormArray>this.parameterForm.get('dropdownList')).controls[index] as FormGroup;
    }
    deleteFormDropDownFormArray(index: number) {
        (<FormArray>this.parameterForm.get('dropdownList')).removeAt(index);
    }
    resetParamDropDownForm() {
        while ((<FormArray>this.parameterForm.get('dropdownList')).length !== 0) {
            (<FormArray>this.parameterForm.get('dropdownList')).removeAt(0);
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
    addOrEditParameter() {
        console.log(this.parameterForm);
        const parameter: Paramter = this.parameterForm.getRawValue();
        parameter.valid = true;
        if (this.addParameterMode) {
            this.stepConfiguration.parameters.push(parameter);
            this.reorderTable();
        } else {
            this.stepConfiguration.parameters[this.editParameterIndex] = parameter;
        }
        this.dialogVisiabilty = false;
    }
    switchAddMode() {
        this.initParameterForm();
        this.addParameterMode = true;
        this.dialogVisiabilty = true;
    }
    reorderTable() {
        this.stepConfiguration.parameters = this.stepConfiguration.parameters.map((el, index) => {
            return {
                ...el,
                parameterOrder: index + 1,
            };
        });
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
        if (parameter.parameterDataType === LIST_DATA_TYPE) {
            this.setResponseMappingModelsFormArray(parameter);
        }
        if(parameter.sourceStepParameterId){
            this.selectSourceStepParameter(parameter.sourceStepParameterId)
        }
    }
    deleteParameter(parameterIndex: number) {
        this.stepConfiguration.parameters.splice(parameterIndex, 1);
        this.reorderTable();
    }
    setParaInputMethodValidation(value) {
        if (value === INPUT_PARAMETER_TYPE_VALUE) {
            this.resetResponseMappingModelsFormArray();
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
            //set 2 to clear Validity
            this.changeOldParameterStepSourceValidity(2);
        }
        this.parameterForm.get('inputMethod').updateValueAndValidity();
        this.parameterForm.get('defaultValue').updateValueAndValidity();
    }
    changeParameterDataType(value) {
        if (!this.addParameterMode) {
            this.stepConfiguration.parameters[this.editParameterIndex].parameterDataType = value;
        }
        if (value === LIST_DATA_TYPE) {
            this.setParaInputMethodValidation(2);
            this.parameterForm.get('parameterType').setValue(2);
        }
        if (value !== LIST_DATA_TYPE) {
            this.resetResponseMappingModelsFormArray();
        }
        if (value === DATE_DATA_TYPE) {
            this.changeDateFormatValidty(true);
        }
        if (value !== DATE_DATA_TYPE) {
            this.changeDateFormatValidty(false);
        }
        this.parameterForm.get('httpResponseMappingModels').updateValueAndValidity();
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
    changeOldParameterStepSourceValidity(value: number) {
        if (value === OUTPUT_FROM_OTHER_STEP_INPUT_METHOD) {
            this.parameterForm.get('sourceStepParameterId').setValidators([Validators.required]);
        } else {
            this.parameterForm.get('sourceStepParameterId').clearValidators();
            this.parameterForm.get('sourceStepParameterId').setValue(null);
        }
        this.parameterForm.get('sourceStepParameterId').updateValueAndValidity();
    }
    parsehttpRequestParameters() {
        let parametersLength: number = this.stepConfiguration.parameters.length;
        const reqQuery = this.formInputMethod === POST_HTTP_METHOD ? this.reqBody : this.formUrl;
        if (this.formInputMethod === GET_HTTP_METHOD) {
            this.parseRequestParamters(parametersLength, reqQuery);
        } else {
            //open dialog for get file
            if (this.formRequestContentType) {
                this.parsePostRequestParametersDialog = true;
            } else {
                this.toastrService.warning('Please Select Request Content Type');
            }
        }
    }
    openReponseDialog() {
        if (this.formResponseContentType) {
            this.parsePostResponseParametersDialog = true;
        } else {
            this.toastrService.warning('Please Select Repsonse Content Type');
        }
    }
    parseRequestParamters(parametersLength: number, reqQuery: string) {
        this.procedureService.parseHttpParameter(reqQuery).subscribe((res) => {
            if (res.statusCode === 0) {
                this.parsePostRequestParametersDialog = false;
                this.toastrService.success(this.messageService.getMessage(114).message);
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
            this.parsedRequestQuery=null;
        });
    }
    onCloseParsePostResponseDialog() {
        this.parsePostResponseParametersDialog = false;
        this.clearResponseParsedFile();
      
    }
    onSelectParsedFile(event) {
        let file = event.files[0];
        let fileExt = file.name.substring(file.name.lastIndexOf('.'), file.name.length);
        console.log('file', file);
        console.log('extention', fileExt);
        if (this.formRequestContentType === JSON_CONTENT_TYPE && fileExt !== '.json') {
            this.toastrService.warning('Please choose .json Extention');
        } else if (this.formRequestContentType === XML_CONTENT_TYPE && fileExt !== '.xml') {
            this.toastrService.warning('Please choose .xml Extention');
        } else {
            let fileText;
            let reader = new FileReader();
            let classRef = this;
            reader.onload = function (e) {
                fileText = reader.result;
                let parametersLength: number = classRef.stepConfiguration.parameters.length;
                classRef.parseRequestParamters(parametersLength, fileText);
            };
            reader.readAsText(file);
        }
    }
    parseRequestTextParameter() {
        let parametersLength: number = this.stepConfiguration.parameters.length;
        this.parseRequestParamters(parametersLength, this.parsedRequestQuery);
    }
  
    clearResponseParsedFile() {
        this.parsePostResponseParametersDialog=false;
        this.responseFileUpload._files=[];
        this.responseParseExtensionFile = null;
        this.rootName = null;
    }
    clearParseRequestData(){
        this.parsePostRequestParametersDialog=false;
        this.requestFileUpload.clear();
        this.parsedRequestQuery=null;
    }
    onSelectFile(event) {
        let file = event.files[0];
        let fileExt = file.name.substring(file.name.lastIndexOf('.'), file.name.length);
        let extention = fileExt.substring(1);
        this.responseParseExtensionFile = extention;
    }
    onSelectResponseParsedFile(event) {
        let file = event.files[0];
        let fileExt = file.name.substring(file.name.lastIndexOf('.'), file.name.length);
        let extention = fileExt.substring(1);

        if (this.formResponseContentType === JSON_CONTENT_TYPE && fileExt !== '.json') {
            this.toastrService.warning('Please choose .json Extention');
        } else if (this.formResponseContentType === XML_CONTENT_TYPE && fileExt !== '.xml' && fileExt !== '.xsd') {
            this.toastrService.warning('Please choose .xml Extention');
        } else {
            let fileText;
            let reader = new FileReader();
            let classRef = this;
            reader.onload = function (e) {
                fileText = reader.result;
                let parametersLength: number = classRef.stepConfiguration.parameters.length;
                const reqObj = {
                    type: classRef.fileExtentions[extention],
                    fileContent: fileText,
                    rootName : classRef.rootName
                };
                classRef.parseResponseParameters(parametersLength, reqObj);
            };
            reader.readAsText(file);
        }
    }
    parseResponseParameters(parametersLength: number, reqObj: any) {
        this.procedureService.parseResponseHttpParameters(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.parsePostResponseParametersDialog = false;
                this.toastrService.success(this.messageService.getMessage(115).message);
                this.paths = res.payload.paths;
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
        });
    }
    ngOnDestroy(): void {
        this.stepParameterSubscription.unsubscribe();
    }
    selectSourceStepParameter(value) {
        const selectedOutputSourceParameter = this.sourceStepParameters.filter((el) => el.id === value)[0];
        if (selectedOutputSourceParameter.parameterDataType === 3) {
            this.selectedOutputSourceParameter = selectedOutputSourceParameter;
            if (selectedOutputSourceParameter?.cursorParameterMappings?.length > 0) {
                this.isProcedureSourceParameter = true;
                this.sourceParameterNames = selectedOutputSourceParameter.cursorParameterMappings;
            } else if (selectedOutputSourceParameter?.httpResponseMappingModels?.length > 0) {
                this.sourceParameterNames = selectedOutputSourceParameter.httpResponseMappingModels;
                this.isProcedureSourceParameter = false;
            } else {
                this.sourceParameterNames = [];
            }
        } else {
            this.selectedOutputSourceParameter = null;
            this.sourceParameterNames = [];
        }
    }
    search(event) {
        this.xPathsSuggestions = this.paths.filter((c) => c.startsWith(event.query));
    }
}
