import {PamAdministrationService} from './../../../../core/service/administrator/pam-administration.service';
import {Component, OnInit, ViewChild} from '@angular/core';
import {map, take, tap} from 'rxjs/operators';
import {Pam} from 'src/app/shared/models/pam';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ConfirmationService} from 'primeng/api';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {PamType} from 'src/app/shared/models/pam-type';
import {Observable} from 'rxjs';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {MessageService} from 'src/app/shared/services/message.service';
import {Table} from 'primeng/table';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {InputNumber} from 'primeng/inputnumber';
import {LoadingService} from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-pam-adminstration',
    templateUrl: './pam-adminstration.component.html',
    styleUrls: ['./pam-adminstration.component.scss'],
    providers: [ConfirmationService],
})
export class PamAdminstrationComponent implements OnInit {
    addPamForm: FormGroup;
    editPamForm: FormGroup;
    pams: Pam[];
    pam$: Observable<Pam>;
    pamTypes$: Observable<PamType[]>;
    selectedPamTypeId: number;
    filterSearch: string;
    tablePams: Pam[];
    addDialog: boolean;
    editDialog: boolean;

    search = false;
    @ViewChild('idInput') idInputFocus: InputNumber;
    permissions = {
        getAllPams: false,
        getPam: false,
        addPam: false,
        updatePam: false,
        deletePam: false,
        getAllPamsType: false,
    };
    searchText: any;
    isFetchingList$ = this.loadingService.fetching$;
    loading$ = this.pamAdminService.loading$;
    constructor(
        private pamAdminService: PamAdministrationService,
        private toasterService: ToastService,
        private confirmationService: ConfirmationService,
        private fb: FormBuilder,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private validationService: ValidationService,
        private loadingService: LoadingService
    ) {}
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
        this.initializeAddPamForm();
        this.initializeEditPamForm();
        if (this.permissions.getAllPams) {
            this.loadingService.startFetchingList();
            this.pamAdminService.getAllPams$
                .pipe(
                    take(1),
                    map((res) => res?.payload?.pams)
                )
                .subscribe(
                    (res) => {
                        this.pams = res;
                        this.tablePams = this.pams;
                        this.loadingService.endFetchingList();
                    },
                    (error) => {
                        this.pams = [];
                        this.tablePams = [];
                    }
                );
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
        if (this.permissions.getAllPamsType) {
            this.pamTypes$ = this.pamAdminService.getAllPamsTypes$.pipe(
                take(1),
                map((res) => res?.payload?.pamTypes)
            );
        } else {
            this.toasterService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }

    initializeAddPamForm() {
        this.addPamForm = this.fb.group({
            id: [null, Validators.required],
            description: ['', [Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]],
            pamTypeId: [this.selectedPamTypeId],
        });
    }
    initializeEditPamForm() {
        this.editPamForm = this.fb.group({
            id: ['', Validators.required],
            description: ['', [Validators.required, Validators.pattern(this.validationService.whiteSpacesPattern)]],
            pamTypeId: [this.selectedPamTypeId],
        });
    }

    toggleAddDialog() {
        this.addDialog = true;
    }
    toggleEditDialog(id) {
        this.getPam(id);
        this.editDialog = true;
    }

    filterTable() {
        if (this.filterSearch && !this.selectedPamTypeId) {
            const filteredTable = this.pams.filter((pams) =>
                pams.description.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
            );
            this.tablePams = filteredTable;
        } else if (this.filterSearch && this.selectedPamTypeId) {
            const filteredTable = this.pams.filter(
                (pams) =>
                    pams.description.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase()) &&
                    pams.pamTypeId == this.selectedPamTypeId
            );
            this.tablePams = filteredTable;
        } else if (!this.filterSearch && this.selectedPamTypeId) {
            const filteredTable = this.pams.filter((pams) => pams.pamTypeId == this.selectedPamTypeId);
            this.tablePams = filteredTable;
        } else {
            this.tablePams = this.pams;
        }
    }

    onChange() {
        this.initializeAddPamForm();
        if (this.selectedPamTypeId) {
            const filteredTable = this.pams.filter((pams) => pams.pamTypeId == this.selectedPamTypeId);
            this.tablePams = filteredTable;
        }
    }

    addPam() {
        this.pamAdminService
            .addPam$(this.addPamForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.addDialog = false;
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', this.messageService.getMessage(52).message);
                        this.addPamForm.reset();
                        this.pamAdminService.getAllPams$
                            .pipe(
                                take(1),
                                map((res) => res?.payload?.pams)
                            )
                            .subscribe((res) => {
                                this.pams = res;
                                const filteredTable = this.pams.filter(
                                    (pams) => pams.pamTypeId == this.selectedPamTypeId
                                );
                                this.tablePams = filteredTable;
                            });
                    }
                },
                error: (err) => {
                    this.addDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }
    getPam(pam) {
        this.editPamForm.setValue({
            id: pam.id,
            description: pam.description,
            pamTypeId: pam.pamTypeId,
        });
    }

    updatePam() {
        this.pamAdminService
            .updatePam$(this.editPamForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.editDialog = false;
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', this.messageService.getMessage(53).message);
                        this.editPamForm.reset();
                        if (this.permissions.getAllPams) {
                            this.pamAdminService.getAllPams$
                                .pipe(
                                    take(1),
                                    map((res) => res?.payload?.pams)
                                )
                                .subscribe((res) => {
                                    this.pams = res;
                                    this.tablePams = this.pams;
                                });
                        }
                    }
                },
                error: (err) => {
                    this.editDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deletePam(id, pamTypeId) {
        this.pamAdminService
            .deletePam$(id, pamTypeId)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    if (res.statusCode === 0) {
                        this.toasterService.success('Success', this.messageService.getMessage(54).message);
                        this.tablePams = this.tablePams.filter((classes) => classes.id !== id);
                    }
                },
                error: (err) => {
                    this.toasterService.error('Error', err);
                },
            });
    }
    confirm(id, pamTypeId) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(55).message,
            accept: () => {
                this.deletePam(id, pamTypeId);
            },
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(111, 'getAllPams')
            .set(115, 'addPam')
            .set(112, 'getPAm')
            .set(113, 'updatePam')
            .set(114, 'deletePam')
            .set(116, 'getAllPamsTypes');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getAllPams = this.featuresService.getPermissionValue(111);
        this.permissions.addPam = this.featuresService.getPermissionValue(115);
        this.permissions.getPam = this.featuresService.getPermissionValue(112);
        this.permissions.updatePam = this.featuresService.getPermissionValue(113);
        this.permissions.deletePam = this.featuresService.getPermissionValue(114);
        this.permissions.getAllPamsType = this.featuresService.getPermissionValue(116);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
    onOpenDialog() {
        this.idInputFocus.input.nativeElement.focus();
    }
}
