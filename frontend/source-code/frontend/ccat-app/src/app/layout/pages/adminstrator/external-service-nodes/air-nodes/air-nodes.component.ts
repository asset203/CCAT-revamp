import {ExternalNodesService} from './../../../../../core/service/administrator/external-nodes.service';
import {Component, OnInit} from '@angular/core';
import {take} from 'rxjs/operators';
import {ConfirmationService} from 'primeng/api';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {faCircleCheck, faXmarkCircle} from '@fortawesome/free-solid-svg-icons';
@Component({
    selector: 'app-air-nodes',
    templateUrl: './air-nodes.component.html',
    styleUrls: ['./air-nodes.component.scss'],
    providers: [ConfirmationService],
})
export class AirNodesComponent implements OnInit {
    constructor(
        private ExternalNodesService: ExternalNodesService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toasterService: ToastService,
        private fb: FormBuilder
    ) {}
    air;
    tableAIR;
    selectedAIR;
    updateAirDialog: boolean = false;
    AddAirDialog: boolean = false;
    updateAirForm: FormGroup;
    addAirForm: FormGroup;
    faCircleCheck = faCircleCheck;
    faXmarkCircle = faXmarkCircle;

    ngOnInit(): void {
        this.getAllAIR();
        this.initializeAddAirForm();
        this.initializeUpdateAirForm();
    }
    initializeAddAirForm() {
        this.addAirForm = this.fb.group({
            agentName: ['', Validators.required],
            authorization: ['', Validators.required],
            capabilityValue: [''],
            failuresCount: [''],
            firstFailureDate: [''],
            host: ['', Validators.required],
            isDown: ['', Validators.required],
            url: ['', Validators.required],
        });
    }
    initializeUpdateAirForm() {
        this.updateAirForm = this.fb.group({
            agentName: ['', Validators.required],
            authorization: ['', Validators.required],
            capabilityValue: [''],
            failuresCount: [''],
            firstFailureDate: [''],
            host: ['', Validators.required],
            isDown: ['', Validators.required],
            url: ['', Validators.required],
            id: [Number],
        });
    }
    getAllAIR() {
        this.ExternalNodesService.allAIR$.pipe(take(1)).subscribe((res) => {
            this.tableAIR = res?.payload?.airServerList;
            this.air = res?.payload?.airServerList;
            console.log(this.tableAIR);
        });
    }
    getAIR(id) {
        this.ExternalNodesService.getAIR$(id)
            .pipe(take(1))
            .subscribe((res) => {
                console.log(res);

                const Air = res.payload.dssNodesModel;
                this.selectedAIR = Air;
                this.updateAirForm.patchValue({
                    agentName: this.selectedAIR.agentName,
                    authorization: this.selectedAIR.authorization,
                    capabilityValue: this.selectedAIR.capabilityValue,
                    failuresCount: this.selectedAIR.failuresCount,
                    firstFailureDate: this.selectedAIR.firstFailureDate,
                    host: this.selectedAIR.host,
                    isDown: this.selectedAIR.isDown,
                    url: this.selectedAIR.url,
                    id: this.selectedAIR.id,
                });
            });
    }
    addAIR() {
        if (this.addAirForm.get('isDown').value == true) {
            this.addAirForm.controls['isDown'].setValue(1);
        } else {
            this.addAirForm.controls['isDown'].setValue(0);
        }
        this.ExternalNodesService.addAIR$(this.addAirForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.AddAirDialog = false;
                    this.getAllAIR();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.addAirForm.reset();
                },
                error: (err) => {
                    this.AddAirDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }
    showUpdateAirDialog(id) {
        this.getAIR(id);
        this.updateAirDialog = true;
    }
    ShowAddAirDialog() {
        this.AddAirDialog = true;
    }
    updateAIR() {
        if (this.updateAirForm.get('isDown').value == true) {
            this.updateAirForm.controls['isDown'].setValue(1);
        } else {
            this.updateAirForm.controls['isDown'].setValue(0);
        }
        this.ExternalNodesService.updateAIR$(this.updateAirForm.value)
            .pipe(take(1))
            .subscribe({
                next: (res) => {
                    this.updateAirDialog = false;
                    this.getAllAIR();
                    if (res?.statusCode === 0) {
                        this.toasterService.success(this.messageService.getMessage(34).message);
                    }
                    this.updateAirForm.reset();
                },
                error: (err) => {
                    this.updateAirDialog = false;
                    this.toasterService.error('Error', err);
                },
            });
    }

    deleteAIR(id) {
        this.ExternalNodesService.deleteAIR$(id).subscribe({
            next: (res) => {
                if (res?.statusCode === 0) {
                    this.toasterService.success(this.messageService.getMessage(36).message);
                    this.tableAIR = this.tableAIR.filter((ods) => ods.id !== id);
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
                this.deleteAIR(id);
            },
        });
    }
}
