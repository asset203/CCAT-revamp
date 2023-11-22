import { Component, ElementRef, Input, OnInit, ViewChild } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { TransactionAdminService } from 'src/app/core/service/administrator/transaction-admin.service';
import { TransactionCode } from 'src/app/shared/models/transaction-admin.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-transaction-codes',
    templateUrl: './transaction-codes.component.html',
    styleUrls: ['./transaction-codes.component.scss'],
    providers: [ConfirmationService],
})
export class TransactionCodesComponent implements OnInit {
    searchText: any;
    constructor(
        private transactionAdminService: TransactionAdminService,
        private toastrService: ToastService,
        private messageService: MessageService,
        private confirmationService: ConfirmationService,
        private validationService: ValidationService
    ) { }
    @Input() permissions: any;
    @ViewChild('nameInput') nameInput: ElementRef;
    filterSearch;
    editedTransactionCode: TransactionCode;
    tableCodes: TransactionCode[];
    codes: TransactionCode[];
    openDialog: boolean = false;
    loading$ = this.transactionAdminService.loading$;
    editMode = false;
    codesForm: FormGroup;
    similarityError = false;
    similarityErrorMsg = '';

    ngOnInit(): void {
        this.getTransactionsCode();
        this.setAddForm();
    }
    checkAuthorized() {
        if (
            !this.permissions.getAllCodes &&
            !this.permissions.addCode &&
            !this.permissions.updateCode &&
            !this.permissions.deleteCode
        ) {
            return true;
        } else {
            return false;
        }
    }
    getTransactionsCode() {
        if (this.permissions.getAllCodes) {
            this.transactionAdminService.transactionsCodes$.subscribe((transactionCodes) => {
                this.tableCodes = transactionCodes;
                this.codes = transactionCodes;
            });
        } else {
            this.toastrService.error('Error', this.messageService.getMessage(401).message);
        }
    }
    deleteTransactionCode(id: number) {
        this.transactionAdminService.deleteTransactionCode(id).subscribe((resp) => {
            if (resp.statusCode === 0) {
                this.toastrService.success('Success', this.messageService.getMessage(24).message);
                this.getTransactionsCode();
            }
        });
    }
    confirmDeleteCode(id: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(25).message,
            accept: () => {
                this.deleteTransactionCode(id);
            },
        });
    }
    setAddForm() {
        this.codesForm = new FormGroup({
            name: new FormControl(null, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            value: new FormControl(null, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            description: new FormControl(null, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
        });
    }
    setEditForm() {
        this.codesForm = new FormGroup({
            name: new FormControl(this.editedTransactionCode.name, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            value: new FormControl(this.editedTransactionCode.value, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            description: new FormControl(this.editedTransactionCode.description, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            id: new FormControl(this.editedTransactionCode.id),
        });
    }
    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.codes.filter((code) =>
                code.name.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tableCodes = filteredTable;
        } else {
            this.tableCodes = this.codes;
        }
    }
    addTransactionCode() {
        this.editMode = false;
        this.setAddForm();
        this.openDialog = true;
    }
    updateTransactionCode(transactionCode: TransactionCode) {
        this.editedTransactionCode = transactionCode;
        this.editMode = true;
        this.setEditForm();
        this.openDialog = true;
    }
    submit() {

        if (!this.editMode) {
            if (
                this.codesForm.value.name.toLocaleLowerCase().trim() ===
                this.codesForm.value.value.toLocaleLowerCase().trim()
            ) {
                this.openDialog = false;
                this.toastrService.warning('Type and Value names should be different');
                return;
            }
            // check for value or type aren't duplicated
            else if (
                this.codes.find(
                    (el) => el.name.toLocaleLowerCase().trim() === this.codesForm.value.name.toLocaleLowerCase().trim()
                )
            ) {
                this.openDialog = false;
                this.toastrService.warning('Code Name Is Already Exist');
                return;
            } else if (
                this.codes.find(
                    (el) =>
                        el.value.toLocaleLowerCase().trim() === this.codesForm.value.value.toLocaleLowerCase().trim()
                )
            ) {
                this.openDialog = false;
                this.toastrService.warning('Code Value Is Already Exist');
                return;
            } else {
                this.similarityError = false;
                this.similarityErrorMsg = '';
                this.transactionAdminService.addTransactionCode(this.codesForm.value).subscribe((resp) => {
                    if (resp.statusCode === 0) {
                        this.toastrService.success('Success', this.messageService.getMessage(28).message);
                        this.getTransactionsCode();
                        this.openDialog = false;
                    }
                });
            }
            /*
             */
        } else {
            if (this.chechSimilrityEditMode()) {
                this.toastrService.error('Name is duplicated');
            } else {
                this.transactionAdminService.editTransactionCode(this.codesForm.value).subscribe((resp) => {
                    if (resp.statusCode === 0) {
                        this.toastrService.success('Success', this.messageService.getMessage(29).message);
                        this.getTransactionsCode();
                    }
                });
            }
        }
        this.openDialog = false;
    }
    chechSimilrityEditMode() {
        return this.codes.find(
            (el) =>
                el.name.toLocaleLowerCase().trim() === this.codesForm.value.name.toLocaleLowerCase().trim() &&
                this.codesForm.value.id !== el.id
        );
    }
    closeDialog() {
        this.openDialog = false;
        this.similarityError = false;
        this.similarityErrorMsg = '';
    }
    clear(table: Table) {
        if (table.filters.global["value"]) {
            table.filters.global["value"] = ''
        }
        this.searchText = null;
        table.clear()
    }
    focusNameInput() {
        this.nameInput.nativeElement.focus();
    }
}
