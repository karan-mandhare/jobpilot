export interface Resume {
  id: number;
  fileName: string;
  fileUrl: string;
  isPrimary: boolean;
  aiFeedback?: string;
  uploadedAt: string;
}