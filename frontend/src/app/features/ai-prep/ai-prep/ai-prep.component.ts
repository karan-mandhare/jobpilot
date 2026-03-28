import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { AiService } from 'src/app/core/services/ai.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-ai-prep',
  templateUrl: './ai-prep.component.html',
  styleUrls: ['./ai-prep.component.scss']
})
export class AiPrepComponent {
  activeTab = 0;
  loadingInterview = false;
  loadingCover     = false;

  interviewForm: FormGroup;
  coverForm: FormGroup;

  interviewResult = '';
  coverResult     = '';

  experienceLevels = ['Fresher (0-1 yr)', 'Junior (1-3 yrs)', 'Mid-level (3-5 yrs)', 'Senior (5+ yrs)'];
  popularRoles = ['Backend Developer', 'Full Stack Developer', 'Java Developer',
                  'Spring Boot Developer', 'Frontend Developer', 'Software Engineer'];

  constructor(
    private fb: FormBuilder,
    private aiService: AiService,
    private toast: ToastService
  ) {
    this.interviewForm = this.fb.group({
      jobRole: ['', Validators.required],
      experienceLevel: ['Junior (1-3 yrs)', Validators.required]
    });

    this.coverForm = this.fb.group({
      jobRole:       ['', Validators.required],
      companyName:   ['', Validators.required],
      resumeSummary: ['', Validators.required]
    });
  }

  generateQuestions() {
    if (this.interviewForm.invalid) return;
    this.loadingInterview = true;
    this.interviewResult = '';

    this.aiService.generateInterviewQuestions(
      this.interviewForm.value.jobRole,
      this.interviewForm.value.experienceLevel
    ).subscribe({
      next: (res) => {
        this.interviewResult = res.data.result;
        this.loadingInterview = false;
      },
      error: () => {
        this.toast.error('AI service failed. Please try again.');
        this.loadingInterview = false;
      }
    });
  }

  generateCoverLetter() {
    if (this.coverForm.invalid) return;
    this.loadingCover = true;
    this.coverResult = '';

    this.aiService.generateCoverLetter(
      this.coverForm.value.jobRole,
      this.coverForm.value.companyName,
      this.coverForm.value.resumeSummary
    ).subscribe({
      next: (res) => {
        this.coverResult = res.data.result;
        this.loadingCover = false;
      },
      error: () => {
        this.toast.error('AI service failed. Please try again.');
        this.loadingCover = false;
      }
    });
  }

  fillRole(role: string) {
    this.interviewForm.patchValue({ jobRole: role });
  }

  copyToClipboard(text: string) {
    navigator.clipboard.writeText(text);
    this.toast.success('Copied to clipboard!');
  }
}