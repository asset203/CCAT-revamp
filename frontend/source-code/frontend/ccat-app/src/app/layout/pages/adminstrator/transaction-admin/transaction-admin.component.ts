import {Component, OnInit} from '@angular/core';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-transaction-admin',
    templateUrl: './transaction-admin.component.html',
    styleUrls: ['./transaction-admin.component.scss'],
})
export class TransactionAdminComponent implements OnInit {
    constructor(private featuresService: FeaturesService) {}
    tab = 'types';
    permissions = {
        getAllTypes: false,
        addType: false,
        updateType: false,
        deleteType: false,
        getAllCodes: false,
        addCode: false,
        updateCode: false,
        deleteCode: false,
        updateLink: false,
    };
    setPermissions() {
        let findTransactionAdminPermissions: Map<number, string> = new Map()
            .set(134, 'getAllTypes')
            .set(138, 'addType')
            .set(136, 'updateType')
            .set(140, 'deleteType')
            .set(135, 'getAllCodes')
            .set(139, 'addCode')
            .set(137, 'updateCode')
            .set(141, 'deleteCode')
            .set(143, 'updateLink');
        this.featuresService.checkUserPermissions(findTransactionAdminPermissions);
        this.permissions.getAllTypes = this.featuresService.getPermissionValue(134);
        this.permissions.addType = this.featuresService.getPermissionValue(138);
        this.permissions.updateType = this.featuresService.getPermissionValue(136);
        this.permissions.deleteType = this.featuresService.getPermissionValue(140);
        this.permissions.getAllCodes = this.featuresService.getPermissionValue(135);
        this.permissions.addCode = this.featuresService.getPermissionValue(139);
        this.permissions.updateCode = this.featuresService.getPermissionValue(137);
        this.permissions.deleteCode = this.featuresService.getPermissionValue(141);
        this.permissions.updateLink = this.featuresService.getPermissionValue(143);
    }

    ngOnInit(): void {
        this.setPermissions();
    }

    switchTab(tab) {
        this.tab = tab;
    }
}
