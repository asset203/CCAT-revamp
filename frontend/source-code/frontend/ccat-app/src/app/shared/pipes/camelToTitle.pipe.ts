import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
    name: 'camelToTitle',
})
export class CamelToTitlePipe implements PipeTransform {
    constructor() {}
    transform(value: any): any {
        if (value !== undefined && value !== null) {
            const result = value.replace(/([A-Z])/g, ' $1');
            return result.charAt(0).toUpperCase() + result.slice(1);
        } else {
            return value;
        }
    }
}
