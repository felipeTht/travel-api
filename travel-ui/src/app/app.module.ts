import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './core/header/header.component';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { FareOfferComponent } from './feature/reservation/components/fare-offer/fare-offer.component';
import { SearchFlightComponent } from './feature/reservation/components/search-flight/search-flight.component';
import { ReservationComponent } from './feature/reservation/reservation.component';
import { MaterialModule } from './shared/module/material.module';
import { SharedModule } from './shared/module/shared.module';
import { AirportsComponent } from './feature/airports/airports.component';
import { PageNotFoundComponent } from './feature/page-not-found/page-not-found.component';
import { NgChartsModule } from 'ng2-charts';

@NgModule({
  declarations: [
    AppComponent,
    ReservationComponent,
    DashboardComponent,
    HeaderComponent,
    SearchFlightComponent,
    FareOfferComponent,
    AirportsComponent,
    PageNotFoundComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MaterialModule,
    HttpClientModule,
    SharedModule,
    NgChartsModule,
  ],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
