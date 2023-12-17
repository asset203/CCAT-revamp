import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Es2alnyMarquee} from 'src/app/shared/models/es2alny-marquee.interface';

@Component({
    selector: 'app-es2alny-marquee-form',
    templateUrl: './es2alny-marquee-form.component.html',
    styleUrls: ['./es2alny-marquee-form.component.scss'],
})
export class Es2alnyMarqueeFormComponent implements OnInit, OnChanges {
    @Input() visability: boolean;
    @Output() closeModal = new EventEmitter<void>();
    marqueeForm: FormGroup;
    @Input() editMode: boolean;
    @Input() editingMarque: Es2alnyMarquee;
    @Output() editAddForm = new EventEmitter<Es2alnyMarquee>();
    constructor(private fb: FormBuilder) {}
    ngOnChanges(changes: SimpleChanges): void {
        if (
            (changes.editMode && changes.editMode.currentValue !== changes.editMode.previousValue) ||
            (changes.editingMarque && changes.editingMarque.currentValue !== changes.editingMarque.previousValue)
        ) {
            this.createForm();
        }
    }
    ngOnInit(): void {
        this.createForm();
    }
    createForm() {
        this.marqueeForm = this.fb.group({
            title: [this.editingMarque.title, [Validators.required ,Validators.maxLength(20)]],
            description: [this.editingMarque.description, [Validators.required,Validators.maxLength(150)]],
        });
    }
    hideDialog() {
        this.closeModal.emit();
    }
    submitForm() {
        this.editAddForm.emit(this.marqueeForm.value);
        this.marqueeForm.reset();
    }
}
