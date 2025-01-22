import {Component, ElementRef, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Subscription} from 'rxjs';
import {map, switchMap, tap} from 'rxjs/operators';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {Note} from 'src/app/shared/models/note.interface';
import {ConfirmationService} from 'primeng/api';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {Table} from 'primeng/table';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {LoadingService} from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-notepad',
    templateUrl: './notepad.component.html',
    styleUrls: ['./notepad.component.scss'],
    providers: [ConfirmationService],
})
export class NotepadComponent implements OnInit, OnDestroy {
    noteForm: FormGroup = new FormGroup({
        note: new FormControl('', [Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]),
    });
    notes: Note[];
    intialNotes: Note[];
    subscriberNumber = '';
    getNotepadSubscription: Subscription = new Subscription();
    loading$ = this.notepadService.loading$;
    getNotesPermission: boolean;
    addNotePermission: boolean;
    deleteNotesPermission: boolean;
    addNoteDialog = false;
    @ViewChild('in') in: ElementRef;
    search = false;
    searchText: string;
    isFetchingList$ = this.loadingService.fetching$;

    constructor(
        private notepadService: NotepadService,
        private subscriberService: SubscriberService,
        private toastrServices: ToastrService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private validationService: ValidationService,
        private footPrintService: FootPrintService,
        private loadingService: LoadingService
    ) {}
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    onSearchInput(inputValue: string): void {
        if (!inputValue) {
            this.dt.clear();
            this.dt.reset();
            this.dt.filterGlobal('', 'contains');
            this.dt.first = 0;
        } else {
            this.dt.filterGlobal(inputValue, 'contains');
        }
    }
    ngOnInit(): void {
        this.setPermissions();
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
        });
        if (this.getNotesPermission) {
            this.loadingService.startFetchingList();
            this.getNotepadSubscription = this.subscriberService?.subscriber$
                .pipe(
                    tap((subscriber) => {
                        this.subscriberNumber = subscriber?.subscriberNumber;
                    }),
                    switchMap((subscriber) => {
                        return this.notepadService.getNotes(subscriber?.subscriberNumber);
                    }),
                    map((res) => res?.payload?.entries),
                    map((notes) => {
                        return notes?.map((note) => {
                            return {
                                ...note,
                                date: new Date(note.date),
                            };
                        });
                    })
                )
                .subscribe(
                    (notes) => {
                        this.loadingService.endFetchingList();
                        this.notes = notes ? [...notes] : [];
                        this.intialNotes = notes ? [...notes] : [];
                    },
                    (err) => {
                        this.loadingService.endFetchingList();
                        this.notes = [];
                        this.intialNotes = [];
                    }
                );
        } else {
            this.toastrServices.error(this.messageService.getMessage(401).message, 'Error');
        }

        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'notepad',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(35, 'canGetNotes')
            .set(36, 'addNote')
            .set(37, 'deleteNotes');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.getNotesPermission = this.featuresService.getPermissionValue(35);
        this.addNotePermission = this.featuresService.getPermissionValue(36);
        this.deleteNotesPermission = this.featuresService.getPermissionValue(37);
    }
    addEntryToNotepad() {
        let noteObj = {
            entry: this.noteForm.value['note'],
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Notepad',
                footPrintDetails: [
                    {
                        paramName: 'entry',
                        oldValue: '',
                        newValue: this.noteForm.value['note'],
                    },
                ],
            },
        };
        this.notepadService.addNote(noteObj, this.subscriberNumber).subscribe(
            (success) => {
                if (success?.statusCode === 0) {
                    const operator = JSON.parse(sessionStorage.getItem('session')).user;
                    this.notes.unshift({
                        note: this.noteForm.value['note'],
                        date: new Date().getTime(),
                        operator: operator.ntAccount,
                    });
                    this.toastrServices.success(this.messageService.getMessage(15).message);
                    this.noteForm.reset();
                    this.hideDialog();
                }
            },
            (error) => {
                this.noteForm.reset();
            }
        );
    }
    deleteNotepad() {
        let noteObj = {
            msisdn: this.subscriberNumber,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Notepad',
                footPrintDetails: [
                    {
                        paramName: 'msisdn',
                        oldValue: '',
                        newValue: this.subscriberNumber,
                    },
                ],
            },
        };

        this.notepadService.deleteNotes(noteObj).subscribe((success) => {
            if (success?.statusCode === 0) {
                this.notes = [];
                this.toastrServices.success(this.messageService.getMessage(16).message);
            }
        });
    }
    confirmDelete() {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(17).message,
            accept: () => {
                this.deleteNotepad();
            },
        });
    }
    ngOnDestroy(): void {
        this.getNotepadSubscription.unsubscribe();
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
    hideDialog() {
        this.noteForm.reset();

        this.addNoteDialog = false;
    }
    focusInput() {
        this.in.nativeElement.focus();
    }
}
