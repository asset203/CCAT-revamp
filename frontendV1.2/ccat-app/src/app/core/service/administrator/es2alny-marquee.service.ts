import {Injectable} from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {take} from 'rxjs/operators';
import {Es2alnyMarquee} from 'src/app/shared/models/es2alny-marquee.interface';
import {indicate} from 'src/app/shared/rxjs/indicate';
import {HttpService} from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class Es2alnyMarqueeService {
    private token = JSON.parse(sessionStorage.getItem('session')).token;
    private loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService) {}
    get loading$() {
        return this.loading;
    }
    get es2alnyMarquee$() {
        return this.httpService
            .request({
                path: '/ccat/marquees/get-all',
                payload: {
                    token: this.token,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    updateAllMarquees(marquees: Es2alnyMarquee[]) {
        return this.httpService
            .request({
                path: '/ccat/marquees/update-all',
                payload: {
                    token: this.token,
                    marquees,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    addMarquee(title: string, description: string) {
        return this.httpService
            .request({
                path: '/ccat/marquees/add',
                payload: {
                    token: this.token,
                    title,
                    description,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    deleteMarquee(marqueeId: number) {
        return this.httpService
            .request({
                path: '/ccat/marquees/delete',
                payload: {
                    token: this.token,
                    id: marqueeId,
                },
            })
            .pipe(take(1), indicate(this.loading));
    }
    deleteAllMarquees(){
        return this.httpService.request({
            path: "/ccat/marquees/delete-all",
            payload:{
                token : this.token
            }
        }).pipe(take(1), indicate(this.loading));
    }
}
