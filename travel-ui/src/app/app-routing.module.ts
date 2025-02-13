import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AirportsComponent } from './feature/airports/airports.component';
import { DashboardComponent } from './feature/dashboard/dashboard.component';
import { PageNotFoundComponent } from './feature/page-not-found/page-not-found.component';
import { ReservationComponent } from './feature/reservation/reservation.component';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'reservation',
    pathMatch: 'full',
  },
  {
    path: 'reservation',
    component: ReservationComponent,
  },
  {
    path: 'dashboard',
    component: DashboardComponent,
  },
  {
    path: 'airports',
    component: AirportsComponent,
  },
  { path: '**', pathMatch: 'full', component: PageNotFoundComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
