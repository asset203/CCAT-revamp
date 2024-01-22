import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {map, take} from 'rxjs/operators';
import {PamInformationService} from 'src/app/core/service/customer-care/pam-information.service';
import {Pam} from 'src/app/shared/models/pam';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-add-pam-information',
    templateUrl: './add-pam-information.component.html',
    styleUrls: ['./add-pam-information.component.scss'],
})
export class AddPamInformationComponent implements OnInit {
    serviceClass: Pam[];
    pamPeriod: Pam[];
    pamClass: Pam[];
    pamSchedule: Pam[];
    pamPriority: Pam[];
    deferredDate;
    addPamForm: FormGroup;
    pamServiceClass;
    pamCurrentPeriod;
    pamClassItem;
    pamServicePariority;
    pamScheduleID;

    constructor(
        private pamInformationService: PamInformationService,
        private fb: FormBuilder,
        private toasterService: ToastService,
        private router: Router
    ) {}

    ngOnInit(): void {
        this.pamInformationService.getAllPams$
            .pipe(
                take(1),
                map((res) => res.payload.pams)
            )
            .subscribe((res) => {
                this.serviceClass = res.filter((pams) => pams.pamTypeId == 1);
                this.pamPeriod = res.filter((pams) => pams.pamTypeId == 4);
                this.pamClass = res.filter((pams) => pams.pamTypeId == 2);
                this.pamSchedule = res.filter((pams) => pams.pamTypeId == 3);
                this.pamPriority = res.filter((pams) => pams.pamTypeId == 5);
            });
        this.initializeAddPamForm();
    }

    initializeAddPamForm() {
        this.addPamForm = this.fb.group({
            pamId: [''],
            pamClassId: [''],
            pamScheduleId: [''],
            currentPamPeriodId: [''],
            pamServicePriorityId: [''],
            deferredToDate: this.deferredDate,
        });
    }
    get isFormVaild(){
        const formValues = this.addPamForm.value;
        for (let key in formValues){
            if (formValues[key]) return true
        }
        return false;
    }

    addPam() {
        console.log(this.addPamForm.value);
        let reqObj = {
            ...this.addPamForm.value,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'PAM Information new',
                footPrintDetails: [
                    {
                        paramName: 'PAM ID',
                        oldValue: null,
                        newValue: this.addPamForm.value.pamId,
                    },
                    {
                        paramName: 'PAM_CLASS',
                        oldValue: null,
                        newValue: this.addPamForm.value.pamClassId,
                    },
                    {
                        paramName: 'PAM Schedule',
                        oldValue: null,
                        newValue: this.addPamForm.value.pamScheduleId,
                    },
                    {
                        paramName: 'Current PAM Period',
                        oldValue: null,
                        newValue: this.addPamForm.value.currentPamPeriodId,
                    },
                    {
                        paramName: 'Deferred to Date',
                        oldValue: null,
                        newValue: new Date(this.addPamForm.value.deferredToDate),
                    },
                    {
                        paramName: 'PAM Service Priority',
                        oldValue: null,
                        newValue: this.addPamForm.value.pamServicePriorityId,
                    },
                ],
            },
        };
        this.pamInformationService.addPam$(reqObj).subscribe({
            next: (res) => {
                if (res.statusCode === 0) {
                    this.toasterService.success("Pam Added Successfully");
                }
                this.addPamForm.reset();
                this.deferredDate = null;
                this.router.navigate(['customer-care/pam-information']);
            },
            error: (err) => {
                this.toasterService.error('Error', err);
            },
        });
    }

    onSelectDate() {
        this.addPamForm.patchValue({
            deferredToDate: this.deferredDate.getTime(),
        });
    }
}
