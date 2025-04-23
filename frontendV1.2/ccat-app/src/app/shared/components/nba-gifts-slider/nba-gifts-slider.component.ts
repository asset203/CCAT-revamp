import {ConstantPool} from '@angular/compiler';
import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
import {Router} from '@angular/router';
import {ToastrService} from 'ngx-toastr';
import {BehaviorSubject, combineLatest, Observable, Subject, Subscription} from 'rxjs';
import {filter, map, switchMap, take, tap, withLatestFrom} from 'rxjs/operators';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {HttpService} from 'src/app/core/service/http.service';
import {StorageService} from 'src/app/core/service/storage.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FootPrint} from '../../models/foot-print.interface';
import {gifts} from '../../models/gifts.interface';
import {indicate} from '../../rxjs/indicate';
import {ViewChild} from '@angular/core';
import {Carousel} from 'primeng/carousel';
import {FeaturesService} from '../../services/features.service';

@Component({
    selector: 'app-nba-gifts-slider',
    templateUrl: './nba-gifts-slider.component.html',
    styleUrls: ['./nba-gifts-slider.component.scss'],
})
export class NbaGiftsSliderComponent implements OnInit {
    // giftsData$: Observable<any>;
    action$ = new BehaviorSubject(false);
    showRejected = false;
    subscribeSubscribtion: Subscription;
    giftsEdited: gifts[] = [];
    giftsShow = [];
    page = 0;
    isopened: boolean;
    isopenedNav: boolean;
    classNameCon = '';
    isOpenedSubscriber: Subscription;
    isOpenedNavSubscriber: Subscription;

    loading$ = new BehaviorSubject(false);
    autoplayInterval: number = 4000;
    @ViewChild('giftCarousel') giftCarousel!: Carousel;

    permissions = {
        acceptNBA: false,
        sendNBA: false,
    };
    pauseCarousel() {
        //console.log('pause clickkkkkkkkkkkk');
        this.giftCarousel.stopAutoplay(); // Set interval to 0 to stop autoplay
    }

    resumeCarousel() {
        // console.log('resume clickkkkkkkkkkkk');
        this.giftCarousel.startAutoplay(); // Restore interval to resume autoplay
    }

    gifts: gifts[] = [];
    giftsCounter = new EventEmitter<number>();
    // items = [1, 2, 3];
    nbaInterfaceSelector = JSON.parse(sessionStorage.getItem('nbaInterfaceSelector'));
    giftsSource$ = (msisdn) =>
        this.httpService
            .request({
                path: '/ccat/nba/get-all',
                payload: {
                    token: this.storageService.getItem('session').token,
                    msisdn,
                },
            })
            .pipe(
                tap((result) => {
                    console.log('result of gifts: ', result);
                    this.subscriberService.giftsCounter.next(result?.payload?.length > 0 ? result?.payload?.length : 0);
                }),
                indicate(this.loading$)
            );
    giftsResponse$ = (msisdn, type, giftShortCode, wlist?: any, item?) =>
        this.httpService.request({
            path: `/ccat/nba/${type}`,
            payload: {
                token: this.storageService.getItem('session').token,
                msisdn,
                giftShortCode,
                wlist,
                username: JSON.parse(sessionStorage.getItem('session')).user.ntAccount,
            },
        });

    giftsRejectResponse$ = (msisdn, type, giftShortCode, giftSeqId, wlist?: any, item?) =>
        this.httpService.request({
            path: `/ccat/nba/${type}`,
            payload: {
                token: this.storageService.getItem('session').token,
                msisdn,
                giftShortCode,
                giftSeqId,
                ...(type !== 'reject' && {wlist}),
                username: JSON.parse(sessionStorage.getItem('session')).user.ntAccount,
            },
        });

    giftsData$ = this.subscriberService.subscriber$.pipe(
        map((msisdn) => msisdn?.subscriberNumber),
        switchMap((msisdn) =>
            combineLatest([
                this.giftsSource$(msisdn).pipe(
                    map((resp) => {
                        console.log('%ctest dkfhjksduhgtfsdjhfgdshjgfh', 'color: red', resp);
                        console.log('actiom ', this.action$.getValue());
                        console.log('%cresponse payload : ', 'color:orange', resp.payload);

                        const giftsShowlist = [...resp?.payload].map((gift) => {
                            return {
                                ...gift,
                                giftDescription: gift.giftDescription.replace(
                                    /(\b(https?|ftp|file):\/\/[-A-Z0-9+&@#\/%?=~_|!:,.;]*[-A-Z0-9+&@#\/%=~_|])/gi,
                                    function (url) {
                                        return '<a target="_blank" href="' + url + '">' + url + '</a>';
                                    }
                                ),
                            };
                        });

                        this.giftsShow = giftsShowlist;
                        console.log('this.giftsShow', this.giftsShow);
                        return resp?.payload;
                    })
                ),
                this.action$,
            ]).pipe(map(([items, isRejected]) => items?.filter((item) => item?.isRejectedGift == this.showRejected)))
        )
    );
    disable = false;
    constructor(
        private subscriberService: SubscriberService,
        private httpService: HttpService,
        private toasterService: ToastrService,
        private storageService: StorageService,
        private footPrintService: FootPrintService,
        private router: Router,
        private featuresService: FeaturesService
    ) {}

    ngOnInit(): void {
        this.setPermissions();
        this.isOpenedSubscriber = this.subscriberService.giftOpened.subscribe((isopened) => {
            this.isopened = isopened;
            this.setResponsiveTableWidth();
        });
        this.isOpenedNavSubscriber = this.subscriberService.sidebarOpened.subscribe((isopened) => {
            this.isopenedNav = isopened;
            this.setResponsiveTableWidth();
        });
    }
    setResponsiveTableWidth() {
        if (this.isopened && this.isopenedNav) {
            this.classNameCon = 'table-width';
        } else if (this.isopened && !this.isopenedNav) {
            this.classNameCon = 'table-width-1';
        } else if (!this.isopened && this.isopenedNav) {
            this.classNameCon = 'table-width-3';
        } else {
            this.classNameCon = 'table-width-2';
        }
    }
    respondToGift(type, code, giftSeqId, wlist?: any, item?) {
        this.disable = true;
        setTimeout(() => {
            this.disable = false;
        }, 8000);
        let successMsg = '';
        if (type === 'accept') {
            this.subscriberService.subscriber$
                .pipe(
                    map((msisdn) => msisdn.subscriberNumber),
                    switchMap((msisdn) => this.giftsResponse$(msisdn, type, code, wlist, item)),
                    take(1)
                )
                .subscribe({
                    next: (resp) => {
                        successMsg = 'Accepted';
                        if (resp.statusCode === 0) {
                            this.toasterService.success('Success', `Gift ${successMsg}`);
                        }
                    },
                });
        } else {
            this.subscriberService.subscriber$
                .pipe(
                    map((msisdn) => msisdn.subscriberNumber),
                    switchMap((msisdn) => this.giftsRejectResponse$(msisdn, type, code, giftSeqId, wlist, item)),
                    take(1)
                )
                .subscribe({
                    next: (resp) => {
                        if (type === 'reject') {
                            successMsg = 'rejected';
                        } else {
                            successMsg = 'sent';
                        }
                        if (resp.statusCode === 0) {
                            this.toasterService.success('Success', `Gift ${successMsg}`);
                        }
                    },
                });
        }
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: this.router.url.split('/')[2],
            msisdn: JSON.parse(sessionStorage.getItem('msisdn')),
            footPrintDetails: [
                {
                    paramName: 'Gift Short Code',
                    oldValue: '',
                    newValue: code,
                },
                {
                    paramName: 'MSISDN',
                    oldValue: '',
                    newValue: JSON.parse(sessionStorage.getItem('msisdn')),
                },
            ],
        };
        this.footPrintService.log(footprintObj);
    }
    mfn(item) {
        console.log(item);
    }
    setPermissions() {
        let nbaPermissions: Map<number, string> = new Map().set(7, 'acceptNBA').set(12, 'acceptNBA');
        this.featuresService.checkUserPermissions(nbaPermissions);
        this.permissions.acceptNBA = this.featuresService.getPermissionValue(7);
        this.permissions.sendNBA = this.featuresService.getPermissionValue(12);
    }
    ngOnDestroy(): void {
        this.isOpenedSubscriber.unsubscribe();
        this.isOpenedNavSubscriber.unsubscribe();
    }
}
