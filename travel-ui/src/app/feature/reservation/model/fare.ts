import { Location } from '../components/search-flight/model/location';

export interface Fare {
  origin: Location;
  destination: Location;
  amount: number;
  currency: string;
}
