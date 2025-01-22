import {Directive, HostListener, Input} from '@angular/core';
@Directive({
    selector: '[appSubmitOnEnter]',
})
export class SubmitOnEnterDirective {
    @Input() formSubmit!: () => void;
    @Input() isValid: boolean;
    @HostListener('keyup.enter', ['$event'])
    handleEnter(event: KeyboardEvent) {
        event.preventDefault(); // Prevent default Enter key behavior
        if (this.formSubmit && this.isValid) {
            console.log("valid")
            this.formSubmit();
        }
    }
}
