import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {faL} from '@fortawesome/free-solid-svg-icons';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {CommunityAdminService} from 'src/app/core/service/administrator/community-admin.service';
import {Community} from 'src/app/shared/models/Community.model';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-community-admin',
    templateUrl: './community-admin.component.html',
    styleUrls: ['./community-admin.component.scss'],
    providers: [ConfirmationService],
})
export class CommunityAdminComponent implements OnInit {
    communityAdminList: Community[];
    addCommunityDialog = false;
    first = 0;
    isIdUnique = true;
    isDescUnique = true;
    loading$ = this.communityAdminService.loading;
    editMode = false;
    search = false;
    communityForm: FormGroup;
    editedCommunity: Community = {communityId: null, communityDescription: null};
    permissions = {
        getCommunityAdmin: false,
        updateCommunityAdmin: false,
        addCommunityAdmin: false,
        deleteCommunityAdmin: false,
    };
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private communityAdminService: CommunityAdminService,
        private fb: FormBuilder,
        private toastrService: ToastService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
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
        this.initAddForm();

        if (this.permissions.getCommunityAdmin) {
            this.loadingService.startFetchingList();
            this.communityAdminService.allCommunities$.subscribe(
                (res) => {
                    this.communityAdminList = res.payload.communitiesAdmin;
                    this.loadingService.endFetchingList();
                },
                (err) => {
                    this.loadingService.endFetchingList();
                    this.communityAdminList = [];
                }
            );
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    submitForm(currentCommunities: Community[]) {
        if (this.editMode) {
            this.communityAdminService.updateCommunityAdmin(this.communityForm.value).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(76).message);
                    this.communityAdminService.allCommunities$.subscribe((res) => {
                        this.communityAdminList = res.payload.communitiesAdmin;
                    });
                }
            });
        } else {
            this.communityAdminService.addCommunityAdmin(this.communityForm.value).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(75).message);
                    this.communityAdminList.unshift(this.communityForm.value);
                    this.communityAdminService.allCommunities$.subscribe((res) => {
                        this.communityAdminList = res.payload.communitiesAdmin;
                    });
                }
            });
        }
        this.addCommunityDialog = false;
        this.editedCommunity = null;
        this.addCommunityDialog = null;
    }
    switchToaddCommunity() {
        this.editedCommunity = null;
        this.initAddForm();
        this.editMode = false;
        this.addCommunityDialog = true;
    }
    switchToUpdateCommunity(community: Community) {
        this.editMode = true;
        this.editedCommunity = community;
        this.initEditForm();
        this.addCommunityDialog = true;
    }
    initEditForm() {
        this.communityForm = this.fb.group({
            communityId: [this.editedCommunity.communityId, [Validators.required]],
            communityDescription: [this.editedCommunity.communityDescription, [Validators.required]],
        });
    }
    initAddForm() {
        this.communityForm = this.fb.group({
            communityId: [null, [Validators.required]],
            communityDescription: [null, [Validators.required]],
        });
    }

    closeDialog() {
        this.addCommunityDialog = false;
    }
    cofirmDeleteCommunity(communityId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(78).message,
            accept: () => {
                this.deleteCommunityAdmin(communityId);
                this.communityAdminService.allCommunities$.subscribe((res) => {
                    this.communityAdminList = res.payload.communitiesAdmin;
                });
            },
        });
    }
    deleteCommunityAdmin(communityId: number) {
        this.communityAdminService.deleteCommunityAdmin(communityId).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(77).message);
                this.communityAdminList = this.communityAdminList.filter(
                    (community) => community.communityId !== communityId
                );
            }
        });
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
    checkUniqueId(event, communities: Community[]) {
        if (communities.find((el) => el.communityId == parseInt(event.target.value))) {
            this.isIdUnique = false;
        } else {
            this.isIdUnique = true;
        }
    }

    checkUniqueDesc(value, communities: Community[]) {
        let repeatedTimes = communities.filter((el) => el.communityDescription === value.target.value.trim());

        if (this.editMode) {
            if (repeatedTimes.length && repeatedTimes[0].communityId !== this.editedCommunity.communityId) {
                this.isDescUnique = false;
            } else {
                this.isDescUnique = true;
            }
        }
        if (!this.editMode)
            if (repeatedTimes.length) this.isDescUnique = false;
            else this.isDescUnique = true;
    }
    reset() {
        this.first = 0;
    }
    cancel() {
        this.addCommunityDialog = false;
        this.editedCommunity = null;
        this.addCommunityDialog = null;
        this.isIdUnique = true;
        this.isDescUnique = true;
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(311, 'getCommunityAdmin')
            .set(274, 'updateCommunityAdmin')
            .set(273, 'addCommunityAdmin')
            .set(275, 'deleteCommunityAdmin');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getCommunityAdmin = this.featuresService.getPermissionValue(311);
        this.permissions.updateCommunityAdmin = this.featuresService.getPermissionValue(274);
        this.permissions.addCommunityAdmin = this.featuresService.getPermissionValue(273);
        this.permissions.deleteCommunityAdmin = this.featuresService.getPermissionValue(275);
    }
}
