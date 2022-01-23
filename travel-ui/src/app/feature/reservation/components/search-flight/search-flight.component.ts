import {
  Component,
  EventEmitter,
  Input,
  OnInit,
  Output,
  ViewEncapsulation,
} from '@angular/core';
import {
  FormBuilder,
  FormControl,
  FormGroup,
  Validators,
} from '@angular/forms';
import { of } from 'rxjs';
import { debounceTime, switchMap, tap } from 'rxjs/operators';
import { LocationService } from 'src/app/shared/service/location.service';
import { Location } from './model/location';

@Component({
  selector: 'app-search-flight',
  templateUrl: './search-flight.component.html',
  styleUrls: ['./search-flight.component.scss'],
  encapsulation: ViewEncapsulation.None,
})
export class SearchFlightComponent implements OnInit {
  @Input() loading: boolean = false;
  @Output() locationsSelected = new EventEmitter();

  form: FormGroup;

  origins: Array<Location> = [];
  destinations: Array<Location> = [];

  constructor(
    private formBuilder: FormBuilder,
    private locationService: LocationService
  ) {}

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      origin: new FormControl('', [Validators.required]),
      destination: new FormControl('', [Validators.required]),
    });
    this.loadAutocompletes();
  }

  private loadAutocompletes() {
    this.form
      .get('origin')
      ?.valueChanges.pipe(
        debounceTime(500),
        tap(() => {
          if (
            !this.autocompleteHasValue(
              this.origins,
              this.form.get('origin')?.value
            )
          ) {
            this.origins = [];
          }
        }),
        switchMap((value) => {
          return this.autocompleteHasValue(
            this.origins,
            this.form.get('origin')?.value
          )
            ? of(this.origins)
            : this.locationService.find(value);
        })
      )
      .subscribe((data) => {
        this.origins = data;
      });
    this.form
      .get('destination')
      ?.valueChanges.pipe(
        debounceTime(500),
        tap(() => {
          if (
            !this.autocompleteHasValue(
              this.destinations,
              this.form.get('destination')?.value
            )
          ) {
            this.destinations = [];
          }
        }),
        switchMap((value) => {
          return this.autocompleteHasValue(
            this.destinations,
            this.form.get('destination')?.value
          )
            ? of(this.destinations)
            : this.locationService.find(value);
        })
      )
      .subscribe((data) => {
        this.destinations = data;
      });
  }

  public autocompleteHasValue(values: Array<Location>, code: string) {
    return !!values.find((el) => el.code === code);
  }

  getDescriptionOriginLocation(code: any) {
    return this.origins.find((el) => el.code === code)?.description || '';
  }

  getDescriptionDestinationLocation(code: any) {
    return this.destinations.find((el) => el.code === code)?.description || '';
  }

  private areLocationsSelected() {
    const origin = this.form.get('origin')?.value;
    const destination = this.form.get('destination')?.value;
    return (
      this.autocompleteHasValue(this.origins, origin) &&
      this.autocompleteHasValue(this.destinations, destination)
    );
  }

  locationSelected() {
    if (this.form.valid && this.areLocationsSelected()) {
      this.locationsSelected.emit({
        origin: this.form.get('origin')?.value,
        destination: this.form.get('destination')?.value,
      });
    }
  }
}
