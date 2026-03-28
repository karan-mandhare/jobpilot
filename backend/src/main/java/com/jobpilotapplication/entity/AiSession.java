package com.jobpilotapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "ai_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AiSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @Column(name = "session_type", length = 100)
    private String sessionType; // RESUME_REVIEW | INTERVIEW_PREP | COVER_LETTER

    @Column(name = "input_data", columnDefinition = "TEXT")
    private String inputData;

    @Column(name = "ai_response", columnDefinition = "TEXT")
    private String aiResponse;

    @Column(name = "tokens_used")
    private Integer tokensUsed;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}
