import {AfterViewInit, Component, OnInit, ViewChildren} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {ToastrService} from 'ngx-toastr';
import {Observable} from 'rxjs';
import {SessionService} from 'src/app/core/service/session.service';
import {StorageService} from 'src/app/core/service/storage.service';
import {SubscriberService} from 'src/app/core/service/subscriber.service';
import {FeaturesService} from 'src/app/shared/services/features.service';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {LockService} from 'src/app/core/service/lock.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
import {CallActivityService} from 'src/app/core/service/customer-care/call-activity.service';

@Component({
    selector: 'app-find-subscriber',
    templateUrl: './find-subscriber.component.html',
    styleUrls: ['./find-subscriber.component.scss'],
})
export class FindSubscriberComponent implements OnInit {
    // boolean for the loading
    loading$ = this.subscriberService.loading$ || this.lockService.loading$;
    session$: Observable<any> = this.sessionService.session$;
    canSearch;

    constructor(
        private sessionService: SessionService,
        private subscriberService: SubscriberService,
        private fb: FormBuilder,
        private validation: ValidationService,
        private toastService: ToastrService,
        private featuresService: FeaturesService,
        private lockService: LockService,
        private footPrintService: FootPrintService,
        private callActivityService: CallActivityService
    ) {
        this.subscriberService.clearSubscriberReset();
    }

    findSubscriberForm: FormGroup;

    ngOnInit(): void {
        let findSubscriberPermissions: Map<number, string> = new Map().set(24, 'canSearch');

        this.createForm();
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.canSearch = this.featuresService.getPermissionValue(24);

        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'FindSubscriber',
            msisdn: null,
        };
        this.footPrintService.log(footprintObj);
    }
    // method to initialize the form
    createForm() {
        this.findSubscriberForm = this.fb.group({
            msisdn: [
                '',
                [
                    Validators.required,
                    Validators.maxLength(15),
                    Validators.pattern(this.validation.msisdnPattern),
                    Validators.minLength(4),
                ],
            ],
        });
    }
    onEnterClicked(msisdn: string) {
        if (this.canSearch) {
            if (this.findSubscriberForm.valid) {
                this.subscriberService.loadSubscriber(msisdn['msisdn']);
                console.log("hiii misis",msisdn['msisdn']);
                sessionStorage.removeItem("pageRediected");
                this.callActivityService.addCallReason(msisdn['msisdn']);
            }
        } else {
            this.toastService.error('You are not allowed to search');
        }
        // footprint
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
            pageName: 'FindSubscriber',
            msisdn: msisdn['msisdn'],
        };
        this.footPrintService.log(footprintObj);
    }

    // setUserPermissions() {
    //     //Using Map() not Set() because i need the description of each feature id to be readable
    //     let featureMap: Map<number, string> = new Map()
    //         .set(24, 'canSearch')
    //         .set(100, 'canUpdate');
    //     console.log(featureMap)
    //     //Check all screen permission by pass feature Map of this screen
    //     let permissionsMap = this.checkUserPermissions(featureMap);

    //     this.canSearch = permissionsMap.get(24) === true ? true : false;
    //     this.canUpdate = permissionsMap.get(100) === true ? true : false;
    // }

    //TODO: move this method to shared service to prevent duplicate code
    // checkUserPermissions(featuresMap: any): any {
    //     let permissionsMap = new Map();
    //     this.storageService.getItem('session')
    //         .userProfile.features
    //         .forEach(feature => {
    //             let attributeName = featuresMap.get(feature.id);
    //             if (attributeName !== undefined) {
    //                 permissionsMap.set(feature.id, true);
    //             }
    //         });
    //     console.log(permissionsMap)
    //     return permissionsMap;
    // }
}
