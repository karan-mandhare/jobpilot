package com.jobpilotapplication.enums;

public enum RoundType {
    DSA,
    SYSTEM_DESIGN,
    HR,
    TECHNICAL;

    public enum InterviewResult {
        PENDING,
        PASSED,
        FAILED
    }

    public enum NotificationType {
        REMINDER,
        SYSTEM,
        INFO
    }
}