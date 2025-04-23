import {Component, HostListener, OnDestroy, OnInit} from '@angular/core';
import {Event, NavigationEnd, Router} from '@angular/router';
import {NgxSmartModalService} from 'ngx-smart-modal';
import {filter, take, takeUntil} from 'rxjs/operators';
import {SessionService} from './core/service/session.service';
import {StorageService} from './core/service/storage.service';
import {SubscriberService} from './core/service/subscriber.service';
import {IdleService} from './shared/services/idle.service';
import {ToastService} from './shared/services/toast.service';
import {Subject} from 'rxjs';

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss'],
})
export class AppComponent implements OnInit, OnDestroy {
    session$ = this.sessionService.session$;
    subscriber$ = this.subscriberService.subscriber$;
    loggedIn = this.StorageService.signedIn;
    isAdministrationUrl = false;
    isNotSubscriberAdmin = false;
    subscriber: any | null = null;
    showGifts = true;
    private destroy$ = new Subject<void>();

    constructor(
        private sessionService: SessionService,
        private subscriberService: SubscriberService,
        private StorageService: StorageService,
        public ngxSmartModalService: NgxSmartModalService,
        private toastr: ToastService,
        private router: Router,
        private idleService: IdleService
    ) {
        this.idleService.idle$.subscribe((s) => {
            if (this.StorageService.getItem('session')) {
                this.toastr.error('User Inactive');
                this.sessionService.logout();
            }
        });
        this.idleService.wake$.subscribe((s) => console.log('im awake!'));
    }

    ngOnInit(): void {
        const msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        if (msisdn) {
            console.log('APP COMPONENT MSISDN');
            this.subscriberService.loadSubscriber(msisdn);
        }
        this.router.events
            .pipe(filter((event: Event): event is NavigationEnd => event instanceof NavigationEnd))
            .subscribe((event: NavigationEnd) => {
                console.log('event.url', event.url);
                sessionStorage.setItem('route', event.url);
                if (event.url.includes('/admin')) {
                    this.isAdministrationUrl = true;
                } else {
                    this.isAdministrationUrl = false;
                }
                if (event.url.includes('/subscriber-admin')) {
                    this.isNotSubscriberAdmin = false;
                } else {
                    this.isNotSubscriberAdmin = true;
                }
                if (event.url.includes('/dss-reports')) {
                    this.showGifts = false;
                } else if (event.url.includes('/dynamic-page') || event.url.includes('/advanced')) {
                    this.showGifts = false;
                } else {
                    this.showGifts = true;
                }
            });
        const savedRoute = sessionStorage.getItem('route');
        if (savedRoute) {
            if (savedRoute === '/') {
                this.router.navigateByUrl('/find-subscriber');
            } else {
                this.router.navigateByUrl(savedRoute);
            }
        }

        this.subscriberService.subscriber$
            .pipe(takeUntil(this.destroy$)) // or takeUntil if you need lifecycle management
            .subscribe((s) => (this.subscriber = s));
    }

    ngOnDestroy(): void {
        // Emit and complete the destroy subject
        this.destroy$.next();
        this.destroy$.complete();
    }
}
