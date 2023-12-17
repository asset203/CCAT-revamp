import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ProfileService } from 'src/app/core/service/administrator/profile.service';
import { Feature } from 'src/app/shared/models/feature.interface';
@Component({
    selector: 'app-profile-pick-list',
    templateUrl: './profile-pick-list.component.html',
    styleUrls: ['./profile-pick-list.component.scss'],
})
export class ProfilePickList implements OnInit, OnChanges {
    @Input() sourceType: number;
    @Input() target: any[];
    @Input() sourceHeader: string;
    @Input() targetHeader: string;
    @Input() searchPro: string;
    @Input() addMode: boolean;
    @Input() features: Feature[];
    source;
    loading$ = this.profileService.loading$;
    constructor(private profileService: ProfileService) { }

    setSourceData(incommingObservable: Observable<any>, requestPayload: string, filterField: string) {
        return incommingObservable.pipe(
            map((response) => {
                const outputList = response?.payload[requestPayload];
                return outputList.filter((outputItem) => {
                    return !this.target.map((el) => el[filterField]).includes(outputItem[filterField]);
                });
            })
        );
    }
    filterFeatures(featureType: number) {
        console.log(featureType)
        if (this.features) {
            const temp = this.features
                .filter((feature) => {
                    return !this.target.map((el) => el.id).includes(feature.id);
                })
                .filter((feature) => feature.type === featureType)
            console.log(temp)
            this.source = of(
                this.features
                    .filter((feature) => {
                        return !this.target.map((el) => el.id).includes(feature.id);
                    })
                    .filter((feature) => feature.type === featureType)
            );
        }
    }
    ngOnInit(): void {
        if (this.sourceType == 3) {
            this.source = this.setSourceData(this.profileService.servicesClasses$, 'serviceClasses', 'code');
        } else if (this.sourceType == 4) {
            this.source = this.setSourceData(this.profileService.menus$, 'menus', 'menuId');
        }
    }
    ngOnChanges(changes: SimpleChanges): void {
        if (this.sourceType == 1) {
            this.filterFeatures(1);
        } else if (this.sourceType == 2) {
            this.filterFeatures(2);
        }
        else if (this.sourceType == 3) {
            this.filterFeatures(3);
        }
    }
}
