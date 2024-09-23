import {UserAccess} from './../../../../shared/models/UserAccess';
import {Profile} from './../../../../shared/models/profile';

import {ToastService} from 'src/app/shared/services/toast.service';
import {Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {saveAs} from 'file-saver';
import {map, take, tap} from 'rxjs/operators';
import {Observable} from 'rxjs';

import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {UserAccessService} from 'src/app/core/service/administrator/user-access.service';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {HttpHeaders, HttpClient} from '@angular/common/http';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {Table} from 'primeng/table';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {environment} from 'src/environments/environment';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FileUpload} from 'primeng/fileupload';
import {AppConfigService} from 'src/app/core/service/app-config.service';
import {LoadingService} from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-user-access',
    templateUrl: './user-access.component.html',
    styleUrls: ['./user-access.component.scss'],
    providers: [ConfirmationService],
})
export class UserAccessComponent implements OnInit {
    allProfiles$: Observable<Profile[]>;
    updateUserForm: FormGroup;
    addUserForm: FormGroup;
    userAccess$: Observable<UserAccess>;
    users: UserAccess[];
    tableUsers: UserAccess[];
    selectedUsers: [] = [];
    filterSearch;
    addUserDialog: boolean;
    updateUserDialog: boolean;
    uploadSheetDialog: boolean;
    uploadState = '1';
    uploadedFiles: any[] = [];
    loading = true;
    permissions = {
        getAllUsers: false,
        getUser: false,
        addUser: false,
        updateUser: false,
        deleteUser: false,
        uploadUser: false,
        exportUsers: false,
    };
    selectedUpdatedUser: UserAccess;
    profile;
    @ViewChild('inpAdd') inpAdd: ElementRef;
    @ViewChild('inpEdit') inpEdit: ElementRef;
    fileuploadLoading = false;
    search = false;
    searchText: string;
    isFetchingList$ = this.loadingService.fetching$;
    constructor(
        private userAccessService: UserAccessService,
        private toasterService: ToastService,
        private fb: FormBuilder,
        private confirmationService: ConfirmationService,
        private profileService: ProfileService,
        private http: HttpClient,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private validationService: ValidationService,
        private footPrintService: FootPrintService,
        private appConfigsService: AppConfigService,
        private loadingService: LoadingService
    ) {}

    ngOnInit(): void {
        // permission set
        this.setPermissions();
        if (this.permissions.getAllUsers) {
            this.getAllUsers();
        } else {
            this.loading = false;
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }

        this.initializeUpdateUserForm();
        this.initializeAddUserForm();

        this.allProfiles$ = this.profileService.allProfiles$.pipe(map((res) => res.payload.profilesList));

        this.setPermissions();
        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'User Access',
        };
        this.footPrintService.log(footprintObj);
    }

    exportExcel() {
        import('xlsx').then((xlsx) => {
            const worksheet = xlsx.utils.json_to_sheet(this.users);
            const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
            const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
            this.saveAsExcelFile(excelBuffer, 'users');
        });
    }
    exportSelectedExcel() {
        import('xlsx').then((xlsx) => {
            const worksheet = xlsx.utils.json_to_sheet(this.selectedUsers);
            const workbook = {Sheets: {data: worksheet}, SheetNames: ['data']};
            const excelBuffer: any = xlsx.write(workbook, {bookType: 'xlsx', type: 'array'});
            this.saveAsExcelFile(excelBuffer, 'users');
        });
    }
    saveAsExcelFile(buffer: any, fileName: string): void {
        let EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
        let EXCEL_EXTENSION = '.xlsx';
        const data: Blob = new Blob([buffer], {
            type: EXCEL_TYPE,
        });
        saveAs(data, fileName + '_export_' + new Date().getTime() + EXCEL_EXTENSION);
    }

    ShowAddUserDialog() {
        this.addUserDialog = true;
    }
    ShowUpdateUserDialog(id) {
        this.getUser(id);
        this.updateUserDialog = true;
    }
    ShowUploadSheetDialog() {
        this.uploadSheetDialog = true;
    }

    onUpload(event, file: FileUpload) {
        this.fileuploadLoading = true;
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
        // this.uploadedFiles[0].name.split(".").pop());
        fileFormData.append('operationType', this.uploadState);
        fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);

        const options = {
            headers: reqheaders,
            reportProgress: true,
        };
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/user/upload`, {
            method: 'POST',
            body: fileFormData,
        })
            .then((response) => {
                // footprint
                let footprintObj: FootPrint = {
                    machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'User Access',
                    footPrintDetails: [
                        {
                            paramName: 'fileName',
                            oldValue: null,
                            newValue: this.uploadedFiles[0].name,
                        },
                        {
                            paramName: 'operationType',
                            oldValue: null,
                            newValue: this.uploadState,
                        },
                    ],
                };
                this.footPrintService.log(footprintObj);
                response
                    .blob()
                    .then((blob) => {
                        var obj = window.URL.createObjectURL(blob);
                        const link = document.createElement('a');
                        link.href = obj;
                        link.setAttribute('download', `summary.csv`);
                        document.body.appendChild(link);
                        link.click();
                        document.body.removeChild(link);
                        window.URL.revokeObjectURL(obj);
                        this.toasterService.success('Success', 'uploaded successfully');
                        this.uploadSheetDialog = false;
                        file.clear();
                        this.fileuploadLoading = false;
                        this.getAllUsers();
                    })
                    .catch((err) => {
                        this.fileuploadLoading = false;
                        this.toasterService.error('Error', err);
                    });
            })
            .catch((err) => this.toasterService.error('Error', err));
    }

    filterTable() {
        if (this.filterSearch.length > 0) {
            const filteredTable = this.tableUsers.filter((user) =>
                user.ntAccount.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tableUsers = filteredTable;
        } else {
            this.tableUsers = this.users;
        }
    }

    initializeUpdateUserForm() {
        this.updateUserForm = this.fb.group({
            userId: [Number, Validators.required],
            profileId: [Number, Validators.required],
            resetSessionCounter: [Boolean, Validators.required],
            resetRebateLimit: [Boolean, Validators.required],
            resetDebitLimit: [Boolean, Validators.required],
            ntAccount: [String, Validators.required],
        });
    }
    initializeAddUserForm() {
        this.addUserForm = this.fb.group({
            profileId: [Number, Validators.required],
            ntAccount: ['', [Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]],
        });
    }

    getAllUsers() {
        this.loadingService.startFetchingList();
        this.userAccessService.allUsers$
            .pipe(
                take(1),
                map((res) => {
                    this.users = res?.payload?.users;
                    this.tableUsers = res?.payload?.users;
                    return res?.payload?.users;
                }),
                tap(() => (this.loading = false))
            )
            .subscribe(
                (res) => {
                    this.tableUsers = res;
                    this.users = res;
                    this.loadingService.endFetchingList();
                },
                (error) => {
                    this.tableUsers = [];
                    this.users = [];
                    this.loadingService.endFetchingList();
                }
            );
    }

    addUser() {
        let reqObj = {
            user: {...this.addUserForm.value},
            footprintModel: {
                machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'User Access',
                footPrintDetails: [
                    {
                        paramName: 'UserName',
                        oldValue: null,
                        newValue: this.addUserForm.value.ntAccount,
                    },
                    {
                        paramName: 'Profile ID',
                        oldValue: null,
                        newValue: this.addUserForm.value.profileId,
                    },
                    {
                        paramName: 'Profile Name',
                        oldValue: null,
                        newValue: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    },
                ],
            },
        };
        this.userAccessService
            .addUser$(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.addUserDialog = false;
                    this.getAllUsers();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(35).message);
                    }
                    this.addUserForm.reset();
                },
                error: (err) => {
                    this.addUserDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    getUser(id) {
        this.userAccess$ = this.userAccessService.getUser$(id).pipe(
            map((res) => {
                const user: UserAccess = res.payload.user;
                this.selectedUpdatedUser = user;
                this.updateUserForm.setValue({
                    userId: user.userId,
                    profileId: user.profileId,
                    resetSessionCounter: false,
                    resetRebateLimit: false,
                    resetDebitLimit: false,
                    ntAccount: user.ntAccount,
                });
                return user;
            })
        );
    }

    updateUser() {
        let reqObj = {
            user: {...this.updateUserForm.value},
            footprintModel: {
                machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'User Access',
                footPrintDetails: [
                    {
                        paramName: 'UserName',
                        oldValue: this.selectedUpdatedUser.ntAccount,
                        newValue: this.updateUserForm.value.ntAccount,
                    },
                    {
                        paramName: 'Profile ID',
                        oldValue: this.selectedUpdatedUser.profileId,
                        newValue: this.updateUserForm.value.profileId,
                    },
                ],
            },
        };
        this.userAccessService
            .updateUser$(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.updateUserDialog = false;
                    this.getAllUsers();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.updateUserForm.reset();
                },
                error: (err) => {
                    this.addUserDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deleteUser(id) {
        let reqObj = {
            userId: id,
            footprintModel: {
                machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'User Access',
                footPrintDetails: [
                    {
                        paramName: 'UserName',
                        oldValue: null,
                        newValue: this.selectedUpdatedUser.ntAccount,
                    },
                    {
                        paramName: 'Profile ID',
                        oldValue: null,
                        newValue: id,
                    },
                ],
            },
        };
        this.userAccessService
            .deleteUser$(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(36).message);
                        this.tableUsers = this.tableUsers.filter((user) => user.userId !== id);
                        this.getAllUsers();
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    confirm(id) {
        this.getUser(id);
        this.confirmationService.confirm({
            message: this.messageService.getMessage(37).message,
            accept: () => {
                this.deleteUser(id);
            },
        });
    }

    onFocus() {
        console.log(this.addUserForm.controls.profileId);
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(28, 'getAllUsers')
            .set(29, 'getUser')
            .set(30, 'AddUser')
            .set(31, 'updateUser')
            .set(32, 'deleteUser')
            .set(78, 'uploadUser')
            .set(79, 'exportUsers');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAllUsers = this.featuresService.getPermissionValue(28);
        this.permissions.getUser = this.featuresService.getPermissionValue(29);
        this.permissions.addUser = this.featuresService.getPermissionValue(30);
        this.permissions.updateUser = this.featuresService.getPermissionValue(31);
        this.permissions.deleteUser = this.featuresService.getPermissionValue(32);
        this.permissions.uploadUser = this.featuresService.getPermissionValue(78);
        this.permissions.uploadUser = this.featuresService.getPermissionValue(78);
        this.permissions.exportUsers = this.featuresService.getPermissionValue(79);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
    focusAdd() {
        this.inpAdd.nativeElement.focus();
    }
    focusEdit() {
        this.inpEdit.nativeElement.focus();
    }
}
