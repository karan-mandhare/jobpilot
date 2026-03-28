package com.jobpilotapplication.dto.response;

import com.jobpilotapplication.enums.Role;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class JobApplicationResponse {
    private Long id;
    private String companyName;
    private String jobRole;
    private String jobUrl;
    private String location;
    private String salaryRange;
    private Role.ApplicationStatus status;
    private LocalDate appliedDate;
    private LocalDate followUpDate;
    private String jobDescription;
    private String notes;
    private String source;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<InterviewRoundResponse> interviewRounds;
}