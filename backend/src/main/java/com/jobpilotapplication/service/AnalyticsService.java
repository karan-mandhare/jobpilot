package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.response.AnalyticsResponse;
import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.enums.Role;
import com.jobpilotapplication.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final JobApplicationRepository applicationRepository;

    public AnalyticsResponse getAnalytics(User user) {
        List<JobApplication> apps = applicationRepository.findByUserOrderByCreatedAtDesc(user);

        // Status breakdown
        Map<String, Long> statusCount = apps.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getStatus().name(),
                        Collectors.counting()
                ));

        // Applications per month
        Map<String, Long> monthlyApps = apps.stream()
                .collect(Collectors.groupingBy(
                        a -> a.getAppliedDate().getYear() + "-" +
                                String.format("%02d", a.getAppliedDate().getMonthValue()),
                        Collectors.counting()
                ));

        // Source breakdown
        Map<String, Long> sourceCount = apps.stream()
                .filter(a -> a.getSource() != null && !a.getSource().isBlank())
                .collect(Collectors.groupingBy(
                        JobApplication::getSource,
                        Collectors.counting()
                ));

        // Role breakdown (top roles)
        Map<String, Long> roleCount = apps.stream()
                .collect(Collectors.groupingBy(
                        JobApplication::getJobRole,
                        Collectors.counting()
                ));

        // Response rate (anything beyond APPLIED)
        long responded = apps.stream()
                .filter(a -> a.getStatus() != Role.ApplicationStatus.APPLIED)
                .count();

        long responseRate = apps.isEmpty() ? 0 :
                Math.round((double) responded / apps.size() * 100);

        long offers = statusCount.getOrDefault("OFFER", 0L) +
                statusCount.getOrDefault("ACCEPTED", 0L);

        long interviews = statusCount.getOrDefault("INTERVIEW", 0L);

        return AnalyticsResponse.builder()
                .totalApplications(apps.size())
                .offersReceived(offers)
                .interviewsScheduled(interviews)
                .responseRate(responseRate)
                .statusBreakdown(statusCount)
                .monthlyApplications(monthlyApps)
                .sourceBreakdown(sourceCount)
                .roleBreakdown(roleCount)
                .build();
    }
}