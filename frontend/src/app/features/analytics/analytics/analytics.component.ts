import { Component, OnInit } from '@angular/core';
import { ChartConfiguration, ChartData } from 'chart.js';
import { Analytics } from 'src/app/core/models/analytics.model';
import { AnalyticsService } from 'src/app/core/services/analytics.service';

@Component({
  selector: 'app-analytics',
  templateUrl: './analytics.component.html',
  styleUrls: ['./analytics.component.scss']
})
export class AnalyticsComponent implements OnInit {
  analytics: Analytics | null = null;
  loading = true;

  // Status Doughnut Chart
  statusChartData: ChartData<'doughnut'> = {
    labels: [],
    datasets: [{ data: [], backgroundColor: ['#1565c0','#f57f17','#6a1b9a','#2e7d32','#b71c1c'] }]
  };

  // Monthly Bar Chart
  monthlyChartData: ChartData<'bar'> = {
    labels: [],
    datasets: [{
      label: 'Applications',
      data: [],
      backgroundColor: '#1a237e',
      borderRadius: 6
    }]
  };

  barOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: { legend: { display: false } },
    scales: { y: { beginAtZero: true, ticks: { stepSize: 1 } } }
  };

  doughnutOptions: ChartConfiguration['options'] = {
    responsive: true,
    plugins: { legend: { position: 'right' } }
  };

  constructor(private analyticsService: AnalyticsService) {}

  ngOnInit() {
    this.analyticsService.getAnalytics().subscribe(res => {
      this.analytics = res.data;
      this.buildCharts();
      this.loading = false;
    });
  }

  buildCharts() {
    if (!this.analytics) return;

    // Status chart
    const statusEntries = Object.entries(this.analytics.statusBreakdown);
    this.statusChartData = {
      labels: statusEntries.map(([k]) => k),
      datasets: [{
        data: statusEntries.map(([, v]) => v),
        backgroundColor: ['#1565c0','#f57f17','#6a1b9a','#2e7d32','#b71c1c','#1b5e20','#616161']
      }]
    };

    // Monthly chart
    const monthlyEntries = Object.entries(this.analytics.monthlyApplications).slice(-6);
    this.monthlyChartData = {
      labels: monthlyEntries.map(([k]) => k),
      datasets: [{
        label: 'Applications',
        data: monthlyEntries.map(([, v]) => v),
        backgroundColor: '#1a237e',
        borderRadius: 6
      }]
    };
  }

  getSourceEntries(): [string, number][] {
    if (!this.analytics) return [];
    return Object.entries(this.analytics.sourceBreakdown)
      .sort(([, a], [, b]) => b - a);
  }

  getSourcePercent(count: number): number {
    return this.analytics ? Math.round(count / this.analytics.totalApplications * 100) : 0;
  }
}