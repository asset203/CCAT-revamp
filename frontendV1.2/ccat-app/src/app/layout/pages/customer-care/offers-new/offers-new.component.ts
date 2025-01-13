import {Component, OnDestroy, OnInit, ViewChild, ElementRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {Observable, of, Subscription} from 'rxjs';
import {map, take} from 'rxjs/operators';
import {NotepadService} from 'src/app/core/service/administrator/notepad.service';
import {OffersService} from 'src/app/core/service/customer-care/offers.service';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {Note} from 'src/app/shared/models/note.interface';
import {Offer} from 'src/app/shared/models/offer.interface';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {SubscriberService} from './../../../../core/service/subscriber.service';
import {LoadingService} from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-offers-new',
    templateUrl: './offers-new.component.html',
    styleUrls: ['./offers-new.component.scss'],
    providers: [ConfirmationService],
})
export class OffersNewComponent implements OnInit, OnDestroy {
    constructor(
        private offersService: OffersService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private toasterService: ToastService,
        private messageService: MessageService,
        private SubscriberService: SubscriberService,
        private notepadService: NotepadService,
        private confirmationService: ConfirmationService,
        private footPrintService: FootPrintService,
        private loadingService: LoadingService
    ) {}
    @ViewChild('dt') dt: Table | undefined; // Declare a reference to the table
    isFetchingList$ = this.loadingService.fetching$;
    allOffersSubject$ = this.offersService.allOffers$;
    offerLookupSubject$ = this.offersService.offersLookup$;
    offersForm: FormGroup;
    offerTypeSelected;
    today = new Date();
    selectedOffer: Offer;
    loading$ = this.offersService.loading$;
    offers;
    updateFlag = false;
    permissions = {
        getAllOffers: false,
        addOffer: false,
        updateOffer: false,
        deleteOffer: false,
    };
    ReasonDialog: boolean;
    reason;
    notes: Note[] = [];
    subscriberNumber;
    offer;
    isAddModalOpened;
    offersFormValue;
    search = false;
    searchText: string;
    isopened: boolean;
    isopenedNav: boolean;
    isOpenedSubscriber: Subscription;
    subscriberSearchSubscription: Subscription;
    isOpenedNavSubscriber: Subscription;
    dateFlag: boolean;

    getAllOffer() {
        if (this.permissions.getAllOffers) {
            this.loadingService.startFetchingList();
            this.offersService
                .getAllOffers()

                .subscribe({
                    next: (offers) => {
                        this.loadingService.endFetchingList();
                        this.offers = offers ? offers : [];
                        this.offerLookupSubject$.subscribe((res) => {
                            /*this.offers = this.offers.map((el) => {
                                el.description = res.filter((element) => el.offerId === element.offerId)[0].offerDesc;
                            });*/
                            let newOffers = [];
                            this.offers.forEach((element) => {
                                const selectedLockup = res.filter((item) => item.offerId === element.offerId) || [];
                                console.log('selectedLockup', selectedLockup);
                                let newElement = {
                                    ...element,
                                    description: null,
                                };
                                if (selectedLockup.length > 0) {
                                    newElement = {
                                        ...element,
                                        description: selectedLockup[0].offerDesc ? selectedLockup[0]?.offerDesc : null,
                                    };
                                }

                                newOffers.push(newElement);
                            });
                            this.offers = [...newOffers];
                        });
                    },
                    error: (err) => {
                        this.loadingService.endFetchingList();
                        this.toasterService.error(err, 'Error');
                        this.offers = [];
                    },
                });
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    ngOnInit(): void {
        this.isOpenedSubscriber = this.SubscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
        });
        this.isOpenedNavSubscriber = this.SubscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
        });
        this.setPermissions();
        this.createForm();
        this.offersService?.getOffersLookup();

        this.subscriberSearchSubscription = this.SubscriberService.subscriber$
            .pipe(map((subscriber) => subscriber?.subscriberNumber))
            .subscribe((res) => {
                this.subscriberNumber = res;
                this.getAllOffer();
            });
        // foot print load
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Offers New',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
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
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
        this.subscriberSearchSubscription.unsubscribe();
    }
    createForm() {
        this.offersForm = this.fb.group({
            offer: [{}, Validators.required],
            startDate: [],
            expiryDate: [''],
        });
    }
    onDateSelect(event: any, formControl: string) {
        /*const selectedDate = event;
        const correctedDate = new Date(
            Date.UTC(selectedDate.getFullYear(), selectedDate.getMonth(), selectedDate.getDate())
        );
        this.offersForm.controls[formControl].setValue(correctedDate);*/
    }
    selectOffer() {
        this.offerTypeSelected = this.offersForm.value.offer.offerType;
        for (let index = 0; index < this.offers.length; index++) {
            if (this.offersForm.value.offer.offerId === this.offers[index].offerId) {
                this.toasterService.warning('This Offer already exists.');
                this.offersForm.reset();
                break;
            } else {
                this.offersForm.patchValue({
                    startDate: '',
                    expiryDate: '',
                });
            }
        }
    }

    onOpenAddDialog() {
        this.isAddModalOpened = true;
        this.updateFlag = false;
        this.dateFlag = false;
        this.offersForm.reset();
    }

    onSubmit() {
        this.isAddModalOpened = false;
        this.ReasonDialog = true;
        this.offersFormValue = this.offersForm.value;
        console.log(this.updateFlag);
    }

    submitReason(enterClick?: boolean) {
        // keep the expiry date in a variable as it's reference changes so saving it for footprint
        if ((enterClick && this.reason) || !enterClick) {
            let expiryDate = new Date(this?.selectedOffer?.expiryDate);
            let noteObj = {
                entry: this.reason,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Offers New',
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
            // add notepad
            this.notepadService.addNote(noteObj, this.subscriberNumber).subscribe((success) => {
                const operator = JSON.parse(sessionStorage.getItem('session')).user;
                this.notes.unshift({
                    note: this.reason,
                    date: new Date().getTime(),
                    operator: operator.ntAccount,
                });
            });
            console.log('hii', this.offersFormValue);
            let offer: any = this.offersFormValue.offer;
            offer.startDate = this.offersFormValue.startDate
                ? new Date(
                      new Date(
                          this.offersFormValue.startDate.getTime() -
                              this.offersFormValue.startDate.getTimezoneOffset() * 60000
                      ).toISOString()
                  ).getTime()
                : null;
            offer.expiryDate = this.offersFormValue.expiryDate ? new Date(
                new Date(
                    this.offersFormValue.expiryDate.getTime() -
                    this.offersFormValue.expiryDate.getTimezoneOffset() * 60000
                ).toISOString()
            ).getTime() : null;

            if (this.updateFlag === true) {
                let reqObj = {
                    offer,
                    footprintModel: {
                        machineName: sessionStorage.getItem('machineName')
                            ? sessionStorage.getItem('machineName')
                            : null,
                        profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                        pageName: 'Offers New',
                        footPrintDetails: [
                            {
                                paramName: 'startDate',
                                oldValue: new Date(this?.selectedOffer?.startDate),
                                newValue: new Date(this.offersFormValue.offer.startDate),
                            },
                            {
                                paramName: 'expiryDate',
                                oldValue: expiryDate,
                                newValue: new Date(this.offersFormValue.offer.expiryDate),
                            },
                            {
                                paramName: 'Offer Type',
                                oldValue: this?.selectedOffer?.offerType,
                                newValue: this.offersFormValue.offer.offerType,
                            },
                            {
                                paramName: 'Offer Description',
                                oldValue: this?.selectedOffer?.offerDesc,
                                newValue: this.offersFormValue.offer.offerDesc,
                            },
                            {
                                paramName: 'Offer Type ID',
                                oldValue: this.offersFormValue.offer.offerTypeId,
                                newValue: this.offersFormValue.offer.offerTypeId,
                            },
                        ],
                    },
                };
                console.log(reqObj);
                this.offersService.updateOffer(reqObj).subscribe({
                    next: (resp) => {
                        if (resp?.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(46).message);
                            this.getAllOffer();
                            this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                        }
                    },
                });
            } else {
                let reqObj = {
                    offer,
                    footprintModel: {
                        machineName: sessionStorage.getItem('machineName')
                            ? sessionStorage.getItem('machineName')
                            : null,
                        profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                        pageName: 'Offers New',
                        footPrintDetails: [
                            {
                                paramName: 'startDate',
                                oldValue: '',
                                newValue: this.offersFormValue.startDate,
                            },
                            {
                                paramName: 'expiryDate',
                                oldValue: '',
                                newValue: this.offersFormValue.expiryDate,
                            },
                            {
                                paramName: 'Offer Type',
                                oldValue: '',
                                newValue: this.offersFormValue.offer.offerType,
                            },
                            {
                                paramName: 'Offer Description',
                                oldValue: '',
                                newValue: this.offersFormValue.offer.offerDesc,
                            },
                            {
                                paramName: 'Offer Type ID',
                                oldValue: '',
                                newValue: this.offersFormValue.offer.offerTypeId,
                            },
                        ],
                    },
                };
                this.offersService.addOffer(reqObj).subscribe({
                    next: (resp) => {
                        console.log(resp);
                        if (resp?.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(44).message);
                            this.getAllOffer();
                            this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                        }
                    },
                });
            }

            // delete selected offer
            let offer2: Offer;
            this.selectedOffer = offer2;
        }
    }

    updateOffer(offer) {
        this.dateFlag = false;
        this.isAddModalOpened = true;
        this.selectedOffer = offer;
        this.offersForm.setValue({
            offer: offer,
            startDate: new Date(offer.startDate),
            expiryDate: offer.expiryDate == undefined ? null : new Date(offer.expiryDate),
        });
        this.offerTypeSelected = offer.offerType;
        if (new Date(offer.startDate).getTime() < this.today.getTime()) {
            this.dateFlag = true;
        }
        this.updateFlag = true;
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(46, 'getAllOffers')
            .set(47, 'addOffer')
            .set(48, 'updateOffer')
            .set(49, 'deleteOffer');

        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAllOffers = this.featuresService.getPermissionValue(46);
        this.permissions.addOffer = this.featuresService.getPermissionValue(47);
        this.permissions.updateOffer = this.featuresService.getPermissionValue(48);
        this.permissions.deleteOffer = this.featuresService.getPermissionValue(49);
    }
    clear(table: Table) {
        if (table.filters) {
            table.filters = {};
        }
        this.searchText = null;
        table.clear();
    }
    hideDialog() {
        let offer: Offer;
        this.selectedOffer = offer;
        this.offerTypeSelected = '';
        this.offersForm.reset();
    }

    deleteOffer(offer) {
        let reqObj = {
            offerId: offer.offerId,
            footprintModel: {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'Offers New',
                footPrintDetails: [
                    {
                        paramName: 'Offer Type',
                        oldValue: '',
                        newValue: offer.offerType,
                    },
                    {
                        paramName: 'Offer Description',
                        oldValue: '',
                        newValue: offer.offerDesc,
                    },
                    {
                        paramName: 'Offer Type ID',
                        oldValue: '',
                        newValue: offer.offerTypeId,
                    },
                ],
            },
        };
        this.offersService.deleteOffer(reqObj).subscribe({
            next: (resp) => {
                if (resp?.statusCode === 0) {
                    this.toasterService.success(this.messageService.getMessage(45).message);
                    this.getAllOffer();
                    this.SubscriberService.loadSubscriber(JSON.parse(sessionStorage.getItem('msisdn')));
                }
            },
        });
    }
    confirmDelete(offer) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(33).message,
            accept: () => {
                this.deleteOffer(offer);
            },
        });
    }
}
