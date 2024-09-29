import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {CallActivityService} from 'src/app/core/service/customer-care/call-activity.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-call-activity',
    templateUrl: './call-activity.component.html',
    styleUrls: ['./call-activity.component.scss'],
})
export class CallActivityComponent implements OnInit {
    msisdn = JSON.parse(sessionStorage.getItem('callReason'))?.msisdn
        ? JSON.parse(sessionStorage.getItem('callReason'))?.msisdn
        : null;
    callActivityForm: FormGroup;
    loading$ = this.callActivityService.loading$;

    constructor(
        private fb: FormBuilder,
        private callActivityService: CallActivityService,
        private footPrintService: FootPrintService,
        private featuresService : FeaturesService
    ) {}

    activityType = 0;
    parentId = 0;
    directions = [];
    families = [];
    types = [];
    reasons = [];
    permissions = {
        viewCallReason: false,
    };
    ngOnInit(): void {
        this.setPermissions();
        this.createForm();
        this.getActivities();
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Call Reason',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }

    createForm() {
        this.callActivityForm = this.fb.group({
            directions: ['', Validators.required],
            families: ['', Validators.required],
            types: ['', Validators.required],
            reasons: ['', Validators.required],
        });
    }

    getActivities() {
        this.callActivityService.getCallActivities(this.activityType, this.parentId).subscribe({
            next: (res) => {
                switch (this.activityType) {
                    case 0:
                        this.directions = res?.payload?.activitiesList;
                        break;

                    case 1:
                        this.families = res?.payload?.activitiesList;
                        break;

                    case 2:
                        this.types = res?.payload?.activitiesList;
                        break;

                    case 3:
                        this.reasons = res?.payload?.activitiesList;
                        break;

                    default:
                        break;
                }
            },
        });
    }

    changeDropDown(type, event) {
        this.parentId = event.value.activityId;
        switch (type) {
            case 'directions':
                this.activityType = 1;
                break;

            case 'families':
                this.activityType = 2;
                break;

            case 'types':
                this.activityType = 3;
                break;

            default:
                this.activityType = 0;
                break;
        }
        this.getActivities();
        this.resetFormFields();
    }

    resetFormFields() {
        switch (this.activityType) {
            case 0:
                this.callActivityForm.controls.families.setValue('');
                this.callActivityForm.controls.types.setValue('');
                this.callActivityForm.controls.reasons.setValue('');
                this.families = [];
                this.types = [];
                this.reasons = [];
                break;

            case 1:
                this.callActivityForm.controls.types.setValue('');
                this.callActivityForm.controls.reasons.setValue('');
                this.types = [];
                this.reasons = [];
                break;

            case 2:
                this.callActivityForm.controls.reasons.setValue('');
                this.reasons = [];
                break;

            default:
                break;
        }
    }

    onSubmit() {
        let formObj = {
            msisdn: this.msisdn,
            callReasonId: JSON.parse(sessionStorage.getItem('callReason')).callReasonId,
            direction: this.callActivityForm.value.directions.activityName,
            family: this.callActivityForm.value.families.activityName,
            type: this.callActivityForm.value.types.activityName,
            reason: this.callActivityForm.value.reasons.activityName,
        };
        this.callActivityService.updateCallReason(formObj);
    }
    setPermissions() {
        let callResonPermissions: Map<number, string> = new Map().set(349, 'viewCallReason');
        this.featuresService.checkUserPermissions(callResonPermissions);
        this.permissions.viewCallReason = this.featuresService.getPermissionValue(349);
    }
}
