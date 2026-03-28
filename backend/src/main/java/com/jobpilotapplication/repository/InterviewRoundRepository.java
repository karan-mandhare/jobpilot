package com.jobpilotapplication.repository;

import com.jobpilotapplication.entity.InterviewRound;
import com.jobpilotapplication.entity.JobApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InterviewRoundRepository extends JpaRepository<InterviewRound, Long> {
    List<InterviewRound> findByApplicationOrderByRoundNumberAsc(JobApplication application);
    Optional<InterviewRound> findByIdAndApplication(Long id, JobApplication application);
    void deleteByApplication(JobApplication application);
}