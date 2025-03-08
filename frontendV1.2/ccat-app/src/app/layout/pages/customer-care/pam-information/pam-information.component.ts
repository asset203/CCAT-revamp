import {NotepadService} from './../../../../core/service/administrator/notepad.service';
import {Observable, Subscription} from 'rxjs';
import {AfterViewInit, Component, OnDestroy, OnInit} from '@angular/core';
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
export class PamInformationComponent implements OnInit, OnDestroy, AfterViewInit {
    pams$: Observable<Pam[]>;
    pams;
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    pamID;
    subscriberNumber;
    subscriberSubscribtion = new Subscription();
    permissions = {
        addPam: false,
        evaluatePam: false,
        deletePam: false,
    };
    constructor(
        private subscriberService: SubscriberService,
        private pamInformation: PamInformationService,
        private toasterService: ToastService,
        private confirmationService: ConfirmationService,
        private noteService: NotepadService,
        private featuresService: FeaturesService,
        private footPrintService: FootPrintService
    ) {}
    ngAfterViewInit(): void {
        this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
    }
    ngOnDestroy(): void {
        this.subscriberSubscribtion.unsubscribe();
    }

    ngOnInit(): void {
        this.setPermissions();
        this.subscriberSubscribtion = this.subscriberService.subscriber$.subscribe((res) => {
            this.pams = res?.pams.map((el) => {
                return {
                    ...el,
                    deferredToDate: new Date(el.deferredToDate),
                    lastEvaluationDate: new Date(el.lastEvaluationDate),
                };
            });
        });
        // foot print load
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'PAM Information new',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }

    setPermissions() {
        let serviceOfferingPermissions: Map<number, string> = new Map()
            .set(118, 'addPam')
            .set(131, 'evaluatePam')
            .set(130, 'deletePam');
        this.featuresService.checkUserPermissions(serviceOfferingPermissions);
        this.permissions.addPam = this.featuresService.getPermissionValue(118);
        this.permissions.evaluatePam = this.featuresService.getPermissionValue(131);
        this.permissions.deletePam = this.featuresService.getPermissionValue(130);
        //this.permissions.getServiceOffering = this.featuresService.getPermissionValue(23);
    }

    deletePam(id) {
        this.pamInformation
            .deletePam$(id)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', 'Pam Deleted');
                        this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
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
            pageName: 'PAM Information new',
            footprintModel: {
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
                        this.toasterService.success('Success', 'Pam Evaluate');
                        this.subscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
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
            footprintModel: {
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
            footprintModel: {
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
        this.noteService.addNote(noteObj, JSON.parse(sessionStorage.getItem('msisdn'))).subscribe((success) => {
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
            this.evaluatePam(this.pamID);
        });
    }
}
