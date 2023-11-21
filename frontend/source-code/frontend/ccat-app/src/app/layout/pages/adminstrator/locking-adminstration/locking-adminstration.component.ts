import { LockingAdminstrationService } from './../../../../core/service/administrator/locking-adminstration.service';
import { Component, OnInit } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { LockingAdmin } from 'src/app/shared/models/locking-admin';
import { ConfirmationService } from 'primeng/api';
import { ToastService } from 'src/app/shared/services/toast.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { Table } from 'primeng/table';

@Component({
    selector: 'app-locking-adminstration',
    templateUrl: './locking-adminstration.component.html',
    styleUrls: ['./locking-adminstration.component.scss'],
    providers: [ConfirmationService],
})
export class LockingAdminstrationComponent implements OnInit {
    filterSearch;
    tableLockingList;
    search = false;
    permissions = {
        viewLockingAdmin: false,
    };
    searchText: any;
    constructor(
        private lockingAdminService: LockingAdminstrationService,
        private confirmationService: ConfirmationService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private messageService: MessageService
    ) { }

    ngOnInit(): void {
        this.setPermissions();
        if (this.permissions.viewLockingAdmin) {
            this.lockingAdminService.getAllLocking$
                .pipe(
                    take(1),
                    map((res) => {
                        return res?.payload?.lockingList;
                    })
                )
                .subscribe((res) => {
                    this.tableLockingList = res.map((locking) => {
                        return {
                            ...locking,
                            date: new Date(locking.date),
                        };
                    });
                });
        } else {
            this.toasterService.error('Error', this.messageService.getMessage(401).message);
        }
    }

    deleteLocking(msisdn, username) {
        this.lockingAdminService
            .unLock$(msisdn, username)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(57).message, 'Success');
                        this.tableLockingList = this.tableLockingList.filter((user) => user.msisdn !== msisdn);
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    confirm(msisdn, username) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(56).message,
            accept: () => {
                this.deleteLocking(msisdn, username);
            },
        });
    }

    setPermissions() {
        let findDisconnCodesPermssions: Map<number, string> = new Map().set(91, 'getAllCode');
        this.featuresService.checkUserPermissions(findDisconnCodesPermssions);
        this.permissions.viewLockingAdmin = this.featuresService.getPermissionValue(91);
    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()
    }
}
