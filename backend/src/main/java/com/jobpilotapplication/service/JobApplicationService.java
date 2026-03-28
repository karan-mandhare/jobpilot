package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.request.JobApplicationRequest;
import com.jobpilotapplication.dto.response.InterviewRoundResponse;
import com.jobpilotapplication.dto.response.JobApplicationResponse;
import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.entity.Notification;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.enums.Role;
import com.jobpilotapplication.exception.BadRequestException;
import com.jobpilotapplication.exception.ResourceNotFoundException;
import com.jobpilotapplication.repository.JobApplicationRepository;
import com.jobpilotapplication.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobApplicationService {

    private final JobApplicationRepository applicationRepository;
    private final NotificationRepository notificationRepository;

    public List<JobApplicationResponse> getAllByUser(User user) {
        return applicationRepository.findByUserOrderByCreatedAtDesc(user)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<JobApplicationResponse> getByStatus(User user, String status) {
        Role.ApplicationStatus appStatus;
        try {
            appStatus = Role.ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: " + status);
        }
        return applicationRepository.findByUserAndStatus(user, appStatus)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<JobApplicationResponse> searchApplications(User user, String keyword) {
        return applicationRepository.searchByUserAndKeyword(user, keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public JobApplicationResponse getById(User user, Long id) {
        JobApplication app = applicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        return mapToResponse(app);
    }

    @Transactional
    public JobApplicationResponse create(User user, JobApplicationRequest request) {
        JobApplication app = JobApplication.builder()
                .user(user)
                .companyName(request.getCompanyName())
                .jobRole(request.getJobRole())
                .jobUrl(request.getJobUrl())
                .location(request.getLocation())
                .salaryRange(request.getSalaryRange())
                .status(request.getStatus() != null ? request.getStatus() : Role.ApplicationStatus.APPLIED)
                .appliedDate(request.getAppliedDate())
                .followUpDate(request.getFollowUpDate())
                .jobDescription(request.getJobDescription())
                .notes(request.getNotes())
                .source(request.getSource())
                .build();

        applicationRepository.save(app);

        // Create notification
        createNotification(user, "New application added for " + request.getJobRole()
                + " at " + request.getCompanyName(), "INFO");

        return mapToResponse(app);
    }

    @Transactional
    public JobApplicationResponse update(User user, Long id, JobApplicationRequest request) {
        JobApplication app = applicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        app.setCompanyName(request.getCompanyName());
        app.setJobRole(request.getJobRole());
        app.setJobUrl(request.getJobUrl());
        app.setLocation(request.getLocation());
        app.setSalaryRange(request.getSalaryRange());
        app.setAppliedDate(request.getAppliedDate());
        app.setFollowUpDate(request.getFollowUpDate());
        app.setJobDescription(request.getJobDescription());
        app.setNotes(request.getNotes());
        app.setSource(request.getSource());
        if (request.getStatus() != null) app.setStatus(request.getStatus());

        applicationRepository.save(app);
        return mapToResponse(app);
    }

    @Transactional
    public JobApplicationResponse updateStatus(User user, Long id, String status) {
        JobApplication app = applicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        Role.ApplicationStatus newStatus;
        try {
            newStatus = Role.ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid status: " + status);
        }

        Role.ApplicationStatus oldStatus = app.getStatus();
        app.setStatus(newStatus);
        applicationRepository.save(app);

        // Notify on important status changes
        if (newStatus == Role.ApplicationStatus.OFFER) {
            createNotification(user, "🎉 Offer received from " + app.getCompanyName() + "!", "INFO");
        } else if (newStatus == Role.ApplicationStatus.INTERVIEW) {
            createNotification(user, "📅 Interview scheduled at " + app.getCompanyName(), "REMINDER");
        }

        return mapToResponse(app);
    }

    @Transactional
    public void delete(User user, Long id) {
        JobApplication app = applicationRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
        applicationRepository.delete(app);
    }

    private void createNotification(User user, String message, String type) {
        Notification notification = Notification.builder()
                .user(user)
                .message(message)
                .type(type)
                .isRead(false)
                .build();
        notificationRepository.save(notification);
    }

    public JobApplicationResponse mapToResponse(JobApplication app) {
        List<InterviewRoundResponse> rounds = app.getInterviewRounds() == null ? List.of() :
                app.getInterviewRounds().stream()
                        .map(r -> InterviewRoundResponse.builder()
                                .id(r.getId())
                                .roundNumber(r.getRoundNumber())
                                .roundType(r.getRoundType())
                                .scheduledAt(r.getScheduledAt())
                                .result(r.getResult())
                                .feedback(r.getFeedback())
                                .interviewer(r.getInterviewer())
                                .createdAt(r.getCreatedAt())
                                .build())
                        .collect(Collectors.toList());

        return JobApplicationResponse.builder()
                .id(app.getId())
                .companyName(app.getCompanyName())
                .jobRole(app.getJobRole())
                .jobUrl(app.getJobUrl())
                .location(app.getLocation())
                .salaryRange(app.getSalaryRange())
                .status(app.getStatus())
                .appliedDate(app.getAppliedDate())
                .followUpDate(app.getFollowUpDate())
                .jobDescription(app.getJobDescription())
                .notes(app.getNotes())
                .source(app.getSource())
                .createdAt(app.getCreatedAt())
                .updatedAt(app.getUpdatedAt())
                .interviewRounds(rounds)
                .build();
    }
}