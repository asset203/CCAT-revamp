import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'appFind',
})
export class AppFindPipe implements PipeTransform {
    constructor() {
    }
    transform(value: any, arr:any[]): any {
        if(value!=='-'){
            const targetItem = arr.filter(item=>item.value===value)[0]
            return targetItem.name;
        }
        else{
            return value
        }
    }
}
