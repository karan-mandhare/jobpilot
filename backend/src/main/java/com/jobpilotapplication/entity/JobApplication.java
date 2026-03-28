package com.jobpilotapplication.entity;

import com.jobpilotapplication.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "job_applications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @Column(name = "company_name", nullable = false, length = 200)
    private String companyName;

    @Column(name = "job_role", nullable = false, length = 200)
    private String jobRole;

    @Column(name = "job_url", length = 500)
    private String jobUrl;

    @Column(length = 150)
    private String location;

    @Column(name = "salary_range", length = 100)
    private String salaryRange;

    @Enumerated(EnumType.STRING)
    @Column(length = 50)
    private Role.ApplicationStatus status = Role.ApplicationStatus.APPLIED;

    @Column(name = "applied_date", nullable = false)
    private LocalDate appliedDate;

    @Column(name = "follow_up_date")
    private LocalDate followUpDate;

    @Column(name = "job_description", columnDefinition = "TEXT")
    private String jobDescription;

    @Column(columnDefinition = "TEXT")
    private String notes;

    @Column(length = 100)
    private String source;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "application", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<InterviewRound> interviewRounds;
}