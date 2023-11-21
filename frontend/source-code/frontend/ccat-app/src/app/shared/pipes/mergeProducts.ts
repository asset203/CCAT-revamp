import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'mergeProducts',
})
export class MergeProducts implements PipeTransform {
    constructor() {
    }
    transform(arr: any[]): any {

        let merged = [];
        for (let index = 0; index < arr.length; index++) {

            for (let index2 = 0; index2 < arr[index].bucket.length; index2++) {
                const element = arr[index].bucket[index2];
                merged.push(element);
            }
        }
        return merged;
    }
}
