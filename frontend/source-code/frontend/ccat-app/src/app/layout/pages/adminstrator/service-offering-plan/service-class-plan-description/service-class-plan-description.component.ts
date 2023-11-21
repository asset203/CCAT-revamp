import { Location } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { ServiceClassesService } from 'src/app/core/service/administrator/service-classes.service';
import { ServiceOfferingPlansService } from 'src/app/core/service/administrator/service-offering-plans.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-service-class-plan-description',
  templateUrl: './service-class-plan-description.component.html',
  styleUrls: ['./service-class-plan-description.component.scss']
})
export class ServiceClassPlanDescriptionComponent implements OnInit, OnDestroy {

  serviceClassPlanDescObs = this.serviceOfferingPlansService.serviceClassPlanDescription$;
  routeSub: Subscription;
  planId;
  serviceClassPlanName = "";
  selectedRow = null;
  serviceClassId;
  exist = false;
  serviceClasses = [];
  permissions = {
    getServiceClassPlanDesc: false,
    addServiceClassPlanDesc: false,
    updateServiceClassPlanDesc: false,
    deleteServiceClassPlanDesc: false,
  };
  constructor(private route: ActivatedRoute, private location: Location,
    private serviceOfferingPlansService: ServiceOfferingPlansService,
    private serviceClassesService: ServiceClassesService,
    private toasterService: ToastService, private featuresService: FeaturesService, private messageService: MessageService) { }

  ngOnInit(): void {
    this.setPermissions()
    if (this.permissions.getServiceClassPlanDesc) {
      this.routeSub = this.route.params.subscribe(params => {
        this.planId = params['id'];
      });

      this.serviceOfferingPlansService.getServiceClassPlanDescription(this.planId);
      this.serviceClassesService.allServiceClasses$.subscribe(res => {
        this.serviceClasses = res.payload.serviceClasses;
      })
    }
  }
  onRowSelect(event) {
    this.exist = true;
    this.selectedRow = event.data;


  }
  onSubmit(form: NgForm, action) {
    console.log(form);
    if (action === "add") {
      this.serviceOfferingPlansService.addServiceClassPlanDescription(
        {
          planId: this.planId, serviceClassId: form.controls.classId.value,
          description: form.controls.description.value
        })
    } else if (action == "update") {
      this.serviceOfferingPlansService.updateServiceClassPlanDescription(
        {
          planId: this.planId, serviceClassId: form.controls.classId.value,
          description: form.controls.description.value
        })
    } else {
      this.serviceOfferingPlansService.deleteServiceClassPlanDescription(
        { planId: this.planId, serviceClassId: form.controls.classId.value })
    }

  }

  selectClass(event, planClasses: any[]) {
    // event.value
    this.serviceClassId = event.value;
    if (planClasses.map(c => c.serviceClassId).includes(event.value)) {
      this.exist = true;
      this.selectedRow = planClasses.find(c => c.serviceClassId === event.value);
    } else {
      this.exist = false;
      this.selectedRow = null;
    }

  }
  back() {
    this.location.back();
  }
  ngOnDestroy(): void {
    this.routeSub.unsubscribe();
  }
  setPermissions() {
    let findSubscriberPermissions: Map<number, string> = new Map()
      .set(328, 'addServiceClassPlanDesc')
      .set(332, 'deleteServiceClassPlanDesc')
      .set(326, 'getServiceClassPlanDesc')
      .set(330, 'updateServiceClassPlanDesc')
    this.featuresService.checkUserPermissions(findSubscriberPermissions);
    this.permissions.addServiceClassPlanDesc = this.featuresService.getPermissionValue(328);
    this.permissions.deleteServiceClassPlanDesc = this.featuresService.getPermissionValue(332);
    this.permissions.getServiceClassPlanDesc = this.featuresService.getPermissionValue(326);
    this.permissions.updateServiceClassPlanDesc = this.featuresService.getPermissionValue(330);
  }
}
