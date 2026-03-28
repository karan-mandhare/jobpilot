import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Resume } from '../models/resume.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class ResumeService {
  private apiUrl = `${environment.apiUrl}/api/v1/resumes`;

  constructor(private http: HttpClient) {}

  getAll(): Observable<ApiResponse<Resume[]>> {
    return this.http.get<ApiResponse<Resume[]>>(this.apiUrl);
  }

  upload(file: File): Observable<ApiResponse<Resume>> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post<ApiResponse<Resume>>(`${this.apiUrl}/upload`, formData);
  }

  setPrimary(id: number): Observable<ApiResponse<Resume>> {
    return this.http.patch<ApiResponse<Resume>>(`${this.apiUrl}/${id}/primary`, {});
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }
}