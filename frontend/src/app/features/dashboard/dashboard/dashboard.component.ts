import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Analytics } from 'src/app/core/models/analytics.model';
import { JobApplication } from 'src/app/core/models/job-application.model';
import { User } from 'src/app/core/models/user.model';
import { AnalyticsService } from 'src/app/core/services/analytics.service';
import { AuthService } from 'src/app/core/services/auth.service';
import { JobApplicationService } from 'src/app/core/services/job-application.service';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  currentUser: User | null = null;
  analytics: Analytics | null = null;
  recentApps: JobApplication[] = [];
  loading = true;

  statCards = [
    { label: 'Total Applied', key: 'totalApplications', icon: 'send', color: '#1a237e' },
    { label: 'Interviews',    key: 'interviewsScheduled', icon: 'people', color: '#7b1fa2' },
    { label: 'Offers',        key: 'offersReceived', icon: 'celebration', color: '#2e7d32' },
    { label: 'Response Rate', key: 'responseRate', icon: 'trending_up', color: '#e65100', suffix: '%' }
  ];

  constructor(
    private authService: AuthService,
    private appService: JobApplicationService,
    private analyticsService: AnalyticsService,
    public router: Router
  ) {}

  get firstName(): string {
  return this.currentUser?.name?.split(' ')[0] || '';
}

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    if (this.authService.isAdmin()) {
      this.router.navigate(['/admin']);
      return;
    }
    this.loadData();
  }

  loadData() {
    this.analyticsService.getAnalytics().subscribe(res => {
      this.analytics = res.data;
    });
    this.appService.getAll().subscribe(res => {
      this.recentApps = res.data.slice(0, 5);
      this.loading = false;
    });
  }

  getStatValue(key: string): number {
    return (this.analytics as any)?.[key] ?? 0;
  }
}