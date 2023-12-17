import {SessionService} from 'src/app/core/service/session.service';
import {Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgxSmartModalService} from 'ngx-smart-modal';
import {MenuItem} from 'primeng/api';
import {take, filter} from 'rxjs/operators';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {map} from 'rxjs/operators';
import {Subscription} from 'rxjs';
import {Router} from '@angular/router';
import {RouterService} from 'src/app/core/service/router.service';
import {PanelMenu} from 'primeng/panelmenu';
import {faThumbtack} from '@fortawesome/free-solid-svg-icons';
@Component({
    selector: 'app-navbar',
    templateUrl: './navbar.component.html',
    styleUrls: ['./navbar.component.scss'],
})
export class NavbarComponent implements OnInit, OnDestroy {
    // TODO: MANAR
    // 1. Make the navigation list dynamic
    // 2. Make the icons font based not image based. (Except the Logo)
    @ViewChild('panelMenu') panel: PanelMenu;
    faThumbtack = faThumbtack;
    sideMenu: boolean = true;
    closeMenu: boolean;
    userMenu: MenuItem[];
    userMenuShow: MenuItem[];
    subscriberTracing: Subscription;
    filterSearch;
    customerBalances = this.subscriberService.subscriber$;
    constructor(
        private subscriberService: SubscriberService,
        private toasterService: ToastService,
        public ngxSmartModalService: NgxSmartModalService,
        private sessionService: SessionService,
        private routerService: RouterService
    ) {
        this.sessionService.session$
            .pipe(
                map((res) => {
                    console.log(res);
                    this.userMenu = res.userProfile.menus;
                    for (let i = 0; i < this.userMenu.length; i++) {
                        for (let index = 0; index < this.userMenu[i].items.length; index++) {
                            this.userMenu[i].items[index].command = () => {
                                this.userMenu[i].items[index].id = '' + index;
                                this.toggleCloseMenu();
                                console.log(this.userMenu[i]);
                            };
                        }
                        if (this.userMenu[i].label == 'Customer Care') {
                            this.userMenu[i].icon = 'pi pi-pw pi-comments';
                            this.userMenu[i].expanded = false;
                        } else if (this.userMenu[i].label == 'Administrators') {
                            this.userMenu[i].icon = 'pi pi-pw pi-users';
                            this.userMenu[i].expanded = false;
                        } else {
                            this.userMenu[i].icon = 'pi pi-pw pi-chart-bar';
                            this.userMenu[i].expanded = false;
                        }
                        this.userMenu[i].id = '' + i;
                        this.userMenu[i].routerLinkActiveOptions;
                        this.userMenuShow = JSON.parse(JSON.stringify(this.userMenu));
                    }
                    console.log(this.userMenuShow);
                }),
                take(1)
            )
            .subscribe();
    }
    ngOnDestroy(): void {
        this.subscriberTracing.unsubscribe();
    }

    ngOnInit(): void {
        this.subscriberTracing = this.subscriberService.trackingRouteState.subscribe((data) => {
            if (data) {
                this.sideMenu = false;
                this.expandMenu();
            }
        });
    }
    pinSideMenu() {
        this.subscriberService.sidebarOpened.next(!this.sideMenu)
        this.sideMenu = !this.sideMenu;
        this.expandMenu();
    }
    expandMenu() {
        this.panel.model = this.panel.model.map((el) => {
            return {
                ...el,
                expanded: false,
            };
        });
    }
    showToaster() {
        this.customerBalances.pipe(take(1)).subscribe({
            next: (res) => {
                if (res === null) {
                    this.ngxSmartModalService.getModal('myModal').open();
                }
            },
        });
    }

    filterMenu() {
        console.log('lenght ', this.filterSearch.length);
        const tempUserMenu = JSON.parse(JSON.stringify(this.userMenu));

        if (this.filterSearch.length > 0) {
            for (let index = 0; index < this.userMenu.length; index++) {
                const filteredMenuItems = tempUserMenu[index].items.filter((item) =>
                    item.label.toLocaleLowerCase().match(this.filterSearch.toLocaleLowerCase())
                );
                tempUserMenu[index].items = filteredMenuItems;
            }
            this.userMenuShow = tempUserMenu;
        } else {
            this.userMenuShow = JSON.parse(JSON.stringify(this.userMenu));
        }
        this.userMenuShow = this.userMenuShow.filter((el) => el.items.length > 0);
    }

    toggleCloseMenu() {
        this.closeMenu = true;
        setTimeout(() => {
            this.closeMenu = false;
            this.expandMenu();
        }, 1000);
    }
    reset() {
        this.routerService.reset();
    }
    leave() {
        if (!this.sideMenu) {
            this.userMenuShow = this.userMenuShow.map((el) => {
                return {
                    ...el,
                    expanded: false,
                };
            });
        }
    }
}
