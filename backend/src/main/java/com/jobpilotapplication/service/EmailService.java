package com.jobpilotapplication.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Async
    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("🎉 Welcome to JobPilot!");
            message.setText(String.format("""
                    Hi %s,
                    
                    Welcome to JobPilot — your smart AI-powered job application tracker!
                    
                    Get started by:
                    1. Adding your first job application
                    2. Uploading your resume for AI review
                    3. Generating interview questions for your target role
                    
                    Good luck with your job search!
                    
                    Best regards,
                    The JobPilot Team
                    """, userName));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send welcome email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendFollowUpReminder(String toEmail, String userName,
                                     String company, String role) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("⏰ Follow-up Reminder: " + company);
            message.setText(String.format("""
                    Hi %s,
                    
                    This is a reminder to follow up on your application for the 
                    %s role at %s.
                    
                    A polite follow-up email can significantly increase your chances!
                    
                    Best of luck,
                    JobPilot Team
                    """, userName, role, company));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send follow-up email to {}: {}", toEmail, e.getMessage());
        }
    }

    @Async
    public void sendStatusUpdateEmail(String toEmail, String userName,
                                      String company, String role, String status) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(toEmail);
            message.setSubject("📊 Application Update: " + company);
            message.setText(String.format("""
                    Hi %s,
                    
                    Your application status for %s at %s has been updated to: %s
                    
                    Keep it up — every application brings you closer to your goal!
                    
                    JobPilot Team
                    """, userName, role, company, status));
            mailSender.send(message);
        } catch (Exception e) {
            log.error("Failed to send status update email: {}", e.getMessage());
        }
    }
}