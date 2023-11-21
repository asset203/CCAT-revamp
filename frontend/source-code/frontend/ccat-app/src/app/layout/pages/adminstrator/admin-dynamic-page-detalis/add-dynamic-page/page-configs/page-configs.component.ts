import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Subscription} from 'rxjs';
import {AdminDynamicPageService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dynamic-page.service';
import {AdminDpPageConfigrationService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-page-configrations.service';
import {ToastService} from 'src/app/shared/services/toast.service';

@Component({
    selector: 'app-page-configs',
    templateUrl: './page-configs.component.html',
    styleUrls: ['./page-configs.component.scss'],
})
export class PageConfigsComponent implements OnInit, OnDestroy {
    pageConfigsForm: FormGroup;
    @Input() addMode;
    @Input() pageId;
    @Output() openAddStepDialog = new EventEmitter<void>();
    pageSubscription: Subscription = new Subscription();
    constructor(
        private fb: FormBuilder,
        private pageConfigrationService: AdminDpPageConfigrationService,
        private dynamicPageService: AdminDynamicPageService,
        private toastrService: ToastService
    ) {}

    ngOnInit(): void {
        this.initForm();
        this.pageSubscription = this.pageConfigrationService.page$.subscribe((pageInfo) => {
            this.pageConfigsForm.patchValue(pageInfo);
            if (pageInfo.pageId) {
                this.pageConfigsForm.get('privilegeName').disable();
            }
        });
    }
    get pageIdFormControl() {
        return this.pageConfigsForm.get('pageId').value;
    }
    initForm() {
        this.pageConfigsForm = this.fb.group({
            pageName: [null, [Validators.required]],
            privilegeName: [{value: null, disabled: this.addMode ? false : true}, [Validators.required]],
            requireSubscriber: [false],
            pageId: [null],
        });
    }
    sumbitPageInfo() {
        if (!this.pageIdFormControl) {
            this.pageConfigrationService.addPage(this.pageConfigsForm.getRawValue()).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.dynamicPageService.setDisabledAddStep(false);
                    this.toastrService.success('Page Added Successfully');
                    this.openAddStepDialog.emit();
                }
            });
        } else {
            this.pageConfigrationService.editPage(this.pageConfigsForm.getRawValue().pageName, this.pageConfigsForm.getRawValue().requireSubscriber).subscribe((res) => {
                if (res.statusCode === 0) {
                    this.toastrService.success('Page Edited Successfully');
                }
            });
        }
    }
    ngOnDestroy(): void {
        this.pageSubscription.unsubscribe();
    }
}
