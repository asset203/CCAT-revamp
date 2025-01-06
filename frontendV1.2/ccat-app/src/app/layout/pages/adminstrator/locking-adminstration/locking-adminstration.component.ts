import {LockingAdminstrationService} from './../../../../core/service/administrator/locking-adminstration.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {map, take} from 'rxjs/operators';
import {LockingAdmin} from 'src/app/shared/models/locking-admin';
import {ConfirmationService} from 'primeng/api';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {Table} from 'primeng/table';
import {LoadingService} from 'src/app/shared/services/loading.service';

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
    isFetchingList$ = this.loadingService.fetching$;
    loading = this.lockingAdminService.loading;
    constructor(
        private lockingAdminService: LockingAdminstrationService,
        private confirmationService: ConfirmationService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private loadingService: LoadingService
    ) {}
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
        if (this.permissions.viewLockingAdmin) {
            this.loadingService.startFetchingList();
            this.lockingAdminService.getAllLocking$
                .pipe(
                    take(1),
                    map((res) => {
                        return res?.payload?.lockingList;
                    })
                )
                .subscribe(
                    (res) => {
                        this.tableLockingList = res.map((locking) => {
                            return {
                                ...locking,
                                date: new Date(locking.date),
                            };
                        });
                        this.loadingService.endFetchingList();
                    },
                    (err) => {
                        this.tableLockingList = [];
                        this.loadingService.endFetchingList();
                    }
                );
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
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
}
