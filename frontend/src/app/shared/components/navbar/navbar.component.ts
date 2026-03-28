import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../../core/services/auth.service';
import { NotificationService } from '../../../core/services/notification.service';
import { Notification } from '../../../core/models/notification.model';
import { User } from '../../../core/models/user.model';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.scss']
})
export class NavbarComponent implements OnInit {
  @Output() toggleSidebar = new EventEmitter<void>();

  currentUser: User | null = null;
  notifications: Notification[] = [];
  unreadCount = 0;

  constructor(
    private authService: AuthService,
    private notificationService: NotificationService,
    private router: Router
  ) {}

  ngOnInit() {
    this.authService.currentUser$.subscribe(u => this.currentUser = u);
    this.notificationService.unreadCount$.subscribe(c => this.unreadCount = c);
    this.loadNotifications();
  }

  loadNotifications() {
    this.notificationService.getAll().subscribe(res => {
      this.notifications = res.data.slice(0, 5);
    });
    this.notificationService.getUnreadCount().subscribe();
  }

  markAllRead() {
    this.notificationService.markAllAsRead().subscribe(() => {
      this.notifications.forEach(n => n.isRead = true);
      this.notificationService.setUnreadCount(0);
    });
  }

  logout() {
    this.authService.logout();
  }

  onToggleSidebar() {
    this.toggleSidebar.emit();
  }
}