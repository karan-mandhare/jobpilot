package com.jobpilotapplication.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

@Data
@Builder
public class AnalyticsResponse {
    private int totalApplications;
    private long offersReceived;
    private long interviewsScheduled;
    private long responseRate;
    private Map<String, Long> statusBreakdown;
    private Map<String, Long> monthlyApplications;
    private Map<String, Long> sourceBreakdown;
    private Map<String, Long> roleBreakdown;
}