import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';

@Injectable({
    providedIn: 'root',
})
export class LoadingService {
    private isLoading = new BehaviorSubject<boolean>(false);
    private isFetchingList = new BehaviorSubject<boolean>(false);

    startLoading() {
        this.isLoading.next(true);
    }
    endLoading() {
        this.isLoading.next(false);
    }
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
