package com.jobpilotapplication.service;

import com.jobpilotapplication.dto.request.InterviewRoundRequest;
import com.jobpilotapplication.dto.response.InterviewRoundResponse;
import com.jobpilotapplication.entity.InterviewRound;
import com.jobpilotapplication.entity.JobApplication;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.exception.ResourceNotFoundException;
import com.jobpilotapplication.repository.InterviewRoundRepository;
import com.jobpilotapplication.repository.JobApplicationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InterviewRoundService {

    private final InterviewRoundRepository roundRepository;
    private final JobApplicationRepository applicationRepository;

    public List<InterviewRoundResponse> getByApplication(User user, Long applicationId) {
        JobApplication app = applicationRepository.findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        return roundRepository.findByApplicationOrderByRoundNumberAsc(app)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public InterviewRoundResponse addRound(User user, Long applicationId,
                                           InterviewRoundRequest request) {
        JobApplication app = applicationRepository.findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        InterviewRound round = InterviewRound.builder()
                .application(app)
                .roundNumber(request.getRoundNumber())
                .roundType(request.getRoundType())
                .scheduledAt(request.getScheduledAt())
                .result(request.getResult())
                .feedback(request.getFeedback())
                .interviewer(request.getInterviewer())
                .build();

        roundRepository.save(round);
        return mapToResponse(round);
    }

    @Transactional
    public InterviewRoundResponse updateRound(User user, Long applicationId,
                                              Long roundId, InterviewRoundRequest request) {
        JobApplication app = applicationRepository.findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        InterviewRound round = roundRepository.findByIdAndApplication(roundId, app)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));

        round.setRoundNumber(request.getRoundNumber());
        round.setRoundType(request.getRoundType());
        round.setScheduledAt(request.getScheduledAt());
        if (request.getResult() != null) round.setResult(request.getResult());
        round.setFeedback(request.getFeedback());
        round.setInterviewer(request.getInterviewer());

        roundRepository.save(round);
        return mapToResponse(round);
    }

    @Transactional
    public void deleteRound(User user, Long applicationId, Long roundId) {
        JobApplication app = applicationRepository.findByIdAndUser(applicationId, user)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));

        InterviewRound round = roundRepository.findByIdAndApplication(roundId, app)
                .orElseThrow(() -> new ResourceNotFoundException("Interview round not found"));

        roundRepository.delete(round);
    }

    private InterviewRoundResponse mapToResponse(InterviewRound round) {
        return InterviewRoundResponse.builder()
                .id(round.getId())
                .roundNumber(round.getRoundNumber())
                .roundType(round.getRoundType())
                .scheduledAt(round.getScheduledAt())
                .result(round.getResult())
                .feedback(round.getFeedback())
                .interviewer(round.getInterviewer())
                .createdAt(round.getCreatedAt())
                .build();
    }
}
