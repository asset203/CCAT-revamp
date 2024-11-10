import {Component, OnInit, ViewChild} from '@angular/core';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {Observable, of} from 'rxjs';
import {DisconnectionCodesService} from 'src/app/core/service/administrator/disconnections-code.service';
import {DisconnectionCode} from 'src/app/shared/models/disconnection-code.model';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-disconnection-codes',
    templateUrl: './disconnection-codes.component.html',
    styleUrls: ['./disconnection-codes.component.scss'],
    providers: [ConfirmationService],
})
export class DisconnectionCodesComponent implements OnInit {
    searchText: any;
    constructor(
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private disconnCodesService: DisconnectionCodesService,
        private toastrService: ToastService,
        private featuresService: FeaturesService,
        private loadingService: LoadingService
    ) {}
    disconnModal: boolean = false;
    editMode: boolean = false;
    filterSearch;
    tableUsers: DisconnectionCode[];
    disconnCodesList: DisconnectionCode[];
    editedDisconnCode: DisconnectionCode;
    loading$ = this.disconnCodesService.loading$;
    search = false;
    permissions = {
        getAllCode: false,
        addCode: false,
        updateCode: false,
        deleteCode: false,
    };
    isFetchingList$ = this.loadingService.fetching$;
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    ngOnInit(): void {
        this.setPermissions();
        this.getAllCodes();
    }
    getAllCodes() {
        if (this.permissions.getAllCode) {
            this.loadingService.startFetchingList();
            this.disconnCodesService.getAllDisconnections().subscribe(
                (disconnCodes) => {
                    this.tableUsers = disconnCodes;
                    this.disconnCodesList = disconnCodes;
                    this.loadingService.endFetchingList();
                },
                (err) => {
                    this.tableUsers = [];
                    this.disconnCodesList = [];
                    this.loadingService.endFetchingList();
                }
            );
        } else {
            this.toastrService.error('Error', this.messageService.getMessage(401).message);
        }
    }
    openAddModal() {
        this.editMode = false;
        this.disconnModal = true;
    }
    hideModal() {
        this.disconnModal = false;
        this.editedDisconnCode = null;
    }
    editDisconnCode(disconnCode: DisconnectionCode) {
        this.disconnModal = true;
        this.editedDisconnCode = disconnCode;
        this.editMode = true;
    }
    confirmDelete(id: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(20).message,
            accept: () => {
                this.deleteDisconnCode(id);
            },
        });
    }
    deleteDisconnCode(id: number) {
        const requestPayload: DisconnectionCode = {id};
        this.disconnCodesService.deleteDisconnectionCode(requestPayload).subscribe((success) => {
            if (success.statusCode === 0) {
                this.toastrService.success('Success', this.messageService.getMessage(23).message);
                this.getAllCodes();
            }
        });
    }

    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.disconnCodesList.filter((user) =>
                user.description.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tableUsers = filteredTable;
        } else {
            this.tableUsers = this.disconnCodesList;
        }
    }
    onSubmitFrom(disconnCode: DisconnectionCode) {
        this.hideModal();
        if (!this.editMode) {
            this.disconnCodesService.addDisconnectionCode(disconnCode).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastrService.success('Success', this.messageService.getMessage(21).message);
                    this.getAllCodes();
                }
            });
        } else {
            this.disconnCodesService.updateDisconnectionCode(disconnCode).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastrService.success('Success', this.messageService.getMessage(22).message);
                    this.getAllCodes();
                }
            });
        }
    }
    setPermissions() {
        let findDisconnCodesPermssions: Map<number, string> = new Map()
            .set(121, 'getAllCode')
            .set(122, 'addCode')
            .set(123, 'updateCode')
            .set(124, 'deleteCode');
        this.featuresService.checkUserPermissions(findDisconnCodesPermssions);
        this.permissions.getAllCode = this.featuresService.getPermissionValue(121);
        this.permissions.addCode = this.featuresService.getPermissionValue(122);
        this.permissions.updateCode = this.featuresService.getPermissionValue(123);
        this.permissions.deleteCode = this.featuresService.getPermissionValue(124);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
}
