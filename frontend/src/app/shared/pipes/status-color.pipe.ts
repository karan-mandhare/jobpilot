import { Pipe, PipeTransform } from '@angular/core';
import { ApplicationStatus } from 'src/app/core/models/job-application.model';

@Pipe({ name: 'statusColor' })
export class StatusColorPipe implements PipeTransform {
  transform(status: ApplicationStatus): string {
    const map: Record<ApplicationStatus, string> = {
      APPLIED:   'status-applied',
      SCREENING: 'status-screening',
      INTERVIEW: 'status-interview',
      OFFER:     'status-offer',
      REJECTED:  'status-rejected',
      ACCEPTED:  'status-accepted',
      WITHDRAWN: 'status-withdrawn'
    };
    return map[status] || '';
  }
}