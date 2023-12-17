import { Component, Input, OnInit } from '@angular/core';
import { map, take } from 'rxjs/operators';
import { ProfileService } from 'src/app/core/service/administrator/profile.service';
import { MonetaryLimits } from 'src/app/shared/models/ProfileRequest.interface';
@Component({
    selector: 'app-profile-limits-table',
    templateUrl: './profile-limits-table.component.html',
    styleUrls: ['./profile-limits-table.component.scss'],
})
export class ProfileLimitsTable implements OnInit {
    display = false;
    selectedMonetary: MonetaryLimits;
    @Input() profileTargetLimits: MonetaryLimits[];
    options: MonetaryLimits[] = [];
    selected;
    first = 0;
    @Input() addView: boolean;
    monetaryLimits: MonetaryLimits[] = [];
    constructor(private profileService: ProfileService) { }
    ngOnInit(): void {
        
        this.profileService.monetaryLimit$
            .pipe(
                take(1),
                map((response) => response?.payload?.monetaryLimits)
            )
            .subscribe(
                (monetaryLimits) => {
                    monetaryLimits.forEach(limit => {
                        if(!limit.value){
                            limit.value = limit.defaultValue
                        }
                        if(!(this.profileTargetLimits.find(tLimit=>tLimit.limitId===limit.limitId))){
                            this.profileTargetLimits.push(limit)
                        }
                    });
                    console.log("profileTargetLimits",this.profileTargetLimits)
                },
                (error) => {
                    // this.options = [
                    //     {
                    //         limitId: 1,
                    //         limitName: 'Credit_Card_Max ',
                    //         value: null,
                    //         defaultValue: 10,
                    //     },
                    //     {
                    //         limitId: 2,
                    //         limitName: 'Credit_Card_Min',
                    //         value: null,
                    //         defaultValue: 100,
                    //     },
                    //     {
                    //         limitId: 3,
                    //         limitName: 'Rebate_Max',
                    //         value: null,
                    //         defaultValue: 150,
                    //     },
                    // ];
                }
            );
    }
    showDialog() {
        this.display = true;
    }
    changeValue() {

        this.profileTargetLimits = this.monetaryLimits;
    }
    addMonetry() {
        // clone the array
        // let newTargetLimits = [...this.profileTargetLimits];
        // edit
        this.profileTargetLimits.push(this.selectedMonetary);

        let newMonetaryLimits: MonetaryLimits[] = this.options.filter(
            (mon) => mon.limitId !== this.selectedMonetary.limitId
        );
        this.options = newMonetaryLimits;
        this.selectedMonetary = undefined;
        this.display = false;
    }
    setValueWithDefault(limitId: number) {
        console.log(this.profileTargetLimits)
        this.profileTargetLimits.map((limit) => {
            return limit.limitId === limitId ? (limit.value = limit.defaultValue) : limit.value;
        });
    }
    deleteQuestion(limitId: number) {
        const deletedMandatory = this.profileTargetLimits.filter((limit) => limit.limitId === limitId);
        this.options.push(...deletedMandatory);
        this.profileTargetLimits = this.profileTargetLimits.filter((limit) => limit.limitId !== limitId);
    }
}
