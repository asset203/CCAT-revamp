import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import { take } from 'rxjs/operators';
import {Defines} from 'src/app/shared/constants/defines';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ToastService} from 'src/app/shared/services/toast.service';
import {CallActivityService} from './customer-care/call-activity.service';
import {CheckCallReasonService} from './customer-care/check-call-reason.service';
import {SubscriberService} from './subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class RouterService {
    permissions = {
        callActivity: false,
    };
    constructor(
        private router: Router,
        private subscriberService: SubscriberService,
        private toastService: ToastService,
        private checkCallActivity: CheckCallReasonService,
        private featuresService: FeaturesService
    ) {
        this.setPermissions();
    }

    public redirectTo(uri: string) {
        this.router.navigateByUrl('/proxy', {skipLocationChange: true}).then(() => this.router.navigate([uri]));
    }

    reset() {
        /*this.setPermissions();
        if (this.permissions.callActivity) {
            if (!sessionStorage.getItem('callReason')) {
                this.checkCallActivityService.checkCallReason().subscribe({
                    next: (resp) => {
                        if (resp.payload) {
                            sessionStorage.setItem('callReason', JSON.stringify(resp.payload))
                            this.toastService.warning('Please add call reason');
                            this.router.navigate(['/customer-care/call-activity']);
                        } else {
                            this.subscriberService.clearSubscriberReset();
                            this.router.navigate(['find-subscriber']);
                        }
                    }
                })

            } else {
                this.toastService.warning('Please add call reason');
                this.router.navigate(['/customer-care/call-activity']);
            }
        } else {
            this.subscriberService.clearSubscriberReset();
            this.router.navigate(['find-subscriber']);
        }*/
        /*this.checkCallActivity.checkCallReason();
        this.checkCallActivity.action.pipe(take(1)).subscribe(action=>{
            console.log("action from a7a ", action)
            if (
                action === Defines.callReasonActions.CALL_ACTIVITY_HAS_NO_PERMISSION ||
                action === Defines.callReasonActions.CALL_ACTIVITY_CHECKED_HAS_NO_REASON
            ) {
                this.subscriberService.clearSubscriberReset();
                this.router.navigate(['find-subscriber']);
            } else if (action === Defines.callReasonActions.CALL_ACTIVITY_HAS_NO_LOCAL_REASON) {
                this.toastService.warning('Please add call reason');
                this.router.navigate(['/customer-care/call-activity']);
            }
        })*/
        const hasCallReason = this.checkCallActivity.checkCallReason();
        if(hasCallReason){
            this.router.navigate(['/customer-care/call-activity']);
        }
        else{
            this.subscriberService.clearSubscriberReset();
            
        }
        
        
    }

    public async refresh() {
        this.subscriberService.refresh();
    }
    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map().set(349, 'callActivity');

        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.callActivity = this.featuresService.getPermissionValue(349);
    }
}
