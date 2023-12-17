import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ApiRequest } from '../../interface/api-request.interface';
import { HttpService } from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class NotepadService {
    private token = '';
    private loading = new BehaviorSubject(false)
    constructor(private httpService: HttpService) { }
    get loading$() {
        return this.loading;
    }
    getNotes(msisdn: string) {
        return this.notepadRequest(msisdn, '/ccat/notepad/get-all');
    }
    addNote(entry, msisdn: string) {
        return this.notepadRequest(msisdn, '/ccat/notepad/add', entry);
    }
    deleteNotes(entry) {
        return this.notepadRequest(entry.msisdn, '/ccat/notepad/delete', entry);
    }
    notepadRequest(msisdn: string, path: string, entry?) {
        let reqObj: ApiRequest = {
            path,
            payload: {
                token: this.token,
                msisdn,
                ...entry
            },
        };
        return this.httpService.request(reqObj).pipe(indicate(this.loading));
    }
}
