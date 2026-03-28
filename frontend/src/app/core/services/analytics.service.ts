import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Analytics } from '../models/analytics.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class AnalyticsService {
  private apiUrl = `${environment.apiUrl}/api/v1/analytics`;

  constructor(private http: HttpClient) {}

  getAnalytics(): Observable<ApiResponse<Analytics>> {
    return this.http.get<ApiResponse<Analytics>>(this.apiUrl);
  }
}