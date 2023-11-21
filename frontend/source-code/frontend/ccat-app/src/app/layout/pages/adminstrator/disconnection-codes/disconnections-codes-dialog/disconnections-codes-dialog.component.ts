import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {InputNumber} from 'primeng/inputnumber';
import {DisconnectionCode} from 'src/app/shared/models/disconnection-code.model';
import {ValidationService} from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-disconnections-codes-dialog',
    templateUrl: './disconnections-codes-dialog.component.html',
    styleUrls: ['./disconnections-codes-dialog.component.scss'],
})
export class DisconnectionsCodesDialogComponent implements OnInit, OnChanges {
    @Input() editMode: boolean;
    @Input() visiablity: boolean;
    @Input() editedDisconnCode: DisconnectionCode;
    @Input() list;
    @Output() hideModal = new EventEmitter<void>();
    @Output() formSubmitted = new EventEmitter<DisconnectionCode>();
    @ViewChild('codeInput') codeInput: InputNumber;
    isFoundedError = false;
    constructor(private validationService: ValidationService) {}
    ngOnChanges(changes: SimpleChanges): void {
        let setCondition =
            (changes.visiablity && changes.editMode && changes.editMode.currentValue) ||
            (changes.editedDisconnCode && changes.editedDisconnCode.currentValue);
        this.disconnCodesForm = setCondition ? this.setEditForm() : this.setAddForm();
    }
    disconnCodesForm: FormGroup;
    ngOnInit(): void {}
    setEditForm() {
        return new FormGroup({
            code: new FormControl(this.editedDisconnCode.code, Validators.required),
            description: new FormControl(this.editedDisconnCode.description, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
                Validators.maxLength(50),
            ]),
            id: new FormControl(this.editedDisconnCode.id),
        });
    }
    setAddForm() {
        return new FormGroup({
            code: new FormControl(null, [Validators.required]),
            description: new FormControl(null, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
                Validators.maxLength(50),
            ]),
        });
    }
    submit() {
        this.formSubmitted.emit(this.disconnCodesForm.value);
    }
    hideDialog() {
        this.hideModal.emit();
        this.isFoundedError=false;
        
    }
    checkIDExist(event) {
        this.isFoundedError = false;
        this.isFoundedError = this.list.some((el) => el.code === event.value) ? true : false;
    }
    onFocusInput() {
        this.codeInput.input.nativeElement.focus();
    }
}
