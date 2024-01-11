import {Component, OnInit} from '@angular/core';
import {interval, of} from 'rxjs';
import {map, mapTo, tap} from 'rxjs/operators';

@Component({
    selector: 'app-date-time',
    templateUrl: './date-time.component.html',
    styleUrls: ['./date-time.component.scss'],
})
export class DateTimeComponent implements OnInit {
    currentTime$ = interval(1000).pipe(map(() => new Date()));

    constructor() {}

    ngOnInit(): void {}
}
