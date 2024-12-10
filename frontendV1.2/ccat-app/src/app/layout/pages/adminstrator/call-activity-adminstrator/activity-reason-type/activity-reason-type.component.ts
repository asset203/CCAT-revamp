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
    selector: 'app-activity-reason-type',
    templateUrl: './activity-reason-type.component.html',
    styleUrls: ['./activity-reason-type.component.scss'],
    providers: [ConfirmationService],
})
export class ActivityReasonTypeComponent implements OnInit {
    searchText: any;
    constructor(
        private fb: FormBuilder,
        private callActivityAdminService: CallActivityAdminService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toastrService: ToastService,
        private loadingService: LoadingService
    ) {}
    @Input() familyId: number;
    @Input() permissions: any;
    @Output() previous = new EventEmitter<void>();
    @Output() next = new EventEmitter<number>();
    reasonTypesList: CallActivityAdmin[];
    loading$ = this.callActivityAdminService.loading$;
    search = false;
    reasonTypeForm: FormGroup;
    editedReasonType: CallActivityAdmin;
    reasonTypePopup: boolean = false;
    editMode: boolean = false;
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
        this.initAddReasonTypeForm();
        this.getReasonTypes();
    }
    previousPage() {
        this.previous.emit();
    }
    initAddReasonTypeForm() {
        this.reasonTypeForm = this.fb.group({
            activityId: [null],
            activityName: [null, [Validators.required]],
            activityType: [Defines.ACTIVITY_TYPES.REASON_TYPE],
            parentId: [this.familyId],
        });
    }
    initEditReasonTypeForm() {
        this.reasonTypeForm = this.fb.group({
            activityId: [this.editedReasonType.activityId],
            activityName: [this.editedReasonType.activityName, [Validators.required]],
            activityType: [this.editedReasonType.activityType],
            parentId: [this.editedReasonType.parentId],
        });
    }
    switchToAddReasonType() {
        this.editMode = false;
        this.initAddReasonTypeForm();
        this.reasonTypePopup = true;
    }
    switchToEditReasonType(reasonType: CallActivityAdmin) {
        this.editMode = true;
        this.editedReasonType = reasonType;
        this.initEditReasonTypeForm();
        this.reasonTypePopup = true;
    }
    getReasonTypes() {
        this.loadingService.startFetchingList();
        this.callActivityAdminService.getCallActivities(Defines.ACTIVITY_TYPES.REASON_TYPE, this.familyId).subscribe(
            (res) => {
                this.reasonTypesList = res?.payload?.reasonActivities;
                this.reasonTypesList = this.reasonTypesList ? this.reasonTypesList : [];
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.reasonTypesList = [];
                this.loadingService.endFetchingList();
            }
        );
    }
    confirmDelete(reasonTypeId: number, index: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(94).message,
            accept: () => {
                this.deleteReasonType(reasonTypeId, index);
            },
        });
    }
    deleteReasonType(reasonTypeId: number, index: number) {
        const reqObj = {activityId: reasonTypeId};
        this.callActivityAdminService.deleteActivity(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(104).message);
                this.reasonTypesList.splice(index, 1);
            }
        });
    }
    toNextPage(reasonTypeId: number) {
        this.next.emit(reasonTypeId);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
    submitReasonType() {
        const reqObj = {reasonActivity: {...this.reasonTypeForm.value}};
        this.hideDialog();
        if (this.editMode) {
            this.callActivityAdminService.updateActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(103).message);
                    this.updateSuccessUpdatedItem();
                }
            });
        } else {
            this.callActivityAdminService.addActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(102).message);
                    this.getReasonTypes();
                }
            });
        }
    }
    hideDialog() {
        this.reasonTypePopup = false;
    }
    updateSuccessUpdatedItem() {
        const updatedReasonTypeIndex = this.reasonTypesList.findIndex(
            (direction) => direction.activityId === this.editedReasonType.activityId
        );
        this.reasonTypesList[updatedReasonTypeIndex] = this.reasonTypeForm.value;
    }
}
