package com.jobpilotapplication.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ResumeResponse {
    private Long id;
    private String fileName;
    private String fileUrl;
    private Boolean isPrimary;
    private String aiFeedback;
    private LocalDateTime uploadedAt;
}