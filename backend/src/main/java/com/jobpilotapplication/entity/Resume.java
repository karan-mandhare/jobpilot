package com.jobpilotapplication.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "resumes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private User user;

    @Column(name = "file_name", length = 255)
    private String fileName;

    @Column(name = "file_url", length = 500)
    private String fileUrl;

    @Column(name = "public_id", length = 300)
    private String publicId; // Cloudinary public ID for deletion

    @Column(name = "is_primary")
    private Boolean isPrimary = false;

    @Column(name = "ai_feedback", columnDefinition = "TEXT")
    private String aiFeedback;

    @CreationTimestamp
    @Column(name = "uploaded_at", updatable = false)
    private LocalDateTime uploadedAt;
}