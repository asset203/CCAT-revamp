import { Component, OnInit } from '@angular/core';
import { TransactionAdminService } from 'src/app/core/service/administrator/transaction-admin.service';
import { TransactionCode } from 'src/app/shared/models/transaction-admin.model';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-transaction-links',
    templateUrl: './transaction-links.component.html',
    styleUrls: ['./transaction-links.component.scss'],
})
export class TransactionLinksComponent implements OnInit {
    constructor(
        private transactionAdminService: TransactionAdminService,
        private toastrService: ToastService,
        private messageService: MessageService
    ) { }
    transactionCodes$ = this.transactionAdminService.transactionsCodes$;
    transactionTypes$ = this.transactionAdminService.transactionsTypes$;
    selectedTypeID: number;
    selectedCodeID: number;
    selectedCode: TransactionCode;
    linkedCodes: TransactionCode[];
    linkedCodesTable: TransactionCode[];
    loading$ = this.transactionAdminService.loading$;
    unLinkMode: boolean;
    filterSearch;

    ngOnInit(): void { }
    onSelectTransactionType() {
        this.transactionAdminService.getLinkedTransactionCodes(this.selectedTypeID).subscribe((res) => {
            this.linkedCodes = res.payload.transactionCodes;
            this.linkedCodesTable = res.payload.transactionCodes;
        });
    }
    onSelectTransactionCode() {
        const isCodeisExist: boolean = this.linkedCodes.some((el) => el.id === this.selectedCodeID);
        this.unLinkMode = isCodeisExist;
    }
    linkOrUnlinkTransaction() {
        const reqPayload = {
            typeId: this.selectedTypeID,
            codeId: this.selectedCodeID,
            linkType: this.unLinkMode ? 0 : 1,
        };
        this.transactionAdminService.updateLinkTransaction(reqPayload).subscribe((resp) => {
            if (resp.statusCode === 0) {
                this.toastrService.success('Success', this.messageService.getMessage((this.unLinkMode) ? 32 : 58).message);
                this.selectedCodeID = null;
                this.onSelectTransactionType();
            }

        });
    }
    addRemoveLinkedCodeTable(id: number) { }
    filterTable() {
        if (this.filterSearch) {
            const filteredTable = this.linkedCodes.filter((code) =>
                code.name.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.linkedCodesTable = filteredTable;
        } else {
            this.linkedCodesTable = this.linkedCodes;
        }
    }
}
