import { Component, Inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { JobApplicationService } from '../../../core/services/job-application.service';
import { ToastService } from '../../../core/services/toast.service';
import { JobApplication } from '../../../core/models/job-application.model';

@Component({
  selector: 'app-application-form',
  templateUrl: './application-form.component.html',
  styleUrls: ['./application-form.component.scss']
})
export class ApplicationFormComponent implements OnInit {
  form: FormGroup;
  loading = false;
  isEdit: boolean;

  statuses = ['APPLIED','SCREENING','INTERVIEW','OFFER','REJECTED','ACCEPTED','WITHDRAWN'];
  sources  = ['LinkedIn','Naukri','Referral','Direct','AngelList','Company Website','Other'];

  constructor(
    private fb: FormBuilder,
    private appService: JobApplicationService,
    private toast: ToastService,
    private dialogRef: MatDialogRef<ApplicationFormComponent>,
    @Inject(MAT_DIALOG_DATA) public data: { application?: JobApplication }
  ) {
    this.isEdit = !!data?.application;
    this.form = this.fb.group({
      companyName:    ['', Validators.required],
      jobRole:        ['', Validators.required],
      jobUrl:         [''],
      location:       [''],
      salaryRange:    [''],
      status:         ['APPLIED'],
      appliedDate:    [new Date().toISOString().split('T')[0], Validators.required],
      followUpDate:   [''],
      jobDescription: [''],
      notes:          [''],
      source:         ['LinkedIn']
    });
  }

  ngOnInit() {
    if (this.isEdit && this.data.application) {
      this.form.patchValue(this.data.application);
    }
  }

  submit() {
    if (this.form.invalid) return;
    this.loading = true;
    const req = this.form.value;

    const obs = this.isEdit
      ? this.appService.update(this.data.application!.id, req)
      : this.appService.create(req);

    obs.subscribe({
      next: () => {
        this.toast.success(this.isEdit ? 'Application updated!' : 'Application added!');
        this.dialogRef.close(true);
      },
      error: () => {
        this.toast.error('Something went wrong');
        this.loading = false;
      }
    });
  }
}