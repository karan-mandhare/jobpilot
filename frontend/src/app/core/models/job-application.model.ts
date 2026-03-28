import { InterviewRound } from "./interview-round.model";

export type ApplicationStatus =
  | 'APPLIED'
  | 'SCREENING'
  | 'INTERVIEW'
  | 'OFFER'
  | 'REJECTED'
  | 'ACCEPTED'
  | 'WITHDRAWN';

export interface JobApplication {
  id: number;
  companyName: string;
  jobRole: string;
  jobUrl?: string;
  location?: string;
  salaryRange?: string;
  status: ApplicationStatus;
  appliedDate: string;
  followUpDate?: string;
  jobDescription?: string;
  notes?: string;
  source?: string;
  createdAt: string;
  updatedAt: string;
  interviewRounds?: InterviewRound[];
}

export interface JobApplicationRequest {
  companyName: string;
  jobRole: string;
  jobUrl?: string;
  location?: string;
  salaryRange?: string;
  status?: ApplicationStatus;
  appliedDate: string;
  followUpDate?: string;
  jobDescription?: string;
  notes?: string;
  source?: string;
}

export interface KanbanColumn {
  status: ApplicationStatus;
  label: string;
  colorClass: string;
  bgClass: string;
  items: JobApplication[];
}