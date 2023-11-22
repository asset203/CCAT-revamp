import { Component, OnInit } from '@angular/core';
import { isInteger } from '@ng-bootstrap/ng-bootstrap/util/util';
import { Table } from 'primeng/table';
import { map } from 'rxjs/operators';
import { AccountGroupsBitsDescService } from 'src/app/core/service/administrator/account-groups-bits-desc.service';
import { AccountGroupBitModel } from 'src/app/shared/models/account-group-bit.model';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-account-groups-bits-description',
    templateUrl: './account-groups-bits-description.component.html',
    styleUrls: ['./account-groups-bits-description.component.scss'],
})
export class AccountGroupsBitsDescriptionComponent implements OnInit {

    accountGroupsBDescriptionObs = this.accountGroupsBitsDescService.allAccountGroupsBitsDesc$;
    filterInput = "";
    currentAccountGroup;
    editDialog = false;
    search = false;
    newBitName = "";
    first = 0;
    accountGroupsBDescription = [];
    permissions = {
        getAccountGroupsBitsDescription: false,
        updateAccountGroupsBitsDescription: false,
    };
    searchText: any;
    constructor(private accountGroupsBitsDescService: AccountGroupsBitsDescService,
        private featuresService: FeaturesService,
        private toastrService: ToastService,
        private messageService: MessageService,) { }

    ngOnInit(): void {
        this.setPermissions();
        this.getAllAccountGroupDesc();
        if (this.permissions.getAccountGroupsBitsDescription) {
            this.accountGroupsBitsDescService.getAllAccountGroupsBitsDesc();
        }
        else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    getAllAccountGroupDesc() {
        this.accountGroupsBDescriptionObs
            .pipe(
                map((response) => response)
            )
            .subscribe((res) => {
                this.accountGroupsBDescription = res;

            });

    }
    filterList(accountGroupList: AccountGroupBitModel[]) {
        let res = accountGroupList.filter(element =>
            element.bitName.toLocaleLowerCase().includes(this.filterInput.toLocaleLowerCase()) || element.bitPosition === parseInt(this.filterInput));


        return res;
    }

    openEditDialog(accountGroup) {
        this.editDialog = true;
        this.currentAccountGroup = accountGroup;

    }
    reset() {

        this.first = 0;
    }

    updateItem() {
        this.editDialog = false;

        this.accountGroupsBitsDescService.UpdateAccountGroupsBitsDesc(
            { bitPosition: this.currentAccountGroup.bitPosition, bitName: this.newBitName })

    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(286, 'getAccountGroupsBitsDescription')
            .set(287, 'updateAccountGroupsBitsDescription')
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAccountGroupsBitsDescription = this.featuresService.getPermissionValue(286);
        this.permissions.updateAccountGroupsBitsDescription = this.featuresService.getPermissionValue(287);

    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()

    }
}
