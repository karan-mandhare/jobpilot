package com.jobpilotapplication.repository;

import com.jobpilotapplication.entity.AiSession;
import com.jobpilotapplication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AiSessionRepository extends JpaRepository<AiSession, Long> {
    List<AiSession> findByUserOrderByCreatedAtDesc(User user);
    List<AiSession> findByUserAndSessionType(User user, String sessionType);
}