package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.request.InterviewRoundRequest;
import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.dto.response.InterviewRoundResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.InterviewRoundService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications/{applicationId}/rounds")
@RequiredArgsConstructor
public class InterviewRoundController {

    private final InterviewRoundService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<InterviewRoundResponse>>> getAll(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId) {
        return ResponseEntity.ok(ApiResponse.success("Rounds fetched",
                service.getByApplication(user, applicationId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<InterviewRoundResponse>> add(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId,
            @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Round added",
                        service.addRound(user, applicationId, request)));
    }

    @PutMapping("/{roundId}")
    public ResponseEntity<ApiResponse<InterviewRoundResponse>> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId,
            @PathVariable Long roundId,
            @Valid @RequestBody InterviewRoundRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Round updated",
                service.updateRound(user, applicationId, roundId, request)));
    }

    @DeleteMapping("/{roundId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long applicationId,
            @PathVariable Long roundId) {
        service.deleteRound(user, applicationId, roundId);
        return ResponseEntity.ok(ApiResponse.success("Round deleted", null));
    }
}