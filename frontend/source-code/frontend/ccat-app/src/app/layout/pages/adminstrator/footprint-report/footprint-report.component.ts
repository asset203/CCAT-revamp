import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {map, tap} from 'rxjs/operators';
import {UserAccessService} from 'src/app/core/service/administrator/user-access.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {footprintDetails} from 'src/app/shared/models/footprint-details';
import {footprintReport} from 'src/app/shared/models/footprint-report.interface';
import { ToastService } from 'src/app/shared/services/toast.service';
import {ValidationService} from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-footprint-report',
    templateUrl: './footprint-report.component.html',
    styleUrls: ['./footprint-report.component.scss'],
})
export class FootprintReportComponent implements OnInit {
    constructor(
        private footprintService: FootPrintService,
        private fb: FormBuilder,
        private validation: ValidationService,
        private userAccessService: UserAccessService,
        private toastrService : ToastService
    ) {}
    footprintReports: footprintReport[] = [];
    footPrintReportForm: FormGroup;
    loading$ = this.footprintService.loading$;
    footprintDetailsDialog = false;
    footprintDetails: footprintDetails[] = [];
    dateTime = new Date();
    allUsers$ = this.userAccessService.allUsers$.pipe(
        tap((res) => console.log(res)),
        map((res) => res.payload.users)
    );

    ngOnInit(): void {
        this.createForm();
    }
    createForm() {
        this.footPrintReportForm = this.fb.group({
            userName: [null],
            msisdn: [null, [Validators.maxLength(10), Validators.pattern(this.validation.msisdnPattern)]],
            dateFrom: [null],
            dateTo: [null],
        });
    }
    getReport() {
        let formObj = this.prepareReqObj();
        const isValid =this.validateDateRange(formObj);
        if(isValid){
            this.footprintService.get(formObj).subscribe({
                next: (resp) => {
                    if (resp.statusCode === 0) {
                        this.footprintReports = Object.values(resp?.payload.footprints);
                    } else {
                        this.footprintReports = [];
                    }
                },
                error: () => {},
            });
        }
        
        
    }
    selectRow(event) {
        this.footprintDetailsDialog = true;
        this.footprintDetails = event.data.footPrintDetails;
    }
    prepareReqObj() {
        return {
            userName: this.footPrintReportForm.value.userName,
            msisdn: this.footPrintReportForm.value.msisdn ? `${this.footPrintReportForm.value.msisdn}` : null,
            dateFrom: this.footPrintReportForm.value.dateFrom
                ? new Date(this.footPrintReportForm.value.dateFrom).getTime()
                : new Date().getTime(),
            dateTo: this.footPrintReportForm.value.dateFrom
                ? new Date(this.footPrintReportForm.value.dateTo).getTime()
                : new Date().getTime(),
        };
    }
    exportFootPrint() {
        let formObj = this.prepareReqObj();
        const isValid =this.validateDateRange(formObj);
        if(isValid){
            this.footprintService.export(formObj).subscribe((res: any) => {
                const a = document.createElement('a');
                document.body.appendChild(a);
                const blob: any = new Blob([res], {type: 'octet/stream'});
                const url = window.URL.createObjectURL(blob);
                a.href = url;
                a.download = 'Footprint_Report.xlsx';
                a.click();
                window.URL.revokeObjectURL(url);
            });
        }
        
    }
    validateDateRange(formObj) {
        let isValid =true
        const dateRange =(formObj.dateTo-formObj.dateFrom)/(24 * 60 * 60 * 1000)
        if(dateRange > 21){
            this.toastrService.warning("Date Maximum Range 21 Days");
            isValid=false;
        }
        return isValid;
    }
}
