import {defer, Observable} from 'rxjs';

/**
 * Custom Rxjs Operator to prepare action before request.
 * @author Ahmed El-Shinawy
 * @param callback
 */
export function prepare<T>(callback: () => void): (source: Observable<T>) => Observable<T> {
    return (source: Observable<T>): Observable<T> =>
        defer(() => {
            callback();
            return source;
        });
}
