import {Component, OnDestroy, Input, OnInit, Output} from '@angular/core';
import {ActivatedRoute, NavigationEnd, Router} from '@angular/router';
import {NgxSmartModalService} from 'ngx-smart-modal';
import {ToastrService} from 'ngx-toastr';
import { Subscription } from 'rxjs';
import {filter} from 'rxjs/operators';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-subscriber-info',
    templateUrl: './subscriber-info.component.html',
    styleUrls: ['./subscriber-info.component.scss'],
})
export class SubscriberInfoComponent implements OnInit , OnDestroy {
    opened: boolean = true;
    viewGifts: boolean = true;
    openedOps = this.subscriberService.giftOpened;
    @Input() isNotSubscriberAdmin: boolean;
    // subscriberProduct = this.subscriberService.subscriberProduct$;
    // subscriberSearch = this.subscriberService.subscriber$;
    subscriber;
    products;

    viewNBAModal: boolean = false;
    viewHOSIModal: boolean = false;
    giftsCounter$ = this.subscriberService.giftsCounter;
    giftNumber;
    giftSubscription = new Subscription();
    constructor(
        private router: Router,
        private subscriberService: SubscriberService,
        private featuresService: FeaturesService,
        public ngxSmartModalService: NgxSmartModalService
    ) {}
    ngOnDestroy(): void {
        this.giftSubscription.unsubscribe();
    }

    ngOnInit(): void {
        this.giftSubscription=this.giftsCounter$.subscribe(number=>{
            this.giftNumber = number
        })
        let findSubscriberPermissions: Map<number, string> = new Map().set(9, 'viewNBAModal').set(10, 'viewHOSIModal');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.viewNBAModal = this.featuresService.getPermissionValue(9);
        this.viewHOSIModal = this.featuresService.getPermissionValue(10);

        this.router.events.pipe(filter((event) => event instanceof NavigationEnd)).subscribe((item: any) => {
            if (item.url.includes('/customer-care/subscriber-admin')) {
                this.viewGifts = false;
            } else {
                this.viewGifts = true;
            }
        });
        this.subscriberService.subscriber$.subscribe((res) => {
            console.log('subscriberrrrrrrr', res);

            this.subscriber = res;
        });
        this.subscriberService.subscriberProduct$.subscribe((res) => {
            console.log('producttttttttttt', res);
            this.products = res;
        });
    }
    closeModal(giftsModal) {
        giftsModal.close();
        giftsModal.dismiss();
    }
    openPopup(){
        if(this.giftNumber>0){
            this.ngxSmartModalService.getModal('giftsModal').open()
        }
        
    }
    toggleModal(){
        this.openedOps.next(!this.opened)
        this.opened = !this.opened
        
    }
}
