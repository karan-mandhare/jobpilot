import { Component, OnInit } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { MatDialog } from '@angular/material/dialog';
import { JobApplicationService } from '../../../core/services/job-application.service';
import { ToastService } from '../../../core/services/toast.service';
import { JobApplication, ApplicationStatus, KanbanColumn } from '../../../core/models/job-application.model';
import { ApplicationFormComponent } from '../application-form/application-form.component';
import { ApplicationDetailComponent } from '../application-detail/application-detail.component';

@Component({
  selector: 'app-kanban-board',
  templateUrl: './kanban-board.component.html',
  styleUrls: ['./kanban-board.component.scss']
})
export class KanbanBoardComponent implements OnInit {
  loading = true;
  searchTerm = '';

  columns: KanbanColumn[] = [
    { status: 'APPLIED',   label: 'Applied',   colorClass: 'col-applied',   bgClass: 'bg-applied',   items: [] },
    { status: 'SCREENING', label: 'Screening', colorClass: 'col-screening', bgClass: 'bg-screening', items: [] },
    { status: 'INTERVIEW', label: 'Interview', colorClass: 'col-interview', bgClass: 'bg-interview', items: [] },
    { status: 'OFFER',     label: 'Offer',     colorClass: 'col-offer',     bgClass: 'bg-offer',     items: [] },
    { status: 'REJECTED',  label: 'Rejected',  colorClass: 'col-rejected',  bgClass: 'bg-rejected',  items: [] }
  ];

  get columnIds(): string[] {
    return this.columns.map(c => c.status);
  }

  constructor(
    private appService: JobApplicationService,
    private toast: ToastService,
    private dialog: MatDialog
  ) {}

  ngOnInit() { this.loadApplications(); }

  loadApplications() {
    this.loading = true;
    this.appService.getAll().subscribe(res => {
      this.columns.forEach(col => {
        col.items = res.data.filter(a => a.status === col.status);
      });
      this.loading = false;
    });
  }

  onDrop(event: CdkDragDrop<JobApplication[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(
        event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex
      );
      const app = event.container.data[event.currentIndex];
      const newStatus = event.container.id as ApplicationStatus;
      this.appService.updateStatus(app.id, newStatus).subscribe({
        next: () => this.toast.success(`Moved to ${newStatus}`),
        error: () => {
          // Revert on error
          transferArrayItem(
            event.container.data,
            event.previousContainer.data,
            event.currentIndex,
            event.previousIndex
          );
          this.toast.error('Failed to update status');
        }
      });
    }
  }

  openForm(app?: JobApplication) {
    const ref = this.dialog.open(ApplicationFormComponent, {
      width: '600px',
      data: { application: app }
    });
    ref.afterClosed().subscribe(result => {
      if (result) this.loadApplications();
    });
  }

  openDetail(app: JobApplication) {
    const ref = this.dialog.open(ApplicationDetailComponent, {
      width: '700px',
      data: { application: app }
    });
    ref.afterClosed().subscribe(result => {
      if (result) this.loadApplications();
    });
  }

  onSearch() {
    if (!this.searchTerm.trim()) {
      this.loadApplications();
      return;
    }
    this.appService.getAll(undefined, this.searchTerm).subscribe(res => {
      this.columns.forEach(col => {
        col.items = res.data.filter(a => a.status === col.status);
      });
    });
  }
}