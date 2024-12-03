import {Component, OnInit, ViewChild} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {AccountGroupsService} from 'src/app/core/service/administrator/account-groups.service';
import {VipMsisdnService} from 'src/app/core/service/administrator/vip-msisdn.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {AccountGroup} from 'src/app/shared/models/AccountGroup.interface';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ValidationService} from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-vip-msisdn-lists',
    templateUrl: './vip-msisdn-lists.component.html',
    styleUrls: ['./vip-msisdn-lists.component.scss'],
})
export class VipMsisdnListsComponent implements OnInit {
    addVipMsisdnDialog = false;
    vibList: any[];
    loading$ = this.vipMsisdnService.loading;
    vipNumberForm: FormGroup;
    isIdUnique = true;
    isDescUnique = true;
    search = false;
    permissions = {
        getVipNumbers: false,
        addVipNumbers: false,
        deleteVipNumbers: false,
    };
    searchText: any;

    constructor(
        private vipMsisdnService: VipMsisdnService,
        private fb: FormBuilder,
        private messageService: MessageService,
        private toastrService: ToastService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService,
        private loadingService: LoadingService,
        private validation: ValidationService
    ) {}

    onSearchInput(inputValue: string, dt): void {
        if (!inputValue) {
            dt.clear();
            dt.reset();
            dt.filterGlobal('', 'contains');
            dt.first = 0;
        } else {
            console.log('search');
            dt.filterGlobal(inputValue, 'contains');
        }
    }
    applyFilter(filterVipNumber) {
        if (filterVipNumber) {
            const filterValue = filterVipNumber.toLowerCase();
            this.vibList = this.vibList.filter((vibNumber) => vibNumber.toLowerCase().includes(filterValue));
        } else {
            this.getVipNumbers();
        }
    }
    ngOnInit(): void {
        this.setPermissions();
        this.initAddForm();
        this.getVipNumbers();

        //footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'VIP',
        };
        this.footPrintService.log(footprintObj);
    }
    getVipNumbers() {
        if (this.permissions.getVipNumbers) {
            this.vipMsisdnService.allVipMsisdn$.subscribe(
                (res) => {
                    this.vibList = (res?.payload?.vipMsisdn).map((item) => ({msisdn: item}));
                },
                (err) => {
                    this.vibList = [];
                }
            );
        }
    }
    initAddForm() {
        this.vipNumberForm = this.fb.group({
            msisdn: [
                '',
                [
                    Validators.required,
                    Validators.maxLength(15),
                    Validators.pattern(this.validation.msisdnPattern),
                    Validators.minLength(4),
                ],
            ],
        });
    }

    switchToAdd() {
        this.initAddForm();
        this.addVipMsisdnDialog = true;
    }

    submitForm() {
        this.addVipMsisdnDialog = false;
        this.vipMsisdnService.addVipNumber(this.vipNumberForm.value).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(118).message);
                this.getVipNumbers();
            }
        });

        this.addVipMsisdnDialog = null;
    }
    confirmDeleteVipNumber(vibNumber: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(121).message,
            accept: () => {
                this.deleteVipNumber(vibNumber);
            },
        });
    }
    cancel() {
        this.addVipMsisdnDialog = false;
        this.addVipMsisdnDialog = null;
    }
    deleteVipNumber(vibNumber: number) {
        // let reqObj = {
        //     accountGroupId: accountGroupId,
        //     footprintModel: {
        //         machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
        //         profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
        //         pageName: 'Account Groups',
        //         footPrintDetails: [
        //             {
        //                 paramName: 'Account Group ID',
        //                 oldValue: null,
        //                 newValue: accountGroupId,
        //             },
        //         ],
        //     },
        // };
        this.vipMsisdnService.deleteVipNumber(vibNumber).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(120).message);
                this.getVipNumbers();
            }
        });
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(489, 'getVipNumbers')
            .set(485, 'addVipNumbers')
            .set(486, 'deleteVipNumbers');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getVipNumbers = this.featuresService.getPermissionValue(489);
        this.permissions.addVipNumbers = this.featuresService.getPermissionValue(485);
        this.permissions.deleteVipNumbers = this.featuresService.getPermissionValue(486);
    }
}
