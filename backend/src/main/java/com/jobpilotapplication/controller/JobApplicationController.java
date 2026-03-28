package com.jobpilotapplication.controller;

import com.jobpilotapplication.dto.request.JobApplicationRequest;
import com.jobpilotapplication.dto.response.ApiResponse;
import com.jobpilotapplication.dto.response.JobApplicationResponse;
import com.jobpilotapplication.entity.User;
import com.jobpilotapplication.service.JobApplicationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/applications")
@RequiredArgsConstructor
public class JobApplicationController {

    private final JobApplicationService service;

    @GetMapping
    public ResponseEntity<ApiResponse<List<JobApplicationResponse>>> getAll(
            @AuthenticationPrincipal User user,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {

        List<JobApplicationResponse> data;
        if (search != null && !search.isBlank()) {
            data = service.searchApplications(user, search);
        } else if (status != null && !status.isBlank()) {
            data = service.getByStatus(user, status);
        } else {
            data = service.getAllByUser(user);
        }
        return ResponseEntity.ok(ApiResponse.success("Applications fetched", data));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> getById(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Application fetched",
                service.getById(user, id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<JobApplicationResponse>> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application created", service.create(user, request)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> update(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @Valid @RequestBody JobApplicationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Application updated",
                service.update(user, id, request)));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<JobApplicationResponse>> updateStatus(
            @AuthenticationPrincipal User user,
            @PathVariable Long id,
            @RequestParam String status) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                service.updateStatus(user, id, status)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal User user,
            @PathVariable Long id) {
        service.delete(user, id);
        return ResponseEntity.ok(ApiResponse.success("Application deleted", null));
    }
}