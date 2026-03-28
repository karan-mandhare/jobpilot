package com.jobpilotapplication.seed;

import com.github.javafaker.Faker;
import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.enums.Role;
import com.jobpilotapplication.repository.JobApplicationRepository;
import com.jobpilotapplication.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final JobApplicationRepository applicationRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        if (userRepository.count() > 0) {
            log.info("Data already seeded. Skipping...");
            return;
        }

        Faker faker = new Faker();
        Random random = new Random();

        // Create demo user
        User demoUser = User.builder()
                .name("Rahul Sharma")
                .email("demo@jobpilot.com")
                .password(passwordEncoder.encode("demo123"))
                .role(Role.USER)
                .isActive(true)
                .build();
        userRepository.save(demoUser);

        // Create admin user
        User adminUser = User.builder()
                .name("Admin User")
                .email("admin@jobpilot.com")
                .password(passwordEncoder.encode("admin123"))
                .role(Role.ADMIN)
                .isActive(true)
                .build();
        userRepository.save(adminUser);

        // Indian tech companies
        List<String> companies = List.of(
                "Google", "Microsoft", "Amazon", "Flipkart", "Razorpay",
                "Zepto", "Swiggy", "CRED", "PhonePe", "Meesho",
                "Groww", "Paytm", "Infosys", "TCS", "Wipro",
                "Zomato", "Ola", "MakeMyTrip", "Nykaa", "Freshworks"
        );

        List<String> roles = List.of(
                "Backend Developer", "Full Stack Developer", "Frontend Developer",
                "Java Developer", "Software Engineer", "Associate Software Engineer",
                "Senior Software Engineer", "API Developer", "Spring Boot Developer"
        );

        List<String> sources = List.of("LinkedIn", "Naukri", "Referral", "Direct", "AngelList");

        List<Role.ApplicationStatus> statuses = List.of(
                Role.ApplicationStatus.APPLIED, Role.ApplicationStatus.APPLIED, Role.ApplicationStatus.APPLIED,
                Role.ApplicationStatus.SCREENING, Role.ApplicationStatus.INTERVIEW,
                Role.ApplicationStatus.OFFER, Role.ApplicationStatus.REJECTED, Role.ApplicationStatus.REJECTED
        );

        companies.forEach(company -> {
            JobApplication app = JobApplication.builder()
                    .user(demoUser)
                    .companyName(company)
                    .jobRole(roles.get(random.nextInt(roles.size())))
                    .location(List.of("Bangalore", "Mumbai", "Hyderabad", "Pune",
                            "Chennai", "Delhi", "Remote").get(random.nextInt(7)))
                    .salaryRange("₹" + (random.nextInt(15) + 8) + "L - ₹"
                            + (random.nextInt(15) + 20) + "L")
                    .status(statuses.get(random.nextInt(statuses.size())))
                    .appliedDate(LocalDate.now().minusDays(random.nextInt(90)))
                    .source(sources.get(random.nextInt(sources.size())))
                    .notes(faker.lorem().sentence())
                    .build();

            applicationRepository.save(app);
        });

        log.info("✅ Demo data seeded successfully!");
        log.info("Demo login: demo@jobpilot.com / demo123");
        log.info("Admin login: admin@jobpilot.com / admin123");
    }
}
