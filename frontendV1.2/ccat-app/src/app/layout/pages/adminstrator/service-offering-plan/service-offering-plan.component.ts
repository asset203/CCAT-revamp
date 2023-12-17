import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ConfirmationService, MenuItem } from 'primeng/api';
import { Table } from 'primeng/table';
import { map, take } from 'rxjs/operators';
import { ServiceOfferingPlansService } from 'src/app/core/service/administrator/service-offering-plans.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-service-offering-plan',
  templateUrl: './service-offering-plan.component.html',
  styleUrls: ['./service-offering-plan.component.scss'],
  providers: [ConfirmationService],
})
export class ServiceOfferingPlanComponent implements OnInit {
  loading$ = this.serviceOfferingPlansService.loading$;
  actionsDropDown = false;
  search = false;
  selectedPlan;
  serviceOfferingPlanList ;
  serviceOfferingPlansObs = this.serviceOfferingPlansService.allServiceOfferingPlans$;

  permissions = {
    getAllServiceOfferingPlans: false,
    addServiceOfferingPlan: false,
    updateServiceOfferingPlan: false,
    deleteServiceOfferingPlan: false,
    updateServiceClassPlanDesc: false

  };
  searchText: any;
  constructor(private route: ActivatedRoute, private router: Router, private serviceOfferingPlansService: ServiceOfferingPlansService,
    private toasterService: ToastService, private featuresService: FeaturesService, private messageService: MessageService, private confirmationService: ConfirmationService) { }
  items = [];
  isFetchingList$ = this.serviceOfferingPlansService.isFetchingList$;
  ngOnInit(): void {
    this.setPermissions();
    this.getAllServiceOffering();
    if (this.permissions.getAllServiceOfferingPlans) {
      this.serviceOfferingPlansService.getAllServiceOfferingBitsDesc();
    }
    else {
      this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
    }
  }
  getAllServiceOffering() {
    this.serviceOfferingPlansObs
      .pipe(
        map((response) => response)
      )
      .subscribe((res) => {
        this.serviceOfferingPlanList = res;

      });

  }

  confirmDeleteProfile(serviceId: number) {
    this.confirmationService.confirm({
      message: this.messageService.getMessage(116).message,
      accept: () => {
        this.deleteServiceOffering(serviceId);
      },
    });
  }
  deleteServiceOffering(id) {
    this.serviceOfferingPlansService.deleteServiceOfferingPlanWithBits(id)
  }
  navigateToAddEditPage(id: number) {
    if (id === -1) this.router.navigate([this.router.url + '/add-plan/'])
    else this.router.navigate([this.router.url + '/update-plan/' + id])

  }
  clear(table: Table) {
    if (table.filters.global["value"]) {
      table.filters.global["value"] = ''
    }
    this.searchText = null;
    table.clear()

  }
  setPermissions() {
    let findSubscriberPermissions: Map<number, string> = new Map()
      .set(327, 'addServiceOfferingPlan')
      .set(331, 'deleteServiceOfferingPlan')
      .set(324, 'getAllServiceOfferingPlans')
      .set(330, 'updateServiceClassPlanDesc')
      .set(329, 'updateServiceOfferingPlan')
    this.featuresService.checkUserPermissions(findSubscriberPermissions);
    this.permissions.addServiceOfferingPlan = this.featuresService.getPermissionValue(327);
    this.permissions.deleteServiceOfferingPlan = this.featuresService.getPermissionValue(331);
    this.permissions.getAllServiceOfferingPlans = this.featuresService.getPermissionValue(324);
    this.permissions.updateServiceClassPlanDesc = this.featuresService.getPermissionValue(330);
    this.permissions.updateServiceOfferingPlan = this.featuresService.getPermissionValue(329);


  }


}
