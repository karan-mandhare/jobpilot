import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ApiResponse } from '../models/api-response.model';

export interface AiResponse {
  result: string;
  sessionType: string;
}

@Injectable({ providedIn: 'root' })
export class AiService {
  private apiUrl = `${environment.apiUrl}/api/v1/ai`;

  constructor(private http: HttpClient) {}

  reviewResume(resumeText: string): Observable<ApiResponse<AiResponse>> {
    return this.http.post<ApiResponse<AiResponse>>(`${this.apiUrl}/resume-review`, { resumeText });
  }

  generateInterviewQuestions(jobRole: string, experienceLevel: string): Observable<ApiResponse<AiResponse>> {
    return this.http.post<ApiResponse<AiResponse>>(`${this.apiUrl}/interview-questions`, {
      jobRole, experienceLevel
    });
  }

  generateCoverLetter(jobRole: string, companyName: string, resumeSummary: string): Observable<ApiResponse<AiResponse>> {
    return this.http.post<ApiResponse<AiResponse>>(`${this.apiUrl}/cover-letter`, {
      jobRole, companyName, resumeSummary
    });
  }
}