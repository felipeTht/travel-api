import { Location } from '../../reservation/components/search-flight/model/location';

export interface Page {
  totalElements: number;
  totalPages: number;
}

export interface PaginatedLocation {
  locations: Array<Location>;
  page: Page;
}
