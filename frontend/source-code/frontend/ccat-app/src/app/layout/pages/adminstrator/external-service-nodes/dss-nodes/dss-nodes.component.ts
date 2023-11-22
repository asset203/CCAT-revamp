import {Component, OnInit} from '@angular/core';
import {ExternalNodesService} from './../../../../../core/service/administrator/external-nodes.service';
import {take} from 'rxjs/operators';
import {ConfirmationService} from 'primeng/api';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-dss-nodes',
    templateUrl: './dss-nodes.component.html',
    styleUrls: ['./dss-nodes.component.scss'],
    providers: [ConfirmationService],
})
export class DssNodesComponent implements OnInit {
    constructor(
        private ExternalNodesService: ExternalNodesService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toasterService: ToastService,
        private fb: FormBuilder
    ) {}

    dss;
    tableDSS;
    selectedDSS;
    updateDssDialog: boolean = false;
    AddDssDialog: boolean = false;
    updateDssForm: FormGroup;
    addDssForm: FormGroup;

    ngOnInit(): void {
        this.getAllDSS();
        this.initializeAddDssForm();
        this.initializeUpdateDssForm();
    }
    initializeAddDssForm() {
        this.addDssForm = this.fb.group({
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
    initializeUpdateDssForm() {
        this.updateDssForm = this.fb.group({
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
    getAllDSS() {
        this.ExternalNodesService.allDSS$.pipe(take(1)).subscribe((res) => {
            this.tableDSS = res?.payload?.dssNodesList;
            this.dss = res?.payload?.dssNodesList;
            console.log(this.tableDSS);
        });
    }
    getDSS(id) {
        this.ExternalNodesService.getDSS$(id)
            .pipe(take(1))
            .subscribe((res) => {
                console.log(res);

                const Dss = res.payload.dssNodesModel;
                this.selectedDSS = Dss;
                this.updateDssForm.patchValue({
                    address: this.selectedDSS.address,
                    concurrentCalls: this.selectedDSS.concurrentCalls,
                    connectionTimeout: this.selectedDSS.connectionTimeout,
                    port: this.selectedDSS.port,
                    extraConf: this.selectedDSS.extraConf,
                    numberOfSessions: this.selectedDSS.numberOfSessions,
                    password: this.selectedDSS.password,
                    userName: this.selectedDSS.userName,
                    id: this.selectedDSS.id,
                    schema: this.selectedDSS.schema,
                });
            });
    }
    addDSS() {
        this.ExternalNodesService.addDSS$(this.addDssForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.AddDssDialog = false;
                    this.getAllDSS();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.addDssForm.reset();
                },
                error: (err) => {
                    this.AddDssDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }
    showUpdateDssDialog(id) {
        this.getDSS(id);
        this.updateDssDialog = true;
    }
    ShowAddDssDialog() {
        this.AddDssDialog = true;
    }
    updateDSS() {
        this.ExternalNodesService.updateDSS$(this.updateDssForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.updateDssDialog = false;
                    this.getAllDSS();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.updateDssForm.reset();
                },
                error: (err) => {
                    this.updateDssDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deleteDSS(id) {
        this.ExternalNodesService.deleteDSS$(id).subscribe({
            next: (res) => {
                if (res?.statusCode === 0) {
                    this.toasterService.success(this.messageService.getMessage(36).message);
                    this.tableDSS = this.tableDSS.filter((ods) => ods.id !== id);
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
                this.deleteDSS(id);
            },
        });
    }
}
