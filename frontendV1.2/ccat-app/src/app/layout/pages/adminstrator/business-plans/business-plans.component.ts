import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { map, take } from 'rxjs/operators';
import { BusinessPlansService } from 'src/app/core/service/business-plans.service';
import { BusinessPlan } from 'src/app/shared/models/business-plans.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { MessageService } from 'src/app/shared/services/message.service';

@Component({
    selector: 'app-business-plans',
    templateUrl: './business-plans.component.html',
    styleUrls: ['./business-plans.component.scss'],
    providers: [ConfirmationService],
})
export class BusinessPlansComponent implements OnInit {
    selectedUsers: [];
    businessPlanDialog: boolean;
    filterSearch;
    tableUsers;
    businessPlans: BusinessPlan[];
    tablePlans: BusinessPlan[];
    editingBusinessPlan: BusinessPlan;
    editMode: boolean = false;
    search = false;
    loading$ = this.businessPlansService.loading$; 
    isFetchingList$ = this.loadingService.fetching$;
    permissions = {
        getPlans: false,
        addPlan: false,
        deletePlan: false,
        updatePlan: false,
    };
    searchText: any;
    constructor(
        private businessPlansService: BusinessPlansService,
        private toastrService: ToastrService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private loadingService : LoadingService
    ) { }

    ngOnInit(): void {
        this.setPermissions();
        this.getBusinessPlans();
    }
    getBusinessPlans() {
        if (this.permissions.getPlans) {
            this.loadingService.startFetchingList()
            this.businessPlansService.businessPlans$
                .pipe(
                    take(1),
                    map((response) => response?.payload?.businessPlans)
                )
                .subscribe((businessPlans) => {
                    this.tablePlans = businessPlans;
                    this.businessPlans = businessPlans;
                    this.loadingService.endFetchingList();
                },error=>{
                    this.tablePlans = [];
                    this.businessPlans = [];
                    this.loadingService.endFetchingList();
                });
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    ShowAddBusinessPlanDialog() {
        this.businessPlanDialog = true;
        this.editMode = false;
    }
    hideModal() {
        this.businessPlanDialog = false;
        this.editingBusinessPlan = null;
    }
    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.businessPlans.filter((bPlan) =>
                bPlan.businessPlanName.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tablePlans = filteredTable;
        } else {
            this.tablePlans = this.businessPlans;
        }
    }
    deleteBusinessPlan(bPlanCode: number) {
        this.businessPlansService.deleteBusinessPlan(bPlanCode).subscribe((res) => {
            if (res?.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(5).message);
            }
            this.getBusinessPlans();
        });
    }
    confirmDeletePlan(bPlanCode: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(6).message,
            accept: () => {
                this.deleteBusinessPlan(bPlanCode);
            },
        });
    }
    editPlan(businessPlanId: number) {
        this.editMode = true;
        this.editingBusinessPlan = this.businessPlans.filter((el) => el.businessPlanId === businessPlanId)[0];
        this.businessPlanDialog = true;
    }
    onSubmitForm(businessPlan: BusinessPlan) {
        if (!this.editMode) {
            this.businessPlansService.addBusinessPlan(businessPlan).subscribe((res) => {
                if (res?.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(7).message);
                }
                this.businessPlanDialog = false;
                this.getBusinessPlans();
            });
        } else {
            this.businessPlansService.updateBusinessPlan(businessPlan).subscribe((res) => {
                if (res?.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(8).message);
                }
                this.businessPlanDialog = false;
                this.getBusinessPlans();
            });
        }
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(50, 'getPlans')
            .set(73, 'addPlan')
            .set(53, 'deletePlan')
            .set(52, 'updatePlan');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getPlans = this.featuresService.getPermissionValue(50);
        this.permissions.addPlan = this.featuresService.getPermissionValue(73);
        this.permissions.deletePlan = this.featuresService.getPermissionValue(53);
        this.permissions.updatePlan = this.featuresService.getPermissionValue(52);
    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()
    }
}
