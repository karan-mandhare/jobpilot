import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { InterviewRound, InterviewRoundRequest } from '../models/interview-round.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class InterviewRoundService {
  private apiUrl = `${environment.apiUrl}/api/v1/applications`;

  constructor(private http: HttpClient) {}

  getByApplication(applicationId: number): Observable<ApiResponse<InterviewRound[]>> {
    return this.http.get<ApiResponse<InterviewRound[]>>(
      `${this.apiUrl}/${applicationId}/rounds`
    );
  }

  addRound(applicationId: number, request: InterviewRoundRequest): Observable<ApiResponse<InterviewRound>> {
    return this.http.post<ApiResponse<InterviewRound>>(
      `${this.apiUrl}/${applicationId}/rounds`, request
    );
  }

  updateRound(applicationId: number, roundId: number, request: InterviewRoundRequest): Observable<ApiResponse<InterviewRound>> {
    return this.http.put<ApiResponse<InterviewRound>>(
      `${this.apiUrl}/${applicationId}/rounds/${roundId}`, request
    );
  }

  deleteRound(applicationId: number, roundId: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(
      `${this.apiUrl}/${applicationId}/rounds/${roundId}`
    );
  }
}