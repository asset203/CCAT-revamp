import { Component, OnInit } from '@angular/core';
import { FormArray, FormBuilder, FormGroup, Validators, FormControl } from '@angular/forms';
import { ScratchCardsService } from 'src/app/core/service/customer-care/scratch-cards.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import { FeaturesService } from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-over-scratch',
    templateUrl: './over-scratch.component.html',
    styleUrls: ['./over-scratch.component.scss'],
})
export class OverScratchComponent implements OnInit {
    scratchCardsForm: FormGroup;

    numbers: FormArray;
    greenColor;
    rightVoucherNumber;
    airVoucherNumberDigits;
    permissions = {
        checkVoucherNumber: false,
        skipValidation: false,
    };
    constructor(
        private fb: FormBuilder,
        private scratchCardsService: ScratchCardsService,
        private featuresService: FeaturesService,
        private subscriberService : SubscriberService
    ) { }

    ngOnInit(): void {
        this.setPermissions();
        this.initializeVoucherBasedForm();

        for (let i = 0; i < 14; i++) {
            this.addNewVoucher();
        }
    }
    initializeVoucherBasedForm() {
        this.scratchCardsForm = this.fb.group({
            voucherSerialNumber: [
                '',
                [
                    Validators.required,
                    Validators.pattern('^[0-9]*$'),
                    Validators.minLength(8),
                    Validators.maxLength(17),
                ],
            ],
            voucherNumber: this.fb.array([]),
            skipValidationFlag: [null],
            serverId: [1],
        });
    }
    get voucherNumberControls() {
        return (this.scratchCardsForm.get('voucherNumber') as FormArray).controls;
    }
    get validationFlag(){
       return  this.scratchCardsForm.get('skipValidationFlag').value
    }
    get voucherNumberValueValidation() {
        const voucherNumber : string[]= (this.scratchCardsForm.get('voucherNumber') as FormArray).value;
        console.log(voucherNumber)
        let counter=0; 
        for(let num of voucherNumber){
            if(num){
                counter++;
            }
        }
        if(!this.validationFlag){
            return counter >=5
        }
        return true;
        
    }
    newVoucherNumber() {
        return new FormControl('');
    }
    addNewVoucher() {
        this.numbers = this.scratchCardsForm.get('voucherNumber') as FormArray;
        this.numbers.push(this.newVoucherNumber());
    }
    
    submit() {
        console.log(this.scratchCardsForm.value);
        this.scratchCardsService.checkVoucherNumber$(this.scratchCardsForm.value).subscribe((result) => {
            this.rightVoucherNumber = result.payload.airVoucherNumber;
            this.airVoucherNumberDigits = result.payload.airVoucherNumberDigits;
            this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')))
            

        });
    }
    reset() {
        this.scratchCardsForm.reset();
        this.rightVoucherNumber = null;
        this.airVoucherNumberDigits = null;
        this.scratchCardsForm.patchValue({
            serverId: 1,
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(276, 'checkVoucherNumber')
            .set(306, 'skipValidation');

        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.checkVoucherNumber = this.featuresService.getPermissionValue(276);
        this.permissions.skipValidation = this.featuresService.getPermissionValue(306);
    }
}
