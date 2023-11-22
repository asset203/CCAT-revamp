import {Component, ElementRef, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import { InputNumber } from 'primeng/inputnumber';
import {Observable} from 'rxjs';
import {map, take, tap} from 'rxjs/operators';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {BusinessPlansService} from 'src/app/core/service/business-plans.service';
import {BusinessPlan} from 'src/app/shared/models/business-plans.interface';
import {HlrProfile} from 'src/app/shared/models/hlr-profile.interface';
import {ServiceClasses} from 'src/app/shared/models/ProfileRequest.interface';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-business-plans-dialog',
    templateUrl: './business-plan-dialog.component.html',
    styleUrls: ['./business-plan-dialog.component.scss'],
})
export class BusinessPlansDialogComponent implements OnChanges {
    @Input() visiablity: boolean;
    @Input() editingBusinessPlan: BusinessPlan;
    @Input() editMode: boolean;
    @Output() hideModal = new EventEmitter<void>();
    @Output() formSubmitted = new EventEmitter<BusinessPlan>();
    @ViewChild('in') in:InputNumber ;
    businessPlanForm: FormGroup;
    serviceClasses: Observable<ServiceClasses[]> = this.profileService.servicesClasses$.pipe(
        take(1),
        map((data) => data.payload.serviceClasses)
    );
    hlrProfiles: Observable<HlrProfile[]> = this.businessPlansService.hlrProfiles$.pipe(
        take(1),
        map((res) => res.payload.hlrProfiles)
    );
    constructor(private profileService: ProfileService, private businessPlansService: BusinessPlansService , private validationService:ValidationService) {}
    ngOnChanges(changes: SimpleChanges) {
        if (
            (changes.visiablity && changes.editMode && changes.editMode.currentValue) ||
            (changes.editingBusinessPlan && changes.editingBusinessPlan.currentValue)
        ) {
            this.businessPlanForm = new FormGroup({
                businessPlanCode: new FormControl(this.editingBusinessPlan.businessPlanCode, Validators.required),
                businessPlanName: new FormControl(this.editingBusinessPlan.businessPlanName, [Validators.required,Validators.pattern(this.validationService.whiteSpacesPattern)]),
                hlrProfileId: new FormControl(this.editingBusinessPlan.hlrProfileId, Validators.required),
                serviceClassId: new FormControl(this.editingBusinessPlan.serviceClassId, Validators.required),
                businessPlanId: new FormControl(this.editingBusinessPlan.businessPlanId),
            });
        } else {
            this.businessPlanForm = new FormGroup({
                businessPlanCode: new FormControl(null, Validators.required),
                businessPlanName: new FormControl(null, [Validators.required,Validators.pattern(this.validationService.whiteSpacesPattern)]),
                hlrProfileId: new FormControl(null, Validators.required),
                serviceClassId: new FormControl(null, Validators.required),
            });
        }
    }
    submit() {
        this.formSubmitted.emit(this.businessPlanForm.value);
    }
    hideDialog() {
        this.businessPlanForm.reset();
        this.hideModal.emit();
    }
    onShowDialog(){
        this.in.input.nativeElement.focus();
    }
}
