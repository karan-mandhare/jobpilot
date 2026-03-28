import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { JobApplication, JobApplicationRequest } from '../models/job-application.model';
import { ApiResponse } from '../models/api-response.model';

@Injectable({ providedIn: 'root' })
export class JobApplicationService {
  private apiUrl = `${environment.apiUrl}/api/v1/applications`;

  constructor(private http: HttpClient) {}

  getAll(status?: string, search?: string): Observable<ApiResponse<JobApplication[]>> {
    let params = new HttpParams();
    if (status) params = params.set('status', status);
    if (search) params = params.set('search', search);
    return this.http.get<ApiResponse<JobApplication[]>>(this.apiUrl, { params });
  }

  getById(id: number): Observable<ApiResponse<JobApplication>> {
    return this.http.get<ApiResponse<JobApplication>>(`${this.apiUrl}/${id}`);
  }

  create(request: JobApplicationRequest): Observable<ApiResponse<JobApplication>> {
    return this.http.post<ApiResponse<JobApplication>>(this.apiUrl, request);
  }

  update(id: number, request: JobApplicationRequest): Observable<ApiResponse<JobApplication>> {
    return this.http.put<ApiResponse<JobApplication>>(`${this.apiUrl}/${id}`, request);
  }

  updateStatus(id: number, status: string): Observable<ApiResponse<JobApplication>> {
    return this.http.patch<ApiResponse<JobApplication>>(
      `${this.apiUrl}/${id}/status`, null, { params: { status } }
    );
  }

  delete(id: number): Observable<ApiResponse<void>> {
    return this.http.delete<ApiResponse<void>>(`${this.apiUrl}/${id}`);
  }
}