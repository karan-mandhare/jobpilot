export type InterviewResult = 'PENDING' | 'PASSED' | 'FAILED';

export interface InterviewRound {
  id: number;
  roundNumber: number;
  roundType: string;
  scheduledAt?: string;
  result: InterviewResult;
  feedback?: string;
  interviewer?: string;
  createdAt: string;
}

export interface InterviewRoundRequest {
  roundNumber: number;
  roundType: string;
  scheduledAt?: string;
  result?: InterviewResult;
  feedback?: string;
  interviewer?: string;
}