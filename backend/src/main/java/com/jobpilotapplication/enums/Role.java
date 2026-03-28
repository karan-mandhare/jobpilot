package com.jobpilotapplication.enums;

public enum Role {
    USER,
    ADMIN;

    public enum ApplicationStatus {
        APPLIED,
        SCREENING,
        INTERVIEW,
        OFFER,
        REJECTED,
        ACCEPTED,
        WITHDRAWN
    }
}
