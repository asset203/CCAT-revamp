import { Component, Input, OnChanges, OnDestroy, OnInit } from '@angular/core';
import { interval, timer } from 'rxjs';
import { count, map, scan, startWith, tap } from 'rxjs/operators';

@Component({
    selector: 'app-text-carousel',
    templateUrl: './text-carousel.component.html',
    styleUrls: ['./text-carousel.component.scss'],
})
export class TextCarouselComponent implements OnInit, OnDestroy, OnChanges {
    @Input() dataStrings;
    lastIndex;
    currentIndex = 0;
    allMarques


    // interval$ = timer(0, 5000);
    // counter$ = this.interval$.pipe(
    //     scan((acc, v) => v % this.dataStrings.length),
    //     // tap(val => {console.log(val)}),
    //     map((i) => this.dataStrings[i])
    // );

    // timer = setInterval(() => {
    //     if (this.currentIndex === this.lastIndex) {
    //         this.currentIndex = 0;
    //     } else {
    //         this.currentIndex++;
    //     }
    // }, 0);

    constructor() {

    }

    ngOnDestroy(): void {
        // clearInterval(this.timer);
    }

    ngOnInit(): void {
        this.lastIndex = this.dataStrings.length - 1;
    }
    ngOnChanges(): void {
        this.allMarques = this.dataStrings.map((item: any) => {
            return (
                `<span class="title" > ` + item.title + `: </span> ` + "  " +
                `<strong class="data" id="text-carousel-description">` + item.description + `</strong>`
            )
        })
    }

}
