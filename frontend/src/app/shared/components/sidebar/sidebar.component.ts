import { Component, Input } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';

interface NavItem {
  label: string;
  icon: string;
  route: string;
}

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.scss']
})
export class SidebarComponent {
  @Input() isOpen = true;

  navItems: NavItem[] = [
    { label: 'Dashboard',     icon: 'dashboard',      route: '/dashboard' },
    { label: 'Applications',  icon: 'view_kanban',     route: '/applications' },
    { label: 'Resume',        icon: 'description',     route: '/resume' },
    { label: 'AI Prep',       icon: 'psychology',      route: '/ai-prep' },
    { label: 'Analytics',     icon: 'bar_chart',       route: '/analytics' }
  ];

  constructor(public authService: AuthService) {}
}