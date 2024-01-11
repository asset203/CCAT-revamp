import {Component, Input, OnInit , OnChanges, SimpleChanges} from '@angular/core';
import {map} from 'rxjs/operators';

import {ExtractProfileFeaturesService} from 'src/app/core/service/administrator/extract-profile-features.service';
import {ProfileService} from 'src/app/core/service/administrator/profile.service';
import {Defines} from 'src/app/shared/constants/defines';
import {Feature} from 'src/app/shared/models/feature.interface';
@Component({
    selector: 'app-extract-feature',
    templateUrl: './extract-feature.component.html',
    styleUrls: ['./extract-feature.component.scss'],
})
export class ExtractFeatureComponent implements OnInit,OnChanges {
    constructor(
        private profileService: ProfileService,
    ) {}
    ngOnChanges(changes: SimpleChanges): void {
        const profileId = changes?.profileId?.currentValue;
        if (profileId) this.getProfileFeaturesById(profileId);
    }
    featuresLoading$ = this.profileService.loading$;
    profiles;
    @Input() profileId;

    //final properies
    admFeatures: Feature[];
    ccFeatures: Feature[];
    reportFeatures: Feature[];

    admFeaturesColoumns = [];
    ccFeaturesColumns = [];
    reportFeaturesColumns = [];

    admData = [];
    ccData = [];
    reportData = [];

    selectedFeature = 'cc';

    dataTable = [];
    featureTable = [];
    featureTableColumns = [];
    selectedProfileFeatures;

    ngOnInit(): void {
        // this.profileService.features$.subscribe((res) => {
        //     this.splitLkFeatures(res?.payload?.features);
        //     this.filterAllFeaturesColumnsNames();
        //     this.extractProfileFeaturesService.getAllProfileFeatures().subscribe((response) => {
        //         this.initProfileFeatureDataTable(response?.payload?.profiles, this.ccData, this.ccFeatures);
        //         this.initProfileFeatureDataTable(response?.payload?.profiles, this.admData, this.admFeatures);
        //         this.initProfileFeatureDataTable(response?.payload?.profiles, this.reportData, this.reportFeatures);
        //         this.setInitialTableData();
        //     });
        // });
    }
    // setInitialTableData() {
    //     this.dataTable = this.ccData;
    //     this.featureTable = this.ccFeatures;
    //     this.featureTableColumns = this.ccFeaturesColumns;
    // }

    // filterLkFeatures(allFeatures: Feature[], type: number) {
    //     return allFeatures.filter((feature) => feature.type === type);
    // }
    // splitLkFeatures(AllFeaturesResponse: Feature[]) {
    //     this.ccFeatures = this.filterLkFeatures(AllFeaturesResponse, Defines.LK_FEATURES_TYPES.CC_FEATURES);
    //     this.admFeatures = this.filterLkFeatures(AllFeaturesResponse, Defines.LK_FEATURES_TYPES.ADM_FEATURES);
    //     this.reportFeatures = this.filterLkFeatures(AllFeaturesResponse, Defines.LK_FEATURES_TYPES.REPORT_FEATURES);
    // }
    // filterAllFeaturesColumnsNames() {
    //     this.admFeaturesColoumns = this.admFeatures.map((el) => el.name);
    //     this.ccFeaturesColumns = this.ccFeatures.map((el) => el.name);
    //     this.reportFeaturesColumns = this.reportFeatures.map((el) => el.name);
    // }
    // initProfileFeatureDataTable(profileResponse: any, featureDataTable: any, features: Feature[]) {
    //     profileResponse.forEach((profile, index) => {
    //         featureDataTable[index] = {};
    //         featureDataTable[index].profile = profile.profileName;
    //         features.forEach((feature) => {
    //             featureDataTable[index][feature.name] = profile.features.includes(feature.id) ? 'X' : '';
    //         });
    //     });
    // }
    getProfileFeaturesById(profileId) {
        this.getSelectedProfileFeatures(profileId);
    }
    getSelectedProfileFeatures(id) {
        this.profileService
            .getProfileByID(+id)
            .pipe(
                map((response) => {
                    return response?.payload?.profile;
                })
            )
            .subscribe((profileData) => {
                this.ccFeatures = profileData.ccFeatures;
                this.admFeatures = profileData.sysFeatures;
                this.reportFeatures = profileData.dssReportsFeatures;
            });
    }
}
