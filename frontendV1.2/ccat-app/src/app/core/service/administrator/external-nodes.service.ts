import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { HttpService } from '../http.service';
import { indicate } from 'src/app/shared/rxjs/indicate';

@Injectable({
    providedIn: 'root',
})
export class ExternalNodesService {
    constructor(private http: HttpService) { }
    loading = new BehaviorSubject(false)
    //ODS Requests
    get allODS$(): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        }).pipe(indicate(this.loading));
    }

    addODS$(odsNode): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/add',
            payload: {
                odsNode,
            },
        }).pipe(indicate(this.loading));
    }

    getODS$(odsNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/get',
            payload: {
                odsNodeId,
            },
        }).pipe(indicate(this.loading));
    }

    updateODS$(Ods): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/update',
            payload: {
                odsNode: Ods,
            },
        }).pipe(indicate(this.loading));
    }

    deleteUser$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/delete',
            payload: {
                odsNodeId: id,
            },
        }).pipe(indicate(this.loading));
    }

    //DSS Requests
    get allDSS$(): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        }).pipe(indicate(this.loading));
    }

    addDSS$(dssNode): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/add',
            payload: {
                dssNode,
            },
        }).pipe(indicate(this.loading));
    }

    getDSS$(dssNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/get',
            payload: {
                dssNodeId,
            },
        }).pipe(indicate(this.loading));
    }

    updateDSS$(Dss): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/update',
            payload: {
                odsNode: Dss,
            },
        }).pipe(indicate(this.loading));
    }

    deleteDSS$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/delete',
            payload: {
                dssNodeID: id,
            },
        }).pipe(indicate(this.loading));
    }

    //Air Requests
    get allAIR$(): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        }).pipe(indicate(this.loading));
    }

    addAIR$(airServer): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/add',
            payload: {
                airServer,
            },
        }).pipe(indicate(this.loading));
    }

    getAIR$(airServerId): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/get',
            payload: {
                airServerId,
            },
        }).pipe(indicate(this.loading));
    }

    updateAIR$(Air): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/update',
            payload: {
                airServer: Air,
            },
        }).pipe(indicate(this.loading));
    }

    deleteAIR$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/delete',
            payload: {
                airServerId: id,
            },
        }).pipe(indicate(this.loading));
    }

    //Air Requests
    get allFlexShare$(): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        }).pipe(indicate(this.loading));
    }

    addFlexShare$(flexShareHistoryNode): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/add',
            payload: {
                flexShareHistoryNode,
            },
        }).pipe(indicate(this.loading));
    }

    getFlexShare$(flexShareHistoryNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/get',
            payload: {
                flexShareHistoryNodeId,
            },
        }).pipe(indicate(this.loading));
    }

    updateFlexShare$(flexShareHistoryNode): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/update',
            payload: {
                flexShareHistoryNode,
            },
        }).pipe(indicate(this.loading));
    }

    deleteFlexShare$(flexShareHistoryNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/delete',
            payload: {
                flexShareHistoryNodeId,
            },
        }).pipe(indicate(this.loading));
    }
}
