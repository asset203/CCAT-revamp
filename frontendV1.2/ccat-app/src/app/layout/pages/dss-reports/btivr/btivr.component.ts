import { AfterViewChecked, ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { BtivrService } from 'src/app/core/service/customer-care/btivr.service';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { ToastService } from 'src/app/shared/services/toast.service';

@Component({
  selector: 'app-btivr',
  templateUrl: './btivr.component.html',
  styleUrls: ['./btivr.component.scss']
})
export class BTIVRComponent implements OnInit, AfterViewChecked {

  constructor(private btivrService: BtivrService,
    private toasterService: ToastService,
    private featuresService: FeaturesService,
    private cdr: ChangeDetectorRef) { }
  headers;
  reports;
  fromDate;
  toDate;
  permissions = {
    GET_BTIVR_REPORT: false
  }
  dateTime = new Date();
  ngOnInit(): void {
    this.setPermissions();
  }
  ngAfterViewChecked(): void {
    this.cdr.detectChanges();
  }

  getBTIVR() {
    const trafficFromtDate = new Date(this.fromDate).getTime()
    const trafficToDate = new Date(this.toDate).getTime()
    this.btivrService.getBTIVR$(trafficFromtDate, trafficToDate).pipe(
      take(1),
      map(res => res.payload)
    ).subscribe(
      {
        next: (res) => {
          this.headers = res.headers
          this.reports = res.data
        },
        error: (err) => {
          this.toasterService.error('Error', err);
        }
      }
    )
  }

  setPermissions() {
    let findSubscriberPermissions: Map<number, string> = new Map()
      .set(155, 'GET_BTIVR_REPORT')
    this.featuresService.checkUserPermissions(findSubscriberPermissions);
    this.permissions.GET_BTIVR_REPORT = this.featuresService.getPermissionValue(155);


  }

}
