import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { RouterService } from 'src/app/core/service/router.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import { ValidationService } from '../../services/validation.service';
import { LockService } from 'src/app/core/service/lock.service';

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
    private validation: ValidationService,
    private lockService: LockService) { }

  findSubscriberForm: FormGroup;
  
  ngOnInit(): void {
    this.createForm();
  }

  // method to initialize the form
  createForm() {
    console.log("sessionStorage.getItem",sessionStorage.getItem("msisdnPattern"))
    this.findSubscriberForm = this.fb.group({
      msisdn: ['', [Validators.required,
      Validators.maxLength(15),
      Validators.pattern(new RegExp(sessionStorage.getItem("msisdnPattern"))),
      Validators.minLength(4)]]
    })
  }

  onEnterClicked(msisdn: string) {
    if (this.findSubscriberForm.valid) {
      let oldmsisdn = sessionStorage.getItem('msisdn');
      if (msisdn) {
          this.lockService.deleteLock(JSON.parse(oldmsisdn));
      }
      this.subscriberService.loadSubscriber(msisdn['msisdn']);
      this.ngxSmartModalService.close('myModal');
      this.findSubscriberForm.reset();
    }
  }
  getErrors(){
    console.log(this.findSubscriberForm.controls.msisdn)
  }

}
