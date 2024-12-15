import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {map, switchMap} from 'rxjs/operators';
import {HttpService} from '../http.service';
import {SubscriberService} from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class ScratchCardsService {
    constructor(private http: HttpService, private subscriberService: SubscriberService) {}

    getVoucherDetails$(voucherSerialNumber, serverId): Observable<any> {
        return this.http.request({
            path: '/ccat/voucher/get',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                voucherSerialNumber,
                serverId,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Scratch Cards',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    tabName: 'View Voucher Details',
                    footPrintDetails: [
                        {
                            paramName: 'voucherSerialNumber',
                            oldValue: null,
                            newValue: voucherSerialNumber,
                        },
                        {
                            paramName: 'serverId',
                            oldValue: null,
                            newValue: serverId,
                        },
                    ],
                },
            },
        });
    }

    updateVoucherDetails$(voucherDetails): Observable<any> {
        return this.http.request({
            path: '/ccat/voucher/update',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                ...voucherDetails,
            },
        })
    }

    getAllMaredCards$(): Observable<any> {
        return this.http.request({
            path: '/ccat/lookup/mared-cards/get-all',
        });
    }

    postVoucherBased$(voucherBased): Observable<any> {
        return this.http.request({
            path: '/ccat/voucher/voucher-based-refill/submit',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                ...voucherBased,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Scratch Cards',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    tabName: 'Voucher Based Refill',
                    footPrintDetails: [
                        {
                            paramName: 'voucherSerialNumber',
                            oldValue: null,
                            newValue: voucherBased.voucherNumber,
                        },
                        {
                            paramName: 'selectedMaredCard',
                            oldValue: null,
                            newValue: voucherBased.selectedMaredCard || null,
                        },
                    ],
                },
            },
        });
    }
    get dedicatedAccounts$(): Observable<any> {
        return this.subscriberService.subscriber$.pipe(
            map((subscriber) => subscriber.subscriberNumber),
            switchMap((msisdn) =>
                this.http.request({
                    path: '/ccat/customer-balances/dedicated-accounts/get',
                    payload: {
                        token: JSON.parse(sessionStorage.getItem('session')).token,
                        msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                        accountModel: this.subscriberService.subscriberSubject.value,
                    },
                })
            ),
            map((dedicatedAcc) => dedicatedAcc.payload)
        );
    }

    checkVoucherNumber$(voucher): Observable<any> {
        return this.http.request({
            path: '/ccat/voucher/check',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                ...voucher,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName')
                        ? sessionStorage.getItem('machineName')
                        : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Scratch Cards',
                    msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                    tabName: 'Over Scratch',
                    footPrintDetails: [
                        {
                            paramName: 'voucherSerialNumber',
                            oldValue: null,
                            newValue: voucher.voucherSerialNumber || null,
                        },
                        {
                            paramName: 'voucherNumber',
                            oldValue: null,
                            newValue: voucher.voucherNumber.join(' '),
                        },
                        {
                            paramName: 'Skip Voucher Validation',
                            oldValue: !voucher.skipValidationFlag,
                            newValue: voucher.skipValidationFlag,
                        },
                    ],
                },
            },
        })
    }
    getPaymentGetwayVoucher(serialNumber: number) {
        return this.http.request({
            path: '/ccat/voucher/payment-gateway-voucher/get',
            payload: {
                msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
                voucherSerialNumber: serialNumber,
            },
        });
    }
}
