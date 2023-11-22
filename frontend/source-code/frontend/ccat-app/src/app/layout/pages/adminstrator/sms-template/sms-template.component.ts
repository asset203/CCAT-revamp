import {Component, OnInit} from '@angular/core';

@Component({
    selector: 'app-sms-template',
    templateUrl: './sms-template.component.html',
    styleUrls: ['./sms-template.component.scss'],
})
export class SmsTemplateComponent implements OnInit {
    constructor() {}
    dialog=false;
    languages;
    actions;
    smsTemplateList = [
        {actionName: 'CHANGE LANGUAGE', lang: 'Arabic', text: '??? ????? $oldValue$ $newValue$'},
        {
            actionName: 'CHANGE LANGUAGE',
            lang: 'Arabic',
            text: 'You are now activated on threshold old value is $thresholdOldAmount$ and new value is $thresholdNewAmount$',
        },
        {actionName: 'CHANGE LANGUAGE', lang: 'Arabic', text: '??? ????? $oldValue$ $newValue$'},
        {actionName: 'CHANGE LANGUAGE', lang: 'Arabic', text: '??? ????? $oldValue$ $newValue$'},
    ];
    ngOnInit(): void {}
}
