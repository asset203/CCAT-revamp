import { Component, OnDestroy, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ConfirmationService } from 'primeng/api';
import { Table } from 'primeng/table';
import { Observable, Subscription } from 'rxjs';
import { map, take } from 'rxjs/operators';
import { NotepadService } from 'src/app/core/service/administrator/notepad.service';
import { OffersService } from 'src/app/core/service/customer-care/offers.service';
import { FootPrintService } from 'src/app/core/service/foot-print.service';
import { FootPrint } from 'src/app/shared/models/foot-print.interface';
import { Note } from 'src/app/shared/models/note.interface';
import { Offer } from 'src/app/shared/models/offer.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { MessageService } from 'src/app/shared/services/message.service';
import { ToastService } from 'src/app/shared/services/toast.service';
import { SubscriberService } from './../../../../core/service/subscriber.service';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-offers-new',
    templateUrl: './offers-new.component.html',
    styleUrls: ['./offers-new.component.scss'],
    providers: [ConfirmationService],
})
export class OffersNewComponent implements OnInit , OnDestroy {
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
        private loadingService : LoadingService

    ) { }
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
    search=false;
    searchText:string;
    isopened : boolean
    isopenedNav :boolean
    isOpenedSubscriber : Subscription
    isOpenedNavSubscriber :Subscription
    getAllOffer() {
        if (this.permissions.getAllOffers) {
            this.loadingService.startFetchingList()
            this.offersService
                .getAllOffers()
                
                .subscribe({
                    next: (offers) => {
                        this.loadingService.endFetchingList()
                        this.offers = offers?offers:[];
                        

                    },
                    error: (err) => {
                        this.loadingService.endFetchingList()
                        this.toasterService.error(err, 'Error');
                        this.offers=[];
                        

                    }
                });
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    ngOnInit(): void {
        this.isOpenedSubscriber =this.SubscriberService.giftOpened.subscribe(isopened=>{
            this.isopened = isopened
        })
        this.isOpenedNavSubscriber =this.SubscriberService.sidebarOpened.subscribe(isopened=>{
            this.isopenedNav = isopened
        })
        this.setPermissions();
        this.createForm();
        this.getAllOffer();
        this.offersService?.getOffersLookup();
        
        this.SubscriberService.subscriber$
            .pipe(
                map((subscriber) => subscriber?.subscriberNumber),
                take(1)
            )
            .subscribe((res) => (this.subscriberNumber = res));
        // foot print load
        let footprintObj: FootPrint = {
            machineName: +sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'Offers New',
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
        };
        this.footPrintService.log(footprintObj);
    }
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe()
        this.isOpenedNavSubscriber.unsubscribe()
    }
    createForm() {
        this.offersForm = this.fb.group({
            offer: [{}, Validators.required],
            startDate: [this.today],
            expiryDate: [''],
        });
    }
    selectOffer() {
        this.offerTypeSelected = this.offersForm.value.offer.offerType;
        for (let index = 0; index < this.offers.length; index++) {
            if (this.offersForm.value.offer.offerId === this.offers[index].offerId) {
                this.toasterService.warning("This Offer isn't avaliable to be added.");
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
    }

    onSubmit() {
        this.isAddModalOpened = false;
        this.ReasonDialog = true;
        this.offersFormValue = this.offersForm.value;
        console.log(this.updateFlag);
    }

    submitReason() {
        // keep the expiry date in a variable as it's reference changes so saving it for footprint
        let expiryDate = new Date(this?.selectedOffer?.expiryDate);
        let noteObj = {
            entry: this.reason,
            footPrint: {
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

        let offer: Offer = this.offersFormValue.offer;
        offer.startDate = this.offersFormValue.startDate?.getTime();
        offer.expiryDate = this.offersFormValue.expiryDate?.getTime();

        if (this.updateFlag === true) {
            let reqObj = {
                offer: this.offersFormValue.offer,
                footPrint: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
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
            console.log(reqObj)
            this.offersService.updateOffer(reqObj).subscribe(
                {
                    next: (resp) => {
                        if (resp?.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(46).message);
                            this.getAllOffer();
                            this.SubscriberService.refresh();
                        }
                    }
                }
            );
        } else {
            let reqObj = {
                offer,
                footPrint: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
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
            this.offersService.addOffer(reqObj).subscribe(
                {
                    next: (resp) => {
                        console.log(resp)
                        if (resp?.statusCode === 0) {
                            this.toasterService.success(this.messageService.getMessage(44).message);
                            this.getAllOffer()
                            // this.subscriberService.refresh();
                        }
                    }
                }
            );
        }

        // delete selected offer
        let offer2: Offer;
        this.selectedOffer = offer2;
    }

    updateOffer(offer) {
        //
        this.isAddModalOpened = true;
        this.selectedOffer = offer;
        this.offersForm.setValue({
            offer: offer,
            startDate: new Date(offer.startDate),
            expiryDate: offer.expiryDate == undefined ? null : new Date(offer.expiryDate),
        });
        this.offerTypeSelected = offer.offerType;
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
        if(table.filters.global["value"]){
            table.filters.global["value"]=''
        }  
        this.searchText=null;
        table.clear()
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
            footPrint: {
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
        this.offersService.deleteOffer(reqObj).subscribe(
            {
                next: (resp) => {
                    if (resp?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(45).message);
                        this.getAllOffer();
                        this.SubscriberService.refresh();
                    }
                }
            }
        );;
    }
    confirmDelete(offer) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(33).message,
            accept: () => {
                this.deleteOffer(offer)
            },
        });
    }
}
