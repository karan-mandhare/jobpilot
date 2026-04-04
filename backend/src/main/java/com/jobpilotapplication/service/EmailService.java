package com.jobpilotapplication.service;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String email;

    @Async
    public void sendWelcomeEmail(String toEmail, String userName) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(email, "JobPilot");
            helper.setTo(toEmail);
            helper.setSubject("🎉 Welcome to JobPilot!");

            String htmlContent = String.format("""
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8">
        <title>Welcome to JobPilot</title>
    </head>
    <body style="margin:0; padding:0; background-color:#f3f6fb; font-family:Arial, Helvetica, sans-serif;">
    
        <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" border="0" style="background-color:#f3f6fb; padding:40px 0;">
            <tr>
                <td align="center">
                    
                    <table role="presentation" width="600" cellspacing="0" cellpadding="0" border="0"
                           style="background-color:#ffffff; border-radius:18px; overflow:hidden; box-shadow:0 8px 30px rgba(0,0,0,0.08);">
                        
                        <!-- HEADER -->
                       <tr>
                                                 <td align="center" style="padding:50px 30px 42px; background:linear-gradient(135deg, #eff6ff 0%%, #dbeafe 100%%); border-bottom:1px solid #e5e7eb;">
                    
                                                     <div style="display:inline-block; background:#ffffff; padding:14px 18px; border-radius:18px; margin-bottom:24px; box-shadow:0 4px 14px rgba(37,99,235,0.12);">
                                                         <span style="font-size:34px; line-height:1;">🎉</span>
                                                     </div>
                    
                                                     <h1 style="margin:0; font-size:34px; line-height:1.2; color:#111827; font-weight:800; letter-spacing:-0.5px;">
                                                         Welcome to JobPilot
                                                     </h1>
                    
                                                     <p style="margin:16px auto 0; font-size:17px; line-height:1.8; color:#374151; max-width:470px;">
                                                         Your smart AI-powered job application tracker to help you stay organized,
                                                         improve faster, and land your next opportunity.
                                                     </p>
                                                 </td>
                                             </tr>

                        <!-- BODY -->
                        <tr>
                            <td style="padding:40px 36px 20px;">
                                <h2 style="margin:0 0 16px; font-size:24px; color:#111827; font-weight:700;">
                                    Hi %s 👋
                                </h2>

                                <p style="margin:0 0 20px; font-size:16px; line-height:1.8; color:#4b5563;">
                                    We’re excited to have you onboard! JobPilot is built to make your job search simpler,
                                    smarter, and less chaotic.
                                </p>

                                <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" border="0"
                                       style="margin:30px 0; background:#f8fafc; border:1px solid #e5e7eb; border-radius:14px;">
                                    <tr>
                                        <td style="padding:28px;">
                                            <h3 style="margin:0 0 18px; font-size:18px; color:#111827; font-weight:700;">
                                                🚀 Get started in 3 quick steps
                                            </h3>

                                            <table role="presentation" width="100%%" cellspacing="0" cellpadding="0" border="0">
                                                <tr>
                                                    <td style="padding:10px 0; font-size:15px; color:#374151; line-height:1.7;">
                                                        <strong style="color:#2563eb;">1.</strong> Add your first job application
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="padding:10px 0; font-size:15px; color:#374151; line-height:1.7;">
                                                        <strong style="color:#2563eb;">2.</strong> Upload your resume for AI review
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td style="padding:10px 0; font-size:15px; color:#374151; line-height:1.7;">
                                                        <strong style="color:#2563eb;">3.</strong> Generate interview questions for your target role
                                                    </td>
                                                </tr>
                                            </table>
                                        </td>
                                    </tr>
                                </table>

                                <div style="text-align:center; margin:35px 0 25px;">
                                    <a href="http://localhost:4200/login"
                                       style="display:inline-block; background-color:#2563eb; color:#ffffff; text-decoration:none; padding:15px 32px; border-radius:12px; font-size:16px; font-weight:700;">
                                        Get Started
                                    </a>
                                </div>

                                <p style="margin:0 0 20px; font-size:15px; line-height:1.8; color:#6b7280; text-align:center;">
                                    Wishing you success in your job search journey 🚀
                                </p>
                            </td>
                        </tr>

                        <!-- FOOTER -->
                        <tr>
                            <td align="center" style="padding:24px 30px 30px; border-top:1px solid #f1f5f9;">
                                <p style="margin:0; font-size:14px; line-height:1.8; color:#6b7280;">
                                    Best regards,<br>
                                    <strong style="color:#111827;">The JobPilot Team</strong>
                                </p>

                                <p style="margin:18px 0 0; font-size:12px; color:#9ca3af;">
                                    © 2026 JobPilot. All rights reserved.
                                </p>
                            </td>
                        </tr>

                    </table>
                </td>
            </tr>
        </table>

    </body>
    </html>
    """, userName);

            helper.setText(htmlContent, true);

            mailSender.send(message);
            log.info("Welcome email sent successfully to {}", toEmail);

        } catch (Exception e) {
            log.error("Failed to send welcome email to {}", toEmail, e);
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