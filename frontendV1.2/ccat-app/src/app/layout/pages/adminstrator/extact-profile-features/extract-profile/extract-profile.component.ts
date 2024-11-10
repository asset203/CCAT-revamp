import {HttpHeaders} from '@angular/common/http';
import {Component, Input, OnInit, OnChanges, SimpleChanges, ViewChild} from '@angular/core';
import * as FileSaver from 'file-saver';
import {Table} from 'primeng/table';
import {map} from 'rxjs/operators';
import {ExtractProfileFeaturesService} from 'src/app/core/service/administrator/extract-profile-features.service';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {environment} from 'src/environments/environment';
@Component({
    selector: 'app-extract-profile',
    templateUrl: './extract-profile.component.html',
    styleUrls: ['./extract-profile.component.scss'],
})
export class ExtractProfileComponent implements OnInit, OnChanges {
    @Input() profileId: any;
    profiles$ = this.profileService.allProfiles$.pipe(map((res) => res?.payload?.profilesList));
    loading$ = this.profileService.loading$;
    loadingProfileFeatures$ = this.extractProfileFeaturesService.loading$;
    profileUsers = [];
    tableUsers = [];
    search = false;
    permissions = {
        extractUsersProfiles: false,
    };
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private profileService: ProfileService,
        private extractProfileFeaturesService: ExtractProfileFeaturesService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private loadingService: LoadingService
    ) {}
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    ngOnChanges(changes: SimpleChanges): void {
        const profileId = changes?.profileId?.currentValue;
        if (profileId) this.getUsers(profileId);
    }

    ngOnInit(): void {
        this.setPermissions();
    }
    getUsers(profileId: number) {
        this.profileUsers = null;
        this.tableUsers = null;
        this.loadingService.startFetchingList();
        this.extractProfileFeaturesService.getAllProfileUsers(profileId).subscribe(
            (res) => {
                this.tableUsers = res?.payload?.profileUsers;
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.tableUsers = [];
                this.loadingService.endFetchingList();
            }
        );
    }
    exportExcel(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.xlsx';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        FileSaver.saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }
    extractProfileCSV() {
        import('xlsx').then((xlsx) => {
            const worksheet = xlsx.utils.json_to_sheet(this.tableUsers);
            const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
            const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
            this.exportExcel(excelBuffer, 'profile-users');
        });
    }
    exportProfilesUsers() {
        this.extractProfileFeaturesService.downloadProfileUsers().subscribe((res) => {
            const a = document.createElement('a');
            document.body.appendChild(a);
            const blob: any = new Blob([res], {type: 'octet/stream'});
            const url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = 'Profiles Users.csv';
            a.click();
            window.URL.revokeObjectURL(url);
        });
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(322, 'extractUsersProfiles');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.extractUsersProfiles = this.featuresService.getPermissionValue(322);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
}
