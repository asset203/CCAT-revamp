import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { SmsTemplateCsService } from 'src/app/core/service/administrator/sms-template-cs.service';
import { language } from 'src/app/shared/models/language.interface';
import { SmsTemplateCs } from 'src/app/shared/models/SmsTemplate.model';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-sms-template-cs',
    templateUrl: './sms-template-cs.component.html',
    styleUrls: ['./sms-template-cs.component.scss'],
    providers: [ConfirmationService],
})
export class SmsTemplateCsComponent implements OnInit {
    dialog = false;
    selectedValue: string = 'text';
    smsTemplateList: SmsTemplateCs[];
    parametersList;
    search = false;
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private smsTemplateService: SmsTemplateCsService,
        private fb: FormBuilder,
        private toastrService: ToastService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private loadingService : LoadingService
    ) { }
    loading$ = this.smsTemplateService.loading$;
    smsForm: FormGroup;
    editMode: boolean = false;
    languages$ = this.smsTemplateService.languages;
    actions;
    actionParamsMap;
    paramIndex: number;
    editedSmsTemplate: SmsTemplateCs;
    permissions = {
        getAllSms: false,
        addSms: false,
        updateSms: false,
        deleteSms: false,
    };
    selectedParameter;
    ngOnInit(): void {
        this.initAddSmsTemplateForm();
        this.setPermissions();
        this.getAllTemplates();
        this.getActions();
        this.getParametersActions();
    }
    getActions() {
        this.smsTemplateService.smsActions.subscribe((actions) => {
            this.actions = actions;
        });
    }
    getParametersActions() {
        this.smsTemplateService.actionParamMap.subscribe((actionParamMap) => {
            this.actionParamsMap = actionParamMap;
            this.paramIndex = +Object.keys(this.actionParamsMap)[0];
            this.parametersList = actionParamMap[this.paramIndex];
        });
    }
    initAddSmsTemplateForm() {
        this.selectedValue = 'text';
        this.smsForm = this.fb.group({
            csTemplateId: [null],
            languageId: [null, [Validators.required]],
            templateId: [null, [Validators.required]],
            templateText: [null],
        });
        this.setMessageType('text');
    }
    get templateIDValue() {
        return this.smsForm.get("templateId").value;
    }
    initEditSmsTemplateForm() {
        this.smsForm = this.fb.group({
            id: [this.editedSmsTemplate.id],
            csTemplateId: [this.editedSmsTemplate.csTemplateId],
            languageId: [this.editedSmsTemplate.languageId, [Validators.required]],
            templateId: [this.editedSmsTemplate.templateId, [Validators.required]],
            templateText: [this.editedSmsTemplate.templateText],
        });
        if (this.editedSmsTemplate && this.editedSmsTemplate.csTemplateId !== 0) {
            this.selectedValue = 'template';
            this.setMessageType('template');
        } else {
            this.selectedValue = 'text';
            this.setMessageType('text');
        }
        this.paramIndex = this.editedSmsTemplate.templateId;
        this.parametersList = this.actionParamsMap[this.paramIndex];
    }
    switchToEditTemplate(editedSmsTemplate: SmsTemplateCs) {
        this.editMode = true;
        this.editedSmsTemplate = editedSmsTemplate;
        this.initEditSmsTemplateForm();
        this.dialog = true;
        if (editedSmsTemplate.csTemplateId) {
            this.selectedParameter = editedSmsTemplate.parameterList
        }

    }
    getAllTemplates() {
        if (this.permissions.getAllSms) {
            this.loadingService.startFetchingList();
            this.smsTemplateService.getAllTemplates().subscribe((res) => {
                this.loadingService.endFetchingList();
                this.smsTemplateList = res?.payload?.smsTemplates;
                
            },err=>{
                this.smsTemplateList=[]
                this.loadingService.endFetchingList();
            });
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message);
        }
    }
    switchToAddSmsTemplate() {
        this.editMode = false;
        this.initAddSmsTemplateForm();
        this.dialog = true;
    }

    submitSms() {
        let reqObj;
        if (this.smsForm.get('csTemplateId').value !== 0) {
            reqObj = {
                ...this.smsForm.value,
                parameterList: this.selectedParameter,
            };
        } else {
            reqObj = { ...this.smsForm.value };
        }
        if (!this.editMode) {
            this.smsTemplateService.addSmsTemplate(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(110).message);
                    if (this.permissions.getAllSms) {
                        this.getAllTemplates();
                    }
                }
            });
        } else {
            this.smsTemplateService.editSmsTemplate(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(111).message);
                    if (this.permissions.getAllSms) {
                        this.getAllTemplates();
                    }
                }
            });
        }
        this.dialog = false;
    }
    setMessageType(selectedValue) {
        if (selectedValue === 'text') {
            this.setTextValidity();
        } else {
            this.setCsTemplateValidity();
        }
    }
    setTextValidity() {
        this.smsForm.get('csTemplateId').clearValidators();
        this.smsForm.get('csTemplateId').setValue(null);
        this.smsForm.get('templateText').setValidators([Validators.required]);
        this.smsForm.get('csTemplateId').updateValueAndValidity();
        this.smsForm.get('templateText').updateValueAndValidity();
    }
    setCsTemplateValidity() {
        this.smsForm.get('csTemplateId').setValidators([Validators.required]);
        this.smsForm.get('templateText').clearValidators();
        this.smsForm.get('templateText').setValue(null);
        this.smsForm.get('csTemplateId').updateValueAndValidity();
        this.smsForm.get('templateText').updateValueAndValidity();
    }
    setParaIndex(value) {
        this.paramIndex = value;
        this.parametersList = this.actionParamsMap[this.paramIndex];
        this.selectedParameter = [...this.actionParamsMap[this.paramIndex]]
    }
    appendText(text) {
        let smsText = this.smsForm.get('templateText').value;
        smsText = smsText ? smsText + ` $${text}$` : `$${text}$`;
        this.smsForm.get('templateText').setValue(smsText);
        this.smsForm.get('templateText').updateValueAndValidity();
    }
    confirmDelete(id: number, templateId: number, index: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(112).message,
            accept: () => {
                this.deleteSmsTemplate(id, templateId, index);
            },
        });
    }
    deleteSmsTemplate(id: number, templateId: number, index: number) {
        this.smsTemplateService.deleteSmsTemplate(id, templateId).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(113).message);
                this.smsTemplateList.splice(index, 1);
            }
        });
    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()

    }
    setPermissions() {
        let smsPermissions: Map<number, string> = new Map()
            .set(355, 'getAllSms')
            .set(354, 'addSms')
            .set(357, 'updateSms')
            .set(356, 'deleteSms');
        this.featuresService.checkUserPermissions(smsPermissions);
        this.permissions.getAllSms = this.featuresService.getPermissionValue(355);
        this.permissions.addSms = this.featuresService.getPermissionValue(354);
        this.permissions.updateSms = this.featuresService.getPermissionValue(357);
        this.permissions.deleteSms = this.featuresService.getPermissionValue(356);
    }
    close() {
        this.dialog = false;
    }
    reorder() {
        this.selectedParameter = this.selectedParameter.map((param, index) => {
            return {
                ...param,
                sequenceId: index + 1
            }
        })
        console.log(this.selectedParameter)
    }
}
