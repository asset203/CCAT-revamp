import {TrafficBehaviorService} from './../../../../core/service/customer-care/traffic-behavior.service';
import {AfterViewChecked, AfterViewInit, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import {map, take} from 'rxjs/operators';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {BehaviorSubject} from 'rxjs';

@Component({
    selector: 'app-traffic-behaviour',
    templateUrl: './traffic-behaviour.component.html',
    styleUrls: ['./traffic-behaviour.component.scss'],
})
export class TrafficBehaviourComponent implements OnInit, AfterViewChecked {
    constructor(
        private trafficBehaviorService: TrafficBehaviorService,
        private toasterService: ToastService,
        private featuresService: FeaturesService,
        private cdr: ChangeDetectorRef
    ) {}
    headers;
    reports;
    fromDate;
    toDate;
    permissions = {
        GET_TRAFFIC_BEHAVIOR_REPORT: false,
    };
    dateTime = new Date();
    loading$ = new BehaviorSubject(false);
    ngOnInit(): void {
        this.setPermissions();
    }
    onDateSelectFromDate(event: any) {
        const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.fromDate = correctedDate;
    }
    onDateSelectToDate(event: any) {
        const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.toDate = correctedDate;
    }
    ngAfterViewChecked(): void {
        this.cdr.detectChanges();
    }
    getTraffic() {
        const trafficFromtDate = new Date(this.fromDate).getTime();
        const trafficToDate = new Date(this.toDate).getTime();
        this.trafficBehaviorService
            .getTrafficBehavior$(trafficFromtDate, trafficToDate)
            .pipe(
                take(1),
                map((res) => res.payload),
                indicate(this.loading$)
            )
            .subscribe({
                next: (res) => {
                    this.headers = res?.headers;
                    this.reports = res?.data;
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(154, 'GET_TRAFFIC_BEHAVIOR_REPORT');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.GET_TRAFFIC_BEHAVIOR_REPORT = this.featuresService.getPermissionValue(154);
    }

    selectStartDate() {
        console.log('start date selected ');
        if (this.fromDate < this.toDate) {
            console.log('hi hi');
        }
    }
}
