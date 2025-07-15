import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {ActivatedRoute, Router} from '@angular/router';
import {MenuItem} from 'primeng/api';
import {DynamicPageService} from 'src/app/core/service/customer-care/dynamic-page.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {ToastService} from 'src/app/shared/services/toast.service';
import {faCircleCheck} from '@fortawesome/free-solid-svg-icons';
import {InputMethods} from 'src/app/shared/enum/input-methods';
import {ParameterTypes} from 'src/app/shared/enum/parameter-types';

@Component({
    selector: 'app-dynamic-page',
    templateUrl: './dynamic-page.component.html',
    styleUrls: ['./dynamic-page.component.scss'],
})
export class DynamicPageComponent implements OnInit {
    constructor(
        private dynamicService: DynamicPageService,
        private router: Router,
        private fb: FormBuilder,
        private toastService: ToastService,
        private activatedRoute: ActivatedRoute,
        private footPrintService: FootPrintService
    ) {}
    // dynamic page id
    pageId;
    privilegeId;

    loading$ = this.dynamicService.loading$;
    // array for steps with primeng interface
    items: MenuItem[] = [];
    // index of current step
    activeIndex = 0;
    // api dynamic page response
    pageModel;
    // holder for steps forms
    stepsForms = {};
    // form to be displayed with current index
    currentForm;
    // special cases variables
    msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
    username = JSON.parse(sessionStorage.getItem('session'))['userProfile']['profileName'];
    today = new Date();

    // output parameter model hashmap
    outputParameters = {};

    // forms submitted flags to disable the next button
    submitFlags = {};

    // shared output map to use it for inputs whose values are from another step
    outputSharedMap = {};
    // icon config
    faCircleCheck = faCircleCheck;
    // input methods variable
    InputMethods: InputMethods;
    steps: any = [];
    orderParameterValues: any[] = [];
    orderParameterKeys: any[] = [];
    ngOnInit(): void {
        // getting Page Id
        // this.privilegeId = this.activatedRoute.snapshot.params.id;

        this.activatedRoute.params.subscribe((params) => {
            this.initCompAtt();
            this.privilegeId = params['id'];
            this.items = [];
            this.dynamicService.getDynamicPage(this.privilegeId).subscribe({
                next: (resp) => {
                    const msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
                    // check if page requires search
                    if (resp.payload?.requireSearch && !msisdn) {
                        this.router.navigate(['/find-subscriber']);
                        this.toastService.warning('Please search for subscriber');
                    } else {
                        console.log(resp.payload);
                        this.pageModel = resp.payload;
                        let newItems = [];
                        this.steps = resp.payload.steps;
                        console.log('steps', this.steps);
                        // forming steps
                        resp.payload.steps.forEach((element, i) => {
                            // form steps UI
                            if (!element.isHidden) {
                                newItems.push({
                                    label: element.stepName,
                                });
                            }
                            // add steps forms to steps object to form them in createForms
                            this.stepsForms[`step${i}Form`] = this.fb.group({});
                        });
                        this.items = newItems;
                        this.createForms();
                    }
                },
            });

            // footprint
            let footprintObj: FootPrint = {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Dynamic Page - Customer Care',
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                footPrintDetails: [
                    {
                        paramName: 'privilegeId',
                        oldValue: null,
                        newValue: this.privilegeId,
                    },
                ],
            };
            this.footPrintService.log(footprintObj);
        });
    }
    initCompAtt() {
        this.pageId = null;
        this.privilegeId;

        this.items = [];

        this.activeIndex = 0;

        this.pageModel = null;

        this.stepsForms = {};

        this.currentForm = null;

        this.outputParameters = {};

        this.submitFlags = {};

        this.outputSharedMap = {};

        this.steps = null;
    }
    createForms() {
        Object.keys(this.stepsForms).forEach((form, i) => {
            this.pageModel.steps[i].stepInputParameters.forEach((input) => {
                // if input is a date or regular input
                // date condition
                if (input.parameterDataType === ParameterTypes.date) {
                    this.stepsForms[form].addControl(
                        input.parameterName,
                        this.fb.control({
                            value: new Date(+input.defaultValue),
                            disabled: input.inputMethod === InputMethods.disabled ? true : false,
                        })
                    );
                }
                // other is regular input
                else {
                    this.stepsForms[form].addControl(
                        input.parameterName,
                        this.fb.control({
                            value: input.defaultValue,
                            disabled: input.inputMethod === InputMethods.disabled ? true : false,
                        })
                    );
                }
                // add ID to each Form Control
                this.stepsForms[form].controls[input.parameterName].id = input.id;

                // batch value of control which has value from another step
                if (input.inputMethod === InputMethods.fromOtherStep) {
                    // element
                    // let sourceForm = this.stepsForms[Object.keys(this.stepsForms)[i - 1]];
                    // let sourceParameterId = input.sourceStepParameterId;
                    // let sourceParameterValue = null;
                    // // get the form of source
                    // Object.keys(sourceForm.controls).forEach(
                    //   (control: any) => {
                    //     if (control.id === sourceParameterId) {
                    //       sourceParameterValue = control.value;
                    //     }
                    //   }
                    // )
                    // // batch value
                    // this.stepsForms[form].get(input.parameterName).patchValue(sourceParameterValue);
                }

                // Special Cases
                // $MSISDN$
                // $USERNAME$
                // $CURRENT_DATE$
                if (input.defaultValue === '$MSISDN$') {
                    this.stepsForms[form].get(input.parameterName).patchValue(this.msisdn);
                }

                if (input.defaultValue === '$USERNAME$') {
                    this.stepsForms[form].get(input.parameterName).patchValue(this.username);
                }

                if (input.defaultValue === '$CURRENT_DATE$') {
                    this.stepsForms[form].get(input.parameterName).patchValue(this.today);
                }
            });

            // add false flags to submit buttons
            this.submitFlags[i] = false;
        });
        this.getFormName();
    }
    // this method is for getting the current form the user selects
    getFormName() {
        Object.keys(this.stepsForms).findIndex((el, i) => {
            if (i === this.activeIndex) {
                this.currentForm = this.stepsForms[el];
            }
        });
    }
    submitForm() {
        let reqData = {
            pageId: this.pageModel.pageId,
            privilegeId: this.privilegeId,
            stepId: this.pageModel.steps[this.activeIndex].stepId,
            inputParameters: {
                ...this.currentForm.getRawValue(),
            },
        };
        this.dynamicService.executeDynamicPage(reqData).subscribe({
            next: (resp) => {
                this.outputParameters[this.activeIndex] = resp.payload.outputParameters;
                this.submitFlags[this.activeIndex] = true;

                // saving hashmap of outputs
                // this.outputSharedMap = new Map();
                resp.payload.outputParameters.forEach((output) => {
                    if (output.parameterDataType !== ParameterTypes.cursor) {
                        // this.outputSharedMap.set(output.parameterId, {
                        //   name: output.parameterName,
                        //   value: output.parameterValue
                        // })
                        this.outputSharedMap[output.parameterId] = {
                            name: output.parameterName,
                            value: output.parameterValue,
                        };
                    }
                });

                const orderParametersOfKeys = resp.payload.outputParameters.filter(
                    (i: any) => i.parameterDataType == 3
                )[0].parameterValue[0];

                // Get keys and values arrays in the same order
                this.orderParameterKeys = Object.keys(orderParametersOfKeys);
                this.orderParameterValues = Object.values(orderParametersOfKeys);

                this.batchValues();
            },
        });
    }
    nextStep() {
        if (this.activeIndex !== this.pageModel.steps.length - 1) {
            this.activeIndex++;
            this.getFormName();
        }
    }
    prevStep() {
        if (this.activeIndex !== 0) {
            this.activeIndex--;
            this.getFormName();
        }
    }
    // this method invokes the apply action in data table of output params
    applyAction(output, row) {
        this.outputSharedMap[output.parameterId] = {
            name: output.parameterName,
            value: row,
        };
        console.log(this.outputSharedMap);
        this.batchValues();

        if (this.steps[this.activeIndex + 1].isHidden) {
            this.setCurrentForm(this.activeIndex + 1);
            let reqData = {
                pageId: this.pageModel.pageId,
                privilegeId: this.privilegeId,
                stepId: this.pageModel.steps[this.activeIndex + 1].stepId,
                inputParameters: {
                    ...this.currentForm.getRawValue(),
                },
            };
            this.dynamicService.executeDynamicPage(reqData).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastService.success('Action Applied Successfully');
                    this.setCurrentForm(this.activeIndex);
                }
            });
        } else {
            this.nextStep();
        }
    }
    setCurrentForm(index) {
        Object.keys(this.stepsForms).findIndex((el, i) => {
            if (i === index) {
                this.currentForm = this.stepsForms[el];
            }
        });
    }
    batchValues() {
        // batch values of inputs that has values from output params
        Object.keys(this.stepsForms).forEach((form, i) => {
            this.pageModel.steps[i].stepInputParameters.forEach((input) => {
                if (input.inputMethod === InputMethods.fromOtherStep) {
                    let inputValue = this.outputSharedMap[input.sourceStepParameterId];

                    if (inputValue !== undefined) {
                        // if it's dataCursor so the slected value will be an object which needs edits
                        if (typeof inputValue?.value === 'object') {
                            let value = inputValue.value[input.sourceStepParameterName];
                            this.stepsForms[form].get(input.parameterName).patchValue(value);
                        } else {
                            this.stepsForms[form].get(input.parameterName).patchValue(inputValue.value);
                        }
                    }
                }
            });
        });
    }
}
