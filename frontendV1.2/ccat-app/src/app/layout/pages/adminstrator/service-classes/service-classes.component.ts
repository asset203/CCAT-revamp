import {ConfirmationService} from 'primeng/api';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ServiceClassesService} from './../../../../core/service/administrator/service-classes.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {take, map, tap} from 'rxjs/operators';
import {ServiceClass} from 'src/app/shared/models/service-class';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {saveAs} from 'file-saver';
import {HttpHeaders, JsonpClientBackend} from '@angular/common/http';
import {Table} from 'primeng/table';
import {environment} from 'src/environments/environment';
import {AppConfigService} from 'src/app/core/service/app-config.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
const baseURL = environment.url;
@Component({
    selector: 'app-service-classes',
    templateUrl: './service-classes.component.html',
    styleUrls: ['./service-classes.component.scss'],
    providers: [ConfirmationService],
})
export class ServiceClassesComponent implements OnInit {
    classes: ServiceClass[];
    selectedUsers: [];
    filterSearch;
    tableClasses;
    exportLoading = false;
    uploadLoading = false;
    loading = true;
    permissions = {
        getAllServiceClass: false, //54
        getServiceClass: false, //55
        updateServiceClass: false, //56
        deleteServiceClass: false, //57
        eleigibleServiceClass: false, //58
        addServiceClass: false, //76
    };
    importSheetDialog = false;
    search = false;
    uploadedFiles: any[] = [];
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private serviceClasses: ServiceClassesService,
        private toasterService: ToastService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private appConfigsService: AppConfigService,
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
        if (this.permissions.getAllServiceClass) {
            this.loadingService.startFetchingList();
            this.serviceClasses.allServiceClasses$
                .pipe(
                    take(1),
                    map((res) => res?.payload?.serviceClasses),
                    tap(() => (this.loading = false))
                )
                .subscribe(
                    (res) => {
                        this.classes = res;
                        this.tableClasses = this.classes;
                        this.loadingService.endFetchingList();
                    },
                    (err) => {
                        this.classes = [];
                        this.tableClasses = [];
                        this.loadingService.endFetchingList();
                        this.loading = false;
                    }
                );
        } else {
            this.loading = false;
        }
    }

    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.classes.filter((classes) =>
                classes.name.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tableClasses = filteredTable;
        } else {
            this.tableClasses = this.classes;
        }
    }

    deleteClass(code) {
        this.serviceClasses
            .deleteClass$(code)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(51).message, 'Success');
                        this.tableClasses = this.tableClasses.filter((classes) => classes.code !== code);
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }
    confirm(code) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(40).message,
            accept: () => {
                this.deleteClass(code);
            },
        });
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(54, 'getAllServiceClass')
            .set(55, 'getServiceClass')
            .set(56, 'updateServiceClass')
            .set(57, 'deleteServiceClass')
            .set(58, 'eleigibleServiceClass')
            .set(76, 'addServiceClass');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAllServiceClass = this.featuresService.getPermissionValue(54);
        this.permissions.getServiceClass = this.featuresService.getPermissionValue(55);
        this.permissions.updateServiceClass = this.featuresService.getPermissionValue(56);
        this.permissions.deleteServiceClass = this.featuresService.getPermissionValue(57);
        this.permissions.eleigibleServiceClass = this.featuresService.getPermissionValue(58);
        this.permissions.addServiceClass = this.featuresService.getPermissionValue(76);
    }
    saveAsExcelFile(buffer: any, fileName: string, fileExtension): void {
        console.log(buffer);
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = `.${fileExtension}`;
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }

    exportServiceClass() {
        this.fetchExportServiceClass();
    }

    fetchExportServiceClass() {
        this.exportLoading = true;
        let data = {
            token: JSON.parse(sessionStorage.getItem('session')).token,
        };
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/admin/service-classes/export`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data),
        })
            .then((response) => {
                return response.arrayBuffer();
            })
            .then((result) => {
                this.saveAsExcelFile(result, 'serviceClass', 'xlsx');
                this.exportLoading = false;
            })
            .catch((err) => {
                this.exportLoading = false;
            });
    }
    importServiceClass() {
        this.importSheetDialog = true;
    }
    onImport(event) {
        this.importSheetDialog = false;
        this.uploadLoading = true;
        let fileFormData = new FormData();
        let reqheaders = new HttpHeaders();
        for (let file of event.files) {
            this.uploadedFiles.push(file);
        }
        fileFormData.append('file', this.uploadedFiles[0]);
        fileFormData.append('fileName', this.uploadedFiles[0].name);
        fileFormData.append(
            'fileExt',
            this.uploadedFiles[0].name.substring(
                this.uploadedFiles[0].name.lastIndexOf('.'),
                this.uploadedFiles[0].name.length
            )
        );
        fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);

        // handeling request
        const options = {
            headers: {
                'Content-Type': 'multipart/form-data',
            },
            reportProgress: true,
        };
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/admin/service-classes/import`, {
            method: 'POST',
            body: fileFormData,
        })
            .then((response: any) => {
                this.toasterService.success('Success', 'Imported Successfully');
                this.importSheetDialog = false;
                console.log(response);
                return response.arrayBuffer();
            })
            .then((result) => {
                this.saveAsExcelFile(result, 'Imported-serviceClass', 'csv');
                this.uploadLoading = false;
            })
            .catch((err) => {
                this.uploadLoading = false;
            });
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
}
