import {Component, OnDestroy, OnInit} from '@angular/core';
import {FormBuilder, Validators} from '@angular/forms';
import {map, take, tap} from 'rxjs/operators';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {ServiceClassService} from 'src/app/core/service/customer-care/service-class.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {Note} from 'src/app/shared/models/note.interface';
import {serviceClass} from 'src/app/shared/models/service-class.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {SubscriberService} from './../../../../core/service/subscriber.service';
import {SendSmsService} from 'src/app/core/service/customer-care/send-sms.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-service-class',
    templateUrl: './service-class.component.html',
    styleUrls: ['./service-class.component.scss'],
})
export class ServiceClassComponent implements OnInit , OnDestroy {
    constructor(
        private serviceClassService: ServiceClassService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private toasterService: ToastService,
        private messageService: MessageService,
        private SubscriberService: SubscriberService,
        private notepadService: NotepadService,
        private footPrintService: FootPrintService,
        private sendSmsService: SendSmsService
    ) {}
    ngOnDestroy(): void {
        this.subscriberSubscribtion.unsubscribe()
    }

    loading$ = this.serviceClassService.loading$;

    serviceClassForm;
    allServicesSubject$ = this.serviceClassService.allServiceClasses$;
    selectedService: serviceClass;
    currentSubscriber;
    permissions = {
        veiwServiceClass: false,
        editServiceClass: false,
    };
    ReasonDialog: boolean;
    parentDialog: boolean;
    reason;
    notes: Note[] = [];
    subscriberNumber;
    sendSMS=true;
    subscriberSubscribtion = new Subscription()
    ngOnInit(): void {
        this.setPermissions();
        if (this.permissions.veiwServiceClass) {
            this.serviceClassService.getAllServiceClasses();
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }

        this.createForm();

        //this.currentSubscriber = this.serviceClassService.getCurrentSubscriber();
        this.subscriberSubscribtion = this.SubscriberService.subscriber$
            .pipe(
                tap((subscriber) => {this.subscriberNumber =subscriber?.subscriberNumber}),
            )
            .subscribe((subscriber)=>{this.currentSubscriber=subscriber
            console.log("Changed")});

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Service Class',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    createForm() {
        this.serviceClassForm = this.fb.group({
            service: ['', Validators.required],
        });
    }
    onSubmit() {
        if (this.currentSubscriber?.serviceClass?.isGrandfather === true) {
            this.parentDialog = true;
        } else {
            this.ReasonDialog = true;
        }
    }
    changeParent() {
        this.parentDialog = false;
        this.ReasonDialog = true;
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(26, 'veiwServiceClass')
            .set(27, 'editServiceClass');

        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.veiwServiceClass = this.featuresService.getPermissionValue(26);
        this.permissions.editServiceClass = this.featuresService.getPermissionValue(27);
    }
    submitReason() {
        let noteObj = {
            entry: this.reason,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Service Class',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.reason,
                    },
                ],
            },
        };
        this.reason=null;
        console.log("this.sendSMS",this.sendSMS)
        if (this.sendSMS) {
            const smsObj = {
                actionName: 'CHANGE_SERVICECLASS',
                templateParam: {
                    oldValue: this.currentSubscriber?.serviceClass?.name,
                    newValue: this.selectedService.name,
                },
            };
            this.sendSmsService.sendSMS(smsObj).subscribe();
        }
        this.ReasonDialog = false;
        this.notepadService.addNote(noteObj, this.subscriberNumber).subscribe((success) => {
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
            // this.toasterService.success('Success', success.statusMessage);
        });
        if (this.selectedService.name !== this.currentSubscriber.serviceClass.name) {
            let data = {
                currentService: this.currentSubscriber.serviceClass,
                newService: this.serviceClassForm.value.service,
                footprint: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Service Class',
                    sendSms:this.sendSMS?1:0,
                    footPrintDetails: [
                        {
                            paramName: 'Service Class',
                            oldValue: JSON.stringify(this.currentSubscriber.serviceClass) ,
                            newValue: JSON.stringify(this.serviceClassForm.value.service),
                        },
                    ],
                },
            };
            this.serviceClassService.updateServiceClass(data);
            this.serviceClassForm.reset()
        }
    }
}
