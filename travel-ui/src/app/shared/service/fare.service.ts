import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Fare } from 'src/app/feature/reservation/model/fare';
import { environment } from 'src/environments/environment';
import { UrlUtilService } from './url-util.service';

@Injectable({
  providedIn: 'root',
})
export class FareService {
  private BASE_URL: String = environment.baseurl;

  constructor(private http: HttpClient, private urlUtil: UrlUtilService) {}

  getFare(origin: string, destination: string) {
    return this.http.get<Fare>(
      `${this.BASE_URL}/api/fares?${this.urlUtil.convertObjectToSearchParam({
        origin,
        destination,
      })}`
    );
  }
}
