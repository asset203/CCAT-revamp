import { ToastrService } from 'ngx-toastr';
import { Observable } from 'rxjs';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ScratchCardsService } from 'src/app/core/service/customer-care/scratch-cards.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { FeaturesService } from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-voucher-based-refill',
    templateUrl: './voucher-based-refill.component.html',
    styleUrls: ['./voucher-based-refill.component.scss'],
})
export class VoucherBasedRefillComponent implements OnInit {
    maredList = [];
    voucherBasedForm: FormGroup;
    disabledDropdown: boolean = true;
    dedicatedAccounts: Observable<any>;
    permissions = {
        submitVoucherBasedRefill: false,
    };
    voucherNumberLength =+JSON.parse(sessionStorage.getItem("voucherNumberLength"))
    constructor(
        private scratchCardsService: ScratchCardsService,
        private fb: FormBuilder,
        private toasterService: ToastService,
        private featuresService: FeaturesService
    ) { }

    ngOnInit(): void {
        this.setPermissions();
        this.scratchCardsService.getAllMaredCards$().subscribe((res) => {
            this.maredList = res.payload.maredCardsList;
        });
        this.initializeVoucherBasedForm();
        this.getDedicatedAccounts();
        this.voucherBasedForm.get('isMaredCard').valueChanges.subscribe((val) => {
            if (val == true) {
                this.voucherBasedForm.controls['selectedMaredCard'].setValidators([Validators.required]);
                this.voucherBasedForm.controls['selectedMaredCard'].enable();
            } else {
                this.voucherBasedForm.controls['selectedMaredCard'].clearValidators();
                this.voucherBasedForm.controls['selectedMaredCard'].disable();
            }
            this.voucherBasedForm.controls['selectedMaredCard'].updateValueAndValidity();
            this.voucherBasedForm.updateValueAndValidity();
        });
    }
    initializeVoucherBasedForm() {
        this.voucherBasedForm = this.fb.group({
            voucherNumber: ['', Validators.required],
            isMaredCard: [false],
            selectedMaredCard: [''],
        });
    }

    submitVoucherBased() {
        this.scratchCardsService.postVoucherBased$(this.voucherBasedForm.value).subscribe((res) => {
            this.getDedicatedAccounts();
            this.toasterService.success(res.statusMessage);
        });
    }
    disableDropdownAndreset() {
        this.disabledDropdown = !this.disabledDropdown;
        this.voucherBasedForm.controls['selectedMaredCard'].setValue(null);
    }

    getDedicatedAccounts() {
        this.dedicatedAccounts = this.scratchCardsService.dedicatedAccounts$;
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(216, 'getVoucherDetails');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.submitVoucherBasedRefill = this.featuresService.getPermissionValue(216);
    }
}
