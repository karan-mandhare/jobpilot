import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobApplication } from '../../../core/models/job-application.model';
import { InterviewRound } from '../../../core/models/interview-round.model';
import { JobApplicationService } from '../../../core/services/job-application.service';
import { InterviewRoundService } from '../../../core/services/interview-round.service';
import { ToastService } from '../../../core/services/toast.service';

@Component({
  selector: 'app-application-detail',
  templateUrl: './application-detail.component.html',
  styleUrls: ['./application-detail.component.scss']
})
export class ApplicationDetailComponent implements OnInit {
  app: JobApplication;
  rounds: InterviewRound[] = [];
  roundForm: FormGroup;
  showRoundForm = false;
  loading = false;

  roundTypes = ['DSA', 'Technical', 'System Design', 'HR', 'Machine Coding', 'Managerial'];
  results    = ['PENDING', 'PASSED', 'FAILED'];

  constructor(
    private fb: FormBuilder,
    private appService: JobApplicationService,
    private roundService: InterviewRoundService,
    private toast: ToastService,
    public dialogRef: MatDialogRef<ApplicationDetailComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { application: JobApplication }
  ) {
    this.app = data.application;
    this.roundForm = this.fb.group({
      roundNumber: [1, Validators.required],
      roundType:   ['Technical', Validators.required],
      scheduledAt: [''],
      result:      ['PENDING'],
      feedback:    [''],
      interviewer: ['']
    });
  }

  ngOnInit() { this.loadRounds(); }

  loadRounds() {
    this.roundService.getByApplication(this.app.id).subscribe(res => {
      this.rounds = res.data;
    });
  }

  addRound() {
    if (this.roundForm.invalid) return;
    this.loading = true;
    this.roundService.addRound(this.app.id, this.roundForm.value).subscribe({
      next: () => {
        this.toast.success('Round added!');
        this.loadRounds();
        this.showRoundForm = false;
        this.loading = false;
      },
      error: () => { this.toast.error('Failed to add round'); this.loading = false; }
    });
  }

  deleteRound(roundId: number) {
    this.roundService.deleteRound(this.app.id, roundId).subscribe(() => {
      this.toast.success('Round deleted');
      this.loadRounds();
    });
  }

  deleteApp() {
    if (!confirm(`Delete application for ${this.app.companyName}?`)) return;
    this.appService.delete(this.app.id).subscribe(() => {
      this.toast.success('Application deleted');
      this.dialogRef.close(true);
    });
  }

  getResultColor(result: string): string {
    return result === 'PASSED' ? 'primary' : result === 'FAILED' ? 'warn' : 'accent';
  }
}