import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { SortDirection } from '@angular/material/sort';
import { of } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { PaginatedLocation } from 'src/app/feature/airports/model/paginatedLocation';
import { Location } from 'src/app/feature/reservation/components/search-flight/model/location';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root',
})
export class LocationService {
  private BASE_URL: String = environment.baseurl;

  constructor(private http: HttpClient) {}

  find(term: string) {
    return this.http
      .get<Array<Location>>(`${this.BASE_URL}/api/locations?term=${term}`)
      .pipe(
        catchError((error) => {
          return of([
            { code: '', name: '', description: '--- No Location found ---' },
          ]);
        })
      );
  }

  paginate(
    term: string,
    page: number,
    size: number,
    sort: string,
    direction: SortDirection
  ) {
    return this.http.get<PaginatedLocation>(
      `${this.BASE_URL}/api/locations/paginated?term=${term}&page=${page}&size=${size}&sort=${sort}&direction=${direction}`
    );
  }
}
