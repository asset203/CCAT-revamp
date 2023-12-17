import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { ConfirmationService } from 'primeng/api';
import { Observable } from 'rxjs';
import { Es2alnyMarqueeService } from 'src/app/core/service/administrator/es2alny-marquee.service';
import { SessionService } from 'src/app/core/service/session.service';
import { Es2alnyMarquee } from 'src/app/shared/models/es2alny-marquee.interface';
import { faBullhorn } from '@fortawesome/free-solid-svg-icons';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
@Component({
    selector: 'app-header',
    templateUrl: './header.component.html',
    styleUrls: ['./header.component.scss'],
    providers: [ConfirmationService],
})
export class HeaderComponent implements OnInit {
    faBullhorn = faBullhorn
    // TODO: MANAR
    // 1. Fix the User Menu Positioning and Style

    // boolean to collapse the dropdown
    //[badge]="notificationsItems ? '' + notificationsItems.length + '' : '0'"
    isDropDownCollapsed = false;
    notificationMenuCollapse = false;
    notificationShowPopup = false;
    // boolean for the ngx loader
    notificationItems = [];
    session$: Observable<any>;
    allMarquessSubject = this.sessionService.allMarquess$;
    selectedNotification;
    allMarquess = [];
    subscriber$ = this.subscriberService.subscriber$;
    @Output() closeNotifcationModal = new EventEmitter<boolean>();
    constructor(private sessionService: SessionService,
        private marqueeService: Es2alnyMarqueeService, private router: Router, private subscriberService: SubscriberService) {
        this.session$ = this.sessionService.session$;
    }

    items = [
        {
            label: 'home',
            icon: 'pi pi-home',
            command: (event) => {
                this.router.navigate(['/find-subscriber'])
            }
        },
        {
            label: 'logout',
            icon: 'pi pi-sign-out',
            command: (event) => {
                this.sessionService.logout();
            },
        },
    ];
    notificationsItems = [];
    ngOnInit(): void {
        // this.sessionService.getAllMarquees();
        this.marqueeService.es2alnyMarquee$.subscribe((marquees) => {
            this.allMarquess = marquees?.payload?.marquees;
            marquees?.payload?.marquees.forEach((element) => {
                this.notificationsItems.push({
                    label: `<p class="p-title">${element.title}</p><p class="p-des">${this.subDescription(
                        element.description
                    )}</p>`,
                    escape: false,
                    command: () => {
                        this.openNotification(element);
                    },
                });
            });
            console.log(this.notificationsItems)
        });
    }
    subDescription(description: string) {
        return description.length > 30 ? description.substring(0, 30) + '......' : description;
    }
    openNotification(element: Es2alnyMarquee) {
        this.selectedNotification = element;
        this.notificationShowPopup = true;
        this.notificationMenuCollapse = false;
    }

    logout() {
        this.sessionService.logout();
    }
}
