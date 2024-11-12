import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {ConfirmationService} from 'primeng/api';
import {Table} from 'primeng/table';
import {map} from 'rxjs/operators';
import {AdminDynamicPageService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dynamic-page.service';
import {AdminDpPageConfigrationService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-page-configrations.service';
import {ShortDetailedDynamicPage} from 'src/app/shared/models/admin-dynamic-page.model';
import {MessageService} from 'src/app/shared/services/message.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {FileUpload} from 'primeng/fileupload';
import {environment} from 'src/environments/environment';
import {AppConfigService} from 'src/app/core/service/app-config.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
@Component({
    selector: 'app-admin-dynamic-page-detalis',
    templateUrl: './admin-dynamic-page-detalis.component.html',
    styleUrls: ['./admin-dynamic-page-detalis.component.scss'],
    providers: [ConfirmationService],
})
export class AdminDynamicPageDetalisComponent implements OnInit {
    searchText: any;
    constructor(
        private router: Router,
        private adminDynamicPageService: AdminDynamicPageService,
        private pageConfigrationService: AdminDpPageConfigrationService,
        private confirmationService: ConfirmationService,
        private messageService: MessageService,
        private toastrService: ToastService,
        private appConfigsService: AppConfigService,
        private loadingService: LoadingService
    ) {}
    pages: ShortDetailedDynamicPage[];
    loading$ = this.adminDynamicPageService.loading;
    uploadDialog: boolean = false;
    isFileInstallExist: boolean = false;
    loading: boolean = false;
    search = false;
    installedFile;
    isFetchingList$ = this.loadingService.fetching$;
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
        this.loadingService.startFetchingList();
        this.adminDynamicPageService.allDynamicPages$
            .pipe(
                map((res) =>
                    res?.payload?.pagesList.map((page) => {
                        return {
                            id: page.id,
                            pageName: page.pageName,
                        };
                    })
                )
            )
            .subscribe(
                (pages) => {
                    this.pages = pages;
                    this.loadingService.endFetchingList();
                },
                (err) => {
                    this.pages = [];
                    this.loadingService.endFetchingList();
                }
            );
    }
    navigateToAddOrEdit(id: number) {
        this.router.navigate([this.router.url + '/add/' + id]);
    }
    confirmDelete(pageId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(66).message,
            accept: () => {
                this.deleteDynamicPage(pageId);
            },
        });
    }
    deleteDynamicPage(pageId: number) {
        this.pageConfigrationService.deletePage(pageId).subscribe((res) => {
            if (res.statusCode === 0) {
                this.deletePageFormList(pageId);
                this.toastrService.success(this.messageService.getMessage(67).message);
            }
        });
    }
    deletePageFormList(pageId) {
        this.pages = this.pages.filter((el) => el.id !== pageId);
    }
    clear(table: Table) {
        if (table.filters.global['value']) {
            table.filters.global['value'] = '';
        }
        this.searchText = null;
        table.clear();
    }
    downloadDynamicPage(pageId: number) {
        this.adminDynamicPageService.downloadDynamicPage(pageId).subscribe((res: any) => {
            const a = document.createElement('a');
            document.body.appendChild(a);
            const blob: any = new Blob([res], {type: 'octet/stream'});
            const url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = 'Dynamic page.json';
            a.click();
            window.URL.revokeObjectURL(url);
        });
    }
    clearUpload(fileUpload: FileUpload) {
        fileUpload.clear();
        this.isFileInstallExist = false;
        this.installedFile = null;
    }
    onUpload(event, fileUpload: FileUpload) {
        this.loading = true;
        let file = event.files[0];
        let fileFormData: FormData = new FormData();
        fileFormData.append('file', file);
        fileFormData.append('token', JSON.parse(sessionStorage.getItem('session')).token);
        fetch(`${this.appConfigsService.config.apiBaseUrl}/ccat/admin-dynamic-pages/export`, {
            method: 'POST',
            body: fileFormData,
        })
            .then((response) => {
                return response.json();
            })
            .then((data) => {
                if (data.statusCode === 0) {
                    this.toastrService.success('File Uploaded Successfully');
                    this.clearUpload(fileUpload);
                    this.ngOnInit();
                } else {
                    this.toastrService.error(data.statusMessage);
                }
                this.loading = false;
                this.uploadDialog = false;
            })
            .catch((error) => {
                this.loading = false;
                this.toastrService.error('Error', error.statusMessage);
                this.uploadDialog = false;
            });
    }
}
