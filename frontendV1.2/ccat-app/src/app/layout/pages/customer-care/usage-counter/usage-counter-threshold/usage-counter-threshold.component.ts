import {Location} from '@angular/common';
import {Component, Input, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {InputNumber} from 'primeng/inputnumber';
import {Table} from 'primeng/table';
import {UsageCounterService} from 'src/app/core/service/customer-care/usage-counter.service';
import {MessageService} from 'src/app/shared/services/message.service';

@Component({
    selector: 'app-usage-counter-threshold',
    templateUrl: './usage-counter-threshold.component.html',
    styleUrls: ['./usage-counter-threshold.component.scss'],
    providers: [ConfirmationService],
})
export class UsageCounterThresholdComponent implements OnInit {
    isThresholdModalOpened = false;
    isFoundedError = false;
    constructor(
        private fb: FormBuilder,
        private usageCounterService: UsageCounterService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private location: Location
    ) {}

    thresholds = [];
    thresholdForm: FormGroup;
    usageCounterData;
    isUpdate = false;
    elementToUpdateIndex = null;
    elementToIpdate = null;
    @ViewChild('threIDInput') idInputFocus: InputNumber;

    // loading boolean
    loading$ = this.usageCounterService.loading$;
    search = false;
    searchText: string;
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    ngOnInit(): void {
        // get threshold list from localStorage
        this.thresholds = JSON.parse(localStorage.getItem('usageCounterData')).usageThresholdInformation;
        this.usageCounterData = JSON.parse(localStorage.getItem('usageCounterData'));
        console.log(this.usageCounterData);
        this.createForm();
    }

    addNewThreshold() {
        this.isThresholdModalOpened = true;
        this.isUpdate = false;
    }

    createForm() {
        this.thresholdForm = this.fb.group({
            usageThresholdID: [null, Validators.required],
            usageThresholdSource: [null, Validators.required],
            usageThresholdValue: [null, Validators.required],
        });
    }
    hideDialog() {
        // reset form
        this.thresholdForm.reset();
    }
    onSubmitThreshold() {
        if (!this.isUpdate) {
            let reqData = {
                id: this.usageCounterData.id,
                value:
                    this.usageCounterData.value === null
                        ? this.usageCounterData.monetaryValue1
                        : this.usageCounterData.value,
                usageTypeId: this.usageCounterData.value === null ? '2' : '1',
                thresholds: [this.thresholdForm.value],
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Usage Counters new',
                    footPrintDetails: [
                        {
                            paramName: 'Usage Type ID',
                            oldValue: null,
                            newValue:
                                this.usageCounterData.monetaryValue1 === null ? 'counter value' : 'monatery value',
                        },
                        {
                            paramName: 'ID',
                            oldValue: null,
                            newValue: this.usageCounterData.id,
                        },
                        {
                            paramName: 'VALUE',
                            oldValue: null,
                            newValue:
                                this.usageCounterData.value === null
                                    ? this.usageCounterData.monetaryValue1
                                    : this.usageCounterData.value,
                        },
                    ],
                },
            };

            // Update UI Array
            // Clone array
            let newThresholds = [];
            newThresholds = [...this.thresholds];
            // Edit array
            newThresholds.push(this.thresholdForm.value);
            // replace
            this.thresholds = newThresholds;

            // local storage replace
            let usageData = JSON.parse(localStorage.getItem('usageCounterData'));
            usageData.usageThresholdInformation = newThresholds;
            localStorage.setItem('usageCounterData', JSON.stringify(usageData));

            // Update Data
            this.usageCounterService.addUsageThreshold(reqData);
            this.isThresholdModalOpened = false;
        } else {
            // update UI array

            // clone
            let newThresholds = [...this.thresholds];
            // edit
            newThresholds.splice(this.elementToUpdateIndex, 1, this.thresholdForm.value);
            // replace
            this.thresholds = newThresholds;

            // update localStorage
            let usageData = JSON.parse(localStorage.getItem('usageCounterData'));
            usageData.usageThresholdInformation = newThresholds;
            localStorage.setItem('usageCounterData', JSON.stringify(usageData));

            // update data
            let reqData = {
                id: this.usageCounterData.id,
                counterValue: this.usageCounterData.value,
                monetaryValue1: this.usageCounterData.monetaryValue1,
                thresholds: [...this.thresholds],
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Usage Counters new',
                    footPrintDetails: [
                        {
                            paramName: 'Usage Type ID',
                            oldValue: null,
                            newValue:
                                this.usageCounterData.monetaryValue1 === null ? 'counter value' : 'monatery value',
                        },
                        {
                            paramName: 'ID',
                            oldValue: null,
                            newValue: this.usageCounterData.id,
                        },
                        {
                            paramName: 'monetaryValue1',
                            oldValue: null,
                            newValue: this.usageCounterData.monetaryValue1,
                        },
                        {
                            paramName: 'monetaryValue2',
                            oldValue: null,
                            newValue: this.usageCounterData.monetaryValue2,
                        },
                        {
                            paramName: 'thresholds',
                            oldValue: this.elementToIpdate.usageThresholdValue,
                            newValue: this.thresholdForm.value.usageThresholdValue,
                        },
                    ],
                },
            };
            this.usageCounterService.updateThreshold(reqData);
            this.isThresholdModalOpened = false;
        }
    }
    confirmDelete(threshold) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(63).message,
            accept: () => {
                this.onDeleteThreshold(threshold);
            },
        });
    }
    onDeleteThreshold(threshold) {
        // Update UI Array
        // clone
        let newThresholds = [];
        newThresholds = [...this.thresholds];
        // edit
        let thresholdIndex = newThresholds.findIndex((el) => el.usageThresholdID === threshold.usageThresholdID);
        newThresholds = newThresholds.filter((el, i) => i !== thresholdIndex);

        // replace
        this.thresholds = newThresholds;
        // local Storage replace
        let usageData = JSON.parse(localStorage.getItem('usageCounterData'));
        usageData.usageThresholdInformation = newThresholds;
        localStorage.setItem('usageCounterData', JSON.stringify(usageData));

        // update data
        let thresholdsToDelete = [];
        thresholdsToDelete.push(threshold.usageThresholdID);
        this.usageCounterService.deleteUsageThreshold(thresholdsToDelete, this.usageCounterData);
    }

    onUpdateThreshold(threshold) {
        this.isThresholdModalOpened = true;
        this.isUpdate = true;
        this.elementToIpdate = threshold;
        // update form
        this.thresholdForm.patchValue({
            usageThresholdID: threshold.usageThresholdID,
            usageThresholdSource: threshold.usageThresholdSource,
            usageThresholdValue: threshold.usageThresholdValue,
        });
        this.elementToUpdateIndex = null;
        this.elementToUpdateIndex = this.thresholds.findIndex(
            (el) => el.usageThresholdID === threshold.usageThresholdID
        );
    }
    clear(table: Table) {
        table.clear();
    }
    backToUsageCounter() {
        this.location.back();
    }
    checkIDExist(event) {
        this.isFoundedError = false;
        this.isFoundedError = this.thresholds.some((el) => el.usageThresholdID === event.value) ? true : false;
    }
    focusThresholdIDInput() {
        this.idInputFocus.input.nativeElement.focus();
    }
}
