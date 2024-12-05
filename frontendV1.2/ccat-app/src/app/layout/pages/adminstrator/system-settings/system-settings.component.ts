import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from '@angular/forms';
import {TreeNode} from 'primeng/api';
import {SystemSettingsService} from 'src/app/core/service/administrator/system-settings.service';
import {LoadingService} from 'src/app/shared/services/loading.service';

@Component({
    selector: 'app-system-settings',
    templateUrl: './system-settings.component.html',
    styleUrls: ['./system-settings.component.scss'],
})
export class SystemSettingsComponent implements OnInit {
    constructor(
        private systemSettingsService: SystemSettingsService,
        private fb: FormBuilder,
        private loadingService: LoadingService
    ) {}
    valueTypesObj = {};
    files: TreeNode[] = [
        {
            label: 'Documents',
            data: 'Documents Folder',
            expandedIcon: 'pi pi-folder-open',
            collapsedIcon: 'pi pi-folder',
            children: [
                {
                    label: 'Work',
                    data: 'Work Folder',
                    expandedIcon: 'pi pi-folder-open',
                    collapsedIcon: 'pi pi-folder',
                    children: [
                        {label: 'Expenses.doc', icon: 'pi pi-file', data: 'Expenses Document'},
                        {label: 'Resume.doc', icon: 'pi pi-file', data: 'Resume Document'},
                    ],
                },
                {
                    label: 'Home',
                    data: 'Home Folder',
                    expandedIcon: 'pi pi-folder-open',
                    collapsedIcon: 'pi pi-folder',
                    children: [{label: 'Invoices.txt', icon: 'pi pi-file', data: 'Invoices for this month'}],
                },
            ],
        },
        {
            label: 'Pictures',
            data: 'Pictures Folder',
            expandedIcon: 'pi pi-folder-open',
            collapsedIcon: 'pi pi-folder',
            children: [
                {label: 'barcelona.jpg', icon: 'pi pi-image', data: 'Barcelona Photo'},
                {label: 'logo.jpg', icon: 'pi pi-file', data: 'PrimeFaces Logo'},
                {label: 'primeui.png', icon: 'pi pi-image', data: 'PrimeUI Logo'},
            ],
        },
        {
            label: 'Movies',
            data: 'Movies Folder',
            expandedIcon: 'pi pi-folder-open',
            collapsedIcon: 'pi pi-folder',
            children: [
                {
                    label: 'Al Pacino',
                    data: 'Pacino Movies',
                    children: [
                        {label: 'Scarface', icon: 'pi pi-video', data: 'Scarface Movie'},
                        {label: 'Serpico', icon: 'pi pi-file-video', data: 'Serpico Movie'},
                    ],
                },
                {
                    label: 'Robert De Niro',
                    data: 'De Niro Movies',
                    children: [
                        {label: 'Goodfellas', icon: 'pi pi-video', data: 'Goodfellas Movie'},
                        {label: 'Untouchables', icon: 'pi pi-video', data: 'Untouchables Movie'},
                    ],
                },
            ],
        },
    ];

    allSettings = [];
    allSettingsSubject$ = this.systemSettingsService.allSettingsSubject;
    loading$ = this.systemSettingsService.loading$;
    allSystemSettings = [];

    settingsForm: FormGroup;
    isFetchingList$ = this.loadingService.fetching$;
    ngOnInit(): void {
        // const formControlFields = [];
        this.loadingService.startFetchingList();
        this.systemSettingsService.getAllSystemSettings().subscribe(
            (resp) => {
                this.allSettings = resp.payload.configurations;
                this.createForm();
                this.loadingService.endFetchingList();
            },
            (err) => {
                this.loadingService.endFetchingList();
            }
        );
    }

    createForm() {
        let formControlFields = {};
        Object.keys(this.allSettings).forEach((service) => {
            console.log(service);
            this.allSettings[service].forEach((element) => {
                console.log('elements ', element);
                // if (typeof element.value === 'string' && element.valueType === '1') {
                //     element.value = 1;
                // } else {
                //     element.value = 0;
                // }
                formControlFields[element.key] = [element.value];
                this.valueTypesObj[element.key] = element.valueType;
            });
        });
        console.log(formControlFields);
        this.settingsForm = this.fb.group(formControlFields);
    }
    onSubmit() {
        let configurationsArr = [];
        console.log(this.settingsForm.value);
        Object.keys(this.settingsForm.value).forEach((el) => {
            let element = {};
            element['key'] = el;
            element['value'] = this.settingsForm.value[el];
            element['valueType'] = this.valueTypesObj[el];
            configurationsArr.push(element);
        });
        //console.log(configurationsArr)
        this.systemSettingsService.updateSystemSettings(configurationsArr);
    }
}
