import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {CallActivityAdminService} from 'src/app/core/service/administrator/call-activity-admin.service';
import {Defines} from 'src/app/shared/constants/defines';
import {CallActivityAdmin} from 'src/app/shared/models/call-activity-admin.model';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-activity-reason',
    templateUrl: './activity-reason.component.html',
    styleUrls: ['./activity-reason.component.scss'],
    providers: [ConfirmationService],
})
export class ActivityReasonComponent implements OnInit {
    searchText: any;
    constructor(
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private callActivityAdminService: CallActivityAdminService,
        private loadingService: LoadingService
    ) {}
    @Output() previous = new EventEmitter<void>();
    @Input() permissions: any;
    @Input() reasonTypeId: number;
    reasonsList: CallActivityAdmin[];
    editedReason: CallActivityAdmin;
    reasonForm: FormGroup;
    editMode: boolean = false;
    reasonPopup: boolean = false;
    loading$ = this.callActivityAdminService.loading$;
    search = false;
    isFetchingList$ = this.loadingService.fetching$;
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
        this.initAddReasonForm();
        this.getReasons();
    }
    getReasons() {
        this.loadingService.startFetchingList();
        this.callActivityAdminService.getCallActivities(Defines.ACTIVITY_TYPES.REASON, this.reasonTypeId).subscribe(
            (res) => {
                this.loadingService.endFetchingList();
                this.reasonsList = res?.payload?.reasonActivities;
            },
            (err) => {
                this.loadingService.endFetchingList();
                this.reasonsList = [];
            }
        );
    }
    previousPage() {
        this.previous.emit();
    }
    initAddReasonForm() {
        this.reasonForm = this.fb.group({
            activityId: [null],
            activityName: [null, [Validators.required]],
            activityType: [Defines.ACTIVITY_TYPES.REASON],
            parentId: [this.reasonTypeId],
        });
    }
    initEditReasonForm() {
        this.reasonForm = this.fb.group({
            activityId: [this.editedReason.activityId],
            activityName: [this.editedReason.activityName, [Validators.required]],
            activityType: [this.editedReason.activityType],
            parentId: [this.editedReason.parentId],
        });
    }
    switchToAddReason() {
        this.editMode = false;
        this.initAddReasonForm();
        this.reasonPopup = true;
    }
    switchToEditReason(reason: CallActivityAdmin) {
        this.editMode = true;
        this.editedReason = reason;
        this.initEditReasonForm();
        this.reasonPopup = true;
    }
    confirmDelete(reasonId: number, index: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(95).message,
            accept: () => {
                this.deleteReason(reasonId, index);
            },
        });
    }
    deleteReason(reasonId: number, index: number) {
        const reqObj = {activityId: reasonId};
        this.callActivityAdminService.deleteActivity(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(107).message);
                this.reasonsList.splice(index, 1);
            }
        });
    }
    submitReason() {
        const reqObj = {reasonActivity: {...this.reasonForm.value}};
        this.hideDialog();
        if (this.editMode) {
            this.callActivityAdminService.updateActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(106).message);
                    this.updateSuccessUpdatedItem();
                }
            });
        } else {
            this.callActivityAdminService.addActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(105).message);
                    this.getReasons();
                }
            });
        }
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
    hideDialog() {
        this.reasonPopup = false;
    }
    updateSuccessUpdatedItem() {
        const updatedReasonIndex = this.reasonsList.findIndex(
            (direction) => direction.activityId === this.editedReason.activityId
        );
        this.reasonsList[updatedReasonIndex] = this.reasonForm.value;
    }
}
