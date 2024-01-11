import { Injectable } from '@angular/core';
import { BehaviorSubject, Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  signedIn = new BehaviorSubject(false);

  constructor() {
    this.signedIn = JSON.parse(localStorage.getItem('staySignedIn'))
  }
  changeFlag(value) {
    localStorage.setItem('staySignedIn', value);
    this.signedIn = value;
  }
  setItem(key, value) {

    if (this.signedIn) {
      localStorage.setItem(key, JSON.stringify(value))
    } else {
      sessionStorage.setItem(key, JSON.stringify(value));
    }
  }
  getItem(key) {
    if (this.signedIn) {
      let data = localStorage.getItem(key);
      return JSON.parse(data);
    }
    else {
      let data = sessionStorage.getItem(key);
      return JSON.parse(data);
    }
  }

  clearItem(item) {
    localStorage.removeItem(item);
    sessionStorage.removeItem(item);
  }
}
