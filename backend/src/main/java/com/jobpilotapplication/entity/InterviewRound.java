package com.jobpilotapplication.entity;

import com.jobpilotapplication.enums.RoundType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "interview_rounds")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewRound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "application_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private JobApplication application;

    @Column(name = "round_number", nullable = false)
    private Integer roundNumber;

    @Column(name = "round_type", length = 100)
    private String roundType; // DSA | System Design | HR | Technical | Machine Coding

    @Column(name = "scheduled_at")
    private LocalDateTime scheduledAt;

    @Enumerated(EnumType.STRING)
    private RoundType.InterviewResult result = RoundType.InterviewResult.PENDING;

    @Column(columnDefinition = "TEXT")
    private String feedback;

    @Column(length = 100)
    private String interviewer;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
