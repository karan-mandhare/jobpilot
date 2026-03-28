package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.dto.response.ResumeResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/resumes")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ResumeResponse>>> getAll(
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success("Resumes fetched",
                resumeService.getAllResumes(user)));
    }

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<ResumeResponse>> upload(
            @AuthenticationPrincipal User user,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Resume uploaded",
                        resumeService.uploadResume(user, file)));
    }

    @PatchMapping("/{id}/primary")
    public ResponseEntity<ApiResponse<ResumeResponse>> setPrimary(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Primary resume updated",
                resumeService.setPrimary(user, id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        resumeService.deleteResume(user, id);
        return ResponseEntity.ok(ApiResponse.success("Resume deleted", null));
    }
}