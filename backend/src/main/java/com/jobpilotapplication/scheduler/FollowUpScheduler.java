package com.jobpilotapplication.scheduler;

import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.repository.JobApplicationRepository;
import com.jobpilotapplication.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class FollowUpScheduler {

    private final JobApplicationRepository applicationRepository;
    private final EmailService emailService;

    // Runs every day at 9:00 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void sendFollowUpReminders() {
        LocalDate today = LocalDate.now();
        List<JobApplication> dueFollowUps = applicationRepository.findByFollowUpDate(today);

        log.info("Sending follow-up reminders for {} applications", dueFollowUps.size());

        dueFollowUps.forEach(app -> {
            emailService.sendFollowUpReminder(
                    app.getUser().getEmail(),
                    app.getUser().getName(),
                    app.getCompanyName(),
                    app.getJobRole()
            );
        });
    }
}
