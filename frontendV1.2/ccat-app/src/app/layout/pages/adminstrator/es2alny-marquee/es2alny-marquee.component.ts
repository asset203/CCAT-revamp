import { Component, OnInit } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { ConfirmationService } from 'primeng/api';
import { map } from 'rxjs/operators';
import { Es2alnyMarqueeService } from 'src/app/core/service/administrator/es2alny-marquee.service';
import { Es2alnyMarquee } from 'src/app/shared/models/es2alny-marquee.interface';
import { FeaturesService } from 'src/app/shared/services/features.service';
import { LoadingService } from 'src/app/shared/services/loading.service';
import { MessageService } from 'src/app/shared/services/message.service';
@Component({
    selector: 'app-es2alny-marquee',
    templateUrl: './es2alny-marquee.component.html',
    styleUrls: ['./es2alny-marquee.component.scss'],
    providers: [ConfirmationService],
})
export class Es2alnyMarqueeComponent implements OnInit {
    constructor(
        private es2alnyMarqueeService: Es2alnyMarqueeService,
        private toastrService: ToastrService,
        private confirmationService: ConfirmationService,
        private featuresService: FeaturesService,
        private messageService: MessageService,
        private loadingService : LoadingService
    ) { }
    loading$ = this.es2alnyMarqueeService.loading$;
    es2alnyMarqueeList: Es2alnyMarquee[];
    addMarqueeDialoag = false;
    editMode = false;
    editingMarquee: Es2alnyMarquee = {
        title: '',
        description: '',
    };
    permissions = {
        getMarquees: false,
        addMarquee: false,
        deleteAllMarquees: false,
        deleteMarquee: false,
        updateAllMarquees: false,
    };
    isFetchingList$ = this.loadingService.fetching$;
    ngOnInit(): void {
        this.setPermissions();
        if (this.permissions.getMarquees) {
            this.loadingService.startFetchingList()
            this.es2alnyMarqueeService.es2alnyMarquee$
                .pipe(map((res) => res?.payload?.marquees))
                .subscribe((marquees) => {
                    this.es2alnyMarqueeList = marquees;
                    this.loadingService.endFetchingList();
                },err=>{
                    this.es2alnyMarqueeList = [];
                    this.loadingService.endFetchingList();
                });
        } else {
            this.toastrService.error(this.messageService.getMessage(401).message, 'Error');
        }
    }
    updateAllMarques() {
        this.es2alnyMarqueeService.updateAllMarquees(this.es2alnyMarqueeList).subscribe((res) => {
            if (res?.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(9).message, 'Success');
            }
        });
    }
    addMarquee() {
        this.editingMarquee = { title: '', description: '' };
        this.editMode = false;
        this.addMarqueeDialoag = true;
    }
    editMarquee(marqueeId: number) {
        this.editingMarquee = this.es2alnyMarqueeList.filter((marquee) => marquee.id === marqueeId)[0];
        this.addMarqueeDialoag = true;
        this.editMode = true;
    }
    confirmDeleteMarque(marqueeId: number) {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(10).message,
            accept: () => {
                this.deleteMarquee(marqueeId);
            },
        });
    }
    confirmDeleteAllMarquees() {
        this.confirmationService.confirm({
            message: this.messageService.getMessage(11).message,
            accept: () => {
                this.deleteAllMarquees();
            },
        });
    }
    deleteMarquee(marqueeId: number) {
        this.es2alnyMarqueeService.deleteMarquee(marqueeId).subscribe((res) => {
            if (res?.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(12).message);
                this.es2alnyMarqueeList = this.es2alnyMarqueeList.filter((marquee) => {
                    return marquee.id !== marqueeId;
                });
            }
        });
    }
    deleteAllMarquees() {
        this.es2alnyMarqueeService.deleteAllMarquees().subscribe((res) => {
            console.log(res?.statusCode)
            if (res?.statusCode === 0) {
                this.toastrService.success(this.messageService.getMessage(13).message);
                this.es2alnyMarqueeList = [];
            }
        });
    }
    submit(marquee: Es2alnyMarquee) {
        // check if title is duplicated
        if (
            this.es2alnyMarqueeList.find(
                (el) => el.title.toLocaleLowerCase().trim() === marquee.title.toLocaleLowerCase().trim()
            )&&!this.editMode
        ) {
            this.toastrService.error(this.messageService.getMessage(59).message);
        } else {
            if (!this.editMode) {
                this.es2alnyMarqueeService.addMarquee(marquee.title, marquee.description).subscribe((res) => {
                    this.addMarqueeDialoag = false;
                    if (res?.statusCode === 0) {
                        this.toastrService.success(this.messageService.getMessage(14).message);
                    }
                    this.ngOnInit();
                });
            } else {
                const updatedIndex = this.es2alnyMarqueeList.findIndex((list) => list.id === this.editingMarquee.id);
                this.es2alnyMarqueeList[updatedIndex] = {
                    id: this.editingMarquee.id,
                    title: marquee.title,
                    description: marquee.description,
                };
                this.updateAllMarques();
                this.addMarqueeDialoag = false;
            }
        }
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(59, 'getAllMarquess')
            .set(60, 'updateAllMarquees')
            .set(61, 'addMarquee')
            .set(62, 'deleteMarquee')
            .set(63, 'deleteAllMarquees');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getMarquees = this.featuresService.getPermissionValue(59);
        this.permissions.updateAllMarquees = this.featuresService.getPermissionValue(60);
        this.permissions.addMarquee = this.featuresService.getPermissionValue(61);
        this.permissions.deleteMarquee = this.featuresService.getPermissionValue(62);
        this.permissions.deleteAllMarquees = this.featuresService.getPermissionValue(63);
    }
}
