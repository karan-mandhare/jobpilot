import { Component, OnInit } from '@angular/core';
import { Resume } from 'src/app/core/models/resume.model';
import { AiService } from 'src/app/core/services/ai.service';
import { ResumeService } from 'src/app/core/services/resume.service';
import { ToastService } from 'src/app/core/services/toast.service';

@Component({
  selector: 'app-resume',
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.scss']
})
export class ResumeComponent implements OnInit {
  resumes: Resume[] = [];
  loading = false;
  uploading = false;
  reviewingId: number | null = null;
  reviewText = '';
  selectedResume: Resume | null = null;

  constructor(
    private resumeService: ResumeService,
    private aiService: AiService,
    private toast: ToastService
  ) {}

  ngOnInit() { this.loadResumes(); }

  loadResumes() {
    this.loading = true;
    this.resumeService.getAll().subscribe(res => {
      this.resumes = res.data;
      this.loading = false;
    });
  }

  onFileSelected(event: Event) {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (!file) return;
    if (file.type !== 'application/pdf') {
      this.toast.error('Only PDF files allowed');
      return;
    }
    this.uploading = true;
    this.resumeService.upload(file).subscribe({
      next: () => {
        this.toast.success('Resume uploaded!');
        this.loadResumes();
        this.uploading = false;
      },
      error: () => {
        this.toast.error('Upload failed');
        this.uploading = false;
      }
    });
  }

  setPrimary(id: number) {
    this.resumeService.setPrimary(id).subscribe(() => {
      this.toast.success('Set as primary resume');
      this.loadResumes();
    });
  }

  deleteResume(id: number) {
    if (!confirm('Delete this resume?')) return;
    this.resumeService.delete(id).subscribe(() => {
      this.toast.success('Resume deleted');
      this.loadResumes();
    });
  }

  reviewResume(resume: Resume) {
    this.reviewingId = resume.id;
    this.selectedResume = resume;
    this.reviewText = '';
    // In real app, extract text from PDF using pdf.js
    // For demo, use a placeholder text
    const resumeText = `Candidate with 1.5 years experience in Spring Boot and Angular. 
      Worked on REST APIs, JWT authentication, database design with PostgreSQL.`;
    this.aiService.reviewResume(resumeText).subscribe({
      next: (res) => {
        this.reviewText = res.data.result;
        this.reviewingId = null;
      },
      error: () => {
        this.toast.error('AI review failed');
        this.reviewingId = null;
      }
    });
  }
}