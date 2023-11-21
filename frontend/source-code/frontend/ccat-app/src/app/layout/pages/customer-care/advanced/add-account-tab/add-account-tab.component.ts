import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BusinessPlansService } from 'src/app/core/service/business-plans.service';
import { ValidationService } from 'src/app/shared/services/validation.service';
import { LanguageService } from 'src/app/core/service/customer-care/language.service';
import { AdvancedService } from 'src/app/core/service/customer-care/advanced.service';
import { ServiceClassService } from 'src/app/core/service/customer-care/service-class.service';
import { FeaturesService } from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-add-account-tab',
    templateUrl: './add-account-tab.component.html',
    styleUrls: ['./add-account-tab.component.scss'],
})
export class AddAccountTabComponent implements OnInit {
    constructor(
        private fb: FormBuilder,
        private validation: ValidationService,
        private businessPlansService: BusinessPlansService,
        private languageService: LanguageService,
        private advancedService: AdvancedService,
        private serviceClassService: ServiceClassService,
        private featuresService: FeaturesService
    ) { }

    fromDate;
    serviceClass;
    language;
    serviceOffering;
    accountGroup;

    addAccountForm: FormGroup;
    businessPlans = this.businessPlansService.businessPlans$;
    allLanguages = this.languageService.allLanguages$;
    allAccountGroups = this.advancedService.allAccountGroups$;
    allServiceOffering = this.advancedService.ServiceOffering$;
    allServiceClasses = this.serviceClassService.allServiceClasses$;

    // Permissions Booleans
    GET_ALL_ACCOUNT_GROUPS = false;
    GET_ALL_SERVICE_OFFERING = false;

    ngOnInit(): void {
        this.createForm();
        this.languageService.getAllLanguages();
        this.advancedService.getAllAccountGroups();
        this.advancedService.getAllServiceOffering();
        this.serviceClassService.getAllServiceClasses();

        let addAccountPermissions: Map<number, string> = new Map()
            .set(126, 'GET_ALL_ACCOUNT_GROUPS')
            .set(128, 'GET_ALL_SERVICE_OFFERING');

        this.featuresService.checkUserPermissions(addAccountPermissions);
        this.GET_ALL_ACCOUNT_GROUPS = this.featuresService.getPermissionValue(126);
        this.GET_ALL_SERVICE_OFFERING = this.featuresService.getPermissionValue(128);

        // this.viewHOSIModal = this.featuresService.getPermissionValue(10);
    }
    createForm() {
        this.addAccountForm = this.fb.group({
            msisdn: [
                '',
                [
                    Validators.required,
                    Validators.maxLength(10),
                    Validators.pattern(this.validation.msisdnPattern),
                    Validators.minLength(8),
                ],
            ],
            language: [''],
            accountGroup: [''],
            serviceOffering: [''],
            tempBlock: [''],
            serviceClass: ['', [Validators.required]],
        });
    }
}
