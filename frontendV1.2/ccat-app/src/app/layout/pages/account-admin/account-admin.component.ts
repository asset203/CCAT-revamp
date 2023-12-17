import { HttpService } from './../../../core/service/http.service';
import { AccountAdminTabComponent } from './account-admin-tab/account-admin-tab.component';
import { Component, OnInit, ViewChild } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { map } from 'rxjs/operators';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';

@Component({
    templateUrl: './account-admin.component.html',
    styleUrls: ['./account-admin.component.scss'],
})
export class AccountAdminComponent implements OnInit {

    @ViewChild('accountAdminTab') accountAdminTab;
    @ViewChild('dedicatedAccountsTab') dedicatedAccountsTab;
    @ViewChild('accumlatorsTab') accumlatorsTab;
    loading$ = this.subscriberService.loading$;
    selectedType;
    selectedCode;
    tab = 'Account Administration';
    typAndCodeForm: FormGroup;
    checked: boolean;
    TypeAndCodeValidation: boolean;

    types: any = this.transactionsType$;
    codes: any;
    permissions = {
        viewCustomerBalancePage: false,
        addBalance: false,
        deductBalance: false,
        submitCustomerBalance: false,
        viewDedicatedAccounts: false,
        viewAccumulators: false,
    }
    constructor(
        private http: HttpService,
        private FormBuilder: FormBuilder,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService,
        private subscriberService : SubscriberService
    ) {
        this.typAndCodeForm = this.FormBuilder.group({
            transactionType: FormBuilder.control('', Validators.required),
            transactionCode: FormBuilder.control('', Validators.required)
        })
    }

    ngOnInit(): void {
        this.setPermissions();
        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'account-admin',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            tabName: this.tab
        }
        this.footPrintService.log(footprintObj);
    }


    getTransactionsCode() {
        this.codes = this.transactionsCode$(this.selectedType.id);
    }
    submitTypeAndCode() {

    }
    switchTab(tab) {
        console.log(tab)
        this.tab = tab;

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'account-admin',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            tabName: tab

        }
        this.footPrintService.log(footprintObj)
    }
    submitAdmin() {
        this.accountAdminTab.balanceAndDateExport();
    }
    submitAccounts() {
        this.dedicatedAccountsTab.dedicatedAccountsExport();
    }
    submitAccumlators() {
        this.accumlatorsTab.accumlatorsExport()
    }

    get transactionsType$(): Observable<any> {
        return this.http.request({
            path: '/ccat/transactions/type/get',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
                featureId: 2
            },
        }).pipe(
            map(types => types.payload)
        )
    }

    transactionsCode$(typId): Observable<any> {
        return this.http.request({
            path: '/ccat/transactions/code/get',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
                typeId: typId
            },
        }).pipe(
            map(code => code.payload)
        )
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(2, 'viewCustomerBalancePage')
            .set(3, 'addBalance')
            .set(4, 'deductBalance')
            .set(21, 'submitCustomerBalance')
            .set(18, 'viewDedicatedAccounts')
            .set(20, 'viewAccumulators')
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.viewCustomerBalancePage = this.featuresService.getPermissionValue(2);
        this.permissions.addBalance = this.featuresService.getPermissionValue(3);
        this.permissions.deductBalance = this.featuresService.getPermissionValue(4);
        this.permissions.submitCustomerBalance = this.featuresService.getPermissionValue(21);
        this.permissions.viewDedicatedAccounts = this.featuresService.getPermissionValue(18);
        this.permissions.viewAccumulators = this.featuresService.getPermissionValue(20);

    }
}