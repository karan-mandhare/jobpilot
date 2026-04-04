import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/core/models/user.model';
import { AdminService, AdminStats } from 'src/app/core/services/admin.service';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-admin-dashboard',
  templateUrl: './admin-dashboard.component.html',
  styleUrls: ['./admin-dashboard.component.scss']
})
export class AdminDashboardComponent implements OnInit {
  currentUser: User | null = null;
  stats: AdminStats | null = null;
  users: User[] = [];
  loading = true;
  usersLoading = true;
  statsError = false;
  usersError = false;

  displayedColumns = ['name', 'email', 'role', 'totalApplications', 'createdAt'];

  statCards = [
    { label: 'Total Users',        key: 'totalUsers',         icon: 'group',       color: '#1a237e' },
    { label: 'Total Applications', key: 'totalApplications',  icon: 'work',        color: '#7b1fa2' },
    { label: 'New This Month',     key: 'newUsersThisMonth',  icon: 'person_add',  color: '#2e7d32' },
    { label: 'Active Users',       key: 'activeUsers',        icon: 'trending_up', color: '#e65100' }
  ];

  constructor(
    private authService: AuthService,
    private adminService: AdminService
  ) {}

  get firstName(): string {
    return this.currentUser?.name?.split(' ')[0] || 'Admin';
  }

  ngOnInit() {
    this.currentUser = this.authService.getCurrentUser();
    this.loadStats();
    this.loadUsers();
  }

  loadStats() {
    this.adminService.getStats().subscribe({
      next: res => {
        this.stats = res.data;
        this.loading = false;
      },
      error: () => {
        this.statsError = true;
        this.loading = false;
      }
    });
  }

  loadUsers() {
    this.adminService.getUsers().subscribe({
      next: res => {
        this.users = res.data;
        this.usersLoading = false;
      },
      error: () => {
        this.usersError = true;
        this.usersLoading = false;
      }
    });
  }

  getStatValue(key: string): number {
    return (this.stats as any)?.[key] ?? 0;
  }
}
