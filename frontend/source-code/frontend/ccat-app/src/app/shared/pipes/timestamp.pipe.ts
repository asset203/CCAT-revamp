import { DatePipe } from '@angular/common';
import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
    name: 'timestamp',
})
export class TimestampPipe implements PipeTransform {
    constructor(private datepipe: DatePipe) {
    }
    transform(value: number, ...args: unknown[]): unknown {
        let newVal: number = value;
        console.log("DATEEEEEEEEEEEEEEEEEEEEEEE", value);
        if (value !== undefined && value !== null) {
            let valStr: string = value.toString();
            if (valStr.length === 10) {
                newVal = newVal * 1000;
            }
        }
        return this.datepipe.transform(new Date(newVal), 'dd/MM/yyyy');
    }
}
