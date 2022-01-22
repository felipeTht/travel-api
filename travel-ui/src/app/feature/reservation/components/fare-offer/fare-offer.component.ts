import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { Location } from '../search-flight/model/location';

@Component({
  selector: 'app-fare-offer',
  templateUrl: './fare-offer.component.html',
  styleUrls: ['./fare-offer.component.scss'],
})
export class FareOfferComponent implements OnInit {
  @Input() origin: Location;
  @Input() destination: Location;
  @Input() amount: number;
  @Input() currencyCode: string;

  @Output() back = new EventEmitter();

  constructor() {}

  ngOnInit(): void {}

  goBack() {
    this.back.emit(true);
  }
}
