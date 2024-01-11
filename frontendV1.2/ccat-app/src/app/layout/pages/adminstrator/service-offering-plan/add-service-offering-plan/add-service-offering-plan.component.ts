import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { map, switchMap, tap } from 'rxjs/operators';
import { ServiceOfferingBitsDescService } from 'src/app/core/service/administrator/service-offering-bits-description.service';
import { ServiceOfferingPlansService } from 'src/app/core/service/administrator/service-offering-plans.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-add-service-offering-plan',
    templateUrl: './add-service-offering-plan.component.html',
    styleUrls: ['./add-service-offering-plan.component.scss'],
})
export class AddServiceOfferingPlanComponent implements OnInit, OnDestroy {
    editMode = false;
    serviceOfferingBDescriptionObs = this.serviceOfferingBitsDesService.allServiceOfferingBitsDesc$;
    serviceOfferingPlanWithBitsObs = this.serviceOfferingPlansService.serviceOfferingPlanWithBits$;
    selectedBits = [];
    routeSub: Subscription;
    serviceOfferingBDescriptionSub: Subscription;
    serviceOfferingPlanSub: Subscription;


    permissions = {
        getServiceOfferingPlan: false
    }
    constructor(private location: Location,
        private route: ActivatedRoute,
        private serviceOfferingBitsDesService: ServiceOfferingBitsDescService,
        private serviceOfferingPlansService: ServiceOfferingPlansService,
        private toasterService: ToastService, private featuresService: FeaturesService, private messageService: MessageService) { }

    ngOnInit(): void {
        this.setPermissions()
        if (this.permissions.getServiceOfferingPlan) {
            this.serviceOfferingBitsDesService.getAllServiceOfferingBitsDesc();
            this.routeSub = this.route.params.subscribe(params => {
                if (params['id']) {
                    this.editMode = true;
                    this.serviceOfferingPlansService.getServiceOfferingPlanWithBits(params['id']);
                }
            });
            if (this.editMode) {
                this.serviceOfferingPlanSub = this.serviceOfferingPlanWithBitsObs.pipe(
                    switchMap((res) => {
                        return this.serviceOfferingBDescriptionObs.pipe(tap(allBits => {
                            if (res)
                                this.selectedBits = allBits.filter((bit, index) => res.planBits[index].isEnabled);
                        }
                        ))
                    })
                ).subscribe();
            }
        }
        else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }

    }


    onSubmit(form: NgForm) {
        console.log(form.controls);
        if (this.editMode)
            this.serviceOfferingPlansService.updateServiceOfferingPlan({
                planId: form.controls.id.value,
                planName: form.controls.name.value,
                serviceOfferingPlanBits: this.selectedBits.map(bit => bit.bitPosition)
            });
        else
            this.serviceOfferingPlansService.addServiceOfferingPlan({
                planId: form.controls.id.value,
                planName: form.controls.name.value,
                serviceOfferingPlanBits: this.selectedBits.map(bit => bit.bitPosition)
            });

    }

    setBits(allBits: any[], enabledBits: any[]) {
        this.selectedBits = allBits.filter((bit, index) => enabledBits[index].isEnabled);
    }


    back() {
        this.location.back();
    }
    ngOnDestroy(): void {
        this.routeSub.unsubscribe();
        this.selectedBits = [];
        if (this.serviceOfferingPlanSub)
            this.serviceOfferingPlanSub.unsubscribe();

    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(325, 'getServiceOfferingPlan')
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getServiceOfferingPlan = this.featuresService.getPermissionValue(325);
    }
    hello(e,test){
        console.log(test)
    }
}
