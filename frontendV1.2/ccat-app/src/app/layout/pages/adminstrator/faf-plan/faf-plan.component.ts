import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {FafPlanService} from 'src/app/core/service/administrator/faf-plan.service';
import {FafPlanModel} from 'src/app/shared/models/faf-plan.model';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-faf-plan',
    templateUrl: './faf-plan.component.html',
    styleUrls: ['./faf-plan.component.scss'],
    providers: [ConfirmationService],
})
export class FafPlanComponent implements OnInit, OnDestroy {
    dialogVisiabilty = false;
    fAFForm: FormGroup;
    planID = null;
    selectedFafIndicator = null;
    isIdUnique = true;
    editMode = false;
    search = false;
    isDescUnique = true;
    editedPlan!: FafPlanModel;

    permissions = {
        getFAFPlans: false,
        addFAFPlan: false,
        deleteFAFPlan: false,
        updateFAFPlan: false,
    };
    searchText: any;
    isFetchingList$ = this.fafPlanService.isFetchingList$;
    constructor(
        private fb: FormBuilder,
        private fafPlanService: FafPlanService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private loadingService: LoadingService
    ) {}
    ngOnDestroy(): void {
        this.fafPlanService.allFAFPlanSubject$.next([]);
    }
    fAFPlans = this.fafPlanService.allFAFPlanSubject$.asObservable();
    loading$ = this.fafPlanService.loading$;
    fAFPlansLookup$ = this.fafPlanService.FAFPlanIndicatorsLookupSubject$;
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
            this.dt.reset();
            this.dt.filterGlobal('', 'contains');
            this.dt.first = 0;
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    ngOnInit(): void {
        this.setPermissions();

        if (this.permissions.getFAFPlans) {
            this.fafPlanService.getAllFAFPlans();
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
        this.fafPlanService.getFAFPlansLookup();
        this.createForm();
    }
    createForm() {
        this.fAFForm = this.fb.group({
            planId: [null, [Validators.required]],
            name: [null, Validators.required],
            fafIndicatorId: [null, Validators.required],
        });
    }
    addFAFPlan() {
        this.isDescUnique = true;
        this.isIdUnique = true;
        this.editMode = false;
        this.createForm();
        this.dialogVisiabilty = true;
        this.planID = null;
    }
    editPlan(plan) {
        this.isDescUnique = true;
        this.isIdUnique = true;
        this.editMode = true;
        this.editedPlan = plan;
        this.dialogVisiabilty = true;
        this.planID = plan.planId;
        // this.selectedFafIndicator = plan.fafIndicatorId;
        if (plan) {
            this.fAFForm.patchValue({
                planId: plan.planId,
                name: plan.name,
                fafIndicatorId: plan.fafIndicatorId,
            });
        }
    }
    cancel() {
        this.dialogVisiabilty = false;
        this.editedPlan = null;
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }

    checkUniqueId(event, fafPlans: FafPlanModel[]) {
        if (fafPlans.find((el) => el.planId == parseInt(event.target.value))) {
            this.isIdUnique = false;
        } else {
            this.isIdUnique = true;
        }
    }

    checkUniqueDesc(value, fafPlans: FafPlanModel[]) {
        let repeatedTimes = fafPlans.filter((el) => el.name === value.target.value);

        if (this.editMode) {
            if (repeatedTimes.length && repeatedTimes[0].planId !== this.editedPlan.planId) {
                this.isDescUnique = false;
            } else {
                this.isDescUnique = true;
            }
        }
        if (!this.editMode)
            if (repeatedTimes.length) this.isDescUnique = false;
            else this.isDescUnique = true;
    }

    onSubmit() {
        if (this.planID != null) {
            this.fafPlanService.updateFAFPlan(this.fAFForm.value).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.dialogVisiabilty = false;
                    this.toastrService.success(res.statusMessage);
                    this.fafPlanService.getAllFAFPlans();
                }
            });
        } else {
            this.fafPlanService.addFAFPlan(this.fAFForm.value).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.dialogVisiabilty = false;
                    this.toastrService.success(res.statusMessage);
                    this.fafPlanService.getAllFAFPlans();
                }
            });
        }
        this.editedPlan = null;
    }
    cofirmDeletePlan(planId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(83).message,
            accept: () => {
                this.deleteFAFPlan(planId);
            },
        });
    }
    deleteFAFPlan(id) {
        this.fafPlanService.deleteFAFPlan(id).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(res.statusMessage);
                this.fafPlanService.getAllFAFPlans();
            }
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(297, 'getFAFPlans')
            .set(298, 'addFAFPlan')
            .set(300, 'deleteFAFPlan')
            .set(299, 'updateFAFPlan');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getFAFPlans = this.featuresService.getPermissionValue(297);
        this.permissions.addFAFPlan = this.featuresService.getPermissionValue(298);
        this.permissions.deleteFAFPlan = this.featuresService.getPermissionValue(300);
        this.permissions.updateFAFPlan = this.featuresService.getPermissionValue(299);
    }
}
