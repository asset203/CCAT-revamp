import { Component, Input, OnInit } from '@angular/core';
import { AbstractControl } from '@angular/forms';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
  selector: 'app-forms-errors',
  templateUrl: './forms-errors.component.html',
  styleUrls: ['./forms-errors.component.scss']
})
export class FormsErrorsComponent implements OnInit {

  @Input() control: AbstractControl;

  @Input() findSubscriber: boolean;
  emailRegex = this.validation.emailPattern;
  passwordRegex = this.validation.passwordPattern;
  alphaRegex = this.validation.alphaPattern;
  numericalRegex = this.validation.numerical;
  msisdnRegex = this.validation.msisdnPattern;
  whiteSpaceRegex = this.validation.whiteSpacesPattern

  constructor(private validation: ValidationService) { }

  ngOnInit(): void {
  }

}
