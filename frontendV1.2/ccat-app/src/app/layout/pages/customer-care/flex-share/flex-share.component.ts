import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { FlexShareService } from 'src/app/core/service/customer-care/flex-share.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-flex-share',
    templateUrl: './flex-share.component.html',
    styleUrls: ['./flex-share.component.scss'],
})
export class FlexShareComponent implements OnInit {
    tab = 'black-white';

    loading$ = this.flexShareService.loading$;

    // BLACK_WHITE TAB
    //black white state variables
    destination = '';
    currentState = '';
    updatedValue = null;

    // profitable state variables
    parameterOut = '';
    showSubmit = false;

    // FLEX_SHARE_ELIGIBILITY TAB
    flexShareEligibilityForm: FormGroup;
    FlexEligibilityData = null;

    // FLEX_SHARE_History TAB
    types = [{ name: 'Sender' }, { name: 'Receiver' }];
    flexShareHistoryForm: FormGroup;
    flexShareHistoryReport = [];
    permissions = {
        viewBlackAndWhite: false,
        updateFlexShare: false,
        getFlexShareStatus: false,
        getFlexShareHistory: false,
        getFlexShareEligabilty: false,
    };
    constructor(
        private flexShareService: FlexShareService,
        private fb: FormBuilder,
        private validation: ValidationService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService
    ) { }

    ngOnInit(): void {
        this.setPermissions()
        this.flexShareService.getFlexShareState().subscribe({
            next: (resp) => {
                // blackWhite --> show submit
                if (resp?.payload?.blackWhiteState) {
                    this.showSubmit = true;
                    this.currentState = resp.payload.blackWhiteState.currentState;
                    this.destination = resp.payload.blackWhiteState.destinationState;
                    this.updatedValue = resp.payload.blackWhiteState.updatedValue;
                } else {
                    // profitable --> hide submit
                    this.showSubmit = false;
                    this.currentState = resp.payload.profitableState.currentState;
                    this.parameterOut = resp.payload.profitableState.parameterOut;
                }
            },
        });
        this.createForms();

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Flex Share View New',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    switchTab(tab) {
        this.tab = tab;
    }
    updateFlexShare() {
        let reqObj = {
            updatedValue: this.updatedValue,
        };
        this.flexShareService.updateFlexShareState(reqObj);
    }
    createForms() {
        this.flexShareEligibilityForm = this.fb.group({
            receiverMsisdn: [
                '',
                [
                    Validators.required,
                    Validators.maxLength(10),
                    Validators.pattern(this.validation.msisdnPattern),
                    Validators.minLength(10),
                ],
            ],
            flexAmount: ['', Validators.required],
        });

        this.flexShareHistoryForm = this.fb.group({
            dateFrom: ['', Validators.required],
            dateTo: ['', Validators.required],
            flag: ['', Validators.required],
        });
    }
    submitFlexEligibility() {
        console.log(this.flexShareEligibilityForm.value);
        this.flexShareService.getFlexShareEligibility(this.flexShareEligibilityForm.value).subscribe({
            next: (resp) => {
                this.FlexEligibilityData = resp?.payload?.flexShareEligibilityModel;
            },
            error: () => { },
        });
    }
    getFlexHistoryReport() {
        this.flexShareHistoryReport = [];
        let reqObj = {
            dateFrom: this.flexShareHistoryForm.value.dateFrom?.getTime(),
            dateTo: this.flexShareHistoryForm.value.dateTo?.getTime(),
            flag: this.flexShareHistoryForm.value.flag?.name,
        };
        this.flexShareService.getFlexShareHistory(reqObj).subscribe({
            next: (resp) => {
                this.flexShareHistoryReport = resp.payload?.flexHistory;
            },
        });
    }
    setPermissions() {
        let flexSharePermission: Map<number, string> = new Map()
            .set(376, 'viewBlackAndWhite')
            .set(361, 'updateFlexShare')
            .set(360, 'getFlexShareStatus')
            .set(359, 'getFlexShareHistory')
            .set(358, 'getFlexShareEligabilty');

        this.featuresService.checkUserPermissions(flexSharePermission);
        this.permissions.viewBlackAndWhite = this.featuresService.getPermissionValue(376);
        this.permissions.updateFlexShare = this.featuresService.getPermissionValue(361);
        this.permissions.getFlexShareStatus = this.featuresService.getPermissionValue(360);
        this.permissions.getFlexShareHistory = this.featuresService.getPermissionValue(359);
        this.permissions.getFlexShareEligabilty = this.featuresService.getPermissionValue(358);
    }
}
