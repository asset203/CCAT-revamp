import {Component, OnInit} from '@angular/core';
import {MenuItem} from 'primeng/api';
import {FileUpload} from 'primeng/fileupload';
import {CallActivityAdminService} from 'src/app/core/service/administrator/call-activity-admin.service';
import { AppConfigService } from 'src/app/core/service/app-config.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {environment} from 'src/environments/environment';

@Component({
    selector: 'app-call-activity-adminstrator',
    templateUrl: './call-activity-adminstrator.component.html',
    styleUrls: ['./call-activity-adminstrator.component.scss'],
})
export class CallActivityAdminstratorComponent implements OnInit {
    constructor(
        private callActivityAdminService: CallActivityAdminService,
        private footPrintService: FootPrintService,
        private toastrService: ToastService,
        private featuresService: FeaturesService,
        private appConfigsService: AppConfigService
    ) {}
    loading$ = this.callActivityAdminService.loading$;
    items: MenuItem[];
    directionId: number;
    activeIndex = 0;
    familyId: number;
    reasonTypeId: number;
    uploadPopup: boolean = false;
    isFileInstallExist: boolean = false;
    loading: boolean = false;
    installedFile;
    permissions = {
        getAllReasons: false,
        uploadFile: false,
        downloadFile: false,
        updateReason: false,
        addReason: false,
        deleteReason: false,
    };
    ngOnInit(): void {
        this.setPermissions();
        this.items = [
            {
                label: 'Direction',
            },
            {
                label: 'Family',
            },
            {
                label: 'Reason Type',
            },
            {
                label: 'Reason',
            },
        ];

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'CALL_ACTIVITY_ADMIN_PAGE ',
        };
        this.footPrintService.log(footprintObj);
    }
    toFamlilyPage(id) {
        this.directionId = id;
        this.activeIndex++;
    }
    previousPage() {
        this.activeIndex--;
    }
    toReasonTypePage(familyId) {
        this.familyId = familyId;
        this.activeIndex++;
    }
    toReasonPage(reasonTypeId) {
        this.reasonTypeId = reasonTypeId;
        this.activeIndex++;
    }
    downloadCallActivity() {
        this.callActivityAdminService.downloadCallActivity().subscribe((res: any) => {
            const a = document.createElement('a');
            document.body.appendChild(a);
            const blob: any = new Blob([res], {type: 'octet/stream'});
            const url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = 'Download Call Activity.xlsx';
            a.click();
            window.URL.revokeObjectURL(url);
        });
    }
    clearUpload(fileUpload: FileUpload) {
        fileUpload.clear();
        this.isFileInstallExist = false;
        this.installedFile = null;
    }
    onUpload(event, fileUpload: FileUpload) {
        this.loading = true;
        this.installedFile = event.files[0];
        let file = event.files[0];
        let fileFormData: FormData = new FormData();
        fileFormData.append('fileName', file.name);
        fileFormData.append('file', file);
        fileFormData.append('fileExt', file.name.substring(file.name.lastIndexOf('.'), file.name.length));
        fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/call-activity-admin/upload`, {
            method: 'POST',
            body: fileFormData,
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data.statusCode === 0) {
                    this.toastrService.success('File Uploaded Successfully');
                    this.clearUpload(fileUpload);
                } else {
                    this.toastrService.error(data.statusMessage);
                }
                this.loading = false;
                this.uploadPopup = false;
            })
            .catch((error) => {
                this.loading = false;
                this.toastrService.error('Error', error.statusMessage);
                this.uploadPopup = false;
            });
    }
    setPermissions() {
        let flexSharePermission: Map<number, string> = new Map()
            .set(335, 'getAllReasons')
            .set(341, 'uploadFile')
            .set(340, 'downloadFile')
            .set(339, 'updateReason')
            .set(337, 'addReason')
            .set(338, 'deleteReason');
        this.featuresService.checkUserPermissions(flexSharePermission);
        this.permissions.getAllReasons = this.featuresService.getPermissionValue(335);
        this.permissions.uploadFile = this.featuresService.getPermissionValue(341);
        this.permissions.downloadFile = this.featuresService.getPermissionValue(340);
        this.permissions.updateReason = this.featuresService.getPermissionValue(339);
        this.permissions.addReason = this.featuresService.getPermissionValue(337);
        this.permissions.deleteReason = this.featuresService.getPermissionValue(338);
    }
}
