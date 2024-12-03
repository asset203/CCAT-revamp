import {
    Component,
    ElementRef,
    EventEmitter,
    Input,
    OnChanges,
    OnInit,
    Output,
    SimpleChanges,
    ViewChild,
} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {InputNumber} from 'primeng/inputnumber';
import {UsageCounter} from 'src/app/shared/models/usage-counter.interface';
import {ValidationService} from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-usage-counter-dialog',
    templateUrl: './usage-counter-dialog.component.html',
    styleUrls: ['./usage-counter-dialog.component.scss'],
})
export class UsageCounterDialogComponent implements OnChanges {
    constructor(private validationService: ValidationService) {}

    @Input() selectedUsage: UsageCounter;
    @Input() modalIsOpen: boolean;
    @Input() editMode: boolean;
    @Output() hideModal = new EventEmitter<void>();
    @Output() formSubmitted = new EventEmitter<UsageCounter>();
    @ViewChild('inp') inp: InputNumber;
    usageCounterForm: FormGroup;
    usageTypes = [
        {label: 'counter value', value: 1},
        {label: 'monetary value', value: 2},
    ];
    isIdUnique = true;
    @Input() countersList: UsageCounter[];

    ngOnInit(): void {}
    ngOnChanges(changes: SimpleChanges): void {
        let setCondition =
            (changes.modalIsOpen && changes.editMode && changes.editMode.currentValue) ||
            (changes.selectedUsage && changes.selectedUsage.currentValue);
        console.log('setCondition', setCondition);
        this.usageCounterForm = setCondition ? this.setEditForm() : this.setAddForm();
        if (setCondition) {
            if (this.selectedUsage?.monetaryValue1) {
                this.usageCounterForm.get('value')?.clearValidators();
                this.usageCounterForm
                    .get('monetaryValue1')
                    ?.setValidators([
                        Validators.required,
                        Validators.pattern(this.validationService.whiteSpacesPattern),
                    ]);
            } else if (this.selectedUsage?.value) {
                this.usageCounterForm.get('monetaryValue1')?.clearValidators();
                this.usageCounterForm
                    .get('value')
                    ?.setValidators([
                        Validators.required,
                        Validators.pattern(this.validationService.whiteSpacesPattern),
                    ]);
            }

            this.usageCounterForm.get('value')?.updateValueAndValidity();
            this.usageCounterForm.get('monetaryValue1')?.updateValueAndValidity();
        } else {
            this.updateValidatorsOnInit();
        }
        console.log('setCondition', setCondition, changes.modalIsOpen, changes.editMode);
    }
    setEditForm() {
        return new FormGroup({
            id: new FormControl(this.selectedUsage?.id, Validators.required),
            value: new FormControl(this.selectedUsage?.value, Validators.required),
            // usageTypeId: (new FormControl(this.selectedUsage.value, Validators.required)),
            monetaryValue1: new FormControl(this.selectedUsage?.monetaryValue1),
            // monetaryValue2: new FormControl(this.selectedUsage.monetaryValue2, Validators.required),
        });
    }
    setAddForm() {
        return new FormGroup({
            id: new FormControl(null, Validators.required),
            value: new FormControl(null, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            monetaryValue1: new FormControl(null, [Validators.pattern(this.validationService.whiteSpacesPattern)]),
            // monetaryValue2: new FormControl(null, [Validators.required,Validators.pattern(this.validationService.whiteSpacesPattern)]),
            usageTypeId: new FormControl(1, Validators.required),
        });
    }

    updateValidatorsOnInit() {
        const usageTypeId = this.usageCounterForm.get('usageTypeId')?.value;
        if (usageTypeId === 2) {
            this.usageCounterForm.get('value')?.clearValidators();
            this.usageCounterForm
                .get('monetaryValue1')
                ?.setValidators([Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]);
        } else if (usageTypeId === 1) {
            this.usageCounterForm.get('monetaryValue1')?.clearValidators();
            this.usageCounterForm
                .get('value')
                ?.setValidators([Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]);
        }

        this.usageCounterForm.get('value')?.updateValueAndValidity();
        this.usageCounterForm.get('monetaryValue1')?.updateValueAndValidity();
    }

    get usageType() {
        return this.usageCounterForm.get('usageTypeId')?.value;
    }
    clearValue() {
        this.usageCounterForm.patchValue({
            value: null,
        });
    }
    submit() {
        this.formSubmitted.emit(this.usageCounterForm.value);
    }
    hideDialog() {
        this.hideModal.emit();
        this.usageCounterForm.reset();
    }
    checkUniqueId(value) {
        if (this.countersList.some((counter) => counter.id === value)) {
            this.isIdUnique = false;
        } else {
            this.isIdUnique = true;
        }
    }
    focusInput() {
        this.inp.input.nativeElement.focus();
    }
}
