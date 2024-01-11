import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';
import { BehaviorSubject } from 'rxjs';
import { StorageService } from 'src/app/core/service/storage.service';

@Injectable({
    providedIn: 'root',
})
export class FeaturesService {


    userPermissionsSubject = new BehaviorSubject(null);
    permissionsMap;
    constructor(private storageService: StorageService) { }

    //TODO: move this method to shared service to prevent duplicate code
    checkUserPermissions(permissions: any): any {
        let permissionsMap = new Map();
        this.storageService.getItem('session')?.userProfile?.features.forEach((feature) => {
            let attributeName = permissions.get(feature.id);
            if (attributeName !== undefined) {
                permissionsMap.set(feature.id, true);
            }
        });
        this.permissionsMap = permissionsMap;
    }

    getPermissionValue(id) {
        if (this.permissionsMap.get(id)) {
            return true;
        } else {
            return false;
        }
    }
}
