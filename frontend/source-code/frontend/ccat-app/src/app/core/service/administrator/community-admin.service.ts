import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Community } from 'src/app/shared/models/Community.model';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { ToastService } from 'src/app/shared/services/toast.service';
import { HttpService } from '../http.service';

@Injectable({
    providedIn: 'root',
})
export class CommunityAdminService {
    loading = new BehaviorSubject(false);
    constructor(private httpService: HttpService, private toastService: ToastService) { }
    get allCommunities$() {
        return this.httpService
            .request({
                path: '/ccat/community-admin/get-all',
            })
            .pipe(indicate(this.loading));
    }
    addCommunityAdmin(addedCommunity: Community) {
     
        
        return this.httpService
            .request({
                path: '/ccat/community-admin/add',
                payload: {
                    addedCommunity,
                },
            })
            .pipe(indicate(this.loading));
    }
    updateCommunityAdmin(updatedCommunity: Community) {

        return this.httpService
            .request({
                path: '/ccat/community-admin/update',
                payload: {
                    updatedCommunity,
                },
            })
            .pipe(indicate(this.loading));
    }
    deleteCommunityAdmin(communityId) {
        return this.httpService
            .request({
                path: '/ccat/community-admin/delete',
                payload: {
                    communityId,
                },
            })
            .pipe(indicate(this.loading));
    }

 
}
