import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AdvancedService } from 'src/app/core/service/customer-care/advanced.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { ValidationService } from 'src/app/shared/services/validation.service';

@Component({
  selector: 'app-disconnect-tab',
  templateUrl: './disconnect-tab.component.html',
  styleUrls: ['./disconnect-tab.component.scss']
})
export class DisconnectTabComponent implements OnInit {

  constructor(private fb: FormBuilder,
    private advancedService: AdvancedService,
    private validation: ValidationService) { }
  businessPlan = [
    'item1',
    'item2',
    'item3'
  ];
  disconnectForm: FormGroup;
  allDisconnectionReasons = this.advancedService.DisconnectionReasons$;
  reason;

  ngOnInit(): void {
    this.createForm();
    this.advancedService.getAllDisconnectReasons();
  }
  createForm() {
    this.disconnectForm = this.fb.group({
      msisdn: ['', [Validators.required,
        Validators.maxLength(15),
        Validators.pattern(this.validation.msisdnPattern),
        Validators.minLength(4),]],
      diconnect: [''],
      reason: ['', Validators.required]
    })
  }

}
