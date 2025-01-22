import { Injectable } from '@angular/core';
import { Resolve } from '@angular/router';
import { Observable } from 'rxjs';
import { of } from 'rxjs';
import { delay } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class DelayResolver implements Resolve<void> {
  resolve(): Observable<void> {
    return of(null).pipe(delay(200)); // Delay by 2 seconds
  }
}