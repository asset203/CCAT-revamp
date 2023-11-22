import {Injectable} from '@angular/core';
import {ValidatorFn, FormGroup, ValidationErrors, AbstractControl, FormControl} from '@angular/forms';

@Injectable({
    providedIn: 'root',
})
export class ValidationService {
    constructor() {}

    emailPattern = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
    numerical = '^[0-9]*$';
    
    passwordPattern =new RegExp(sessionStorage.getItem('passwordPattern'));
    alphaPattern =
        /^(?:[a-zA-Z\s\u0600-\u06FF\u0750-\u077F\u08A0-\u08FF\uFB50-\uFDCF\uFDF0-\uFDFF\uFE70-\uFEFF]|(?:\uD802[\uDE60-\uDE9F]|\uD83B[\uDE00-\uDEFF])){0,25}$/;
    msisdnPattern = new RegExp(sessionStorage.getItem("msisdnPattern")) ;
    whiteSpacesPattern = /^[^\s]+?[^#+&\'\"\\\\]*$/;

    // this is custom validator to check if password and retyped password are matching
    matchingPasswords(): ValidatorFn {
        return (control: FormGroup): ValidationErrors | null => {
            return control.get('password').value !== control.get('password_confirmation').value
                ? {unmatchedPasswords: true}
                : null;
        };
    }
    noWhitespaceValidator(control: FormControl) {
        const isWhitespace = (control.value || '').trim().length === 0;
        const isValid = !isWhitespace;
        return isValid ? null : {whitespace: true};
    }
    setRegex(msisdnRegex: any, passwordRegex: any) {
        this.passwordPattern = new RegExp(passwordRegex) ;
        this.msisdnPattern = new RegExp(msisdnRegex);
    }
}
