import { ToastrService } from 'ngx-toastr';
import { ActivatedRoute, Router } from '@angular/router';
import { ServiceClassesService } from './../../../../../core/service/administrator/service-classes.service';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Component, OnInit, ViewChild } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { ServiceClass } from 'src/app/shared/models/service-class';
import { Observable } from 'rxjs';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ValidationService } from 'src/app/shared/services/validation.service';
import { InputNumber } from 'primeng/inputnumber';

@Component({
    selector: 'app-add-service-class',
    templateUrl: './add-service-class.component.html',
    styleUrls: ['./add-service-class.component.scss'],
})
export class AddServiceClassComponent implements OnInit {
    dedicatedAccountSequence$: Observable<[]>;
    accumulatorSequence$: Observable<[]>;
    loading$ = this.serviceClassesService.loading$;
    tab = 'mainSettings';
    addVeiw: boolean;
    id;
    tabSwitch="detAcc"
    permissions = {
        getServiceClass: false, //55
        updateServiceClass: false, //56
    };
    @ViewChild('focusedInput') input: InputNumber;

    public addServiceClassForm: FormGroup;
    constructor(
        private serviceClassesService: ServiceClassesService,
        private activaRoute: ActivatedRoute,
        private route: Router,
        private fb: FormBuilder,
        private toasterService: ToastrService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private validationService: ValidationService
    ) { }
    ngAfterViewInit(): void {
        // ExpressionChangedAfterItHasBeenCheckedError: Expression has changed after it was checked.
        if (this.input) {
            setTimeout(() => this.input.input.nativeElement.focus(), 0);
        }
    }
    switchTab(tab) {
        this.tabSwitch = tab;
    }
    ngOnInit(): void {

        this.setPermissions();
        this.addServiceClassForm = this.fb.group({
            name: ['', [Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]],
            code: [null, Validators.required],
            preActivationAllowed: [false],
            isGrandfather: [false],
            isCiConversion: [false],
            ciServiceName: [''],
            ciPackageName: [''],
            isAllowedMigration: [false],
            dedAccounts: this.fb.array([this.initialDedicatedRows()]),
            accumlators: this.fb.array([this.initialAccumlatorsRows()]),
            serviceOfferingPlanDescs : [[]],
            id: [null],
        });

        let id = this.activaRoute.snapshot.paramMap.get('id');
        this.id = id;
        if (parseInt(id) === -1) {
            this.addVeiw = true;
            console.log(this.addVeiw);
        } else {
            this.serviceClassesService
                .getServiceClass$(parseInt(id))
                .pipe(
                    map((res) => res?.payload?.serviceClass),
                    take(1)
                )
                .subscribe({
                    next: (res) => {
                        res.dedAccounts.forEach((element, index) => {
                            if (index > 0) {
                                this.addDedicatedRow();
                            }
                            this.dedicatedFormArray[index].get('description').patchValue(element.description);
                            this.dedicatedFormArray[index].get('daID').patchValue(element.daID);
                            this.dedicatedFormArray[index].get('rattingFactor').patchValue(element.rattingFactor);
                        }),
                            res.accumlators.forEach((accumlator, i) => {
                                if (i > 0) {
                                    this.addAccumlatorsRow();
                                }
                                this.accumlatorsFormArray[i].get('description').patchValue(accumlator.description);
                                this.accumlatorsFormArray[i].get('accID').patchValue(accumlator.accID);
                            }),
                            this.addServiceClassForm.patchValue({
                                name: res.name,
                                code: res.code,
                                preActivationAllowed: res.preActivationAllowed,
                                isGrandfather: res.isGrandfather,
                                isCiConversion: res.isCiConversion,
                                ciServiceName: res.ciServiceName,
                                ciPackageName: res.ciPackageName,
                                isAllowedMigration: res.isAllowedMigration,
                                serviceOfferingPlanDescs : res.serviceOfferingPlanDescs,
                                id: parseInt(id),
                            });
                            this.changeValidityCsConvension(res.isCiConversion)
                            
                    },
                    error: (err) => {
                        this.toasterService.error('Error', err);
                    },
                });
                console.log("form validation",this.addServiceClassForm)
        }

        this.dedicatedAccountSequence$ = this.serviceClassesService.dedicatedAccountSequence$.pipe(
            map((res) => res?.payload?.Ids)
        );

        this.accumulatorSequence$ = this.serviceClassesService.accumulatorSequence$.pipe(
            map((res) => res?.payload?.Ids)
        );
    }

    //dedicated form array

    get dedicatedFormArray() {
        return (this.addServiceClassForm.get('dedAccounts') as FormArray).controls;
    }
    initialDedicatedRows() {
        return this.fb.group({
            daID: [-1],
            description: [''],
            rattingFactor: [''],
        });
    }
    addDedicatedRow() {
        this.dedicatedFormArray.push(this.initialDedicatedRows());
    }
    deleteDedicatedRow(i: number) {
        return (this.addServiceClassForm.get('dedAccounts') as FormArray).removeAt(i);
    }
    get serviceOfferingPlanDescs(){
        return this.addServiceClassForm.get('serviceOfferingPlanDescs').value;
    }
    // Accumlators form array

    get accumlatorsFormArray() {
        return (this.addServiceClassForm.get('accumlators') as FormArray).controls;
    }
    initialAccumlatorsRows() {
        return this.fb.group({
            accID: [-1],
            description: [''],
        });
    }
    addAccumlatorsRow() {
        this.accumlatorsFormArray.push(this.initialAccumlatorsRows());
    }
    deleteAccumlatorsRow(i: number) {
        (this.addServiceClassForm.get('accumlators') as FormArray).removeAt(i);
    }

    private refreshFormArray() {
        this.addAccumlatorsRow();
        this.deleteAccumlatorsRow(this.accumlatorsFormArray.length - 1);
        this.addDedicatedRow();
        this.deleteDedicatedRow(this.dedicatedFormArray.length - 1);
    }

    addServiceClass() {
        this.refreshFormArray();
        if (this.addVeiw) {
            let ded = this.addServiceClassForm.value.dedAccounts;
            for (let index = 0; index < ded.length; index++) {
                const element = ded[index];
                console.log(element);
                if (ded[index].daID == -1) {
                    this.addServiceClassForm.value.dedAccounts.splice(index, 1);
                    console.log(this.addServiceClassForm.value.dedAccounts);
                }
            }
            let acc = this.addServiceClassForm.value.accumlators;
            for (let index = 0; index < acc.length; index++) {
                const element = acc[index];
                console.log(element);
                if (acc[0].accID == -1) {
                    this.addServiceClassForm.value.accumlators.splice(index, 1);
                    console.log(this.addServiceClassForm.value.accumlators);
                }
            }
            this.serviceClassesService
                .addServiceClass$(this.addServiceClassForm.value)
                .pipe(take(1))
                .subscribe({
                    next: (res) => {
                        if (res.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(38).message);
                            this.addServiceClassForm.reset();
                            this.route.navigateByUrl('/admin/service-classes');
                        }
                    },
                    error: (err) => {
                        this.toasterService.error('Error', err);
                    },
                });
        } else {
            console.log(this.addServiceClassForm.value);
            let ded = this.addServiceClassForm.value.dedAccounts;
            for (let index = 0; index < ded.length; index++) {
                const element = ded[index];
                console.log(element);
                if (ded[index].daID == -1) {
                    this.addServiceClassForm.value.dedAccounts.splice(index, 1);
                    console.log(this.addServiceClassForm.value.dedAccounts);
                }
            }
            let acc = this.addServiceClassForm.value.accumlators;
            for (let index = 0; index < acc.length; index++) {
                const element = acc[index];
                console.log(element);
                if (acc[0].accID == -1) {
                    this.addServiceClassForm.value.accumlators.splice(index, 1);
                    console.log(this.addServiceClassForm.value.accumlators);
                }
            }

            this.serviceClassesService
                .updateServiceClass$(this.addServiceClassForm.value)
                .pipe(take(1))
                .subscribe({
                    next: (res) => {
                        if (res.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(39).message);
                            this.route.navigateByUrl('/admin/service-classes');
                        }
                    },
                });
        }
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(55, 'getServiceClass')
            .set(56, 'updateServiceClass');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getServiceClass = this.featuresService.getPermissionValue(55);
        this.permissions.updateServiceClass = this.featuresService.getPermissionValue(56);
    }
    changeValidityCsConvension(event){
        if(event.checked){
            this.addServiceClassForm.get('ciServiceName').setValidators([Validators.required]);
            this.addServiceClassForm.get('ciPackageName').setValidators([Validators.required]);
        }else{
            this.addServiceClassForm.get('ciServiceName').clearValidators();
            this.addServiceClassForm.get('ciPackageName').clearValidators();
        }
        this.addServiceClassForm.get('ciServiceName').updateValueAndValidity();
        this.addServiceClassForm.get('ciPackageName').updateValueAndValidity();
    }
}
