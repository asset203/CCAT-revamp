import {Observable, Subject} from 'rxjs';
import {finalize} from 'rxjs/operators';
import {prepare} from './prepare';

/**
 * Custom Rxjs Operator to indicate loading in request.
 * @author Ahmed El-Shinawy
 * @param indicator Loading flag as a Subject.
 */
export function indicate<T>(indicator: Subject<boolean>): (source: Observable<T>) => Observable<T> {
    return (source: Observable<T>): Observable<T> =>
        source.pipe(
            prepare(() => indicator.next(true)),
            finalize(() => indicator.next(false))
        );
}
