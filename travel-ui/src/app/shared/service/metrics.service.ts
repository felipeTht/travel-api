import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Metrics } from '../model/metrics';

@Injectable({
  providedIn: 'root',
})
export class MetricsService {
  private BASE_URL = environment.baseurl;

  constructor(private http: HttpClient) {}

  get() {
    return this.http.get<Metrics>(`${this.BASE_URL}/metrics`);
  }
}
