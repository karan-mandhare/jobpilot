package com.jobpilotapplication.dto.response;

import com.jobpilotapplication.enums.RoundType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class InterviewRoundResponse {
    private Long id;
    private Integer roundNumber;
    private String roundType;
    private LocalDateTime scheduledAt;
    private RoundType.InterviewResult result;
    private String feedback;
    private String interviewer;
    private LocalDateTime createdAt;
}