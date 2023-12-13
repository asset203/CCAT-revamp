import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LoadingService {
    private isFetchingList = new BehaviorSubject<boolean>(false);
    startFetchingList() {
        this.isFetchingList.next(true);
    }
    endFetchingList() {
        this.isFetchingList.next(false);
    }
    get fetching$() {
        return this.isFetchingList.asObservable();
    }
    constructor() {}
}
