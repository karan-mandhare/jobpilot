package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.response.AiResponse;
import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/resume-review")
    public ResponseEntity<ApiResponse<AiResponse>> reviewResume(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        String resumeText = body.get("resumeText");
        if (resumeText == null || resumeText.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Resume text is required"));
        }
        AiResponse response = aiService.reviewResume(user, resumeText);
        return ResponseEntity.ok(ApiResponse.success("Resume reviewed", response));
    }

    @PostMapping("/interview-questions")
    public ResponseEntity<ApiResponse<AiResponse>> generateQuestions(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        String jobRole = body.get("jobRole");
        String experienceLevel = body.get("experienceLevel");
        if (jobRole == null || jobRole.isBlank()) {
            return ResponseEntity.badRequest()
                    .body(ApiResponse.error("Job role is required"));
        }
        AiResponse response = aiService.generateInterviewQuestions(
                user, jobRole, experienceLevel != null ? experienceLevel : "mid-level");
        return ResponseEntity.ok(ApiResponse.success("Questions generated", response));
    }

    @PostMapping("/cover-letter")
    public ResponseEntity<ApiResponse<AiResponse>> generateCoverLetter(
            @AuthenticationPrincipal User user,
            @RequestBody Map<String, String> body) {
        AiResponse response = aiService.generateCoverLetter(
                user,
                body.get("jobRole"),
                body.get("companyName"),
                body.get("resumeSummary")
        );
        return ResponseEntity.ok(ApiResponse.success("Cover letter generated", response));
    }
}