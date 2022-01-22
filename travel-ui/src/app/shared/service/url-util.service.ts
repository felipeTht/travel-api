import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class UrlUtilService {
  constructor() {}

  convertObjectToSearchParam(obj: any) {
    let params = new URLSearchParams();
    for (let key in obj) {
      params.set(key, obj[key]);
    }
    return params.toString();
  }
}
