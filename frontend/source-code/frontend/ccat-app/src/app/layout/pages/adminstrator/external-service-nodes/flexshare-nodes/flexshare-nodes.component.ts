import {ExternalNodesService} from './../../../../../core/service/administrator/external-nodes.service';
import {Component, OnInit} from '@angular/core';
import {take} from 'rxjs/operators';
import {ConfirmationService} from 'primeng/api';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';

@Component({
    selector: 'app-flexshare-nodes',
    templateUrl: './flexshare-nodes.component.html',
    styleUrls: ['./flexshare-nodes.component.scss'],
    providers: [ConfirmationService],
})
export class FlexshareNodesComponent implements OnInit {
    constructor(
        private ExternalNodesService: ExternalNodesService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toasterService: ToastService,
        private fb: FormBuilder
    ) {}
    flexShares;
    tableFlexShares;
    selectedFlexShare;
    updateFlexShareDialog: boolean = false;
    AddFlexShareDialog: boolean = false;
    updateFlexShareForm: FormGroup;
    addFlexShareForm: FormGroup;

    ngOnInit(): void {
        this.getAllFlexShares();
        this.initializeAddFlexShareForm();
        this.initializeUpdateFlexShareForm();
    }
    initializeAddFlexShareForm() {
        this.addFlexShareForm = this.fb.group({
            address: ['', Validators.required],
            concurrentCalls: [1],
            connectionTimeout: [10000, Validators.min(10000)],
            port: [1, Validators.required],
            extraConf: [''],
            numberOfSessions: [1],
            password: ['', Validators.required],
            username: ['', Validators.required],
            schema: [null, Validators.required],
        });
    }
    initializeUpdateFlexShareForm() {
        this.updateFlexShareForm = this.fb.group({
            address: ['', Validators.required],
            concurrentCalls: [Number],
            connectionTimeout: [Number, Validators.min(10000)],
            port: [Number, Validators.required],
            extraConf: [''],
            numberOfSessions: [''],
            password: [''],
            username: ['', Validators.required],
            schema: [null, Validators.required],
            id: [Number],
        });
    }
    getAllFlexShares() {
        this.ExternalNodesService.allFlexShare$.pipe(take(1)).subscribe((res) => {
            this.tableFlexShares = res?.payload?.flexShareHistoryNodeModelNodesList;
            this.flexShares = res?.payload?.flexShareHistoryNodeModelNodesList;
            console.log(this.tableFlexShares);
        });
    }
    getFlexShare(id) {
        this.ExternalNodesService.getFlexShare$(id)
            .pipe(take(1))
            .subscribe((res) => {
                console.log(res);

                const flexShare = res.payload.flexShareHistoryNodeModel;
                this.selectedFlexShare = flexShare;
                this.updateFlexShareForm.patchValue({
                    address: this.selectedFlexShare.address,
                    concurrentCalls: this.selectedFlexShare.concurrentCalls,
                    connectionTimeout: this.selectedFlexShare.connectionTimeout,
                    port: this.selectedFlexShare.port,
                    extraConf: this.selectedFlexShare.extraConf,
                    numberOfSessions: this.selectedFlexShare.numberOfSessions,
                    password: this.selectedFlexShare.password,
                    username: this.selectedFlexShare.username,
                    id: this.selectedFlexShare.id,
                    schema: this.selectedFlexShare.schema,
                });
            });
    }
    addFlexShare() {
        this.ExternalNodesService.addFlexShare$(this.addFlexShareForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.AddFlexShareDialog = false;
                    this.getAllFlexShares();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.addFlexShareForm.reset();
                },
                error: (err) => {
                    this.AddFlexShareDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }
    showUpdateFlexShareDialog(id) {
        this.getFlexShare(id);
        this.updateFlexShareDialog = true;
    }
    ShowAddFlexShareDialog() {
        this.AddFlexShareDialog = true;
    }
    updateFlexShare() {
        this.ExternalNodesService.updateFlexShare$(this.updateFlexShareForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.updateFlexShareDialog = false;
                    this.getAllFlexShares();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.updateFlexShareForm.reset();
                },
                error: (err) => {
                    this.updateFlexShareDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deleteFlexShare(id) {
        this.ExternalNodesService.deleteFlexShare$(id).subscribe({
            next: (res) => {
                if (res?.statusCode === 0) {
                    this.toasterService.success(this.messageService.getMessage(36).message);
                    this.tableFlexShares = this.tableFlexShares.filter((ods) => ods.id !== id);
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
                this.deleteFlexShare(id);
            },
        });
    }
}
