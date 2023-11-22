import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'threeDots',
})
export class ThreeDotsPipe implements PipeTransform {
    constructor() {}
    transform(value: any): any {
        if (value !== undefined && value !== null) {
            return value.substring(0, 15) + ' ...';
        } else {
            return value;
        }
    }
}
