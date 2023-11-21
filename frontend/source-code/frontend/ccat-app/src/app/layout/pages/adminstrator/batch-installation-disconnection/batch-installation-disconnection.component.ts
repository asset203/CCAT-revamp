import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs/operators';
import { ProfileService } from 'src/app/core/service/administrator/profile.service';
import * as FileSaver from 'file-saver';
import { FailedUploadError } from 'src/app/shared/models/failed-upload-error.model';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { AppConfigService } from 'src/app/core/service/app-config.service';

@Component({
    selector: 'app-batch-installation-disconnection',
    templateUrl: './batch-installation-disconnection.component.html',
    styleUrls: ['./batch-installation-disconnection.component.scss'],
})
export class BatchInstallationDisconnectionComponent implements OnInit {
    constructor(
        private profileService: ProfileService,
        private featuresService: FeaturesService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private footPrintService: FootPrintService,
        private appConfigsService : AppConfigService 
    ) { }
    failedInstallData: FailedUploadError;
    isFileInstallExist = false;
    failedDisconnectData: FailedUploadError;
    isFileDisconnectExist = false;
    tab = 'install';
    installedFile;
    disconnectedFile;
    noServiceClassPopup = false;
    noValidationPopup = false;
    selectedServiceClass=null;
    validate = true;
    loading = false;
    serviceClasses$ = this.profileService.servicesClasses$.pipe(map((res) => res.payload.serviceClasses));
    permissions = {
        batchInstall: false,
        batchDisconnect: false,
    };
    ngOnInit(): void {
        this.setPermissions();
        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Batch installation/Disconnection',
        }
        this.footPrintService.log(footprintObj)
    }
    switchTab(tab) {
        this.tab = tab;
    }
    onUploadInstall(event) {
        this.installedFile = event.files[0];
        if (this.selectedServiceClass) {
            this.loading = true;
            let file = event.files[0];
            let fileFormData = new FormData();
            fileFormData.append('fileName', file.name);
            fileFormData.append('file', file);
            fileFormData.append('fileExt', file.name.substring(file.name.lastIndexOf('.'), file.name.length));
            fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);
            fileFormData.append('serviceClassId', this.selectedServiceClass);
            fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/batch-install-disconnect/install`, {
                method: 'POST',
                body: fileFormData,
            })
                .then((response) => {
                    return response.json();
                })
                .then((data) => {
                    if (data.statusCode === 0) {
                        this.failedInstallData = data.payload;
                        this.isFileInstallExist = true;
                    } else {
                        this.toastrService.error('Error', data.statusMessage);
                    }
                    this.loading = false;
                    // footprint
                    let footprintObj: FootPrint = {
                        machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                        profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                        pageName: 'Batch installation/Disconnection',
                        footPrintDetails: [
                            {
                                paramName: 'ServiceClass',
                                oldValue: null,
                                newValue: this.selectedServiceClass
                            },
                            {
                                paramName: 'FileName',
                                oldValue: null,
                                newValue: file.name
                            }
                        ]
                    }
                    this.footPrintService.log(footprintObj)

                })
                .catch((error) => {
                    this.loading = false;
                    this.toastrService.error('Error', error.statusMessage);
                });
        } else {
            this.noServiceClassPopup = true;
        }
    }
    onUploadDiconnect(event) {
        this.loading = true;
        this.disconnectedFile = event.files[0];
        let file = event.files[0];
        let fileFormData = new FormData();
        fileFormData.append('fileName', file.name);
        fileFormData.append('file', file);
        fileFormData.append('fileExt', file.name.substring(file.name.lastIndexOf('.'), file.name.length));
        fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);
        fileFormData.append('validateDisconnection', this.validate ? "1" : "0")
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/batch-install-disconnect/disconnect`, {
            method: 'POST',
            body: fileFormData,
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data.statusCode === 0) {
                    this.failedDisconnectData = data.payload;
                    this.isFileDisconnectExist = true;

                } else {
                    this.toastrService.error('Error', data.statusMessage);
                }
                this.loading = false;
                // footprint
                let footprintObj: FootPrint = {
                    machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Batch installation/Disconnection',
                    footPrintDetails: [
                        {
                            paramName: 'validateDisconnection',
                            oldValue: null,
                            newValue: this.validate
                        },
                        {
                            paramName: 'FileName',
                            oldValue: null,
                            newValue: file.name
                        }
                    ]
                }
                this.footPrintService.log(footprintObj)
            })
            .catch((error) => {
                this.loading = false;
                this.toastrService.error('Error', error.statusMessage);
            });
    }
    exportExcel() {
        import('xlsx').then((xlsx) => {
            const worksheet = xlsx.utils.json_to_sheet(
                this.tab === 'install' ? this.failedInstallData.failedMsisdns : this.failedDisconnectData.failedMsisdns
            );
            const workbook = { Sheets: { data: worksheet }, SheetNames: ['data'] };
            const excelBuffer: any = xlsx.write(workbook, { bookType: 'xlsx', type: 'array' });
            this.saveAsExcelFile(
                excelBuffer,
                this.tab === 'install' ? 'batch-install-failedMsisdns' : 'batch-disconnect-failedMsisdns'
            );
        });
    }
    clearInstall() {
        this.failedInstallData = null;
        this.selectedServiceClass = null;
        this.isFileInstallExist = false;
        this.installedFile = null;
    }
    clearDisconnect() {
        this.failedDisconnectData = null;
        this.isFileDisconnectExist = false;
        this.disconnectedFile = null;
    }
    saveAsExcelFile(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.xlsx';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
    setPermissions() {
        let findBatchPermissions: Map<number, string> = new Map().set(132, 'batchConnect').set(133, 'batchDisconnect');
        this.featuresService.checkUserPermissions(findBatchPermissions);
        this.permissions.batchInstall = this.featuresService.getPermissionValue(132);
        this.permissions.batchDisconnect = this.featuresService.getPermissionValue(133);
    }
}
