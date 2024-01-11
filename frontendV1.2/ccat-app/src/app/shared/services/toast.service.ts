import { Injectable } from '@angular/core';
import { ToastrService } from 'ngx-toastr';

@Injectable({
    providedIn: 'root',
})
export class ToastService {
    constructor(private toastr: ToastrService) { }

    // success(head, body) {
    //     this.toastr.success(body, head);
    // }
    // warning(head, body) {
    //     this.toastr.warning(body, head);
    // }
    // error(head, body) {
    //     this.toastr.error(body, head);
    // }

    success(title?: string, message?: string): void {
        this.toastr.success(message, title, {
            easing: 'ease-in-out',
            easeTime: 350,
            enableHtml: true,
            progressBar: true,
            tapToDismiss: true,
            closeButton: true,
            disableTimeOut : true,
            newestOnTop : true
        });
    }

    error(title?: string, message?: string): void {
        this.toastr.error(message, title, {
            easing: 'ease-in',
            easeTime: 350,
            enableHtml: true,
            progressBar: true,
            tapToDismiss: false,
            closeButton: true,
            disableTimeOut : true,
            newestOnTop : false
            
        });

    }

    warning(title?: string, message?: string): void {
        this.toastr.warning(message, title, {
            timeOut: 3000,
            extendedTimeOut: 5000,
            easing: 'ease-in-out',
            easeTime: 350,
            enableHtml: true,
            progressBar: true,
            tapToDismiss: true,
            closeButton: true,
        });
    }
}
