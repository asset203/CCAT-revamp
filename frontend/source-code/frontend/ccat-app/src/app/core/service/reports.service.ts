import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {ContractBillRequest, FlagReportRequest, ReportRequest} from 'src/app/shared/models/ReportRequest.interface';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from './http.service';
import {SubscriberService} from './subscriber.service';

@Injectable({providedIn: 'root'})
export class ReportsService {
    constructor(private httpService: HttpService, private subscriberService: SubscriberService) {}
    loading = new BehaviorSubject(false);
    allVodafoneOne$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/vodafone-one/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allVodafoneOneProfile$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/vodafone-one-profile/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allVisitedUrls$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/visited-urls/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allContractBalance$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/contract-balance/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allVodafoneOneRedeem$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/vodafone-one-redeem/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allEtopupTransactions$(reportObj: ReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/etopup-transactions/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allContractBalanceTransfer$(reportObj: FlagReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/contract-balance-transfer/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allOutgoingView$(reportObj: FlagReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/outgoing-view/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allComplaintView$(reportObj: FlagReportRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/complaint-view/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
    allContractBill$(reportObj: ContractBillRequest) {
        const payload = {
            ...reportObj,
            msisdn: this.subscriberService.subscriberSubject.getValue().subscriberNumber,
        };
        return this.httpService
            .request({
                path: '/ccat/dss-report/contract-bill/get-all',
                payload,
            })
            .pipe(indicate(this.loading));
    }
}
