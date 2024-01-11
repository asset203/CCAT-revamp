import {ExternalNodesService} from './../../../../../core/service/administrator/external-nodes.service';
import {Component, OnInit} from '@angular/core';
import {take} from 'rxjs/operators';
import {ConfirmationService} from 'primeng/api';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import { LoadingService } from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-ods-nodes',
    templateUrl: './ods-nodes.component.html',
    styleUrls: ['./ods-nodes.component.scss'],
    providers: [ConfirmationService],
})
export class OdsNodesComponent implements OnInit { 
    constructor(
        private ExternalNodesService: ExternalNodesService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toasterService: ToastService,
        private fb: FormBuilder,
        private loadingService : LoadingService
    ) {}
    ods;
    tableODS;
    selectedODS;
    updateOdsDialog: boolean = false;
    AddOdsDialog: boolean = false;
    updateOdsForm: FormGroup;
    addOdsForm: FormGroup;
    isFetchingList$ = this.loadingService.fetching$;
    ngOnInit(): void {
        this.getAllODS();
        this.initializeAddOdsForm();
        this.initializeUpdateOdsForm();
    }
    initializeAddOdsForm() {
        this.addOdsForm = this.fb.group({
            address: ['', Validators.required],
            concurrentCalls: [1],
            connectionTimeout: [10000, Validators.min(10000)],
            port: [1, Validators.required],
            extraConf: [''],
            numberOfSessions: [1],
            password: ['', Validators.required],
            userName: ['', Validators.required],
            schema: [null, Validators.required],
        });
    }
    initializeUpdateOdsForm() {
        this.updateOdsForm = this.fb.group({
            address: ['', Validators.required],
            concurrentCalls: [Number],
            connectionTimeout: [Number, Validators.min(10000)],
            port: [Number, Validators.required],
            extraConf: [''],
            numberOfSessions: [''],
            password: [''],
            userName: ['', Validators.required],
            schema: [null, Validators.required],
            id: [Number],
        });
    }
    getAllODS() {
        this.loadingService.startFetchingList()
        this.ExternalNodesService.allODS$.pipe(take(1)).subscribe((res) => {
            this.tableODS = res?.payload?.odsNodesModelList;
            this.ods = res?.payload?.odsNodesModelList;
            this.loadingService.endFetchingList();
        },err=>{
            this.tableODS=[];
            this.ods=[];
            this.loadingService.endFetchingList();
        });
    }
    getODS(id) {
        this.ExternalNodesService.getODS$(id)
            .pipe(take(1))
            .subscribe((res) => {
                console.log(res);

                const Ods = res.payload.odsNodesModel;
                this.selectedODS = Ods;
                this.updateOdsForm.patchValue({
                    address: this.selectedODS.address,
                    concurrentCalls: this.selectedODS.concurrentCalls,
                    connectionTimeout: this.selectedODS.connectionTimeout,
                    port: this.selectedODS.port,
                    extraConf: this.selectedODS.extraConf,
                    numberOfSessions: this.selectedODS.numberOfSessions,
                    password: this.selectedODS.password,
                    userName: this.selectedODS.userName,
                    id: this.selectedODS.id,
                    schema: this.selectedODS.schema,
                });
            });
    }
    addODS() {
        this.ExternalNodesService.addODS$(this.addOdsForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.AddOdsDialog = false;
                    this.getAllODS();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.addOdsForm.reset();
                },
                error: (err) => {
                    this.AddOdsDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }
    showUpdateOdsDialog(id) {
        this.getODS(id);
        this.updateOdsDialog = true;
    }
    ShowAddOdsDialog() {
        this.AddOdsDialog = true;
    }
    updateODS() {
        this.ExternalNodesService.updateODS$(this.updateOdsForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.updateOdsDialog = false;
                    this.getAllODS();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.updateOdsForm.reset();
                },
                error: (err) => {
                    this.updateOdsDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deleteODS(id) {
        this.ExternalNodesService.deleteUser$(id).subscribe({
            next: (res) => {
                if (res?.statusCode === 0) {
                    this.toasterService.success(this.messageService.getMessage(36).message);
                    this.tableODS = this.tableODS.filter((ods) => ods.id !== id);
                }
            },
            error: (err) => {
                this.toasterService.error('Error', err);
            },
        });
    }

    confirm(id) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(37).message,
            accept: () => {
                this.deleteODS(id);
            },
        });
    }
}
