import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { ReservationComponent } from './feature/reservation/reservation.component';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MaterialModule } from './shared/shared/module/material.module';
import { HeaderComponent } from './core/header/header.component';
import { SearchFlightComponent } from './feature/reservation/components/search-flight/search-flight.component';

@NgModule({
  declarations: [AppComponent, ReservationComponent, DashboardComponent, HeaderComponent, SearchFlightComponent],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
