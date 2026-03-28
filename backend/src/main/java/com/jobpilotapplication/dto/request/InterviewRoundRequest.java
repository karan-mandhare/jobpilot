package com.jobpilotapplication.dto.request;

import com.jobpilotapplication.enums.RoundType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class InterviewRoundRequest {

    @NotNull(message = "Round number is required")
    private Integer roundNumber;

    private String roundType;
    private LocalDateTime scheduledAt;
    private RoundType.InterviewResult result;
    private String feedback;
    private String interviewer;
}