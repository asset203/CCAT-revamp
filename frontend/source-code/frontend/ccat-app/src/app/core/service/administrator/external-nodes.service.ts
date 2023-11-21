import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { HttpService } from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class ExternalNodesService {
    constructor(private http: HttpService) { }

    //ODS Requests
    get allODS$(): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        });
    }

    addODS$(odsNode): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/add',
            payload: {
                odsNode,
            },
        });
    }

    getODS$(odsNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/get',
            payload: {
                odsNodeId,
            },
        });
    }

    updateODS$(Ods): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/update',
            payload: {
                odsNode: Ods,
            },
        });
    }

    deleteUser$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/ods-nodes/delete',
            payload: {
                odsNodeId: id,
            },
        });
    }

    //DSS Requests
    get allDSS$(): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        });
    }

    addDSS$(dssNode): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/add',
            payload: {
                dssNode,
            },
        });
    }

    getDSS$(dssNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/get',
            payload: {
                dssNodeId,
            },
        });
    }

    updateDSS$(Dss): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/update',
            payload: {
                odsNode: Dss,
            },
        });
    }

    deleteDSS$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/dss-nodes/delete',
            payload: {
                dssNodeID: id,
            },
        });
    }

    //Air Requests
    get allAIR$(): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        });
    }

    addAIR$(airServer): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/add',
            payload: {
                airServer,
            },
        });
    }

    getAIR$(airServerId): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/get',
            payload: {
                airServerId,
            },
        });
    }

    updateAIR$(Air): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/update',
            payload: {
                airServer: Air,
            },
        });
    }

    deleteAIR$(id): Observable<any> {
        return this.http.request({
            path: '/ccat/air-servers/delete',
            payload: {
                airServerId: id,
            },
        });
    }

    //Air Requests
    get allFlexShare$(): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/get-all',
            payload: {
                token: JSON.parse(sessionStorage.getItem('session')).token,
            },
        });
    }

    addFlexShare$(flexShareHistoryNode): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/add',
            payload: {
                flexShareHistoryNode,
            },
        });
    }

    getFlexShare$(flexShareHistoryNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/get',
            payload: {
                flexShareHistoryNodeId,
            },
        });
    }

    updateFlexShare$(flexShareHistoryNode): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/update',
            payload: {
                flexShareHistoryNode,
            },
        });
    }

    deleteFlexShare$(flexShareHistoryNodeId): Observable<any> {
        return this.http.request({
            path: '/ccat/flex-history-nodes/delete',
            payload: {
                flexShareHistoryNodeId,
            },
        });
    }
}
