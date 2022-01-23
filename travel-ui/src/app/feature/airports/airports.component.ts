import {
  AfterViewInit,
  Component,
  OnInit,
  ViewChild,
  ViewEncapsulation,
} from '@angular/core';
import { Form, FormControl } from '@angular/forms';
import { MatPaginator } from '@angular/material/paginator';
import { MatSort } from '@angular/material/sort';
import { merge, of } from 'rxjs';
import {
  catchError,
  debounceTime,
  map,
  startWith,
  switchMap,
} from 'rxjs/operators';
import { LocationService } from 'src/app/shared/service/location.service';
import { Location } from '../reservation/components/search-flight/model/location';

@Component({
  selector: 'app-airports',
  templateUrl: './airports.component.html',
  styleUrls: ['./airports.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class AirportsComponent implements AfterViewInit {
  displayedColumns: string[] = ['code', 'name', 'description'];
  data: Array<Location> = [];
  filter = new FormControl('');

  resultsLength = 0;
  isLoadingResults = true;
  isRateLimitReached = false;

  @ViewChild(MatPaginator) paginator: MatPaginator;
  @ViewChild(MatSort) sort: MatSort;

  constructor(private locationService: LocationService) {}

  ngAfterViewInit(): void {
    this.sort.sortChange.subscribe(() => (this.paginator.pageIndex = 0));

    merge(this.sort.sortChange, this.paginator.page, this.filter.valueChanges)
      .pipe(
        debounceTime(500),
        startWith({}),
        switchMap(() => {
          this.isLoadingResults = true;
          return this.locationService
            .paginate(
              this.filter.value,
              this.paginator.pageIndex,
              10,
              this.sort.active,
              this.sort.direction
            )
            .pipe(catchError(() => of(null)));
        }),
        map((data) => {
          this.isLoadingResults = false;
          if (data === null) {
            return [];
          }
          this.resultsLength = data.page.totalElements;
          return data.locations;
        })
      )
      .subscribe((data) => (this.data = data));
  }
}
