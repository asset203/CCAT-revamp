import {NotepadService} from './../../../../core/service/administrator/notepad.service';
import {Observable} from 'rxjs';
import {Component, OnInit} from '@angular/core';
import {PamInformationService} from 'src/app/core/service/customer-care/pam-information.service';
import {Pam} from 'src/app/shared/models/pam';
import {map, take} from 'rxjs/operators';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ConfirmationService} from 'primeng/api';
import {Note} from 'src/app/shared/models/note.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';

@Component({
    selector: 'app-pam-information',
    templateUrl: './pam-information.component.html',
    styleUrls: ['./pam-information.component.scss'],
    providers: [ConfirmationService],
})
export class PamInformationComponent implements OnInit {
    pams$: Observable<Pam[]>;
    pams;
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    pamID;
    subscriberNumber;

    constructor(
        private subscriberService: SubscriberService,
        private pamInformation: PamInformationService,
        private toasterService: ToastService,
        private confirmationService: ConfirmationService,
        private noteService: NotepadService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService
    ) {}

    ngOnInit(): void {
        this.subscriberService.subscriber$.pipe(take(1)).subscribe((res) => {
            this.pams = res.pams
            console.log(res)
        });
        this.subscriberService.subscriber$
            .pipe(
                map((subscriber) => subscriber?.subscriberNumber),
                take(1)
            )
            .subscribe((res) => (this.subscriberNumber = res));

        // foot print load
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'PAM Information new',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    setPermissions() {
        let pamInformationPermissions: Map<number, string> = new Map();
    }

    deletePam(id) {
        this.pamInformation
            .deletePam$(id)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', res.statusMessage);
                        this.pams.splice(this.pams.indexOf(id), 1);
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    evaluatePam(id) {
        let reqObj = {
            pamId: id,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'PAM Information new',
                footPrintDetails: [
                    {
                        paramName: 'PAM ID',
                        oldValue: '',
                        newValue: id,
                    },
                ],
            },
        };
        this.pamInformation
            .evaluatePam$(reqObj)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', res.statusMessage);
                    }
                    this.pamID = null;
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }

    confirm(id) {
        let reqObj = {
            pamId: id,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'PAM Information new',
                footPrintDetails: [
                    {
                        paramName: 'PAM ID',
                        oldValue: '',
                        newValue: id,
                    },
                ],
            },
        };
        this.confirmationService.confirm({
            message: 'Are you sure that you want to delete this user?',
            accept: () => {
                this.deletePam(reqObj);
            },
        });
    }

    showReasonDialog(pamId) {
        this.ReasonDialog = true;
        this.pamID = pamId;
    }

    submitReason() {
        let noteObj = {
            entry: this.reason,
            footPrint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'PAM Information New',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.reason,
                    },
                ],
            },
        };
        this.ReasonDialog = false;
        this.noteService.addNote(noteObj, this.subscriberNumber).subscribe((success) => {
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
        });
    }
}
