import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {TransactionCode, TransactionType} from 'src/app/shared/models/transaction-admin.model';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class TransactionAdminService {
    constructor(private httpService: HttpService) {}
    private loading = new BehaviorSubject(false);
    get loading$() {
        return this.loading;
    }
    get transactionsTypes$() {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/type/get-all',
            })
            .pipe(
                take(1),
                indicate(this.loading),
                map((res) => res?.payload?.transactionTypes)
            );
    }
    get transactionsCodes$() {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/code/get-all',
            })
            .pipe(
                take(1),
                indicate(this.loading),
                map((res) => res?.payload?.transactionCodes)
            );
    }
    deleteTransactionCode(id: number) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/code/delete',
                payload: {
                    id,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    deleteTransactionType(id: number) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/type/delete',
                payload: {
                    id,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    addTransactionCode(transactionCode: TransactionCode) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/code/add',
                payload: {
                    ...transactionCode,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    editTransactionCode(transactionCode: TransactionCode) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/code/update',
                payload: {
                    ...transactionCode,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    addTransactionType(transactionType: TransactionType) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/type/add',
                payload: {
                    ...transactionType,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    updateTransactionType(transactionType: TransactionType) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/type/update',
                payload: {
                    ...transactionType,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    getLinkedTransactionCodes(typeId: number) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/link/get-all',
                payload: {
                    typeId,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    updateLinkTransaction(payload) {
        return this.httpService
            .request({
                path: '/ccat/transaction-admin/link/update',
                payload,
            })
            .pipe(take(1), indicate(this.loading));
    }
}
