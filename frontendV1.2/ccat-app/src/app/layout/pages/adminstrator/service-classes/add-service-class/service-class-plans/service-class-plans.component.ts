import { Component, OnInit, Input } from '@angular/core';
import { ServiceOfferingPlansService } from 'src/app/core/service/administrator/service-offering-plans.service';
import { ServiceClassPlanDescription } from 'src/app/shared/models/service-class';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-service-class-plans',
    templateUrl: './service-class-plans.component.html',
    styleUrls: ['./service-class-plans.component.scss'],
})
export class ServiceClassPlansComponent implements OnInit {
    constructor(private serviceOfferingPlanService: ServiceOfferingPlansService, private toastrService: ToastService) { }
    @Input() plans: ServiceClassPlanDescription[];
    planId: number;
    planDesc: string;
    lookupPlans$ = this.serviceOfferingPlanService.getAllServiceOfferingPlansForServiceClass();
    plan;
    editedPlanIndex: number;
    editMode = false;
    ngOnInit(): void { }
    setSelectedPlan(event) {
        this.planId = event;
    }
    addToPlans() {
        if (this.plans.filter((e) => e.planId === this.planId).length === 0) {
            this.plans.push({
                planId: this.planId,
                planDescription: this.planDesc,
            });
        } else {
            this.toastrService.warning('This Plan already exist');
        }
        this.planId = null;
        this.planDesc = null;
        this.plan = null;
    }
    deletePlan(index: number) {
        this.plans.splice(index, 1);
    }
    submitUpdatePlan() {
        this.plans[this.editedPlanIndex] = {
            planId: this.planId,
            planDescription: this.planDesc,
        };
        this.planId = null;
        this.planDesc = null;
        this.plan = null;
        this.editMode = false;
    }
    updatePlan(index: number) {
        this.editMode = true;
        this.editedPlanIndex = index;
        this.plan= this.plans[index].planId
        this.planId = this.plans[index].planId;
        this.planDesc = this.plans[index].planDescription;
        console.log("this.plan",this.plan)
        

    }
}
