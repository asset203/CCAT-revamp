import {Component, EventEmitter, Input, OnInit, Output, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {CallActivityAdminService} from 'src/app/core/service/administrator/call-activity-admin.service';
import {Defines} from 'src/app/shared/constants/defines';
import {CallActivityAdmin, CallActivityAdminFamily} from 'src/app/shared/models/call-activity-admin.model';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-activity-family',
    templateUrl: './activity-family.component.html',
    styleUrls: ['./activity-family.component.scss'],
    providers: [ConfirmationService],
})
export class ActivityFamilyComponent implements OnInit {
    searchText: any;
    constructor(
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private callActivityAdminService: CallActivityAdminService,
        private toastrService: ToastService,
        private loadingService: LoadingService
    ) {}
    @Input() directionId: number;
    @Input() permissions: any;
    @Output() previous = new EventEmitter<void>();
    @Output() next = new EventEmitter<number>();
    loading$ = this.callActivityAdminService.loading$;
    editMode: boolean = false;
    familyPopup: boolean = false;
    familyForm: FormGroup;
    familyList: CallActivityAdmin[];
    editedFamily: CallActivityAdmin;
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
        console.log(this.directionId);
        this.initAddForm();
        this.getFamilies();
    }
    getFamilies() {
        this.loadingService.startFetchingList();
        this.callActivityAdminService.getCallActivities(Defines.ACTIVITY_TYPES.FAMILY, this.directionId).subscribe(
            (res) => {
                this.familyList = res?.payload?.reasonActivities;
                this.familyList = this.familyList ? this.familyList : [];
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.loadingService.endFetchingList();
                this.familyList = [];
            }
        );
    }
    previousPage() {
        this.previous.emit();
    }
    initAddForm() {
        this.familyForm = this.fb.group({
            activityId: [null],
            activityName: [null, [Validators.required]],
            activityType: [Defines.ACTIVITY_TYPES.FAMILY],
            parentId: [this.directionId],
        });
    }
    initEditForm() {
        this.familyForm = this.fb.group({
            activityId: [this.editedFamily.activityId],
            activityName: [this.editedFamily.activityName, [Validators.required]],
            activityType: [this.editedFamily.activityType],
            parentId: [this.editedFamily.parentId],
        });
    }
    switchTaAddFamily() {
        this.editMode = false;
        this.initAddForm();
        this.familyPopup = true;
    }
    switchToEditFamily(family: CallActivityAdmin) {
        this.editMode = true;
        this.editedFamily = family;
        this.initEditForm();
        this.familyPopup = true;
    }
    submitFamily() {
        const reqObj = {reasonActivity: {...this.familyForm.value}};
        this.familyPopup = false;
        if (this.editMode) {
            this.callActivityAdminService.updateActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(100).message);
                    this.updateSuccessUpdatedItem();
                }
            });
        } else {
            this.callActivityAdminService.addActivity(reqObj).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(99).message);
                    this.getFamilies();
                }
            });
        }
    }
    confirmDelete(familyId: number, index: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(93).message,
            accept: () => {
                this.deleteFamily(familyId, index);
            },
        });
    }
    deleteFamily(familyId: number, index: number) {
        const reqObj = {activityId: familyId};
        this.callActivityAdminService.deleteActivity(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(101).message);
                this.familyList.splice(index, 1);
            }
        });
    }
    toNextPage(familyId: number) {
        this.next.emit(familyId);
    }
    hideDialog() {
        this.familyPopup = false;
    }
    updateSuccessUpdatedItem() {
        const updatedFamilyIndex = this.familyList.findIndex(
            (family) => family.activityId === this.editedFamily.activityId
        );
        this.familyList[updatedFamilyIndex] = this.familyForm.value;
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
}
