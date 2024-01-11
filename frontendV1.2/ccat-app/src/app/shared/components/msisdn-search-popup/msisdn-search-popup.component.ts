import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { RouterService } from 'src/app/core/service/router.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import { ValidationService } from '../../services/validation.service';

@Component({
  selector: 'app-msisdn-search-popup',
  templateUrl: './msisdn-search-popup.component.html',
  styleUrls: ['./msisdn-search-popup.component.scss']
})
export class MsisdnSearchPopupComponent implements OnInit {

  constructor(private routerService: RouterService,
    public ngxSmartModalService: NgxSmartModalService,
    private subscriberService: SubscriberService,
    private fb: FormBuilder,
    private validation: ValidationService) { }

  findSubscriberForm: FormGroup;
  
  ngOnInit(): void {
    this.createForm();
  }

  // method to initialize the form
  createForm() {
    console.log("sessionStorage.getItem",sessionStorage.getItem("msisdnPattern"))
    this.findSubscriberForm = this.fb.group({
      msisdn: ['', [Validators.required,
      Validators.maxLength(10),
      Validators.pattern(new RegExp(sessionStorage.getItem("msisdnPattern"))),
      Validators.minLength(10)]]
    })
  }

  onEnterClicked(msisdn: string) {
    if (this.findSubscriberForm.valid) {
      this.subscriberService.loadSubscriber(msisdn['msisdn']);
      this.ngxSmartModalService.close('myModal');
      this.findSubscriberForm.reset();
    }
  }
  getErrors(){
    console.log(this.findSubscriberForm.controls.msisdn)
  }

}
