import {
  animate,
  state,
  style,
  transition,
  trigger,
} from '@angular/animations';
import { Component, OnInit } from '@angular/core';
import { FareService } from 'src/app/shared/service/fare.service';
import { Location } from './components/search-flight/model/location';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss'],
  animations: [
    trigger('flipState', [
      state(
        'active',
        style({
          transform: 'rotateY(179deg)',
        })
      ),
      state(
        'inactive',
        style({
          transform: 'rotateY(0)',
        })
      ),
      transition('active => inactive', animate('500ms ease-out')),
      transition('inactive => active', animate('500ms ease-in')),
    ]),
  ],
})
export class ReservationComponent implements OnInit {
  loading: boolean = false;
  origin: Location;
  destination: Location;
  amount: number;
  currency: string;

  constructor(private fareService: FareService) {}

  ngOnInit(): void {}

  flip: string = 'inactive';

  toggleFlip() {
    this.flip = this.flip == 'inactive' ? 'active' : 'inactive';
  }

  locationsSelected(event: any) {
    const { origin, destination } = event;
    this.loading = true;
    this.fareService.getFare(origin, destination).subscribe((fare) => {
      ({
        origin: this.origin,
        destination: this.destination,
        amount: this.amount,
        currency: this.currency,
      } = fare);
      this.loading = false;
      this.toggleFlip();
    });
  }
}
