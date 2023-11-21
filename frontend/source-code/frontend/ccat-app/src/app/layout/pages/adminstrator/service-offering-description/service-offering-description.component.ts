import { Component, OnInit } from '@angular/core';
import { Table } from 'primeng/table';
import { map } from 'rxjs/operators';
import { ServiceOfferingBitsDescService } from 'src/app/core/service/administrator/service-offering-bits-description.service';
import { ServiceOfferingBitModel } from 'src/app/shared/models/service-offering-bit.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-service-offering-description',
    templateUrl: './service-offering-description.component.html',
    styleUrls: ['./service-offering-description.component.scss'],
})
export class ServiceOfferingDescriptionComponent implements OnInit {

    serviceOfferingBDescriptionObs = this.serviceOfferingBitsDescService.allServiceOfferingBitsDesc$;
    filterInput = "";
    currentServiceOfferingB;
    editDialog = false;
    search = false;
    newBitName;
    first = 0;
    serviceOfferingDescList = [];
    permissions = {
        getServiceOfferingDescription: false,
        updateServiceOfferingDescription: false,
    };
    searchText: any;
    constructor(private serviceOfferingBitsDescService: ServiceOfferingBitsDescService,
        private featuresService: FeaturesService,
        private toastrService: ToastService,
        private messageService: MessageService,) { }

    ngOnInit(): void {
        this.setPermissions();
        this.getAllServiceOfferingDesc();
        if (this.permissions.getServiceOfferingDescription) {
            this.serviceOfferingBitsDescService.getAllServiceOfferingBitsDesc();
        }
        else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    getAllServiceOfferingDesc() {
        this.serviceOfferingBDescriptionObs
            .pipe(
                map((response) => response)
            )
            .subscribe((res) => {
                this.serviceOfferingDescList = res;

            });

    }
    filterList(serviceOfferingBitsList: ServiceOfferingBitModel[]) {
        let res = serviceOfferingBitsList.filter(element =>
            element.bitName.toLocaleLowerCase().includes(this.filterInput.toLocaleLowerCase()) || element.bitPosition === parseInt(this.filterInput));
        console.log(res);

        return res;
    }

    openEditDialog(serviceOfferingBit) {
        this.editDialog = true;
        this.currentServiceOfferingB = serviceOfferingBit;

    }

    updateItem() {
        this.editDialog = false;

        this.serviceOfferingBitsDescService.updateServiceOfferingBitDesc(
            { bitPosition: this.currentServiceOfferingB.bitPosition, bitName: this.newBitName })

    }
    reset() {

        this.first = 0;
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(282, 'getServiceOfferingDescription')
            .set(284, 'updateServiceOfferingDescription')
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getServiceOfferingDescription = this.featuresService.getPermissionValue(282);
        this.permissions.updateServiceOfferingDescription = this.featuresService.getPermissionValue(284);

    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()

    }
}
