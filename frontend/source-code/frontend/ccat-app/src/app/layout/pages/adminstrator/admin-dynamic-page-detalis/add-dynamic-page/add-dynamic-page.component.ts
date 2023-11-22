import {Component, OnDestroy, OnInit} from '@angular/core';
import {AdminDynamicPageService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dynamic-page.service';
import {ActivatedRoute} from '@angular/router';
import {MenuItem} from 'primeng/api';
import {AdminDpPageConfigrationService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-page-configrations.service';
import {Location} from '@angular/common';
import {AdminDPStepService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-step.service';
import {Step} from 'src/app/shared/models/admin-dynamic-page.model';
import {Subscription} from 'rxjs';
import {faDatabase, faCode} from '@fortawesome/free-solid-svg-icons';
import {AdminDPProcedureService} from 'src/app/core/service/administrator/admin-dynamic-page/admin-dp-procedure.service';
@Component({
    selector: 'app-add-dynamic-page',
    templateUrl: './add-dynamic-page.component.html',
    styleUrls: ['./add-dynamic-page.component.scss'],
})
export class AddDynamicPageComponent implements OnInit, OnDestroy {
    faDatabase = faDatabase;
    faCode = faCode;
    activeIndex = 0;
    items: MenuItem[];
    addMode: boolean;
    editedPageId: number;
    loading$ = this.pageConfigsService.loading;
    typeDialog: boolean = false;
    steps: Step[];

    configsTypes = [
        {name: 'Procedure', value: 1},
        {name: 'Http', value: 2},
    ];
    stepType: number;
    stepsSubscription: Subscription = new Subscription();
    constructor(
        private adminDynamicPageService: AdminDynamicPageService,
        private route: ActivatedRoute,
        private pageConfigsService: AdminDpPageConfigrationService,
        private location: Location,
        private stepService: AdminDPStepService,
        private procedureServices: AdminDPProcedureService
    ) {}

    disableAddPage$ = this.adminDynamicPageService.disabledAddStep$;

    ngOnInit(): void {
        this.items = [
            {
                label: 'Page Details',
            },
        ];
        let id = +this.route.snapshot.paramMap.get('id');
        this.stepService.setPageId(id);
        this.procedureServices.resetParameter();
        if (id === -1) {
            this.addMode = true;
            this.pageConfigsService.setPageInfo({});
            this.stepService.setSteps([]);
            this.stepService.setStepOrder(0);
            this.adminDynamicPageService.setDisabledAddStep(true);
        } else {
            this.addMode = false;
            this.editedPageId = id;
            this.pageConfigsService.getEditedPage(id).subscribe((dynamicPage) => {
                let stepOrderEdita = dynamicPage.steps.length>0 ?dynamicPage.steps[dynamicPage.steps.length - 1].stepOrder :0
                this.stepService.setSteps([...dynamicPage.steps]);
                this.setStepMenu(dynamicPage.steps);
                this.stepService.setStepOrder(stepOrderEdita);
                this.adminDynamicPageService.setDisabledAddStep(false);
                dynamicPage.steps.forEach((element) => {
                    this.procedureServices.initSourceStepParameter(
                        element.stepConfiguration?.parameters.filter((params) => params.parameterType === 2),
                        element.stepOrder
                    );
                });
            });
        }
        this.stepsSubscription = this.stepService.steps$.subscribe((steps) => {
            this.steps = steps;
        });
    }
    addStep() {
        this.adminDynamicPageService.setDisabledAddStep(true);
        this.items = [...this.items, {label: this.stepType === 1 ? 'Procedure' : 'Http'}];
        this.typeDialog = false;
        this.activeIndex += 1;
        if(this.stepType === 1 ){
            this.stepService.addProcedureSteptoSteps();
        }
        else if(this.stepType === 2){
            this.stepService.addHttpStepsToSteps();
        }
        
    }
    back() {
        this.location.back();
    }
    setStepMenu(steps: Step[]) {
        const newItems: MenuItem[] = [];
        for (let step of steps) {
            newItems.push({label: step.stepName});
        }
        this.items = [...this.items, ...newItems];
    }
    deleteStep(event) {
        let newItems: MenuItem[] = [...this.items];
        newItems.splice(event + 1, 1);
        this.items = newItems;
        this.activeIndex = 0;
        this.stepService.decreaseLastOrder();
    }
    ngOnDestroy(): void {
        this.stepsSubscription.unsubscribe();
    }
}
