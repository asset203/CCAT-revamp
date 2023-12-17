import { Injectable } from '@angular/core';
import { ActivatedRoute, ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { map, tap } from 'rxjs/operators';
import { ToastService } from 'src/app/shared/services/toast.service';
import { RouterService } from '../service/router.service';
import { SubscriberService } from '../service/subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class SubscriberGuard implements CanActivate {
    constructor(private router: Router
        , private subscriberService: SubscriberService
        , private toastService: ToastService) { }
    canActivate(
        route: ActivatedRouteSnapshot,
        state: RouterStateSnapshot
    ): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
        return this.subscriberService.subscriber$.pipe(
            map((data) => {
                if (!data && !JSON.parse(sessionStorage.getItem('msisdn'))) {
                    this.subscriberService.trackingRouteState.next(state.url);
                    this.toastService.warning("Please search for subscriber")
                    this.router.navigate(['find-subscriber']);
                }
                return true;
            })
        );
    }
}
