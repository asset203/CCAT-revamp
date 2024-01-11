import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {tap} from 'rxjs/operators';
import {ServiceOfferingBitModel} from 'src/app/shared/models/service-offering-bit.interface';
import {HttpService} from '../http.service';
import {LoadingService} from 'src/app/shared/services/loading.service';
import {indicate} from 'src/app/shared/rxjs/indicate';

@Injectable({
    providedIn: 'root',
})
export class ServiceOfferingBitsDescService {
    loading = new BehaviorSubject(false);
    isFetchingList$ = this.loadingService.fetching$;
    allServiceOfferingBitsDescSubject = new BehaviorSubject<ServiceOfferingBitModel[]>([]);
    constructor(private http: HttpService, private loadingService: LoadingService) {}

    get allServiceOfferingBitsDesc$(): Observable<any> {
        return this.allServiceOfferingBitsDescSubject.asObservable();
    }

    public getAllServiceOfferingBitsDesc() {
        this.loadingService.startFetchingList();
        this.http
            .request({
                path: '/ccat/service-offering-description/get-all',
                payload: {
                    token: JSON.parse(sessionStorage.getItem('session')).token,
                },
            })
            .pipe(indicate(this.loading))
            .subscribe(
                (res) => {
                    this.loadingService.endFetchingList();
                    this.allServiceOfferingBitsDescSubject.next(res.payload.serviceOfferingBitList);
                },
                (err) => {
                    this.loadingService.endFetchingList();
                    this.allServiceOfferingBitsDescSubject.next([]);
                }
            );
    }

    public updateServiceOfferingBitDesc(serviceOffering: ServiceOfferingBitModel) {
        this.http
            .request({
                path: '/ccat/service-offering-description/update',
                payload: {
                    token: JSON.parse(sessionStorage.getItem('session')).token,
                    serviceOffering,
                },
            })
            .pipe(
                tap((res) => this.getAllServiceOfferingBitsDesc()),
                indicate(this.loading)
            )
            .subscribe();
    }
}
