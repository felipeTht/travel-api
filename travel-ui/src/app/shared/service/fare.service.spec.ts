import { HttpClientModule } from '@angular/common/http';
import { TestBed } from '@angular/core/testing';

import { FareService } from './fare.service';

describe('FareService', () => {
  let service: FareService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientModule],
    });
    service = TestBed.inject(FareService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
