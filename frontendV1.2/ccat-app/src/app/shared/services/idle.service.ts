import {Injectable} from '@angular/core';
import {Subject, fromEvent} from 'rxjs';
import { AppConfigService } from 'src/app/core/service/app-config.service';

@Injectable({
    providedIn: 'root',
})
export class IdleService {
    public idle$: Subject<boolean> = new Subject();
    public wake$: Subject<boolean> = new Subject();

    isIdle = false;
    private idleAfterSeconds = this.appConfigsService.config.idleAllowedTimeInSec; //30min
    private countDown;
    constructor(private appConfigsService : AppConfigService) {
        fromEvent(document, 'mousemove').subscribe(() => this.onInteraction());
        fromEvent(document, 'touchstart').subscribe(() => this.onInteraction());
        fromEvent(document, 'keydown').subscribe(() => this.onInteraction());
    }
    onInteraction() {
        if (this.isIdle) {
            this.isIdle = false;
            this.wake$.next(true);
        }

        clearTimeout(this.countDown);
        this.countDown = setTimeout(() => {
            this.isIdle = true;
            this.idle$.next(true);
        }, this.idleAfterSeconds * 1_000);
    }
}
