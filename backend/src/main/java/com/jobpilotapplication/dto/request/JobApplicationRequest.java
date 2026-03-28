package com.jobpilotapplication.dto.request;

import com.jobpilotapplication.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class JobApplicationRequest {

    @NotBlank(message = "Company name is required")
    private String companyName;

    @NotBlank(message = "Job role is required")
    private String jobRole;

    private String jobUrl;
    private String location;
    private String salaryRange;
    private Role.ApplicationStatus status;

    @NotNull(message = "Applied date is required")
    private LocalDate appliedDate;

    private LocalDate followUpDate;
    private String jobDescription;
    private String notes;
    private String source;
}