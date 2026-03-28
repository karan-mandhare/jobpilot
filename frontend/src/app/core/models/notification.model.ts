export interface Notification {
  id: number;
  message: string;
  type: 'REMINDER' | 'SYSTEM' | 'INFO';
  isRead: boolean;
  createdAt: string;
}