import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { AccountGroupsService } from 'src/app/core/service/administrator/account-groups.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { AccountGroup } from 'src/app/shared/models/AccountGroup.interface';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-account-groups',
    templateUrl: './account-groups.component.html',
    styleUrls: ['./account-groups.component.scss'],
    providers: [ConfirmationService],
})
export class AccountGroupsComponent implements OnInit {
    addAccountGroupDialog = false;
    accountGroupsList: AccountGroup[] = [];
    loading$ = this.accountGroupsService.loading;
    accountGroupForm: FormGroup;
    editMode = false;
    isIdUnique = true;
    isDescUnique = true;
    search = false;
    editedAccountGroup: AccountGroup;
    permissions = {
        getAccountGroup: false,
        updateAccountGroup: false,
        addAccountGroup: false,
        deleteAccountGroup: false,
    };
    searchText: any;
    constructor(
        private accountGroupsService: AccountGroupsService,
        private fb: FormBuilder,
        private messageService: MessageService,
        private toastrService: ToastService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService

    ) { }

    ngOnInit(): void {
        this.setPermissions();
        this.initAddForm();
        if (this.permissions.getAccountGroup) {
            this.accountGroupsService.allAccountGroups$.subscribe((res) => {
                this.accountGroupsList = res?.payload?.accountGroups;
            });
        }
        else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Account Groups'
        }
        this.footPrintService.log(footprintObj)
    }
    initAddForm() {
        this.accountGroupForm = this.fb.group({
            accountGroupId: [null, [Validators.required]],
            accountGroupDescription: [null, [Validators.required]],
        });
    }
    initEditForm() {
        this.accountGroupForm = this.fb.group({
            accountGroupId: [this.editedAccountGroup.accountGroupId, [Validators.required]],
            accountGroupDescription: [this.editedAccountGroup.accountGroupDescription, [Validators.required]],
        });
    }
    switchToAddAccount() {
        this.editMode = false;
        this.editedAccountGroup = null;
        this.initAddForm();
        this.addAccountGroupDialog = true;
    }
    switchToEditAccount(editedAccount: AccountGroup) {
        this.editMode = true;
        this.editedAccountGroup = editedAccount;
        this.initEditForm();
        this.addAccountGroupDialog = true;
    }
    submitForm(currentAccountGroups: AccountGroup[]) {
        this.addAccountGroupDialog = false;
        if (!this.editMode) {
            this.accountGroupsService.addAccountGroup(this.accountGroupForm.value, currentAccountGroups).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(79).message);
                    this.accountGroupsList.unshift(this.accountGroupForm.value);
                }
            });
        } else {

            let reqObj = {
                updatedAccountGroup: { ...this.accountGroupForm.value },
                footPrint: {
                    machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Account Groups',
                    footPrintDetails: [{
                        paramName: 'Account Group ID',
                        oldValue: null,
                        newValue: this.accountGroupForm.value.aaccountGroupId
                    }]
                }
            }
            this.accountGroupsService.updateAccountGroup(reqObj, currentAccountGroups).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success(this.messageService.getMessage(80).message);
                    if (this.permissions.getAccountGroup) {
                        this.accountGroupsService.allAccountGroups$.subscribe((res) => {
                            this.accountGroupsList = res?.payload?.accountGroups;
                        });
                    }
                }
            });
        }
        this.editedAccountGroup = null;
        this.addAccountGroupDialog = null;
    }
    confirmDeleteAccount(accountGroupId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(82).message,
            accept: () => {
                this.deleteAccountGroup(accountGroupId);
            },
        });
    }
    cancel() {
        this.addAccountGroupDialog = false;
        this.editedAccountGroup = null;
        this.addAccountGroupDialog = null;

    }
    deleteAccountGroup(accountGroupId: number) {
        let reqObj = {
            accountGroupId: accountGroupId,
            footPrint: {
                machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Account Groups',
                footPrintDetails: [{
                    paramName: 'Account Group ID',
                    oldValue: null,
                    newValue: accountGroupId
                }]
            }
        }
        this.accountGroupsService.deleteAccountGroup(reqObj).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(81).message);
                this.accountGroupsList = this.accountGroupsList.filter(
                    (account) => account.accountGroupId !== accountGroupId
                );
            }
        });
    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()
    }


    checkUniqueId(event, accountGroups: AccountGroup[]) {

        console.log(accountGroups.find(el => el.accountGroupId === parseInt(event.target.value)));

        if (accountGroups.find(el => el.accountGroupId == parseInt(event.target.value))) {
            this.isIdUnique = false;
        } else {
            this.isIdUnique = true;
        }
    }

    checkUniqueDesc(value, accountGroups: AccountGroup[]) {


        let repeatedTimes = accountGroups.filter(el => el.accountGroupDescription === value.target.value);

        if (this.editMode) {
            if ((repeatedTimes.length && repeatedTimes[0].accountGroupId !== this.editedAccountGroup.accountGroupId)) {
                this.isDescUnique = false;
            } else {
                this.isDescUnique = true;
            }
        }
        if (!this.editMode)
            if (repeatedTimes.length)
                this.isDescUnique = false; else this.isDescUnique = true;


    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(278, 'getAccountGroup')
            .set(280, 'updateAccountGroup')
            .set(279, 'addAccountGroup')
            .set(281, 'deleteAccountGroup')
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAccountGroup = this.featuresService.getPermissionValue(278);
        this.permissions.updateAccountGroup = this.featuresService.getPermissionValue(280);
        this.permissions.addAccountGroup = this.featuresService.getPermissionValue(279);
        this.permissions.deleteAccountGroup = this.featuresService.getPermissionValue(281);
    }
}
