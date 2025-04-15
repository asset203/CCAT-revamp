import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { ServiceOfferingCC } from 'src/app/shared/models/ServiceOfferingCC.interface';
import { indicate } from 'src/app/shared/rxjs/indicate';
import { HttpService } from '../http.service';
import { SubscriberService } from '../subscriber.service';

@Injectable({
    providedIn: 'root',
})
export class ServiceOfferingCCService {
    loading$ = new BehaviorSubject(false);
    constructor(private httpService: HttpService, private subscriberService: SubscriberService) { }
    getAllServiceOffering(serviceClassID) {
        const msisdn = JSON.parse(sessionStorage.getItem('msisdn'))
        
        //const serviceClassId = this.subscriberService.subscriberSubject.getValue().serviceClass.code;
        const profileSOB = JSON.parse(sessionStorage.getItem('session')).userProfile.profileSOB
        return this.httpService
            .request({
                path: '/ccat/service-offering/get-all',
                payload: {
                    msisdn,
                    serviceClassId:serviceClassID,
                    profileSOB
                },
            })
            .pipe(indicate(this.loading$));
    }
    updateServiceOffering(currentServiceOffering: ServiceOfferingCC, newServiceOffering: ServiceOfferingCC) {
        const msisdn = JSON.parse(sessionStorage.getItem('msisdn'));
        return this.httpService.request({
            path: "/ccat/service-offering/update",
            payload: {
                msisdn,
                currentServiceOffering,
                newServiceOffering,
                footprintModel: {
                    machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                    profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                    pageName: 'Service Offering',
                    msisdn: msisdn,
                    footPrintDetails: [
                        {
                            paramName: 'Service Offering',
                            oldValue: currentServiceOffering.name,
                            newValue: newServiceOffering.name,
                        },
                    ],
                },
            }
        }).pipe(indicate(this.loading$))
    }
}
