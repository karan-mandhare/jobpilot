package com.jobpilotapplication.repository;

import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface JobApplicationRepository extends JpaRepository<JobApplication, Long> {

    List<JobApplication> findByUserOrderByCreatedAtDesc(User user);

    List<JobApplication> findByUserAndStatus(User user, Role.ApplicationStatus status);

    Optional<JobApplication> findByIdAndUser(Long id, User user);

    List<JobApplication> findByFollowUpDate(LocalDate date);

    long countByUser(User user);

    long countByUserAndStatus(User user, Role.ApplicationStatus status);

    @Query("SELECT ja FROM JobApplication ja WHERE ja.user = :user " +
            "AND (LOWER(ja.companyName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            "OR LOWER(ja.jobRole) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<JobApplication> searchByUserAndKeyword(User user, String keyword);
}