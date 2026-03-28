export interface Analytics {
  totalApplications: number;
  offersReceived: number;
  interviewsScheduled: number;
  responseRate: number;
  statusBreakdown: { [key: string]: number };
  monthlyApplications: { [key: string]: number };
  sourceBreakdown: { [key: string]: number };
  roleBreakdown: { [key: string]: number };
}