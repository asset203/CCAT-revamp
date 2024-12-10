import {Component, OnInit, ViewChild} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {Observable} from 'rxjs';
import {map, take, tap} from 'rxjs/operators';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';

import {Profile} from 'src/app/shared/models/profile';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';

import {MessageService} from 'src/app/shared/services/message.service';

@Component({
    selector: 'app-user-profiles',
    templateUrl: './user-profiles.component.html',
    styleUrls: ['./user-profiles.component.scss'],
    providers: [ConfirmationService],
})
export class UserProfilesComponent implements OnInit {
    selectedUsers;
    filterSearch;
    search = false;
    tableProfiles: Profile[];
    profiles: Profile[];
    loading$ = this.profileService.loading$;
    permissions = {
        getUserProfile: false,
        addProfile: false,
        deleteProfile: false,
        updateProfile: false,
    };
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private profileService: ProfileService,
        private toastrService: ToastrService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private footPrintService: FootPrintService,
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
        if (this.permissions.getUserProfile) {
            this.loadingService.startFetchingList();
            this.profileService.allProfiles$
                .pipe(
                    map((response) => {
                        return response?.payload?.profilesList;
                    })
                )
                .subscribe(
                    (profiles) => {
                        this.profiles = profiles;
                        this.tableProfiles = profiles;
                        this.loadingService.endFetchingList();
                    },
                    (err) => {
                        this.profiles = [];
                        this.tableProfiles = [];
                        this.loadingService.endFetchingList();
                    }
                );
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'User Profiles',
        };
        this.footPrintService.log(footprintObj);
    }

    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.profiles.filter((profile) =>
                profile.profileName.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tableProfiles = filteredTable;
        } else {
            this.tableProfiles = this.profiles;
        }
    }
    deleteProfile(profileId: number) {
        let reqObj = {
            profileId,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'User Profiles',
                footPrintDetails: [
                    {
                        paramName: 'Profile ID',
                        oldValue: null,
                        newValue: profileId,
                    },
                    {
                        paramName: 'Profile Name',
                        oldValue: null,
                        newValue: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    },
                ],
            },
        };
        this.profileService.deleteProfile(reqObj).subscribe((success) => {
            if (success.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(1).message);
                this.tableProfiles = this.deleteFromTable(this.tableProfiles, profileId);
                this.profiles = this.deleteFromTable(this.profiles, profileId);
            }
        });
    }
    confirmDeleteProfile(profileId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(2).message,
            accept: () => {
                this.deleteProfile(profileId);
            },
        });
    }
    deleteFromTable(profiles: Profile[], profileId: number) {
        return profiles.filter((profileItem) => profileItem.profileId !== profileId);
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(41, 'getUserProfiles')
            .set(44, 'addProfile')
            .set(42, 'deleteProfile')
            .set(43, 'updateProfile');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getUserProfile = this.featuresService.getPermissionValue(41);
        this.permissions.addProfile = this.featuresService.getPermissionValue(44);
        this.permissions.deleteProfile = this.featuresService.getPermissionValue(42);
        this.permissions.updateProfile = this.featuresService.getPermissionValue(43);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
}
