import {HttpHeaders} from '@angular/common/http';
import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {FileUpload} from 'primeng/fileupload';
import {Table} from 'primeng/table';
import {map} from 'rxjs/operators';
import {CallActivityAdminService} from 'src/app/core/service/administrator/call-activity-admin.service';
import {Defines} from 'src/app/shared/constants/defines';
import {CallActivityAdmin} from 'src/app/shared/models/call-activity-admin.model';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {environment} from 'src/environments/environment';

@Component({
    selector: 'app-activity-direction',
    templateUrl: './activity-direction.component.html',
    styleUrls: ['./activity-direction.component.scss'],
    providers: [ConfirmationService],
})
export class ActivityDirectionComponent implements OnInit {
    searchText: any;
    constructor(
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private callActivityAdminService: CallActivityAdminService,
        private toastrService: ToastService,
        private loadingService: LoadingService
    ) {}
    @Input() permissions: any;
    @Output() next = new EventEmitter<number>();
    loading: boolean = false;
    loading$ = this.callActivityAdminService.loading$;
    editMode: boolean = false;
    directionPopup: boolean = false;
    search = false;
    directionForm: FormGroup;
    directionsList: CallActivityAdmin[];
    editedDirection: CallActivityAdmin;
    isFetchingList$ = this.loadingService.fetching$;
    uploadPopup: boolean = false;
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
        this.initAddForm();
        this.getDirections();
    }
    getDirections() {
        this.loadingService.startFetchingList();
        this.callActivityAdminService.getCallActivities(0, 0).subscribe(
            (res) => {
                this.directionsList = res?.payload?.reasonActivities;
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.directionsList = [];
                this.loadingService.endFetchingList();
            }
        );
    }
    switchToAddMode() {
        this.editMode = false;
        this.initAddForm();
        this.directionPopup = true;
    }
    switchToEditMode(direction: CallActivityAdmin) {
        this.editMode = true;
        this.editedDirection = direction;
        this.initEditForm();
        this.directionPopup = true;
    }

    submitDirection() {
        this.directionPopup = false;
        let reqObj = {
            reasonActivity: {...this.directionForm.value},
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'CALL_ACTIVITY_ADMIN_PAGE ',
                footPrintDetails: [],
            },
        };
        if (this.editMode) {
            this.callActivityAdminService.updateActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(97).message);
                    this.updateSuccessUpdatedItem();
                }
            });
        } else {
            let reqObj = {
                reasonActivity: {...this.directionForm.value},
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'CALL_ACTIVITY_ADMIN_PAGE ',
                    footPrintDetails: [],
                },
            };
            this.callActivityAdminService.addActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(96).message);
                    this.getDirections();
                }
            });
        }
    }
    initAddForm() {
        this.directionForm = this.fb.group({
            activityId: [null],
            activityName: [null, [Validators.required]],
            activityType: [Defines.ACTIVITY_TYPES.DIRECTION],
            parentId: [0],
        });
    }
    initEditForm() {
        this.directionForm = this.fb.group({
            activityId: [this.editedDirection.activityId],
            activityName: [this.editedDirection.activityName, [Validators.required]],
            activityType: [this.editedDirection.activityType],
            parentId: [this.editedDirection.parentId],
        });
    }
    hideDialog() {
        this.directionPopup = false;
    }
    toNextPage(directionId: number) {
        this.next.emit(directionId);
    }
    confirmDelete(directionId: number, index: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(92).message,
            accept: () => {
                this.deleteDirection(directionId, index);
            },
        });
    }
    deleteDirection(directionId: number, index: number) {
        let reqObj = {
            activityId: directionId,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'CALL_ACTIVITY_ADMIN_PAGE ',
                footPrintDetails: [],
            },
        };
        this.callActivityAdminService.deleteActivity(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(98).message);
                this.directionsList.splice(index, 1);
            }
        });
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
    updateSuccessUpdatedItem() {
        const updatedDirectionIndex = this.directionsList.findIndex(
            (direction) => direction.activityId === this.editedDirection.activityId
        );
        this.directionsList[updatedDirectionIndex] = this.directionForm.value;
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
}
