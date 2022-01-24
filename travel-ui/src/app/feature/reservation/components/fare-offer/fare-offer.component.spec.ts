import { NO_ERRORS_SCHEMA } from '@angular/core';
import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FareOfferComponent } from './fare-offer.component';

describe('FareOfferComponent', () => {
  let component: FareOfferComponent;
  let fixture: ComponentFixture<FareOfferComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [FareOfferComponent],
      schemas: [NO_ERRORS_SCHEMA],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FareOfferComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
