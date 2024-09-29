import {Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {BehaviorSubject} from 'rxjs';
import {FootPrintService} from 'src/app/core/service/foot-print.service';
// import { ngxLoadingAnimationTypes } from 'ngx-loading/public_api';
import {SessionService} from 'src/app/core/service/session.service';
import {SSOService} from 'src/app/core/service/sso.service';
import {FootPrint} from 'src/app/shared/models/foot-print.interface';
import {ToastService} from 'src/app/shared/services/toast.service';
import {ValidationService} from 'src/app/shared/services/validation.service';

@Component({
    selector: 'app-login',
    templateUrl: './login.component.html',
    styleUrls: ['./login.component.scss'],
})
export class LoginComponent implements OnInit {
    constructor(
        private fb: FormBuilder,
        private sessionService: SessionService,
        private ssoService: SSOService,
        private toastService: ToastService,
        private footPrintService: FootPrintService
    ) {}

    // ---VARIABLES---
    usernameFetchLoading$ = new BehaviorSubject(false);
    ssoLoading = this.ssoService.loading$;
    usernameFetchLoading = this.usernameFetchLoading$;
    sessionLoading = this.sessionService.loading$;
    // form variable
    loginForm: FormGroup;

    userName: String = '';
    usernameDisabled = true;

    ngOnInit(): void {
        this.createForm();

        this.ssoService.getSSOConfig().subscribe({
            next: (resp) => {
                sessionStorage.setItem('voucherNumberLength', JSON.stringify(resp.payload.voucherNumberLength));
                sessionStorage.setItem(
                    'voucherSerialNumberLength',
                    JSON.stringify(resp.payload.voucherSerialNumberLength)
                );
                console.log('response is ', resp);
                if (resp?.payload?.ssoIntegration === true) {
                    console.log('response is ', resp);
                    // enable loading
                    this.usernameFetchLoading$.next(true);

                    // disable form control (username)
                    this.loginForm.controls.username.disable();

                    // get username from sso service
                    fetch(resp.payload.ssoVfUrl, {
                        method: 'GET',
                    })
                        .then((response) => {
                            response.json().then((result) => {
                                let jsonData = JSON.parse(result);

                                // still username disable
                                // patch value of username
                                this.loginForm.controls.username.setValue(jsonData.UserName);

                                // store Machine name
                                sessionStorage.setItem('machineName', jsonData.MachineName);

                                // disable loading
                                this.usernameFetchLoading$.next(false);
                            });
                        })
                        .catch((err) => {
                            // disable loading
                            this.usernameFetchLoading$.next(false);
                            // enable form control (username)
                            this.loginForm.controls.username.enable();
                        });
                } else {
                    // enable form control (username)
                    console.log('response is ', resp);
                    console.log('start enable username ', resp.payload.ssoIntegration);
                    this.loginForm.controls.username.enable();
                    console.log('username enabled successfully ', resp.payload.ssoIntegration);
                }
            },
            error: (err) => {
                console.log('Catching error ', err);
                this.toastService.error('Network Error', '');
            },
        });
    }

    // method to initialize the form
    createForm() {
        this.loginForm = this.fb.group({
            username: [{value: '', disabled: true}, [Validators.required]],
            password: ['', [Validators.required]],
            staylogged: [false],
        });
    }

    // submit method
    onSubmit() {
        let formData = this.loginForm.getRawValue();
        let formValue = {
            username: formData.username.trim(),
            password: formData.password.trim(),
            staylogged: formData.staylogged,
        };

        this.sessionService.login(formValue);
        //footprint
    }

    logfootPrint() {
        let footprintObj: FootPrint = {
            machineName: sessionStorage.getItem('machineName') ? sessionStorage.getItem('machineName') : null,
            profileName: null,
            pageName: 'Login',
            msisdn: null,
        };
        this.footPrintService.log(footprintObj);
    }
}
