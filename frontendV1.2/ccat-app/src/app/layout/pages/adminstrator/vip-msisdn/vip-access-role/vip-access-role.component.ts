import {Component, OnInit} from '@angular/core';
import {ToastrService} from 'ngx-toastr';
import {VipMsisdnService} from 'src/app/core/service/administrator/vip-msisdn.service';
import {FeaturesService} from 'src/app/shared/services/features.service';

@Component({
    selector: 'app-vip-access-role',
    templateUrl: './vip-access-role.component.html',
    styleUrls: ['./vip-access-role.component.scss'],
})
export class VipAccessRoleComponent implements OnInit {
    constructor(
        private vipMsisdnService: VipMsisdnService,
        private toastrService: ToastrService,
        private featuresService: FeaturesService
    ) {}

    allVipPages: any;
    newVipPages: any;
    permissions = {
        getVipPages: false,
        updateVipPages: false,
    };
    ngOnInit() {
        this.setPermissions();
        this.getAllVipList();
    }
    searchPro;

    getAllVipList() {
        if (this.permissions.getVipPages) {
            let updateVipPage = [];
            this.vipMsisdnService.allVipMsisdn$.subscribe(
                (res) => {
                    this.allVipPages = res?.payload?.appPages;
                    updateVipPage = Object.values(res?.payload?.vipPages);
                    this.newVipPages = updateVipPage.reduce((acc, curr) => acc.concat(curr), []);
                    console.log('this.allVipPages', this.allVipPages);
                    console.log('this.newVipPages', this.newVipPages);
                },
                (err) => {
                    this.allVipPages = [];
                }
            );
        }
    }
    updateVipPage() {
        const updatemenuIDVipPages = [...new Set(this.newVipPages.map((item) => item.menuId))];
        console.log('the new ', updatemenuIDVipPages);
        this.vipMsisdnService.updateVipPages(updatemenuIDVipPages).subscribe((res) => {
            if (res.statusCode === 0) {
                this.toastrService.success('Pages updated successfully');
            }
        });
    }

    setPermissions() {
        let findSubscriberPermissions: Map<number, string> = new Map()
            .set(489, 'getVipPages')
            .set(487, 'updateVipPages');
        this.featuresService.checkUserPermissions(findSubscriberPermissions);
        this.permissions.getVipPages = this.featuresService.getPermissionValue(489);
        this.permissions.updateVipPages = this.featuresService.getPermissionValue(487);
    }
}
