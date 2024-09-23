import { SendSmsService } from './../../../../core/service/customer-care/send-sms.service';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { map, take, tap } from 'rxjs/operators';
import { NotepadService } from 'src/app/core/service/administrator/notepad.service';
import { LanguageService } from 'src/app/core/service/customer-care/language.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { Note } from 'src/app/shared/models/note.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { SubscriberService } from './../../../../core/service/subscriber.service';
import { Subscription } from 'rxjs';

@Component({
    selector: 'app-language',
    templateUrl: './language.component.html',
    styleUrls: ['./language.component.scss'],
})
export class LanguageComponent implements OnInit, OnDestroy {
    loading$ = this.languageService.loading$;
    loadingSubscriber$ = this.SubscriberService.loading$;
    newLanguage;
    selectedLanguage;
    currentLanguage;
    allLanguagesSubject$ = this.languageService.allLanguages$;
    languageForm: FormGroup;
    permissions = {
        getLanguages: false,
        updateLanguages: false,
    };
    ReasonDialog: boolean;
    sendSMS: boolean = true;
    reason;
    notes: Note[] = [];
    subscriberNumber;

    constructor(
        private languageService: LanguageService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private toasterService: ToastService,
        private messageService: MessageService,
        private SubscriberService: SubscriberService,
        private notepadService: NotepadService,
        private footPrintService: FootPrintService,
        private SendSmsService: SendSmsService
    ) { }

    subscriberSubscription = new Subscription();
    ngOnDestroy(): void {
        this.subscriberSubscription.unsubscribe()
    }
    ngOnInit(): void {
        this.setPermissions();
        if (this.permissions.getLanguages) {
            this.languageService.getAllLanguages();
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
        this.createForm();
        this.subscriberSubscription = this.SubscriberService.subscriberSubject.subscribe((subscriber) => {
            if (subscriber) {
                this.subscriberNumber = subscriber.subscriberNumber;
                this.currentLanguage = subscriber.language;
                this.selectedLanguage = { name: this.currentLanguage };
            }


        });

        // footprint
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'language',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            
        };
        this.footPrintService.log(footprintObj);
    }
    createForm() {
        this.languageForm = this.fb.group({
            languageId: ['', Validators.required],
        });
    }
    onSubmit() {
        this.ReasonDialog = true;
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(23, 'getLanguages')
            .set(17, 'updateLanguages');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getLanguages = this.featuresService.getPermissionValue(23);
        this.permissions.updateLanguages = this.featuresService.getPermissionValue(17);
    }

    submitReason() {
        let noteObj = {
            entry: this.reason,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'language',
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
        this.notepadService.addNote(noteObj, this.subscriberNumber).subscribe((success) => {

            this.reason = null;
            const operator = JSON.parse(sessionStorage.getItem('session')).user;
            this.notes.unshift({
                note: this.reason,
                date: new Date().getTime(),
                operator: operator.ntAccount,
            });
            if (this.sendSMS) {

                let smsObject = {
                    actionName: 'Change_Language',
                    templateParam: {
                        oldValue: this.currentLanguage,
                        newValue: this.languageForm.value.languageId.name,
                    },
                };
                console.log(smsObject);
                this.SendSmsService.sendSMS(smsObject).subscribe((res) => {
                    this.languageForm.reset();

                    console.log(res)
                });
            }
        });
        console.log('languageId', this.languageForm.value.languageId.value);
        let data = {
            languageId: this.languageForm.value.languageId.value,
            footprint: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Language',
                sendSms:this.sendSMS?1:0,
                footPrintDetails: [
                    {
                        paramName: 'language',
                        oldValue: this.currentLanguage,
                        newValue: this.languageForm.value.languageId.name,
                    },
                ],
            },
        };
        this.languageService.updateLanguage(data, this.subscriberNumber);
    }
}
