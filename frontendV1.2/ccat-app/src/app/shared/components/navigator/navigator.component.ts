import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { NgxSmartModalService } from 'ngx-smart-modal';
import { CheckCallReasonService } from 'src/app/core/service/customer-care/check-call-reason.service';
import { RouterService } from 'src/app/core/service/router.service';
import { SubscriberService } from 'src/app/core/service/subscriber.service';
import { ValidationService } from '../../services/validation.service';

@Component({
    selector: 'app-navigator',
    templateUrl: './navigator.component.html',
    styleUrls: ['./navigator.component.scss'],
})
export class NavigatorComponent implements OnInit {
    constructor(private routerService: RouterService,
        public ngxSmartModalService: NgxSmartModalService,private checkCallActivity :CheckCallReasonService) {

    }



    ngOnInit(): void { }

    refresh() {
        this.routerService.refresh();
    }

    reset() {
        this.routerService.reset();
    }
    search(){
        this.checkCallActivity.setPermissions();
        const permission = this.checkCallActivity.permissions.callActivity;
        if(permission){
            this.routerService.reset();
        }
        else{
            this.ngxSmartModalService.getModal('myModal').open()
        }
        
    }


}
