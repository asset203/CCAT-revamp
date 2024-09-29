import {ActivatedRoute, Router} from '@angular/router';
import {Component, OnInit, ViewChild} from '@angular/core';
import {Observable} from 'rxjs';
import {Profile} from 'src/app/shared/models/profile';
import {map} from 'rxjs/operators';
import {MonetaryLimits, ProfileRequest} from 'src/app/shared/models/ProfileRequest.interface';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Feature} from 'src/app/shared/models/feature.interface';
import {ToastrService} from 'ngx-toastr';
import {MessageService} from 'src/app/shared/services/message.service';
import {ValidationService} from 'src/app/shared/services/validation.service';
import {ProfileLimitsTable} from './profile-limits-table/profile-limits-table.component';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {FootPrintService} from 'src/app/core/service/foot-print.service';

@Component({
    selector: 'app-add-user-profile',
    templateUrl: './add-user-profile.component.html',
    styleUrls: ['./add-user-profile.component.scss'],
})
export class AddUserProfileComponent implements OnInit {
    addView = false;
    addRequestBody: ProfileRequest = {
        profileName: '',
        sessionLimit: null,
        ccFeatures: [],
        serviceClasses: [],
        sysFeatures: [],
        menus: [],
        monetaryLimits: [],
        dssReportsFeatures: [],
        ...(!this.addView && {profileId: null}),
    };
    profileForm: FormGroup = new FormGroup({});
    features: Feature[];
    selectedUsers;
    tab = 'features';
    editedProfile$: Observable<Profile> = new Observable();
    selectedMonetary: MonetaryLimits;
    @ViewChild(ProfileLimitsTable) profileLimitsTable;
    constructor(
        private route: ActivatedRoute,
        private profileService: ProfileService,
        private fb: FormBuilder,
        private toastrService: ToastrService,
        private router: Router,
        private messageService: MessageService,
        private validationService: ValidationService,
        private footPrintService: FootPrintService
    ) {}

    ngOnInit(): void {

        let id = this.route.snapshot.paramMap.get('id');
        this.createForm();
        this.profileService.features$.subscribe((response) => {
            this.features = response?.payload?.features;
            this.perpareDefaultFeature(this.features)

        });
        if (id === '-1') {
            this.addView = true;
        //set default features
        

        } else {
            this.addRequestBody.profileId = +id;
            this.profileService
                .getProfileByID(+id)
                .pipe(
                    map((response) => {
                        return response?.payload?.profile;
                    })
                )
                .subscribe((profileData) => {
                    this.addRequestBody.profileName = profileData.profileName;
                    this.addRequestBody.sessionLimit = profileData.sessionLimit;
                    this.addRequestBody.ccFeatures = profileData.ccFeatures;
                    this.addRequestBody.sysFeatures = profileData.sysFeatures;
                    this.addRequestBody.serviceClasses = profileData.serviceClasses;
                    this.addRequestBody.menus = profileData.menus;
                    this.addRequestBody.monetaryLimits = profileData.monetaryLimits;
                    this.addRequestBody.dssReportsFeatures = profileData.dssReportsFeatures;
                });
        }
    }
    switchTab(tab) {
        this.tab = tab;
    }
    getFeatureById(features, selectedFeatureId){
        const index = features.findIndex(el => el.id == selectedFeatureId);
        return features[index]
    }
    perpareDefaultFeature(features){
        console.log(this.getFeatureById(features,1))
        if(this.addView){
            this.addRequestBody.ccFeatures.push(this.getFeatureById(features,1));
            this.addRequestBody.ccFeatures.push(this.getFeatureById(features,24));
        }
        
        

    }
    createForm() {
        this.profileForm = this.fb.group({
            name: new FormControl(this.addRequestBody.profileName, [
                Validators.required,
                Validators.pattern(this.validationService.whiteSpacesPattern),
            ]),
            sessionLimit: new FormControl(this.addRequestBody.sessionLimit, Validators.required),
        });
    }
    submit() {
        console.log()
        if (this.addView) {
            this.profileLimitsTable?.monetaryLimits?.forEach((element) => {
                if (element.value === null) {
                    element.value = element.defaultValue;
                }
            });
            //this.addRequestBody.monetaryLimits = this.profileLimitsTable?.monetaryLimits;
            console.log(this.addRequestBody);
            this.profileService.addProfile(this.addRequestBody).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastrService.success('Success', this.messageService.getMessage(3).message);
                    this.router.navigate(['/admin/user-profiles']);
                }
            });
            // footprint
            let footprintObj: FootPrint = {
                machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
                profileName: JSON.parse(sessionStorage.getItem('session')).userProfile.profileName,
                pageName: 'User Profiles',
                footPrintDetails: [
                    {
                        paramName: 'Profile Name',
                        oldValue: null,
                        newValue: this.addRequestBody.profileName,
                    },
                    {
                        paramName: 'SESSION_LIMIT',
                        oldValue: null,
                        newValue: this.addRequestBody.sessionLimit,
                    },
                    {
                        paramName: 'Selected CC Features',
                        oldValue: null,
                        newValue: this.addRequestBody?.ccFeatures?.map((el) => el.id).join(','),
                    },
                    {
                        paramName: 'Selected System Features',
                        oldValue: '',
                        newValue: this.addRequestBody?.sysFeatures?.map((el) => el.id).join(','),
                    },
                    {
                        paramName: 'Selected Service Classes',
                        oldValue: '',
                        newValue: this.addRequestBody?.serviceClasses?.map((el) => el.id).join(','),
                    },
                    {
                        paramName: 'Monetary Limit Value',
                        oldValue: '',
                        // newValue: this.addRequestBody?.monetaryLimits?.map(el => el.id).join(',')
                    },
                ],
            };
            console.log(footprintObj);
            // this.footPrintService.log(footprintObj)
        } else {
            console.log(this.addRequestBody);
            this.profileService.updateProfile(this.addRequestBody).subscribe((success) => {
                if (success.statusCode === 0) {
                    this.toastrService.success('Success', this.messageService.getMessage(4).message);
                    this.router.navigate(['/admin/user-profiles']);
                }
            });
        }
        this.profileForm.reset();
    }
    setCCOrderedMenu(orederedMenus: Feature[]) {
        for (let item of orederedMenus) {
            this.addRequestBody.ccFeatures = this.addRequestBody.ccFeatures.map((feature) => {
                return item.id === feature.id ? item : feature;
            });
        }
    }
    setSysOrderedMenu(orederedMenus: Feature[]) {
        for (let item of orederedMenus) {
            this.addRequestBody.sysFeatures = this.addRequestBody.sysFeatures.map((feature) => {
                return item.id === feature.id ? item : feature;
            });
        }
    }
    setRepOrderedMenu(orederedMenus: Feature[]) {
        for (let item of orederedMenus) {
            this.addRequestBody.dssReportsFeatures = this.addRequestBody.dssReportsFeatures.map((feature) => {
                return item.id === feature.id ? item : feature;
            });
        }
    }
}
